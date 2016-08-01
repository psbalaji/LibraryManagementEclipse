/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Jul 31, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;



/**
 * The Class Employee.
 */

@Component
@XmlRootElement
public class Employee {

	/** The email id. */
	private String emailId;
	
	/** The employee id. */
	private String employeeId;
	
	/** The google id. */
	private String googleId;
	
	/** The first name. */
	private String firstName;
	
	/** The middle name. */
	private String middleName;
	
	/** The last name. */
	private String lastName;
	
	/** The manager id. */
	private String managerId;
	
	/** The mobile no. */
	private String mobileNo;
	
	/** The city id. */
	private String cityId;
	
	/** The password. */
	private String password;
	
	
	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	/**
	 * Gets the google id.
	 *
	 * @return the google id
	 */
	public String getGoogleId() {
		return googleId;
	}
	
	/**
	 * Sets the google id.
	 *
	 * @param googleId the new google id
	 */
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}
	
	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the manager id.
	 *
	 * @return the manager id
	 */
	public String getManagerId() {
		return managerId;
	}
	
	/**
	 * Sets the manager id.
	 *
	 * @param managerId the new manager id
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	
	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	/**
	 * Gets the city id.
	 *
	 * @return the city id
	 */
	public String getCityId() {
		return cityId;
	}
	
	/**
	 * Sets the city id.
	 *
	 * @param cityId the new city id
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
