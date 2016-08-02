/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 2, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.library.dao.AuthorizationDao;
import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.model.Employee;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizationService.
 */
@Service
public class AuthorizationService {

	/** The authorization dao. */
	@Autowired
	private AuthorizationDao authorizationDao;

	/**
	 * Gets the authorization dao.
	 *
	 * @return the authorization dao
	 */
	public AuthorizationDao getAuthorizationDao() {
		return authorizationDao;
	}

	/**
	 * Sets the authorization dao.
	 *
	 * @param authorizationDao the new authorization dao
	 */
	public void setAuthorizationDao(AuthorizationDao authorizationDao) {
		this.authorizationDao = authorizationDao;
	}

	/**
	 * Gets the employee dao.
	 *
	 * @return the employee dao
	 */
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	/**
	 * Sets the employee dao.
	 *
	 * @param employeeDao the new employee dao
	 */
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/** The employee dao. */
	@Autowired
	private EmployeeDao employeeDao;

	/** The employee. */
	private Employee employee;

	public String googleLogin(String emailId) {
		employee = employeeDao.getEmployee(emailId);
		if (employee != null) {
			String dupliacteEmailId = employeeDao.isAdmin(emailId);
			if (dupliacteEmailId != null) {
				if (dupliacteEmailId.equals(emailId)) {
					return "admin";
				}
				else{
					return "user";
				}
			} else {
				return "user";
			}
		} else {
			return "data";
		}
	}

	/**
	 * Login.
	 *
	 * @param emailId the email id
	 * @param password the password
	 * @return the string
	 */
	public String login(String emailId, String password) {
		employee = employeeDao.getEmployee(emailId);
		String dbPassword = employee.getPassword();
		String duplicateEmailId = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			password = hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		if(password.equals(dbPassword)){
			duplicateEmailId = employeeDao.isAdmin(emailId);
			if(duplicateEmailId != null){
				if(duplicateEmailId.equals(emailId)){
					return "admin";
				}
				else{
					return "user";
				}
			}
			else{
				return "user";
			}
		}
		else{
			return "failure";
		}
		
	}
	
	
}
