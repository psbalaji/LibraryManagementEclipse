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

@Service
public class AuthorizationService {

	@Autowired
	private AuthorizationDao authorizationDao;

	@Autowired
	private EmployeeDao employeeDao;

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
