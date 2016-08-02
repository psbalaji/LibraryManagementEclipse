package com.accolite.library.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.model.Employee;

import sun.util.logging.resources.logging;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	private Employee employee = null;
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	
	public Employee getEmployee(String emailId){
		
		employee = employeeDao.getEmployee(emailId);
		//System.out.println(employee.getEmailId());
		return employee;
		
	}
	
	public String addAdmin(String emailId){
		boolean flag = employeeDao.addAdmin(emailId);
		
		System.out.println(flag);
		
		if(flag){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	public boolean createUser(Employee employee){
		return employeeDao.createUser(employee);
	}
	
	public String deleteAdmin(String emailId){
		long i = employeeDao.deleteAdmin(emailId);
		if(i >= 0){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	public boolean blackListEmployee(String emailId){
		return employeeDao.blackListEmployee(emailId);
	}

	public boolean removeBlackListEmployee(String emailId) {
		
		int i =  employeeDao.removeBlackListEmployee(emailId);
		if(i > 0){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean createUserNormal(Employee employee) {
		String password = employee.getPassword();
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
			employee.setPassword(hashtext);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return employeeDao.createUser(employee);
	}
	
	
}
