package bo;


import java.util.List;

import model.Student;

import org.hibernate.exception.ConstraintViolationException;

public interface StudentBO {
	
	
	void save(Student student) throws ConstraintViolationException, Exception;
	void update(Student student);
	void delete(Student student);
	Student findUser(String student_id);
	List<Student> findStudents() throws IllegalArgumentException;



}
