package model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Transient;


@Entity
@Table(name="users", catalog="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username") })
public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	public enum roles {ADMIN, STUDENT, INSTRUCTOR};
	
	private String userName;
	private String password;
	private String email;
	private String role;
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;

	
	/**
	 * @return the userName
	 */
	@Id
	@Column(name = "username", unique = true, nullable = false, length = 10)
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/**
	 * @return the password
	 */
	@Column(name = "password", unique = true, nullable = false, length = 15)
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	@Column(name = "email", unique = true, nullable = false, length = 25)
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * @return the role
	 */
	@Column(name = "role", nullable = false, length = 20)
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * @return the firstName
	 */
	@Transient
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	@Transient
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
