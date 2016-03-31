package controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;


import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;


import model.Student;
import model.User;
import model.Instructor;


import bo.StudentBO;
import bo.UserBO;
import bo.InstructorBO;


@Controller
public class UserController {
	
	@Autowired
	UserBO userBo;
	
	@Autowired
	StudentBO studentBo;
	
	@Autowired
	InstructorBO instructorBo;
	
	@ModelAttribute("user")
	public User getUser()
	{
		return new User();
	}
	
	@ModelAttribute("roleTypes")
	public User.roles[] getRoles()
	{
		return User.roles.values();
	}
	
	
	@RequestMapping(value="registerUser", method=RequestMethod.GET)
	public ModelAndView loadForm()
	{
		ModelAndView mv = new ModelAndView("registerUser");
		mv.addObject("registerUser", new User());
		return mv;
	}
	
	@RequestMapping(value="registerUser", method=RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("registerUser") User user, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("registerUser");
		String message = "Success";
		try
		{
			//Hibernate call to save the user to DB
			userBo.save(user);
			
			//if the user is a student, make an entry in Student table
			if(user.getRole().equals(User.roles.STUDENT.toString()))
			{
				Student student = new Student();
				student.setStudentId(user.getUserName());
				student.setFname(user.getFirstName());
				student.setLname(user.getLastName());
				//Hibernate call to save the student object in DB.
				studentBo.save(student);
			}
			
			if(user.getRole().equals(User.roles.INSTRUCTOR.toString()))
			{
				Instructor instructor = new Instructor();
				instructor.setInstructorId(user.getUserName());
				instructor.setFname(user.getFirstName());
				instructor.setLname(user.getLastName());
				//Hibernate call to save the instructor object in DB
				instructorBo.save(instructor);
			}
		}
		catch(ConstraintViolationException cvexception)
		{
			message = "Username "+user.getUserName()+"is already taken";
		}
		catch(Exception e)
		{
			//TO_DO - add loggers
			e.printStackTrace();
			message = "Failed: Unexpected System error";
		}
		mv.addObject("message", message);
		return mv;
	}


}
