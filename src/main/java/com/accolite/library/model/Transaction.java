package com.accolite.library.model;

import java.util.Date;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class Transaction.
 */
@Component
public class Transaction {
	
	/** The tansaction id. */
	private int transactionId ;
	
	/** The book id. */
	private int resourceId;
	
	private String name;
	
	private String locationName;
	
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", resourceId=" + resourceId + ", name=" + name
				+ ", locationName=" + locationName + ", titleName=" + titleName + ", titleId=" + titleId + ", emailId="
				+ emailId + ", issueDate=" + issueDate + ", returnDate=" + returnDate + ", requestDate=" + requestDate
				+ ", status=" + status + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	private String titleName;
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	private int titleId;
	
	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	/** The email id. */
	private String emailId;
	
	/** The issue date. */
	private Date issueDate;
	
	/** The return date. */
	private Date returnDate;
	
	private Date requestDate;
	
	private String status;

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the tansaction id.
	 *
	 * @return the tansaction id
	 */
	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the tansaction id.
	 *
	 * @param tansactionId the new tansaction id
	 */
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the issue date.
	 *
	 * @return the issue date
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * Sets the issue date.
	 *
	 * @param issueDate the new issue date
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * Gets the return date.
	 *
	 * @return the return date
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * Sets the return date.
	 *
	 * @param returnDate the new return date
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
}
