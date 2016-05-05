package bo.implementation;


import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.NotesDAO;
import bo.NotesBO;
import model.Notes;


@Service("notesBo")
@Transactional
public class NotesBOImplementation implements NotesBO
{
	
	@Autowired
	NotesDAO notesDao;
	

	@Override
	public void save(Notes notes) throws ConstraintViolationException,
			Exception {
		notesDao.save(notes);
		
	}

	@Override
	public void update(Notes notes) {
		notesDao.update(notes);
		
	}

	@Override
	public void delete(Notes notes) {
		notesDao.delete(notes);
		
	}

	@Override
	public Notes findNotes(String studentId, int topicId)
			throws IllegalArgumentException, Exception {
		// TODO Auto-generated method stub
		return notesDao.findNotes(studentId, topicId);
	}

	@Override
	public Notes findNotes(String notesId) throws IllegalArgumentException,
			Exception {
		// TODO Auto-generated method stub
		return notesDao.findNotes(notesId);
	}

	@Override
	public List<Notes> findNotesByStudentId(String studentId)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return notesDao.findNotesByStudentId(studentId);
	}

	@Override
	public List<Notes> findAllNotes() throws IllegalArgumentException
	{
		return notesDao.findAllNotes();
	}
}
