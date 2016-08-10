package com.accolite.library.controller;
import com.accolite.library.service.*;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accolite.library.model.Employee;
import com.accolite.library.model.Location;
import com.accolite.library.model.Topic;
import com.accolite.library.model.Transaction;
import com.accolite.library.model.UserMessage;
import com.accolite.library.service.EmployeeService;
import com.accolite.library.service.TransactionService;
@Controller
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private NotificationServices notificationServices;
	
	public TransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public NotificationServices getNotificationServices() {
		return notificationServices;
	}

	public void setNotificationServices(NotificationServices notificationServices) {
		this.notificationServices = notificationServices;
	}

	public UserMessage getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(UserMessage userMessage) {
		this.userMessage = userMessage;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Autowired
	private UserMessage userMessage;
	
	@Autowired 
	private Transaction transaction;
	/* to be called by admin for requestid*/
	

	@RequestMapping(value = "/requestBook",method=RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public UserMessage addRequest(@RequestParam("titleId") int titleId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String emailId = (String)session.getAttribute("user");
		System.out.println("email: "+emailId+" requested: "+titleId);
		if(emailId != null){
			String message = transactionService.requestBook(titleId, emailId);
			userMessage.setMessage(message);
		}
		else{
			userMessage.setMessage("failure");
		}
		return userMessage;
	}
	
	@RequestMapping(value = "/getAllProcessingTransactions",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Transaction> getAllProcessingTransactions( HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			return transactionService.getAllProcessingTransactions();
		}
		return null;
	}
	
	@RequestMapping(value = "/returnResource",method=RequestMethod.GET)
	@ResponseBody
	public void returnBook(@RequestParam("transactionId") int transactionId,@RequestParam("resourceId") int resourceId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		String emailId = (String)session.getAttribute("user");
		if(emailId != null){
			transactionService.returnResource(transactionId, resourceId, emailId);
		}
	}
	
	@RequestMapping(value = "/approveRequest",method=RequestMethod.GET)
	@ResponseBody
	public void approveTransaction(@RequestParam("transactionId") int transactionId, @RequestParam("titleId") int titleId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			transactionService.approveRequest(transactionId, titleId);
		}
	}
	
	@RequestMapping(value = "/borrowedList",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Transaction> borrowedList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String emailId = (String)session.getAttribute("user");
		if(emailId != null){
			return transactionService.borrowedList(emailId);
		}
		return null;
	}
	
	@RequestMapping(value = "/borrowedListByTitle",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Transaction> borrowListByTitle(@RequestParam("titleName") String titleName, HttpServletRequest request){
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			return transactionService.borrowListByTitle(titleName);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/borrowedListByLocation",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Transaction> borrowListByLocation(@RequestParam("locationName") String locationName, HttpServletRequest request){
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			return transactionService.borrowListByLocation(locationName);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/borrowedListByDate",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Transaction> borrowListByDate(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,HttpServletRequest request){
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			return transactionService.borrowListByDate(startDate, endDate);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/suggestBook",method=RequestMethod.GET)
	@ResponseBody
	public String suggestFriendBook(@RequestParam("titleName") String titleName, @RequestParam("fEmailId") String fEmailId , HttpServletRequest request){
		HttpSession session = request.getSession();
		String semailId = (String) session.getAttribute("user");
		System.out.println(semailId);
		notificationServices.suggestBookNtfc(semailId, titleName, fEmailId);
		return null;
	}
	
	
	
	@RequestMapping(value = "/rejectRequest",method=RequestMethod.GET)
	@ResponseBody
	public void rejectTransaction(@RequestParam("transactionId") int transactionId, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			transactionService.rejectRequest(transactionId);
		}
	}
	

	@RequestMapping(value = "/summaryByTopic",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ArrayList<Topic> summaryByTopic (){
		return transactionService.summaryByTopic();
	}

	@RequestMapping(value = "/summaryByLocation",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ArrayList<Location> summaryByLocation (){
		return transactionService.summaryByLocation();
	}
	
	@RequestMapping(value = "/onDemandBooks",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ArrayList<String> onDemandBooks(HttpServletRequest request){
		HttpSession session = request.getSession();
		if((Boolean)session.getAttribute("isAdmin")){
			return transactionService.onDemandBooks();
		}
		return null;
	}
}
