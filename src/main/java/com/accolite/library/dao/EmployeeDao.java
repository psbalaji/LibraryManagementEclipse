/****************************************************************************

* Copyright (c) 2016 by Accolite.com. All rights reserved

*

* Created date :: Jul 31, 2016

*

*  @author :: Balaji P

* ***************************************************************************

*/
package com.accolite.library.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
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

	public boolean createUser(final Employee employee) {

		// SQL query to inset values into the LibraryUser table which contains
		// all the employee details
		String sql = "insert into LibraryUser(emailId, employeeId, googleId, firstName, middleName, lastName, ManagerId, MobileNo, cityId, password) values(?,?,?,?,?,?,?,?,?,?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setString(1, employee.getEmailId());
				ps.setString(2, employee.getEmployeeId());
				ps.setString(3, employee.getGoogleId());
				ps.setString(4, employee.getFirstName());
				ps.setString(5, employee.getMiddleName());
				ps.setString(6, employee.getLastName());
				ps.setString(7, employee.getManagerId());
				ps.setString(8, employee.getMobileNo());
				ps.setString(9, employee.getCityId());
				ps.setString(10, employee.getPassword());

				return ps.execute();
			}

		}); // End of return statement
	}

	/*
	 * Updates an employee details
	 */
	public int updateUser(Employee employee) {
		String sql = "update LibraryUser set employeeid = ?, googleId = ?, firstName = ?, middleName = ?, lastName = ?, ManagerId = ?, MobileNo = ?, cityId = ?, password = ?  where emailId = ?";

		return jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getGoogleId(), employee.getFirstName(),
				employee.getMiddleName(), employee.getLastName(), employee.getManagerId(), employee.getMobileNo(),
				employee.getCityId(), employee.getPassword(), employee.getEmailId());
	}
	
	
	/*
	 * Gives the details of the Employee by taking emailId as argument.
	 */
	public Employee getEmployee(String emailId){
		String sql = "select * from LibraryUser where emailId = " + emailId;
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<Employee>(){

			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
			 */
			public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
				Employee employee = new Employee();
				while(rs.next()){
					employee.setEmailId(rs.getString("emailId"));
					employee.setEmployeeId(rs.getString("employeeId"));
					employee.setGoogleId(rs.getString("googleId"));
					employee.setFirstName(rs.getString("firstName"));
					employee.setMiddleName(rs.getString("middleName"));
					employee.setLastName(rs.getString("lastName"));
					employee.setManagerId(rs.getString("ManagerId"));
					employee.setMobileNo(rs.getString("MobileNo"));
					employee.setCityId(rs.getString("cityId"));
					employee.setPassword(rs.getString("password"));
				}
				return employee;
			}
			
		});
	}
	
	
	/*
	 * Adding an Employee to the BlackList
	 */
	public boolean blackListEmployee(final String emailId){
		String sql = "insert into blackListEmployee (emailId) values(?)";
		
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, emailId);
				return ps.execute();
			}
			
		});
	}
	
	/*
	 * Removing an employee from BlackList 
	 */
	public int removeBlackListEmployee(final String emailId){
		String sql = "delete from blackListEmployee where emailId = ?";
		
		return jdbcTemplate.update(sql,emailId);
	}

	/*
	 * Function that returns the emailId if it is present else returns null String 
	 */
	public String isAdmin(String emailId){
		String sql = "select count(*) from admins where emailId = " + emailId;
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<String>(){

			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String emailId = null;
				
				while(rs.next()){
					emailId = rs.getString(emailId);
				}
				
				return emailId;
			}
			
		});
	}
	
	public boolean addAdmin(final String emailId){
		String duplicateEmailId = null;
		duplicateEmailId = isAdmin(emailId);
		
		/*
		 * Checking if the user is already present in the database
		 */
		if(duplicateEmailId.equalsIgnoreCase(emailId)){
			return true;
		}
		
		/*
		 * Adding the user to the admins database.
		 */
		else{
			String sql = "insert into admins (emailId) values(?)";
			
			return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, emailId);
					return ps.execute();
				}			
			});
		}
	}
	
	
	/*
	 * Removing an admin from the admins table.
	 */
	public int deleteAdmin(String emailId){
		String sql = "delete from admins where emailId = " + emailId;
		
		return jdbcTemplate.update(sql);
	}
	
}
