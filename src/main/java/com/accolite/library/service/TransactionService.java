package com.accolite.library.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.dao.RequestDao;
import com.accolite.library.dao.ResourceDao;
import com.accolite.library.dao.TransactionDao;
import com.accolite.library.model.Employee;
import com.accolite.library.model.Location;
import com.accolite.library.model.Resource;
import com.accolite.library.model.Title;
import com.accolite.library.model.Topic;
import com.accolite.library.model.Transaction;
import com.sun.javafx.collections.MappingChange.Map;

@Service
public class TransactionService {

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private ResourceDao resourceDao;

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Autowired
	private EmployeeDao employeeDao;

	
	private Transaction transaction = null;

	/*public void addRequest(int resourceId, String emailId) {

		if (!employeeDao.isBlackListedEmployee(emailId)) {
			boolean didRequestEarlier = transactionDao.didRequestEarlier(resourceId,emailId);
			if(!didRequestEarlier)	
				transactionDao.addRequest(resourceId, emailId);
		}
		
	}*/

	public ArrayList<Topic> summaryByTopic() {
		// System.out.println("emailId is: "+emailId);
		return transactionDao.summaryByTopic();
	}

	public ArrayList<Location> summaryByLocation() {
		// System.out.println("emailId is: "+emailId);
		return transactionDao.summaryByLocation();
	}

	public String requestBook(int titleId, String emailId) {
		boolean status;
		if (!employeeDao.isBlackListedEmployee(emailId)) {
			boolean didRequestEarlier = transactionDao.didRequestEarlier(titleId,emailId);
			System.out.println(didRequestEarlier);
			if(!didRequestEarlier)	{
				
				status = transactionDao.addRequest(titleId, emailId);
				if(status){
					return "success";
				}
				else{
					return "failure";
				}
			}
			else{
				return "failure";
			}
		}
		else{
			return "failure";
		}
	}

	public String approveRequest(int transactionId, int titleId) {

		Transaction transaction = transactionDao.getTransactionDetails(transactionId);
		if (transaction != null) {
			if (transaction.getTitleId() == titleId) {
				int bookId = transactionDao.isBookAvailable(titleId);
				if (bookId > 0) {
					int status = transactionDao.approveRequest(transactionId, bookId);
					if (status == -1) {
						return "unable to process request";
					} else {
						return "approval successful";
					}

				} else {
					return "Sorry!! No books of that title available";
				}
			} else {
				return "Failure: Titles didn't match";
			}
		} else {
			return "Invalid Transaction";
		}
	}

	public void rejectRequest(int transactionId) {
		transactionDao.rejectRequest(transactionId);

	}

	public ArrayList<Transaction> getAllProcessingTransactions() {

		return transactionDao.getAllProcessingTransaction();
	}

	public void returnResource(int transactionId, int resourceId, String emailId) {
		Transaction transaction = transactionDao.getTransactionDetails(transactionId);
		if (transaction.getResourceId() == resourceId) {
			transactionDao.returnResource(transactionId, resourceId, emailId);
		}
	}

	public ArrayList<Transaction> borrowedList(String emailId) {
		return transactionDao.borrowedList(emailId);
	}

	public ArrayList<Transaction> borrowListByTitle(String titleName) {
		return transactionDao.borrowListByTitle(titleName);
		
	}

	public ArrayList<Transaction> borrowListByLocation(String locationName) {
		
		return transactionDao.borrowListByLocation(locationName);
	}

	public ArrayList<Transaction> borrowListByDate(Date startDate, Date endDate) {
		return transactionDao.borrowListByDate(startDate, endDate);
	}

	public ArrayList<String> onDemandBooks() {
		HashMap<Title, Integer> counts = new HashMap<Title,Integer>();
		ArrayList<String> bod = new ArrayList<String>();
		bod = transactionDao.getTitleDemandCount();
		return bod;
	}

}
