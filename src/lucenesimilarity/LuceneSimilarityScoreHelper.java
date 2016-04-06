package lucenesimilarity;


import info.debatty.java.stringsimilarity.KShingling;
import info.debatty.java.stringsimilarity.StringProfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;



public class LuceneSimilarityScoreHelper {
	
	
	private String application_path;
	private String CORPUS_ROOT_FOLDER = "CrawledData";
	private String LUCENE_INDEX_FOLDER = "LuceneIndex";
	
	private final String CONTENTS_FIELD_NAME = "contents";
	private final String FILENAME_FIELD_NAME = "filename";
	private StandardAnalyzer analyzer; 
	
	
	
	public LuceneSimilarityScoreHelper()
	{
		this.application_path = this.getClass().getResource("/luceneindex").getPath();
		if(application_path.charAt(0) =='/')
		{
			application_path = application_path.substring(1);
		}
		CORPUS_ROOT_FOLDER = application_path+"CrawledData";
		LUCENE_INDEX_FOLDER = application_path+"LuceneIndex";
		analyzer = new StandardAnalyzer();
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
			//doc.add(new Field("newContents", stringBuffer.toString(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));

		}
		catch (Exception e) {
			System.out.println("something wrong with indexing content of the files");
		}    
		writer.addDocument(doc);

	}
	
	private IndexReader buildLuceneIndex() throws IOException {
		File dataDir = new File(CORPUS_ROOT_FOLDER); 
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
		IndexReader reader = DirectoryReader.open(indexDir);
		//System.out.println("Lucene index successfully built and lookup suggests have been persisted");
		return reader;
	}

	
	private void createLuceneIndexFolder() throws IOException 
	{
		File indexDirectory = new File(LUCENE_INDEX_FOLDER);
		System.out.println("Creating folder");
		Path path = Paths.get(LUCENE_INDEX_FOLDER);
		
		if (!indexDirectory.isDirectory()) 
		{
			System.out.println("CREATED");
			Files.createDirectories(path);
		}
	}
	
	
	public IndexReader getIndexReader() throws IOException
	{
		IndexReader reader = null;
		final String filePath = LUCENE_INDEX_FOLDER;
		System.out.println(filePath);
		File file = new File(filePath);
		if(!file.exists() || !file.isDirectory())
		{
			System.out.println("it doesn't exists");
			reader = buildLuceneIndex();
			System.out.println("Now reader is fetched");
		}
		else
		{
			Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));
			reader = DirectoryReader.open(indexDir);
		}
		
		return reader;
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
		System.out.println(searcher.doc(hits[0].doc).get(FILENAME_FIELD_NAME));
		System.out.println(searcher.doc(hits[0].doc).get(CONTENTS_FIELD_NAME));
		return searcher.doc(hits[0].doc);
	}
	
//	public static void main(String[] args) throws Exception
//	{
//		LuceneSimilarityScoreHelper similarityChecker = new LuceneSimilarityScoreHelper();
//		IndexReader reader = similarityChecker.getIndexReader();
//		String query = "A COMPUTER IS";
//		KShingling ks = new KShingling(2);
//		StringProfile p1 = ks.getProfile(query);
//		StringProfile p2 = ks.getProfile(similarityChecker.findTopDocument(reader, "1.txt").get("contents"));
//		System.out.println("Notes match Percentage is "+p1.cosineSimilarity(p2)*100);
//	}
//	
	public double getScore(String topicName, String noteSentence) throws Exception
	{
		IndexReader reader = getIndexReader();
		KShingling ks = new KShingling(9);// number of characters to be grouped together to build a profile.
		StringProfile p1 = ks.getProfile(noteSentence);
		StringProfile p2 = ks.getProfile(findTopDocument(reader, topicName+".txt").get("contents"));
		return p1.cosineSimilarity(p2)*100;
	}

}
