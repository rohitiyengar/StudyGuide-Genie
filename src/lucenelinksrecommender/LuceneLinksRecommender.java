package lucenelinksrecommender;

import java.io.BufferedReader;
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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneLinksRecommender {
	private String application_path;
	private String LUCENE_INDEX_FOLDER = "LinksIndex";
	private String CORPUS_ROOT_FOLDER = "contents";

	private final String CONTENTS_FIELD_NAME = "contents";
	private final String URL_FIELD_NAME = "url";
	
	private StandardAnalyzer analyzer = new StandardAnalyzer();

	private IndexWriter writer;
	//private IndexWriterConfig config = new IndexWriterConfig(analyzer);
	private IndexSearcher searcher = null;
	
	public LuceneLinksRecommender() throws IOException {
		String osname = System.getProperty("os.name");
		this.application_path = this.getClass().getResource("/lucenelinksrecommender").getPath();
		if (osname.toLowerCase().contains("windows")) {
			this.application_path = application_path.substring(1);
		}
		LUCENE_INDEX_FOLDER = application_path+"/LinksIndex";
		CORPUS_ROOT_FOLDER = application_path+"contents";
		buildLuceneIndex();	
	}
	
	private void createLinksIndexFolder() throws IOException {
		File indexDirectory = new File(LUCENE_INDEX_FOLDER);
		Path path = Paths.get(LUCENE_INDEX_FOLDER);
		if (!indexDirectory.exists()) {
			Files.createDirectories(path);
		}
	}
	
	private void buildLuceneIndex() throws IOException {
		File dataDir = new File(CORPUS_ROOT_FOLDER); //my sample file folder path
		// Check whether the directory to be indexed exists
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			System.out.println("Couldn't find the CrawledData directory at "+CORPUS_ROOT_FOLDER);
			throw new IOException(
					dataDir + " does not exist or is not a directory");
		}
		createLinksIndexFolder();
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);	
		IndexWriter writer = new IndexWriter(indexDir, config);
		indexDirectory(writer, dataDir);
		writer.close();
		
		System.out.println("Lucene index successfully built for links recommendation");
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
		//doc.add(new TextField(FILENAME_FIELD_NAME, f.getName(), TextField.Store.YES));

		//open each file to index the content
		try{
			FileInputStream is = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			String url = "";
			boolean isFirst = true;
			while((line = reader.readLine())!=null){
				if (isFirst) {
					url = line;
					isFirst = false;
				}
				else {
					stringBuffer.append(line).append("\n");
				}				
			}
			reader.close();
			doc.add(new TextField(URL_FIELD_NAME, url, TextField.Store.YES));
			doc.add(new TextField(CONTENTS_FIELD_NAME, stringBuffer.toString(), TextField.Store.YES));		

		}
		catch (Exception e) {
			System.out.println("something wrong with indexing content of the files");
		}    
		writer.addDocument(doc);

	}
	
	public Set<String> getLinksRecommendations(String ip, int noOfRecos) throws Exception {
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));
		if (DirectoryReader.indexExists(indexDir)) {
			IndexReader reader = DirectoryReader.open(indexDir);
			searcher = new IndexSearcher(reader);
			Query q = new QueryParser(CONTENTS_FIELD_NAME , analyzer).parse(ip);
			TopScoreDocCollector collector = TopScoreDocCollector.create(noOfRecos*2); // we just need 1 index here
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			
			Set<String> result = new HashSet<String>();
			for (int i = 0; i < hits.length && result.size() < noOfRecos; i++) {
				Document d;
				d = searcher.doc(hits[i].doc);
				String url = d.get(URL_FIELD_NAME);
				if (!url.trim().equals("")) {
					result.add(url);
				}
			}
			return result;
		}
		else {
			throw new Exception ("Links index folder not present");
		}
	}
}
