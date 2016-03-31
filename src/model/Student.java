package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="student", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "student_id") })
public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String studentId;
	private String fname;
	private String lname;
	private int noOfTopicsInComplete;
	private int noOfTopicsNotTouched;
	private int noOfTopicsComplete;
	private int  noOfCodeSnippets;
	private int noOfCompilationFailures;
	private String topicLastCompleted;
	private int noOfDaysSinceLastComplete;
	private String currentTopic;
	private String currentCourse;
	private boolean isRegistered;
	
	
	/**
	 * @return the studentId
	 */
	@Id
	@Column(name = "student_id", unique = true, nullable = false, length = 10)
	public String getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the fname
	 */
	@Column(name = "fname", nullable = false, length = 20)
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return the lname
	 */
	@Column(name = "lname", nullable = false, length = 20)
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	/**
	 * @return the noOfTopicsInComplete
	 */
	@Column(name = "topics_incomplete")
	public int getNoOfTopicsInComplete() {
		return noOfTopicsInComplete;
	}
	/**
	 * @param noOfTopicsInComplete the noOfTopicsInComplete to set
	 */
	public void setNoOfTopicsInComplete(int noOfTopicsInComplete) {
		this.noOfTopicsInComplete = noOfTopicsInComplete;
	}
	/**
	 * @return the noOfTopicsNotTouched
	 */
	@Column(name = "topics_untouched")
	public int getNoOfTopicsNotTouched() {
		return noOfTopicsNotTouched;
	}
	/**
	 * @param noOfTopicsNotTouched the noOfTopicsNotTouched to set
	 */
	public void setNoOfTopicsNotTouched(int noOfTopicsNotTouched) {
		this.noOfTopicsNotTouched = noOfTopicsNotTouched;
	}
	/**
	 * @return the noOfTopicsComplete
	 */
	@Column(name = "topics_complete")
	public int getNoOfTopicsComplete() {
		return noOfTopicsComplete;
	}
	/**
	 * @param noOfTopicsComplete the noOfTopicsComplete to set
	 */
	public void setNoOfTopicsComplete(int noOfTopicsComplete) {
		this.noOfTopicsComplete = noOfTopicsComplete;
	}
	/**
	 * @return the noOfCodeSnippets
	 */
	@Column(name = "code_snippets")
	public int getNoOfCodeSnippets() {
		return noOfCodeSnippets;
	}
	/**
	 * @param noOfCodeSnippets the noOfCodeSnippets to set
	 */
	public void setNoOfCodeSnippets(int noOfCodeSnippets) {
		this.noOfCodeSnippets = noOfCodeSnippets;
	}
	/**
	 * @return the noOfCompilationFailures
	 */
	@Column(name = "compilation_fails")
	public int getNoOfCompilationFailures() {
		return noOfCompilationFailures;
	}
	/**
	 * @param noOfCompilationFailures the noOfCompilationFailures to set
	 */
	public void setNoOfCompilationFailures(int noOfCompilationFailures) {
		this.noOfCompilationFailures = noOfCompilationFailures;
	}
	/**
	 * @return the topicLastCompleted
	 */
	@Column(name = "topic_last_completed")
	public String getTopicLastCompleted() {
		return topicLastCompleted;
	}
	/**
	 * @param topicLastCompleted the topicLastCompleted to set
	 */
	public void setTopicLastCompleted(String topicLastCompleted) {
		this.topicLastCompleted = topicLastCompleted;
	}
	/**
	 * @return the noOfDaysSinceLastComplete
	 */
	@Column(name = "days_since_last_complete")
	public int getNoOfDaysSinceLastComplete() {
		return noOfDaysSinceLastComplete;
	}
	/**
	 * @param noOfDaysSinceLastComplete the noOfDaysSinceLastComplete to set
	 */
	public void setNoOfDaysSinceLastComplete(int noOfDaysSinceLastComplete) {
		this.noOfDaysSinceLastComplete = noOfDaysSinceLastComplete;
	}
	/**
	 * @return the currentTopic
	 */
	@Column(name = "current_topic")
	public String getCurrentTopic() {
		return currentTopic;
	}
	/**
	 * @param currentTopic the currentTopic to set
	 */
	public void setCurrentTopic(String currentTopic) {
		this.currentTopic = currentTopic;
	}
	/**
	 * @return the currentCourse
	 */
	@Column(name = "current_course")
	public String getCurrentCourse() {
		return currentCourse;
	}
	/**
	 * @param currentCourse the currentCourse to set
	 */
	public void setCurrentCourse(String currentCourse) {
		this.currentCourse = currentCourse;
	}
	/**
	 * @return the isRegistered
	 */
	@Column(name = "is_registered")
	public boolean isRegistered() {
		return isRegistered;
	}
	/**
	 * @param isRegistered the isRegistered to set
	 */
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
		
	


}
