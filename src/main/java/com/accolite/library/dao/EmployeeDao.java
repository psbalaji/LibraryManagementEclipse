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

	public int updateUser(Employee employee) {
		String sql = "update LibraryUser set employeeid = ?, googleId = ?, firstName = ?, middleName = ?, lastName = ?, ManagerId = ?, MobileNo = ?, cityId = ?, password = ?  where emailId = ?";

		return jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getGoogleId(), employee.getFirstName(),
				employee.getMiddleName(), employee.getLastName(), employee.getManagerId(), employee.getMobileNo(),
				employee.getCityId(), employee.getPassword(), employee.getEmailId());
	}
	
	public Employee getEmployee(String emailId){
		String sql = "select * from LibraryUser where emailId = " + emailId;
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<Employee>(){

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
	
	public boolean blackListEmployee(){
		
	}

}
