package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.context.annotation.Scope;

@Scope("session")
@Entity
@Table(name="exam", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "exam_id") })
public class Exam {

	private int examId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="exam_exam_id_seq")
    @SequenceGenerator(name="exam_exam_id_seq", sequenceName="exam_exam_id_seq", allocationSize=1)
	@Column(name = "exam_id", unique = true, nullable = false)
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	
	@Column(name = "exam_value")
	public String getExamValue() {
		return examValue;
	}
	public void setExamValue(String examValue) {
		this.examValue = examValue;
	}
	private String examValue;

}
