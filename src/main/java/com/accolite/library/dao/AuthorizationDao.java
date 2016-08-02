/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 2, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizationDao.
 */
@Repository
public class AuthorizationDao {

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
	 * @param jdbcTemplate the new jdbc template
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
