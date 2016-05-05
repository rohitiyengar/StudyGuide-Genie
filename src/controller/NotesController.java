package controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import visualization.models.OriginalityForStudents;
import visualization.models.Student;
import lucenelinksrecommender.LuceneLinksRecommender;
import lucenenotesrecommender.LuceneNotesRecommender;
import model.Content;
import model.Exam;
import model.Notes;
import model.Topic;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import bo.ContentBO;
import bo.ExamBO;
import bo.InstructorBO;
import bo.InteractionsBO;
import bo.NotesBO;
import bo.StudentBO;
import bo.TopicBO;
import bo.UserBO;
import cheatsheet.jaxbclasses.Chapter;
import cheatsheet.jaxbclasses.Exams;

@Controller
@SessionAttributes({"sessionUser", "topic"})
public class NotesController {


	@Autowired
	UserBO userBo;

	@Autowired
	StudentBO studentBo;

	@Autowired
	InstructorBO instructorBo;

	@Autowired
	TopicBO topicBo;

	@Autowired
	NotesBO notesBo;
	
	@Autowired
	ExamBO examBo;
	
	@Autowired
	ContentBO contentBo;
	
	@Autowired
	InteractionsBO interactionsBo;
	
	private static String MID_TERM_1 = "Mid term 1";
	private static String MID_TERM_2 = "Mid term 2";
	private static String MID_TERM_3 = "Mid term 3";
	private static String FINAL_EXAM = "Final exam";
	
	

	@RequestMapping(value="/notes", method=RequestMethod.GET)
	public ModelAndView loadForm(HttpServletRequest request) throws IllegalArgumentException, Exception
	{
		ModelAndView mv = new ModelAndView("notes");
		//System.out.println("NOTES FROM EXAM");
		model.Student student = (model.Student)request.getSession().getAttribute("sessionUser");
		if (request.getParameter("topicName") != null)
		{
			//System.out.println("topicName is:"+request.getParameter("topicName"));
			student.setCurrentTopic(request.getParameter("topicName"));
			Topic newtopic = topicBo.findTopicByName(request.getParameter("topicName"));
			//System.out.println("new Topic id, when trying to change it is"+newtopic.getTopicId());
			request.getSession().setAttribute("topic", newtopic);
			mv.addObject("topic", newtopic);
			
			
		}
		
		if (request.getParameter("examName") != null)
		{
			//System.out.println("Exam name is:"+request.getParameter("examName"));
			int exam_id = 0;
			if(request.getParameter("examName").equals(MID_TERM_1))
			{
				exam_id = 2;
			}
			else if(request.getParameter("examName").equals(MID_TERM_2))
			{
				exam_id = 3;
			}
			else if(request.getParameter("examName").equals(MID_TERM_3))
			{
				exam_id = 4;
			}
			else if(request.getParameter("examName").equals(FINAL_EXAM))
			{
				exam_id = 5;
			}
				
			List<Content> examContent = contentBo.findContentList(exam_id);
			//System.out.println("Setting exam_id to"+exam_id);
			request.getSession().setAttribute("examid", exam_id);
			int examid = 0;
			if(student.getCurrentTopic() != null)
			{
				Topic currentTopic = topicBo.findTopicByName(student.getCurrentTopic());
				//System.out.println("id is "+currentTopic.getTopicId());
				//System.out.println(contentBo.findContentByTopicId(currentTopic.getTopicId()));
				examid = contentBo.findContentByTopicId(currentTopic.getTopicId()).getExamId();
				//Check if the exam id last left is the same as the student has clicked on now in a new session.
				if(examid == exam_id)
				{
					//System.out.println("Yes, Setting as exam id is the same");
					request.getSession().setAttribute("examid", examid);
					request.getSession().setAttribute("topic", currentTopic);
					mv.addObject("topic", currentTopic);
				}
			}
			if(examid != exam_id)
			{
				student.setCurrentTopic(topicBo.findTopicById(examContent.get(1).getTopicId()).getTopicName());
				Topic newtopic = topicBo.findTopicByName(topicBo.findTopicById(examContent.get(1).getTopicId()).getTopicName());
				request.getSession().setAttribute("topic", newtopic);
				mv.addObject("topic", newtopic);

				
				
			}
			
			
			
		}
		
		
		Topic topic = (Topic)request.getSession().getAttribute("topic");
		Notes notes = new Notes();
		if(topic == null)
		{
			mv.addObject("registerMessage", "You have not registered for any course");
			mv.setViewName("studentDetails");
		}
		else
		{
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			LuceneLinksRecommender luceneLinksBean = (LuceneLinksRecommender)webApplicationContext.getBean("luceneLinksBean");
			Set<String> recommendedLinks = luceneLinksBean.getLinksRecommendations(topic.getTopicName(), 5);
			mv.addObject("recommendedLinks", recommendedLinks);
			request.getSession().setAttribute("listOfRecommendedLinks", recommendedLinks);
			try
			{
				//System.out.println(topic.getTopicId());
				//System.out.println(student.getStudentId());
				notes = notesBo.findNotes(student.getStudentId(), topic.getTopicId());
				LuceneNotesRecommender luceneNotesBean = (LuceneNotesRecommender)webApplicationContext.getBean("luceneNotesBean");
				luceneNotesBean.updateNotesIndex(topic.getTopicId(), student.getStudentId(), notes.getTopicText());
				Set<String> recommendedWords = luceneNotesBean.getNotesRecommendations(topic.getTopicId(), student.getStudentId(), notes.getTopicText(), 5);
				notes.setRecommmendedWords(recommendedWords);
				mv.addObject("recommendedWords", notes.getRecommmendedWords());
			}
			catch(IllegalArgumentException exception)
			{
				if(exception.getMessage().equals("Notes doesnt exists"))
				{
					notes.setStudentId(student.getStudentId());
					notes.setTopicid(topic.getTopicId());
					notes.setTopicName(student.getCurrentTopic());
					mv.addObject("notes", notes);
					return mv;
				} 
			} 
			catch (Exception e) {
				mv.setViewName("studentDetails");
				mv.addObject("registerMessage", "Unexpected System Error");
				e.printStackTrace();
			}
			mv.addObject("notes", notes);
		}
		return mv;
	}


