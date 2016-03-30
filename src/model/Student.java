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
	 * @return the isRegistered
	 */
	@Column(name = "is_registered", nullable = false)
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
