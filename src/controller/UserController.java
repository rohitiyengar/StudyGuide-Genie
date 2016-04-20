package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Scope;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;








import model.Student;
import model.Topic;
import model.User;
import model.Instructor;


import bo.StudentBO;
import bo.TopicBO;
import bo.UserBO;
import bo.InstructorBO;


@Controller
@SessionAttributes({"sessionUser", "topic"})
public class UserController {

	@Autowired
	UserBO userBo;

	@Autowired
	StudentBO studentBo;

	@Autowired
	InstructorBO instructorBo;
	
	@Autowired
	TopicBO topicBo;

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


	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView loadForm(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("registerUser");
		if(request.getSession().getAttribute("sessionUser") != null)
		{
			mv.setViewName("studentDetails");
			return mv;
		}
		mv.addObject("registerUser", new User());
		return mv;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("registerUser") User user, HttpServletRequest request, BindingResult result)
	{
		ModelAndView mv = new ModelAndView("registerUser");
		if(user.getEmail() == null)
		{
			String loginMessage = "";
			try
			{
				User userFound = userBo.findUser(user.getUserName());
				System.out.println(user.getPassword());
				if(userFound.getPassword().equals(user.getPassword()))
				{
					loginMessage = "Correct User Name/Password";
					System.out.println("User role is"+userFound.getRole());
					if(userFound.getRole().equals(User.roles.STUDENT.toString()))
					{
						Student student = studentBo.findUser(userFound.getUserName());
						mv.addObject("sessionUser", student);	
						System.out.println(student==null);
						if(student.getCurrentTopic() != null && !student.getCurrentTopic().isEmpty())
						{
							Topic topic = topicBo.findTopicByName(student.getCurrentTopic());
							mv.addObject("topic", topic);
							request.getSession().setAttribute("topic", topic);
							
						}
						request.getSession().setAttribute("sessionUser", student);
						mv.setViewName("studentDetails");
				
					}
					else if(userFound.getRole().equals(User.roles.INSTRUCTOR.toString()))
					{
						Instructor instructor = instructorBo.findInstructor(userFound.getUserName());
						mv.addObject("sessionUser", instructor);
						request.getSession().setAttribute("sessionUser", instructor);
						mv.setViewName("instructor");
					}
					else if(userFound.getRole().equals(User.roles.ADMIN))
					{
						
					}
				}
				else
				{
					loginMessage = "Incorrect Password";
				}
			}
			catch(IllegalArgumentException e)
			{
				if (e.getMessage().equals("User doesnt exists"))
				{
					loginMessage = "Invalid UserName";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				loginMessage = "Unexpected System Error";
			}
			
			mv.addObject("loginMessage", loginMessage);
			//mv.setViewName("student");
			
		}

		else
		{
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
		}
		return mv;
	}


}
