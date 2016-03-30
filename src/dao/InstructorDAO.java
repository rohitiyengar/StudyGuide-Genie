package dao;

import model.Instructor;

public interface InstructorDAO {
	
	void save(Instructor instructor);
	void update(Instructor instructor);
	void delete(Instructor instructor);
	Instructor findInstructor(String instructor_id);


}
