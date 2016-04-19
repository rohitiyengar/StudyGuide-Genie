package bo;

import java.util.List;

import model.Exam;

import org.hibernate.exception.ConstraintViolationException;

public interface ExamBO {
	
	void save(Exam exam) throws ConstraintViolationException, Exception;
	void update(Exam exam);
	void delete(Exam exam);
	Exam findExam(int exam_id);
	List<Exam> findAllExams();
}
