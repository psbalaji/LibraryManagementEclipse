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
import org.springframework.jdbc.core.PreparedStatementSetter;
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
	
	@Autowired
	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

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
		String sql = "insert into libraryUser(emailId, employeeId, googleId, firstName, middleName, lastName, ManagerId, MobileNo, cityId, password, roleId, blackListed) values(?,?,?,?,?,?,?,?,?,?,?,?)";

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
				if (employee.getCityId() > 0) {
					ps.setInt(9, employee.getCityId());
				} else {
					ps.setString(9, null);
				}
				ps.setString(10, employee.getPassword());
				ps.setInt(11, employee.getRoleId());
				ps.setInt(12, employee.getBlackListed());

				return ps.execute();
			}

		}); // End of return statement
	}

	/*
	 * Updates an employee details
	 */
	public int updateUser(Employee employee) {
		if (employee.getPassword() == null) {
			String sql = "update LibraryUser set employeeid = ?, googleId = ?, firstName = ?, middleName = ?, lastName = ?, ManagerId = ?, MobileNo = ?, cityId = ?  where emailId = ?";

			return jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getGoogleId(), employee.getFirstName(),
					employee.getMiddleName(), employee.getLastName(), employee.getManagerId(), employee.getMobileNo(),
					employee.getCityId(), employee.getEmailId());
		}
		else{
			String sql = "update LibraryUser set employeeid = ?, googleId = ?, firstName = ?, middleName = ?, lastName = ?, ManagerId = ?, MobileNo = ?, cityId = ?, password=?  where emailId = ?";

			return jdbcTemplate.update(sql, employee.getEmployeeId(), employee.getGoogleId(), employee.getFirstName(),
					employee.getMiddleName(), employee.getLastName(), employee.getManagerId(), employee.getMobileNo(),
					employee.getCityId(), employee.getPassword(),employee.getEmailId());
		}
	}

	/*
	 * Gives the details of the Employee by taking emailId as argument.
	 */
	public Employee getEmployee(final String emailId) {

		System.out.println(jdbcTemplate);

		String sql = "select * from LibraryUser where emailId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, emailId);

			}

		}, new ResultSetExtractor<Employee>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
				//Employee employee = null;
				while (rs.next()) {
					employee = new Employee();
					System.out.println("in rs next");
					employee.setEmailId(rs.getString("emailId"));
					employee.setEmployeeId(rs.getString("employeeId"));
					employee.setGoogleId(rs.getString("googleId"));
					employee.setFirstName(rs.getString("firstName"));
					employee.setMiddleName(rs.getString("middleName"));
					employee.setLastName(rs.getString("lastName"));
					employee.setManagerId(rs.getString("ManagerId"));
					employee.setMobileNo(rs.getString("MobileNo"));
					employee.setCityId(rs.getInt("cityId"));
					employee.setPassword(rs.getString("password"));
					employee.setRoleId(rs.getInt("roleId"));
					employee.setBlackListed(rs.getInt("blackListed"));
				}
				return employee;
			}

		});
	}

	/*
	 * Adding an Employee to the BlackList
	 */
	public int blackListEmployee(final String emailId) {
		System.out.println(emailId);
		String sql = "update libraryUser set blackListed = 1  where emailId = ? and roleId != 2";

		return jdbcTemplate.update(sql, emailId);
	}

	public boolean isBlackListedEmployee(final String emailId) {
		String sql = "select blackListed  from libraryUser where emailId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, emailId);

			}

		}, new ResultSetExtractor<Boolean>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				int isBlacklisted = 0;
				while (rs.next()) {
					isBlacklisted = rs.getInt(1);
				}

				if (isBlacklisted == 0) {
					return false;
				}

				else {
					return true;
				}
			}

		});
	}

	/*
	 * Removing an employee from BlackList
	 */
	public int removeBlackListEmployee(final String emailId) {
		String sql = "update libraryUser set blackListed = 0  where emailId = ?";
		return jdbcTemplate.update(sql, emailId);
	}

	/*
	 * Function that returns the emailId if it is present else returns null
	 * String
	 */
	public String isAdmin(final String emailId) {
		String sql = "use Library select roleId, emailId from libraryUser where emailId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, emailId);

			}

		}, new ResultSetExtractor<String>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String emailId = null;
				int roleId = 0;
				while (rs.next()) {
					roleId = rs.getInt(1);
					emailId = rs.getString("emailId");
				}

				if (roleId == 2) // id for admin
					return emailId;
				else
					return null;
			}

		});
	}

	public int addAdmin(final String emailId) {
		String sql = "update libraryUser set roleId = 2  where emailId = ?";

		return jdbcTemplate.update(sql, emailId);

	}

	/*
	 * Removing an admin from the admins table.
	 */
	public int deleteAdmin(String emailId) {
		String sql = "update libraryUser set roleId = 1  where emailId = ?";

		return jdbcTemplate.update(sql, emailId);
	}

	public String addCity(final String cityName, final String stateName, final String country) {
		// TODO Auto-generated method stub

		String sql = "insert into city (cityName, stateName, countryName) values (?,?,?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<String>() {

			public String doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, cityName);
				ps.setString(2, stateName);
				ps.setString(3, country);
				try {
					ps.execute();
					return "success";
				} catch (Exception e) {
					e.printStackTrace();
					return "failure";
				}

			}
		});
	}

}
