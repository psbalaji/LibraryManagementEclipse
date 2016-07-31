/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Jul 31, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accolite.library.model.Employee;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeeDao.
 */
@Repository
public class EmployeeDao {

	/** The jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate
	 *            the new jdbc template
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean createUserGmail(final Employee employee) {

		String sql = "insert into LibraryUser(emailId, employeeId, googleId, firstName, middleName, lastName, ManagerId, MobileNo, cityId, password) values(?,?,?,?,?,?,?,?,?,?)";

		return 
	}

}
