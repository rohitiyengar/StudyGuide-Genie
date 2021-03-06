package luceneindex;

import info.debatty.java.stringsimilarity.KShingling;
import info.debatty.java.stringsimilarity.StringProfile;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/*
 * This is a helper class to build and maintain lucene index of the crawled web pages
 * This helper class also provides autocomplete suggests to the user
 */

public class LuceneIndexHelper {
	
	private String application_path;
	private String CORPUS_ROOT_FOLDER = "CrawledData";
	private String LUCENE_INDEX_FOLDER = "LuceneIndex";
	private String AUTOSUGGEST_DATASTRUCTURE_DIR = "LuceneIndexLookup";
	private String AUTOSUGGEST_DATASTRUCTURE_FILE = "LookupFile";
	
	private final String CONTENTS_FIELD_NAME = "contents";
	private final String FILENAME_FIELD_NAME = "filename";
	
	private StandardAnalyzer analyzer = new StandardAnalyzer();
	private AnalyzingSuggester suggester = new AnalyzingSuggester(analyzer);
	
	public LuceneIndexHelper() throws IOException {
		String osname = System.getProperty("os.name");
		this.application_path = this.getClass().getResource("/luceneindex").getPath();		
		if (osname.toLowerCase().contains("windows")) {
			this.application_path = application_path.substring(1);
		}
		CORPUS_ROOT_FOLDER = application_path+"CrawledData";
		LUCENE_INDEX_FOLDER = application_path+"LuceneIndex";
		AUTOSUGGEST_DATASTRUCTURE_DIR = application_path+"LuceneIndexLookup";
		AUTOSUGGEST_DATASTRUCTURE_FILE = application_path+"LuceneIndexLookup/LookupFile";
		start();
		/*File f = new File(AUTOSUGGEST_DATASTRUCTURE_FILE);
		if (!f.exists()) {
			start();
		}*/		
	}
	
	/*
	 * Index a directory with zero or more files
	 */
	private void indexDirectory(IndexWriter writer, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f); // recurse
			} else if (f.getName().endsWith(".txt")) {
				// call indexFile to add the title of the txt file to your index (you can also index html)
				indexFile(writer, f);
			}
		}
	}

	/*
	 * Index a file with its file contents
	 */
	private void indexFile(IndexWriter writer, File f) throws IOException {
		System.out.println("Indexing " + f.getName());
		Document doc = new Document();
		doc.add(new TextField(FILENAME_FIELD_NAME, f.getName(), TextField.Store.YES));

		//open each file to index the content
		try{
			FileInputStream is = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while((line = reader.readLine())!=null){
				stringBuffer.append(line).append("\n");
			}
			reader.close();
			doc.add(new TextField(CONTENTS_FIELD_NAME, stringBuffer.toString(), TextField.Store.YES));		

		}
		catch (Exception e) {
			System.out.println("something wrong with indexing content of the files");
		}    
		writer.addDocument(doc);

	}
	
	private void start() throws IOException {
		final String filePath = AUTOSUGGEST_DATASTRUCTURE_FILE;
		File file = new File(filePath);
		if (file.exists()) {
			DataInputStream dataIn = new DataInputStream(new FileInputStream(filePath));
			suggester.load(dataIn);
		}
		else {
			buildLuceneIndexAndLookupLibrary();
		}
	}
	
	public void buildLuceneIndexAndLookupLibrary() throws IOException {
		File dataDir = new File(CORPUS_ROOT_FOLDER); //my sample file folder path
		// Check whether the directory to be indexed exists
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			System.out.println("Couldn't find the CrawledData directory at "+CORPUS_ROOT_FOLDER);
			throw new IOException(
					dataDir + " does not exist or is not a directory");
		}
		createLuceneIndexFolder();
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);	
		IndexWriter writer = new IndexWriter(indexDir, config);
		indexDirectory(writer, dataDir);
		writer.close();
		
		// build lookup library
		IndexReader reader = DirectoryReader.open(indexDir);
		LuceneDictionary dict = new LuceneDictionary(reader, CONTENTS_FIELD_NAME);
		suggester.build(dict);
		
		//store the built suggester to file system
		final String filePath = AUTOSUGGEST_DATASTRUCTURE_FILE;
		File lookupFile = new File(AUTOSUGGEST_DATASTRUCTURE_DIR);
		if (lookupFile.exists()) {
			lookupFile.delete();
		}
		Files.createDirectories(Paths.get(AUTOSUGGEST_DATASTRUCTURE_DIR));				
		Files.createFile(Paths.get(filePath));
		DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(filePath));
	    suggester.store(dataOut);
	    
		System.out.println("Lucene index successfully built and lookup suggests have been persisted");
	}
	
	private void createLuceneIndexFolder() throws IOException {
		File indexDirectory = new File(LUCENE_INDEX_FOLDER);
		Path path = Paths.get(LUCENE_INDEX_FOLDER);
		if (!indexDirectory.exists()) {
			Files.createDirectories(path);
		}
	}
	
	public Set<String> getAutoSuggests (String query,int NO_OF_SUGGESTIONS) throws IOException {
		Set<String> result = new HashSet<String>();
		if (suggester.getCount() == 0) {
			start();
		}
		List<LookupResult> list = suggester.lookup(query, false, NO_OF_SUGGESTIONS);
		if (list != null && list.size() > 0) {
			for (LookupResult r : list) {
				if (!r.key.equals(query)) {
					result.add(r.key.toString());
				}
			}
		}
		return result;
	}
	
	public Document findTopDocument(IndexReader reader, String topicName) throws ParseException, IOException
	{
		QueryParser q = new QueryParser(FILENAME_FIELD_NAME , analyzer);
		Query query = q.parse(QueryParser.escape(topicName));
		IndexSearcher searcher = null;
		int hitsPerPage = 1;
		TopScoreDocCollector collector = null;
		searcher = new IndexSearcher(reader);
		collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		//System.out.println(searcher.doc(hits[0].doc).get(FILENAME_FIELD_NAME));
		//System.out.println(searcher.doc(hits[0].doc).get(CONTENTS_FIELD_NAME));
		return searcher.doc(hits[0].doc);
	}
	
	public double getScore(String topicName, String noteSentence) throws Exception
	{
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));
		IndexReader reader = DirectoryReader.open(indexDir);
		KShingling ks = new KShingling(9);// number of characters to be grouped together to build a profile.
		StringProfile p1 = ks.getProfile(noteSentence);
		StringProfile p2 = ks.getProfile(findTopDocument(reader, topicName+".txt").get("contents"));
		return p1.cosineSimilarity(p2)*100;
	}
	
}
