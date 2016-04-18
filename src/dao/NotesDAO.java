package dao;



import org.hibernate.exception.ConstraintViolationException;

import model.Notes;

public interface NotesDAO {
	
	
	void save(Notes notes) throws ConstraintViolationException, Exception;
	void update(Notes notes);
	void delete(Notes notes);
	Notes findNotes(String studentId, int topicId) throws IllegalArgumentException, Exception;
	Notes findNotes(String notesId) throws IllegalArgumentException, Exception;


}
