package controller;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.util.xml.XmlDocument;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;

import util.*;

import java.io.StringWriter;
import java.util.*;

import model.Content;
import model.Exam;
import model.Student;
import model.Topic;
import model.User;
import model.Instructor;
import model.Notes;


import bo.ContentBO;
import bo.ExamBO;
import bo.NotesBO;
import bo.StudentBO;
import bo.TopicBO;
import bo.UserBO;
import bo.InstructorBO;


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


	@RequestMapping(value="/notes", method=RequestMethod.GET)
	public ModelAndView loadForm(HttpServletRequest request) throws IllegalArgumentException, Exception
	{
		Student student = (Student)request.getSession().getAttribute("sessionUser");
		if (request.getParameter("topicName") != null)
		{
			System.out.println("topicName is:"+request.getParameter("topicName"));
			student.setCurrentTopic(request.getParameter("topicName"));
			Topic newtopic = topicBo.findTopicByName(request.getParameter("topicName"));
			request.getSession().setAttribute("topic", newtopic);
			
		}
		else
		{
			System.out.println("Topicname is null");
		}
		Topic topic = (Topic)request.getSession().getAttribute("topic");
		ModelAndView mv = new ModelAndView("notes");
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
	public ModelAndView saveNotes(HttpServletRequest request, @ModelAttribute("notes") Notes notes)
	{
		Student student = (Student)request.getSession().getAttribute("sessionUser");
		student.setCurrentTopic(notes.getTopicName());
		ModelAndView mv = new ModelAndView("notes");
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
		request.getSession().setAttribute("examid", list.get(0).getExamId());
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
		System.out.println("Hello");
		System.out.println(result.trim());
		
		return mv;
	}
	
	
	




}
