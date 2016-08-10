/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 1, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.accolite.library.model.Employee;
import com.accolite.library.model.UserMessage;
//import com.accolite.library.model.LoggedUser;
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
	private UserMessage userMessage;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private Employee employee;

	/**
	 * Gets the employee.
	 *
	 * @param emailId
	 *            the email id
	 * @return the employee
	 */
	@RequestMapping(value = "/Employee", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Employee getEmployee(@RequestBody Employee employee) {
		String emailId = employee.getEmailId();
		System.out.println("emailId is: " + emailId);
		return employeeService.getEmployee(emailId);
	}

	@RequestMapping(value = "/updateAccount", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateUser(@RequestParam("emailId") String emailId, @RequestParam("employeeId") String empId,
			@RequestParam("firstName") String firstName, @RequestParam("middleName") String middleName,
			@RequestParam("lastName") String lastName, @RequestParam("managerId") String managerId,
			@RequestParam("mobileNo") String mobileNo, @RequestParam("cityId") int cityId,
			@RequestParam("password") String password, HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println(password);
		String sEmailId = (String) session.getAttribute("user");
		if (sEmailId.equals(emailId)) {
			employee.setEmailId(emailId);
			employee.setEmployeeId(empId);
			employee.setFirstName(firstName);
			employee.setMiddleName(middleName);
			employee.setLastName(lastName);
			employee.setManagerId(managerId);
			employee.setMobileNo(mobileNo);
			employee.setCityId(cityId);
			employee.setPassword(password);

			boolean status = employeeService.updateUser(employee);
			return "success";
		}
		return "Failure";
	}

	@RequestMapping(value = "/getEmployeeDetails", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Employee getEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String emailId = (String) session.getAttribute("user");
		System.out.println("emailId is: " + emailId);
		return employeeService.getEmployee(emailId);
	}

	@RequestMapping(value = "/Admin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public UserMessage addAdmin(@RequestParam("emailId") String emailId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean adminFlag = (Boolean) session.getAttribute("isAdmin");
		System.out.println(emailId);

		if (adminFlag) {
			String hstatus = employeeService.addAdmin(emailId);
			System.out.println(hstatus);
			userMessage.setMessage(hstatus);
			return userMessage;
		} else {
			userMessage.setMessage("you donot have permissions");
			return userMessage;
		}
	}

	@RequestMapping(value = "/Admin", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage deleteAdmin(@RequestParam("emailId") String emailId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean adminFlag = (Boolean) session.getAttribute("isAdmin");
		String user = (String) session.getAttribute("user");
		if (adminFlag) {
			if (!user.equalsIgnoreCase(emailId)) {
				String status = employeeService.deleteAdmin(emailId);
				userMessage.setMessage(status);
				return userMessage;
			}
			else{
				String status = "Cannot remove admin";
				userMessage.setMessage(status);
				return userMessage;
			}
			
		} else {
			userMessage.setMessage("you donot have permissions");
			return userMessage;
		}
	}

	@RequestMapping(value = "/blacklist", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage blackList(@RequestParam("emailId") String emailId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean adminFlag = (Boolean) session.getAttribute("isAdmin");
		System.out.println("in blacklist employee function");
		if (adminFlag) {
			boolean flag = employeeService.blackListEmployee(emailId);
			System.out.println(flag);
			if (flag) {
				userMessage.setMessage("success");
				return userMessage;
			} else {
				userMessage.setMessage("failure to add");
				return userMessage;
			}
		} else {
			userMessage.setMessage("you donot have permissions");
			return userMessage;
		}
	}

	@RequestMapping(value = "/blacklist", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public UserMessage removeBlackList(@RequestParam("emailId") String emailId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean adminFlag = (Boolean) (session.getAttribute("isAdmin"));
		if (adminFlag) {
			boolean flag = employeeService.removeBlackListEmployee(emailId);
			if (flag) {
				userMessage.setMessage("success");
				return userMessage;
			} else {
				userMessage.setMessage("failure to add");
				return userMessage;
			}
		} else {
			userMessage.setMessage("you donot have permissions");
			return userMessage;
		}
	}

	@RequestMapping(value = "/addCity", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage addCity(@RequestParam("cityName") String cityName, @RequestParam("stateName") String stateName,
			@RequestParam("country") String country, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean adminFlag = (Boolean) session.getAttribute("isAdmin");
		if (adminFlag) {
			cityName = cityName.toUpperCase();
			stateName = stateName.toUpperCase();
			country = country.toUpperCase();

			String message = employeeService.addCity(cityName, stateName, country);
			userMessage.setMessage(message);
			return userMessage;
		} else {
			String message = "no previleges";
			userMessage.setMessage(message);
			return userMessage;
		}
	}

}
