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
@Table(name="content", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "content_id") })
public class Content {
	private int contentId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="content_content_id_seq")
    @SequenceGenerator(name="content_content_id_seq", sequenceName="content_content_id_seq", allocationSize=1)
	@Column(name = "content_id", unique = true, nullable = false)
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	@Column(name = "topic_id")
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	@Column(name = "exam_id")
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	private int topicId;
	private int examId;
}
