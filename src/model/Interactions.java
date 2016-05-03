package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="interactions", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "interaction_id") })
public class Interactions {
	
	
	private int interactionId;
	private int actionId;
	private int topicId;
	private String studentId;
	private double matchPercentage;
	private String url;

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="interactions_interaction_id_seq")
    @SequenceGenerator(name="interactions_interaction_id_seq", sequenceName="interactions_interaction_id_seq", allocationSize=1)
	@Column(name = "interaction_id", unique = true, nullable = false)
	public int getInteractionId() {
		return interactionId;
	}
	
	
	public void setInteractionId(int interactionId) {
		this.interactionId = interactionId;
	}
	
	@Column(name = "action_id")
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	@Column(name = "topic_id")
	public int getTopicId() {
		return topicId;
	}
	
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	@Column(name = "match_percentage", nullable = true)
	public double getMatchPercentage() {
		return matchPercentage;
	}
	
	public void setMatchPercentage(double matchPercentage) {
		this.matchPercentage = matchPercentage;
	}
	
	@Column(name = "url", nullable = true)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "student_id")
	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

		
}
