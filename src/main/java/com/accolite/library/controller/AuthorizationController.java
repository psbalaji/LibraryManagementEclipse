/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 1, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accolite.library.model.Employee;
import com.accolite.library.service.AuthorizationService;
import com.accolite.library.service.EmployeeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizationController.
 */
@Controller
public class AuthorizationController {
	
	/** The authorization service. */
	@Autowired 
	private AuthorizationService authorizationService;
	
	/** The employee service. */
	@Autowired
	private EmployeeService employeeService;
	
	/** The employee. */
	@Autowired
	private Employee employee; 
	

	/**
	 * Gets the authorization service.
	 *
	 * @return the authorization service
	 */
	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	/**
	 * Sets the authorization service.
	 *
	 * @param authorizationService the new authorization service
	 */
	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	/**
	 * Gets the employee service.
	 *
	 * @return the employee service
	 */
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	/**
	 * Sets the employee service.
	 *
	 * @param employeeService the new employee service
	 */
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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
	
	/**
	 * Google log in.
	 *
	 * @param emailId the email id
	 * @param googleId the google id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the string
	 */
	@RequestMapping(value = "/GoogleLogin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String googleLogIn(@RequestParam("emailId") String emailId, @RequestParam("googleId") String googleId,@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, HttpServletRequest request, HttpServletResponse response){
		String message = authorizationService.googleLogin(emailId);
		if(message.equals("data")){
			employee.setEmailId(emailId);
			employee.setGoogleId(googleId);
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeService.createUser(employee);
			HttpSession session = request.getSession();
			session.setAttribute("user", emailId);
			session.setAttribute("isAdmin", false);
			try {
				response.sendRedirect(null); //redirect to normal user page
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		else if (message.equals("admin")) {
			HttpSession session = request.getSession();
			session.setAttribute("user", emailId);
			session.setAttribute("isAdmin", true);
			try {
				response.sendRedirect(null); //Redirect to admin page
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		else if (message.equals("user")) {
			HttpSession session = request.getSession();
			session.setAttribute("user", emailId);
			session.setAttribute("isAdmin", false);
			try {
				response.sendRedirect(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	@RequestMapping(value = "/GoogleRegister",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String googleRegister(@RequestParam("emailId") String emailId, @RequestParam("googleId") String googleId,@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, HttpServletRequest request, HttpServletResponse response){
		employee.setEmailId(emailId);
		employee.setGoogleId(googleId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		boolean status = employeeService.createUser(employee);
		if(status){
			try {
				response.sendRedirect(null); //redirect to login page
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		else
			return "failure";
	}
	
	@RequestMapping(value = "/Register",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String register(@RequestParam("emailId") String emailId, @RequestParam("employeeId") String empId,@RequestParam("googleId") String googleId,@RequestParam("firstName") String firstName, @RequestParam("middleName") String middleName,@RequestParam("lastName") String lastName, @RequestParam("managerId") String managerId, @RequestParam("mobileNo") String mobileNo, @RequestParam("cityId") String cityId, @RequestParam("password") String password){
		employee.setEmailId(emailId);
		employee.setEmployeeId(empId);
		employee.setGoogleId(googleId);
		employee.setFirstName(firstName);
		employee.setMiddleName(middleName);
		employee.setLastName(lastName);
		employee.setManagerId(managerId);
		employee.setMobileNo(mobileNo);
		employee.setCityId(cityId);
		employee.setPassword(password);
		
		boolean status = employeeService.createUserNormal(employee);
		
		if(status){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	@RequestMapping(value = "/Login",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
		String status = authorizationService.login(emailId,password);
		if(status.equals("user")){
			try {
				HttpSession session = request.getSession();
				session.setAttribute("user", emailId);
				session.setAttribute("isAdmin", false);
				response.sendRedirect(null); //redirect to normal user page 
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return "success";
		}
		else if (status.equals("admin")) {
			try {
				HttpSession session = request.getSession();
				session.setAttribute("user", emailId);
				session.setAttribute("isAdmin", true);
				response.sendRedirect(null); //redirect to admin page
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
		else{
			return "Invalid Credentials";
		}
	}

	@RequestMapping(value = "/Logout",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String logout(@RequestParam("emailId") String emailId, HttpServletRequest request, HttpServletResponse response){
		HttpSession session  =  request.getSession();
		String sessionEmailId =  (String) session .getAttribute("emailId");
		if(sessionEmailId != null){
			session.invalidate();
			return "success";	
		}
		else{
			return "Login First";
		}
	}
	
}
