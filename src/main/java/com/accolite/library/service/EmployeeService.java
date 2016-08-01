package com.accolite.library.service;

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
	
	public String deleteAdmin(String emailId){
		long i = employeeDao.deleteAdmin(emailId);
		if(i >= 0){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	
}
