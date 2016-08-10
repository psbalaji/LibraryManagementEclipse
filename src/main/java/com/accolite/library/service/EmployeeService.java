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
	
	@Autowired
	private Employee employee ;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	
	public Employee getEmployee(String emailId){
		
		employee = employeeDao.getEmployee(emailId);
		//System.out.println(employee.getEmailId());
		System.out.println(employee.toString());
		return employee;
		
	}
	
	public String addAdmin(String emailId){
		int flag = employeeDao.addAdmin(emailId);
		
		System.out.println(flag);
		
		if(flag >= 1 ){
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
		if(i > 0){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	public boolean blackListEmployee(String emailId){
		if(employeeDao.blackListEmployee(emailId) >= 1 ){
			return true;
		}
		else{
			return false;
		}
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

	public boolean updateUser(Employee employee) {
		if(employee.getPassword() != null){
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
				
				System.out.println(hashtext);
				employee.setPassword(hashtext);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		int i = employeeDao.updateUser(employee); 
		if(i > 0){
			return true;
		}
		else{
			return false;
		}
	}

	public String addCity(String cityName, String stateName, String country) {
		
		String message = employeeDao.addCity(cityName, stateName, country);
		
		return message;
	}
	
	
}
