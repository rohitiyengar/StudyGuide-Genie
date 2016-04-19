package dao;

import java.util.List;

import model.Exam;

import org.hibernate.exception.ConstraintViolationException;

public interface ExamDAO {
	
	void save(Exam exam) throws ConstraintViolationException, Exception;
	void update(Exam exam);
	void delete(Exam exam);
	Exam findExam(int exam_id);
	List<Exam> findAllExams();

}
