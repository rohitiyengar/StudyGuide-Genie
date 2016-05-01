package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Transient;





@Entity
@Table(name="notes", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "topic_id"}) })
public class Notes {
	
	private int notesId;
	private String studentId;
	private int topicid;
	private String topicText;
	private String code;
	private String topicName;
	
	@Transient
	private Set<String> recommmendedWords;
	
	
	@Transient
	public Set<String> getRecommmendedWords() {
		return recommmendedWords;
	}


	public void setRecommmendedWords(Set<String> recommmendedWords) {
		this.recommmendedWords = recommmendedWords;
	}


	@Column(name = "topic_name")
	public String getTopicName() {
		return topicName;
	}


	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


	@Column(name = "match")
	public String getMatchPercentage() {
		return matchPercentage;
	}


	public void setMatchPercentage(String matchPercentage) {
		this.matchPercentage = matchPercentage;
	}
	private String matchPercentage;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="notes_id_seq")
    @SequenceGenerator(name="notes_id_seq", sequenceName="notes_id_seq", allocationSize=1)
	@Column(name = "notes_id", unique = true, nullable = false)
	public int getNotesId() {
		return notesId;
	}
	
	
	public void setNotesId(int notesId) {
		this.notesId = notesId;
	}

	@Column(name = "student_id")
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Column(name = "topic_id")
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	
	@Column(name = "topic_text")
	public String getTopicText() {
		return topicText;
	}
	public void setTopicText(String topicText) {
		this.topicText = topicText;
	}
	
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	

}
