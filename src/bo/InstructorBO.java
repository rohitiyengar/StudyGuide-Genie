package bo;

import org.hibernate.exception.ConstraintViolationException;

import model.Instructor;

public interface InstructorBO {
	
	void save(Instructor instructor) throws ConstraintViolationException, Exception;
	void update(Instructor instructor);
	void delete(Instructor instructor);
	Instructor findInstructor(String instructor_id);

}
