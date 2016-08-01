package com.accolite.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accolite.library.model.Employee;
import com.accolite.library.service.EmployeeService;

@Controller
public class EmployeeController {

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired 
	private Employee employee;
	
	@RequestMapping(value = "/Employee",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public Employee getEmployee(@RequestParam("emailId") String emailId){
		System.out.println("emailId is: "+emailId);
		return employeeService.getEmployee(emailId);
	}
	
	@RequestMapping(value = "/Admin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String addAdmin(@RequestParam("emailId") String emailId ){
		System.out.println(emailId);
		String status = employeeService.addAdmin(emailId);
		return status;
	}
	
	@RequestMapping(value = "/Admin",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public String deleteAdmin(@RequestParam("emailId") String emailId){
		String status = employeeService.deleteAdmin(emailId);
		return status;
	}
}
