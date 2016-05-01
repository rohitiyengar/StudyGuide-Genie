package lucenenotesrecommender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class LuceneNotesRecommender {
	private String application_path;
	private String LUCENE_INDEX_FOLDER = "NotesIndex";

	private final String CONTENTS_FIELD_NAME = "contents";
	private final String STUDENT_FIELD_NAME = "studentid";
	private final String TOPIC_FIELD_ID = "topicid";
	private StandardAnalyzer analyzer = new StandardAnalyzer();

	private IndexWriter writer;
	//private IndexWriterConfig config = new IndexWriterConfig(analyzer);
	private IndexSearcher searcher = null;

	public LuceneNotesRecommender() throws IOException {
		String osname = System.getProperty("os.name");
		this.application_path = this.getClass().getResource("/lucenenotesrecommender").getPath();
		if (osname.toLowerCase().contains("windows")) {
			this.application_path = application_path.substring(1);
		}
		LUCENE_INDEX_FOLDER = application_path+"/NotesIndex";
		createNotesIndexFolder();		
	}

	private void createNotesIndexFolder() throws IOException {
		File indexDirectory = new File(LUCENE_INDEX_FOLDER);
		Path path = Paths.get(LUCENE_INDEX_FOLDER);
		if (!indexDirectory.exists()) {
			Files.createDirectories(path);
		}
	}

	public void updateNotesIndex(int topicid, String studentid, String studentnotes) throws IOException, ParseException {
		System.out.println("updateNotesIndex is called");
		
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));

		TopScoreDocCollector collector;
		boolean foundIndex = false;		

		// create a field
		FieldType type = new FieldType();
		type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		type.setStored(true);
		type.setStoreTermVectors(true);
		Field field = new Field(CONTENTS_FIELD_NAME, studentnotes.toLowerCase(), type);

		// query to check if a corresponding index exists for this student,topic combo
		Query query = MultiFieldQueryParser.parse(TOPIC_FIELD_ID+":"+topicid+" AND "+STUDENT_FIELD_NAME+":"+studentid, new String[]{TOPIC_FIELD_ID, STUDENT_FIELD_NAME}, new Occur[] {Occur.MUST, Occur.MUST}, analyzer);

		if (DirectoryReader.indexExists(indexDir)) {
			System.out.println("NotesIndex exists");
			ScoreDoc[] hits;
			IndexReader myreader = DirectoryReader.open(indexDir);
			searcher = new IndexSearcher(myreader);
			collector = null;
			collector = TopScoreDocCollector.create(1); // we just need 1 index here
			searcher.search(query, collector);
			hits = collector.topDocs().scoreDocs;
			System.out.println("searching for topicid "+topicid+" and studentid "+studentid+" is called");
			// found the index, so update it if different
			if (hits.length == 1) {
				System.out.println("Found index");
				foundIndex = true;
				int docID = hits[0].doc;
				Document d;
				d = searcher.doc(docID);
				// get notes present in the index for this student,topic
				String currentNotes = d.get(CONTENTS_FIELD_NAME);
				
				System.out.println("Current notes = "+currentNotes);
				System.out.println("New notes = "+studentnotes.toLowerCase());

				// if no changes in notes, don't update
				if (!studentnotes.toLowerCase().equals(currentNotes)) {
					System.out.println("Updating new notes");
					// delete the document
					IndexWriterConfig config = new IndexWriterConfig(analyzer);
					writer = new IndexWriter(indexDir,config);
					writer.deleteDocuments(query);

					// create new document
					Document doc = new Document();
					doc.add(new TextField(TOPIC_FIELD_ID,Integer.toString(topicid),TextField.Store.YES));
					doc.add(new TextField(STUDENT_FIELD_NAME,studentid,TextField.Store.YES));			
					doc.add(field);				
					writer.addDocument(doc);
					writer.close();	
				}
				else {
					System.out.println("No updates in the notes. So no need to re-create the index");
				}
			}			
		}
		// didnot find the index, add it
		if (!foundIndex) {
			System.out.println("Didnot find index");
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(indexDir,config);
			Document doc = new Document();
			doc.add(new TextField(TOPIC_FIELD_ID,Integer.toString(topicid),TextField.Store.YES));
			doc.add(new TextField(STUDENT_FIELD_NAME,studentid,TextField.Store.YES));			
			doc.add(field);				
			writer.addDocument(doc);
			writer.close();
			System.out.println("Created new index");
		}
		
		System.out.println("Exiting updateNotesIndex method");
	}

	public Set<String> getNotesRecommendations(int topicid, String studentid, String studentnotes, int noOfReco) throws IOException, ParseException {
		System.out.println("Get notes recommendations method is called");
		Directory indexDir = FSDirectory.open(Paths.get(LUCENE_INDEX_FOLDER));

		// query to check if a corresponding index exists for this student,topic combo
		Query query;// = MultiFieldQueryParser.parse(TOPIC_FIELD_ID+":"+topicid+" AND "+STUDENT_FIELD_NAME+":"+studentid, new String[]{TOPIC_FIELD_ID, STUDENT_FIELD_NAME}, new Occur[] {Occur.MUST, Occur.MUST}, analyzer);

		TopScoreDocCollector collector;
		IndexReader reader;
		
		// search other students' notes for the given topic
		int noofstudents = 10;
		query = MultiFieldQueryParser.parse(TOPIC_FIELD_ID+":"+topicid+" AND NOT "+STUDENT_FIELD_NAME+":"+studentid, new String[]{TOPIC_FIELD_ID, STUDENT_FIELD_NAME}, new Occur[] {Occur.MUST, Occur.MUST}, analyzer);
		reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		collector = TopScoreDocCollector.create(noofstudents);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;		
		Map<String,Integer> wordFreq = new HashMap<String,Integer>();
		System.out.println("Found "+hits.length+" hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Terms terms = reader.getTermVector(docId, CONTENTS_FIELD_NAME);
			TermsEnum itr = terms.iterator();
			BytesRef text = null;
			while ((text = itr.next()) != null) {
				String word = text.utf8ToString();
				if (wordFreq.containsKey(word)) {
					wordFreq.put(word, wordFreq.get(word)+1);
				}
				else {
					wordFreq.put(word,1);
				}
			}
		}
		reader.close();

		System.out.println("Found "+wordFreq.size()+" number of keywords");
		
		// remove those words already present in students' notes.
		String[] words = studentnotes.toLowerCase().split(" ");
		for (int i = 0; i < words.length; i++) {
			if (wordFreq.containsKey(words[i])) {
				wordFreq.remove(words[i]);
			}
		}
		// sort according to descending order of frequency
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( wordFreq.entrySet() );
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		// prepare the final result set
		Set<String> result = new HashSet<String>();
		int pos = 0;
		for (Map.Entry<String, Integer> ent : list) {
			if (pos > noOfReco) {
				break;
			}
			pos++;
			result.add(ent.getKey());
		}		
		return result;
	}
}
