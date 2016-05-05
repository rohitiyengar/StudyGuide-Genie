package bo.implementation;

import java.util.List;

import model.Student;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StudentDAO;
import bo.StudentBO;


@Service("studentBo")
@Transactional
public class StudentBOImplementation implements StudentBO {
	
	@Autowired
	StudentDAO studentDao;

	@Override
	public void save(Student student) throws ConstraintViolationException,
			Exception {
		studentDao.save(student);
	}

	@Override
	public void update(Student student) {
		studentDao.update(student);
		
	}

	@Override
	public void delete(Student student) {
		studentDao.delete(student);
		
	}

	@Override
	public Student findUser(String student_id) {
		return studentDao.findStudent(student_id);
	}

	@Override
	public List<Student> findStudents() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return studentDao.findStudents();
	}

}
