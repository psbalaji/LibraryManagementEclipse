package com.accolite.library.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.dao.RequestDao;

public class RequestService {

	@Autowired
	private RequestDao requestDao;
	@Autowired
	private EmployeeDao employeeDao;

	public String request(final int bookID, final String emailID) {
		String status = requestDao.request(bookID, emailID);

		if (!employeeDao.isBlackListedEmployee(emailID)) {
			if (status.equals("success")) {
				requestDao.addRequest(bookID, emailID);
				return "success";
			} else {
				return "failure";
			}
		}
		else{
			return "failure";
		}
	}

	public int deleteRequest(final int requestId) {
		return requestDao.deleteRequest(requestId);
	}

	public int rejectRequest(final int requestId) {
		return requestDao.rejectRequest(requestId);
	}
}
