package com.accolite.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accolite.library.model.Employee;
import com.accolite.library.service.RequestService;

public class RequestController {

	@Autowired
	private RequestService requestService;
	@RequestMapping(value = "/Request",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public String request(@RequestParam("emailId") String emailId,@RequestParam("bookId") int bookId){
		//System.out.println(request);
		//System.out.println("emailId is: "+emailId);
		return requestService.request(bookId,emailId);
	}
	
	@RequestMapping(value = "/deleteRequest",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public int deleteRequest(@RequestParam("requestId") int requestId){
		//System.out.println(request);
		//System.out.println("emailId is: "+emailId);
		return requestService.deleteRequest(requestId);
	}
	
	@RequestMapping(value = "/rejectRequest",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public int rejectRequest(@RequestParam("requestId") int requestId){
		//System.out.println(request);
		//System.out.println("emailId is: "+emailId);
		return requestService.rejectRequest(requestId);
	}
	
}
