/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Aug 2, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.accolite.library.model.City;

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

	public ArrayList<City> getCity() {
		
		String sql = "select cityId, cityName from city";
		
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

			}

		}, new ResultSetExtractor<ArrayList<City>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<City> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<City> cities = new ArrayList<City> ();
				City city = null;
				while(rs.next()){
					city = new City();
					city.setCityId(rs.getInt("cityId"));
					city.setCityName(rs.getString("cityName"));
					cities.add(city);
				}
				return cities;
			}

		});
		
		
		
	}
	
	
}
