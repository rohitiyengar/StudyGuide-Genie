package dao;

import java.util.List;

import model.Student;


public interface StudentDAO {
	
	void save(Student student);
	void update(Student student);
	void delete(Student student);
	Student findStudent(String student_id);
	List<Student> findStudents() throws IllegalArgumentException;

}
