package controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;







import model.Student;
import model.Topic;
import model.User;
import model.Instructor;
import model.Notes;


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


	@RequestMapping(value="/notes", method=RequestMethod.GET)
	public ModelAndView loadForm(HttpServletRequest request)
	{
		Student student = (Student)request.getSession().getAttribute("sessionUser");
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
		ModelAndView mv = new ModelAndView("notes");
		try 
		{
			notes.setStudentId(student.getStudentId());
			notesBo.save(notes);
		} 
		
		catch (ConstraintViolationException e ) 
		{
			mv.addObject("notes", notes);
			mv.addObject("notesMessage", "Constraint Violation Exception");
			return mv;

		}
		catch(Exception e)
		{
			mv.addObject("notes", notes);
			mv.addObject("notesMessage", "Unexpected System Error. Please try again");
			return mv;
		}
		
		mv.addObject("notes", notes);
		mv.addObject("notesMessage", "Saved");

		return mv;
	}




}
