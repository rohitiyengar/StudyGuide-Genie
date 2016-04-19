package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;



public class XMLTopicGenerator {
	
	/*
	private static String path = "C:\\Users\\abhi\\git\\StudyGenie\\resources\\luceneindex\\CrawledData";
	public static TopicList topicList;
	
	
	/*public static void readDirectory(File dir)
	{
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			Topic topic = new Topic();
			topic.setTopicId("1");
			String name = f.getName();
			if(name.contains("Chapter"))
			{
				//System.out.println();
				name = name.substring(11);
			}
			else if(name.contains(".txt"))
			{
				name = name.substring(2, name.length()-4);
			}
			topic.setTopicName(name);
			if (f.isDirectory()) 
			{
				topic = readSubTopics(topic, f);
			} 
			topicList.getTopic().add(topic);
			
		}
	}
	
	public static Topic readSubTopics(Topic topic, File dir)
	{
		File[] files = dir.listFiles();
		//System.out.println("LEngth of files "+files.length);
		SubTopics subTopics = new SubTopics();
		for(int i = 0; i < files.length; i++)
		{
			//System.out.println("Ading subtopics");
			File f = files[i];
			topic.setSubTopics(subTopics);
			Topic subtopic = new Topic();
			subtopic.setTopicId("2");
			String name = f.getName();
			if(name.contains(".txt"))
			{
				name = name.substring(2, name.length()-4);
			}
			else
			{
				name = name.substring(2);
			}

			subtopic.setTopicName(name);
			if(f.isDirectory())
			{
				subtopic = readSubTopics(subtopic, f);
			}
			topic.getSubTopics().getTopic().add(subtopic);	
		}
		return topic;
	}

	
	
	/*public static void main(String[] args) {
		
		topicList = new TopicList();
		String fileName = "TopicModel";
		readDirectory(new File(path));
		
		JAXBContext context;
		File f = new File(fileName+".xml");
		try 
		{
			context = JAXBContext.newInstance(TopicList.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					new Boolean(true));
			marshal.marshal(topicList, f);
			
		}
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
		}
*/
		
				
		
		
	

}