	@RequestMapping(value="/notes", method=RequestMethod.POST)
	public ModelAndView saveNotes(HttpServletRequest request, @ModelAttribute("notes") Notes notes) throws IllegalArgumentException, Exception
	{
		ModelAndView mv = new ModelAndView("notes");
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		LuceneNotesRecommender luceneNotesBean = (LuceneNotesRecommender)webApplicationContext.getBean("luceneNotesBean");
		model.Student student = (model.Student)request.getSession().getAttribute("sessionUser");
		model.Topic obj = topicBo.findTopicById(notes.getTopicid());
		request.getSession().setAttribute("topic", obj);
		student.setCurrentTopic(notes.getTopicName());
		request.getSession().setAttribute("sessionUser", student);
		mv.addObject("sessionUser", student);
		mv.addObject("topic", obj);
		try 
		{
			notes.setStudentId(student.getStudentId());
			notesBo.save(notes);
			studentBo.save(student);
			//update the index by calling LuceneNotesIndexer
			luceneNotesBean.updateNotesIndex(notes.getTopicid(), notes.getStudentId(), notes.getTopicText());

		} 
		
		catch (ConstraintViolationException e ) 
		{
			mv.addObject("notes", notes);
			mv.addObject("notesMessage", "Constraint Violation Exception");
			return mv;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			mv.addObject("notes", notes);
			mv.addObject("notesMessage", "Unexpected System Error. Please try again");
			return mv;
		}
		Set<String> recommendedWords = luceneNotesBean.getNotesRecommendations(notes.getTopicid(), student.getStudentId(), notes.getTopicText(), 5);
		notes.setRecommmendedWords(recommendedWords);
		mv.addObject("recommendedWords", notes.getRecommmendedWords());
		Set<String> recommendedLinks = (Set<String>)request.getSession().getAttribute("listOfRecommendedLinks");
		//LuceneLinksRecommender luceneLinksBean = (LuceneLinksRecommender)webApplicationContext.getBean("luceneLinksBean");
		mv.addObject("recommendedLinks", recommendedLinks);
		mv.addObject("notes", notes);
		mv.addObject("notesMessage", "Saved");

		return mv;
	}
	
	
	@RequestMapping(value="/exam", method=RequestMethod.GET)
	public ModelAndView getExamsList(HttpServletRequest request) throws IllegalArgumentException, Exception
	{
		List<Exam> list = examBo.findAllExams();
		//request.getSession().setAttribute("examid", list.get(0).getExamId());
		ModelAndView mv = new ModelAndView("getExams");
		mv.addObject("exams", list);
		
		model.Student student = (model.Student)request.getSession().getAttribute("sessionUser");
		Topic topic = (Topic)request.getSession().getAttribute("topic");
		
		List<Content> contentList = contentBo.findContentList((int)request.getSession().getAttribute("examid"));
		Notes notes = new Notes();
		
		util.Content contentXML = new util.Content();
		
		Map<Integer,util.Chapter> mapContentXML = new LinkedHashMap<Integer,util.Chapter>();
		
//		Collections.sort(contentList, new Comparator<Content>()
//				{
//					@Override
//					public int compare(Content o1, Content o2) {
//						// TODO Auto-generated method stub
//						return new Integer(o1.getTopicId()).compareTo(new Integer(o2.getTopicId()));
//					}
//				});
		
		for (Content c : contentList) {
			int topicId = c.getTopicId();
			String studentId = student.getStudentId();
			
			String doneStatus = "n";
			try {
				notes = notesBo.findNotes(studentId, topicId);
				//set done = "Y" if notes not null
				doneStatus = "y";
				if (topicId == topicBo.findTopicByName(student.getCurrentTopic()).getTopicId()) {
					// set done = "I"
					doneStatus = "i";
				}
			} 
			catch(IllegalArgumentException exception)
			{
				if(exception.getMessage().equals("Notes doesnt exists"))
				{
					notes.setStudentId(student.getStudentId());
					notes.setTopicid(topic.getTopicId());
					notes.setTopicName(student.getCurrentTopic());
					mv.addObject("notes", notes);
					
				}
			}  
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//display
			Topic topicObj = topicBo.findTopicById(topicId);
			int parentTopicId = topicObj.getParentTopicId();
			
			
			
			if (parentTopicId == 0) {
				if (!mapContentXML.containsKey(topicId)) {
					util.Chapter chapObj = new util.Chapter();
					chapObj.setTitle(topicObj.getTopicName());
					mapContentXML.put(topicId, chapObj);
				}
			}
			else {
				if (mapContentXML.containsKey(parentTopicId)) {
					util.Topic xmlTopic = new util.Topic();
					xmlTopic.setName(topicObj.getTopicName());
					xmlTopic.setDone(doneStatus);
					mapContentXML.get(parentTopicId).getTopic().add(xmlTopic);
				}
			}
			
		}
		
		for (Integer in : mapContentXML.keySet()) {
			contentXML.getChapter().add(mapContentXML.get(in));
		}
		
		
		JAXBContext context = JAXBContext.newInstance(util.Content.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		StringWriter writer = new StringWriter();
		marshaller.marshal(contentXML, writer);
		
		String result = writer.toString();
		mv.addObject("chapterList", result.trim());
		
		return mv;
	}
	
	
	@RequestMapping(value="/allexams", method=RequestMethod.GET)
	public ModelAndView getAllExams(HttpServletRequest request) throws IllegalArgumentException, Exception
	{
		ModelAndView mv = new ModelAndView("getAllExams");
		LinkedHashMap<Integer, List<Content>> examContentMapWithExamId = new LinkedHashMap<Integer, List<Content>>();
		exam.jaxbclasses.Exams examsXML = new exam.jaxbclasses.Exams();
		List<model.Exam> list = examBo.findAllExams();
		request.getSession().setAttribute("listofAllExams", list); //Use this session variable in above method.
		//boolean noContent = false;
		for(model.Exam exam : list)
		{
			List<Content> examContent = null;
			try
			{
				examContent = contentBo.findContentList(exam.getExamId());
			}
			catch(IllegalArgumentException e)
			{
				if(e.getMessage().equals("Content List doesnt exists"));
				{
					//do nothing
				}
			}
			if((examContent != null) && !examContent.isEmpty())
			{
				LinkedHashMap<Integer, exam.jaxbclasses.Chapter> chapterTopicMap = new LinkedHashMap<Integer, exam.jaxbclasses.Chapter>();
				///noContent = true;
				exam.jaxbclasses.Exam examobj = new exam.jaxbclasses.Exam();
				examobj.setNumber(exam.getExamValue());
				for(Content c : examContent)
				{
					int topicId = c.getTopicId();
					model.Topic topicobj = topicBo.findTopicById(topicId);
					int parentTopicId = topicobj.getParentTopicId();
					if(parentTopicId == 0)
					{
						if(!chapterTopicMap.containsKey(topicId))
						{
							exam.jaxbclasses.Chapter chapterObj = new exam.jaxbclasses.Chapter();
							chapterObj.setName(topicobj.getTopicName());
							chapterTopicMap.put(topicId, chapterObj);
						}
					}
					else
					{
						if(chapterTopicMap.containsKey(parentTopicId))
						{
							chapterTopicMap.get(parentTopicId).getTopic().add(topicobj.getTopicName());
						}
					}

				}
				for(Integer in : chapterTopicMap.keySet())
				{
					examobj.getChapter().add(chapterTopicMap.get(in));
				}
				examobj.setComplete(String.format("%.2f", (calculateCompletePercentage(request, exam.getExamId(), examobj))));
				examsXML.getExam().add(examobj);
				examContentMapWithExamId.put(exam.getExamId(), examContent);
			}
		}

		request.getSession().setAttribute("examContentMapWithExamId", examContentMapWithExamId);

		JAXBContext context = JAXBContext.newInstance(exam.jaxbclasses.Exams.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter writer = new StringWriter();
		marshaller.marshal(examsXML, writer);
		//System.out.println("Response xml is "+writer);
		mv.addObject("allExams", writer);
		return mv;
	}
	
	
	public double calculateCompletePercentage(HttpServletRequest request, int examId, exam.jaxbclasses.Exam examobj) throws IllegalArgumentException, Exception
	{
		double complete = 0.0;
		double totalNoOfTopicExams = 0;
		double totalNoOfCompleteTopicsinExams = 0;
		
		model.Student student = (model.Student)request.getSession().getAttribute("sessionUser");
		List<Notes> notes = null;
		
		List<exam.jaxbclasses.Chapter> listExamChapters = examobj.getChapter();	
		for(exam.jaxbclasses.Chapter chapter : listExamChapters)
		{
			totalNoOfTopicExams =  totalNoOfTopicExams + chapter.getTopic().size();
		}
		try
		{
			notes = notesBo.findNotesByStudentId(student.getStudentId());
		}
		catch(IllegalArgumentException e)
		{
			if(e.getMessage().equals("Notes List doesnt exists"))
			{
				return 0.0;
			}
		}
		
		for(Notes note : notes)
		{
			String topicName = note.getTopicName();
			Content c = contentBo.findContentByTopicId(topicBo.findTopicByName(topicName).getTopicId());
			if (c.getExamId() == examId)
			{
				totalNoOfCompleteTopicsinExams = totalNoOfCompleteTopicsinExams+1;
			}
		}
		
		complete = totalNoOfCompleteTopicsinExams/totalNoOfTopicExams;
		return complete;

	}
	
	
	@RequestMapping(value="/cheatSheet", method=RequestMethod.GET)
	public ModelAndView getDetailsForCheatSheet(HttpServletRequest request) 	{
		
		ModelAndView mv = new ModelAndView("cheatSheetGenerator");
		int examid = (int)request.getSession().getAttribute("examid");
		cheatsheet.jaxbclasses.Exams examObj = new Exams();
		if(examid == 2)
		{
			examObj.setNumber(MID_TERM_1);
		}
		else if(examid == 3)
		{
			examObj.setNumber(MID_TERM_2);
		}
		else if(examid == 4)
		{
			examObj.setNumber(MID_TERM_3);
		}
		else if(examid == 5)
		{
			examObj.setNumber(FINAL_EXAM);
		}
		
		model.Student student = (model.Student)request.getSession().getAttribute("sessionUser");
		HashMap<String, List<cheatsheet.jaxbclasses.Topic>> chapterTopicMap = new HashMap<>();
		try
		{
			List<Notes> notes = notesBo.findNotesByStudentId(student.getStudentId());
			for(Notes note: notes)
			{
				
				String topicName = note.getTopicName();
				Topic topic = topicBo.findTopicByName(topicName);
				Content contentobj = contentBo.findContentByTopicId(topic.getTopicId());
				if(contentobj.getExamId() == examid)
				{

					Topic parentTopic = topicBo.findTopicById(topic.getParentTopicId());			
				
					if(!chapterTopicMap.containsKey(parentTopic.getTopicName()))
					{
						List<cheatsheet.jaxbclasses.Topic> topicList = new ArrayList<>();
						cheatsheet.jaxbclasses.Topic cTopic = new cheatsheet.jaxbclasses.Topic();
						cTopic.setCode(note.getCode());
						cTopic.setNotes(note.getTopicText());
						cTopic.setTitle(topicName);
						topicList.add(cTopic);
						chapterTopicMap.put(parentTopic.getTopicName(), topicList);	
					}
					else if(chapterTopicMap.containsKey(parentTopic.getTopicName()))
					{
						cheatsheet.jaxbclasses.Topic cTopic = new cheatsheet.jaxbclasses.Topic();
						cTopic.setCode(note.getCode());
						cTopic.setNotes(note.getTopicText());
						cTopic.setTitle(topicName);
						chapterTopicMap.get(parentTopic.getTopicName()).add(cTopic);
					}
				}
			}
			
			for(Map.Entry<String, List<cheatsheet.jaxbclasses.Topic>> entry : chapterTopicMap.entrySet())
			{
				String chapterName =  entry.getKey();
				cheatsheet.jaxbclasses.Chapter chapterobj = new Chapter();
				chapterobj.setName(chapterName);
				chapterobj.getTopic().addAll(entry.getValue());
				examObj.getChapter().add(chapterobj);
				
			}
			
			JAXBContext context = JAXBContext.newInstance(cheatsheet.jaxbclasses.Exams.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			StringWriter writer = new StringWriter();
			marshaller.marshal(examObj, writer);
			mv.addObject("cheatsheet", writer);

			
			
		}
		catch(IllegalArgumentException e)
		{
			if(e.getMessage().equals("Notes List doesnt exists"))
			{
				//student has not written any notes.
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
		
		
		
	}
	
	@RequestMapping(value="/cheatSheetGenerate", method=RequestMethod.GET)
	public ModelAndView redirectToCheatSheet()
	{
		return new ModelAndView("cheatsheet");
	}
	
	@RequestMapping(value="/about", method=RequestMethod.GET)
	public ModelAndView redirectToAbout()
	{
		return new ModelAndView("aboutpage");
	}
	
	
	
	@RequestMapping(value="/visualize", method=RequestMethod.GET)
	public ModelAndView redirectToVisualizationPage(HttpServletRequest request) throws JsonProcessingException
	{
		
		OriginalityForStudents obj = new OriginalityForStudents();
		ModelAndView mv = new ModelAndView("visualize");
		
		List<model.Student> students = studentBo.findStudents();
		for (model.Student student : students)
		{
			HashMap<Integer, Double> examScoreMap = new HashMap<Integer, Double>();
			HashMap<Integer, Integer> examCountMap = new HashMap<>();
			try
			{
				List<Notes> notes = notesBo.findNotesByStudentId(student.getStudentId());
				Student vizStudent = new Student();
				for(Notes note : notes)
				{
					int topicId = note.getTopicid();
					Content c = contentBo.findContentByTopicId(topicId);
					int examId = c.getExamId();
					double matchPercentage = 100.0 - Double.valueOf(note.getMatchPercentage());
					if(examScoreMap.containsKey(examId))
					{	
						examScoreMap.put(examId, examScoreMap.get(examId)+matchPercentage);
						examCountMap.put(examId, examCountMap.get(examId)+1);
					}
					else
					{
						examScoreMap.put(examId, matchPercentage);
						examCountMap.put(examId, 1);
						
					}
					
					
				}
				if(!examScoreMap.containsKey(2))
				{
					vizStudent.getValues().put(MID_TERM_1, "NA");
				}
				if(!examScoreMap.containsKey(3))
				{
					vizStudent.getValues().put(MID_TERM_2, "NA");
				}
				if(!examScoreMap.containsKey(4))
				{
					vizStudent.getValues().put(MID_TERM_3, "NA");
				}
				if(!examScoreMap.containsKey(5))
				{
					vizStudent.getValues().put(FINAL_EXAM, "NA");
				}
				
				double sumTotalScore = 0.0;
				for(Map.Entry<Integer, Double> entry : examScoreMap.entrySet())
				{
					
					double avg = 0.0;
					if(entry.getKey() == 2)
					{
						avg = entry.getValue()/examCountMap.get(2);
						vizStudent.getValues().put(MID_TERM_1, String.format("%.2f", avg));
					}
					else if(entry.getKey() == 3)
					{
						avg = entry.getValue()/examCountMap.get(3);
						vizStudent.getValues().put(MID_TERM_2, String.format("%.2f", avg));
					}
					else if(entry.getKey() == 4)
					{
						avg = entry.getValue()/examCountMap.get(4);
						vizStudent.getValues().put(MID_TERM_3, String.format("%.2f", avg));
					}
					else if(entry.getKey() == 5)
					{
						avg = entry.getValue()/examCountMap.get(5);
						vizStudent.getValues().put(FINAL_EXAM, String.format("%.2f", avg));
					}
					sumTotalScore = sumTotalScore + avg;

				}
				vizStudent.setOriginalityscore((int)sumTotalScore/examScoreMap.size());
				vizStudent.setName(student.getStudentId());
				obj.getStudents().add(vizStudent);				
								
			}
			catch(IllegalArgumentException e)
			{
				if(e.getMessage().equals("Notes List doesnt exists"))
				{
					//do nothing
				}
				else {
						e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			
		}
		Collections.sort(obj.getStudents());
		ObjectMapper mapper = new ObjectMapper();
		String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		//System.out.println(prettyStaff1);
		mv.addObject("visualize", prettyStaff1);	
		return mv;
	}




}
