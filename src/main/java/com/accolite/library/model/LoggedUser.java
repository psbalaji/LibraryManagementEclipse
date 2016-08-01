/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 1, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LoggedUser {
	private String userName;
	private String emailId;
	private boolean adminFlag;
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public boolean isAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	} 
}
