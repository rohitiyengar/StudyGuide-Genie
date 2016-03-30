package dao;

import model.Student;


public interface StudentDAO {
	
	void save(Student student);
	void update(Student student);
	void delete(Student student);
	Student findStudent(String student_id);

}
