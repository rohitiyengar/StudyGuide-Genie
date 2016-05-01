package controller;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.Content;
import model.Exam;
import model.Notes;
import model.Student;
import model.Topic;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import util.Chapter;
import bo.ContentBO;
import bo.ExamBO;
import bo.InstructorBO;
import bo.NotesBO;
import bo.StudentBO;
import bo.TopicBO;
import bo.UserBO;


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
	
	private static String MID_TERM_1 = "Mid term 1";
	private static String MID_TERM_2 = "Mid term 2";
	private static String MID_TERM_3 = "Mid term 3";
	private static String FINAL_EXAM = "Final exam";
	



	@RequestMapping(value="/notes", method=RequestMethod.GET)
	public ModelAndView loadForm(HttpServletRequest request) throws IllegalArgumentException, Exception
	{
		ModelAndView mv = new ModelAndView("notes");
		System.out.println("NOTES FROM EXAM");
		Student student = (Student)request.getSession().getAttribute("sessionUser");
		if (request.getParameter("topicName") != null)
		{
			System.out.println("topicName is:"+request.getParameter("topicName"));
			student.setCurrentTopic(request.getParameter("topicName"));
			Topic newtopic = topicBo.findTopicByName(request.getParameter("topicName"));
			System.out.println("new Topic id, when trying to change it is"+newtopic.getTopicId());
			request.getSession().setAttribute("topic", newtopic);
			mv.addObject("topic", newtopic);
			
			
		}
		
		if (request.getParameter("examName") != null)
		{
			System.out.println("Exam name is:"+request.getParameter("examName"));
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
			System.out.println("Setting exam_id to"+exam_id);
			request.getSession().setAttribute("examid", exam_id);
			int examid = 0;
			if(student.getCurrentTopic() != null)
			{
				Topic currentTopic = topicBo.findTopicByName(student.getCurrentTopic());
				System.out.println("id is "+currentTopic.getTopicId());
				System.out.println(contentBo.findContentByTopicId(currentTopic.getTopicId()));
				examid = contentBo.findContentByTopicId(currentTopic.getTopicId()).getExamId();
				//Check if the exam id last left is the same as the student has clicked on now in a new session.
				if(examid == exam_id)
				{
					System.out.println("Yes, Setting as exam id is the same");
					request.getSession().setAttribute("examid", examid);
					request.getSession().setAttribute("topic", currentTopic);
					mv.addObject("topic", currentTopic);

				}
			}
			if(examid != exam_id)
			{
				student.setCurrentTopic(topicBo.findTopicById(examContent.get(0).getTopicId()).getTopicName());
				Topic newtopic = topicBo.findTopicByName(topicBo.findTopicById(examContent.get(0).getTopicId()).getTopicName());
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
			try
			{
				System.out.println(topic.getTopicId());
				System.out.println(student.getStudentId());
				notes = notesBo.findNotes(student.getStudentId(), topic.getTopicId());
				
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
		Student student = (Student)request.getSession().getAttribute("sessionUser");
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
		
		Student student = (Student)request.getSession().getAttribute("sessionUser");
		Topic topic = (Topic)request.getSession().getAttribute("topic");
		
		List<Content> contentList = contentBo.findContentList((int)request.getSession().getAttribute("examid"));
		Notes notes = new Notes();
		
		util.Content contentXML = new util.Content();
		
		Map<Integer,Chapter> mapContentXML = new HashMap<Integer,Chapter>();
		
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
					Chapter chapObj = new Chapter();
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
		HashMap<Integer, List<Content>> examContentMapWithExamId = new HashMap<Integer, List<Content>>();
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
				HashMap<Integer, exam.jaxbclasses.Chapter> chapterTopicMap = new HashMap<Integer, exam.jaxbclasses.Chapter>();
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
		mv.addObject("allExams", writer);
		return mv;
	}
	
	
	




}
