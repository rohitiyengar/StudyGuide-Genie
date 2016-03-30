package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="instructor", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "instructor_id") })
public class Instructor implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private String instructorId;
	private String fname;
	private String lname;
	
	
	/**
	 * @return the instructorId
	 */
	@Id
	@Column(name = "instructor_id", unique = true, nullable = false, length = 10)
	public String getInstructorId() {
		return instructorId;
	}
	
	
	/**
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
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
	
	
	

}
