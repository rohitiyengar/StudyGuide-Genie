package bo.implementation;

import model.Instructor;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.InstructorDAO;
import bo.InstructorBO;


@Service("instructorBo")
@Transactional
public class InstructorBOImplementation implements InstructorBO {
	
	@Autowired
	InstructorDAO instructorDao;

	@Override
	public void save(Instructor instructor)
			throws ConstraintViolationException, Exception {
		instructorDao.save(instructor);
		
	}

	@Override
	public void update(Instructor instructor) {
		instructorDao.update(instructor);
		
	}

	@Override
	public void delete(Instructor instructor) {
		instructorDao.delete(instructor);
		
	}

	@Override
	public Instructor findInstructor(String instructor_id) {
		return instructorDao.findInstructor(instructor_id);
	}

}
