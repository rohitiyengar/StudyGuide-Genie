package bo.implementation;

import java.util.List;

import model.Exam;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ExamDAO;
import bo.ExamBO;

@Service("examBo")
@Transactional
public class ExamBOImplementation implements ExamBO {

	@Autowired
	ExamDAO examDao;
	
	@Override
	public void save(Exam exam) throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
		examDao.save(exam);
	}

	@Override
	public void update(Exam exam) {
		// TODO Auto-generated method stub
		examDao.update(exam);
	}

	@Override
	public void delete(Exam exam) {
		// TODO Auto-generated method stub
		examDao.delete(exam);
	}

	@Override
	public Exam findExam(int exam_id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return examDao.findExam(exam_id);
	}

	@Override
	public List<Exam> findAllExams() {
		// TODO Auto-generated method stub
		return examDao.findAllExams();
	}

}
