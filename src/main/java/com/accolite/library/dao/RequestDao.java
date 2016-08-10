package com.accolite.library.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.accolite.library.model.Resource;

@Repository
public class RequestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String request(final int bookID, final String emailID) {
		String sql = "use Library; select * from book where bookId = "+bookID+" AND allocated = 0";
		 return jdbcTemplate.query(sql , new ResultSetExtractor<String>() {

			   public String extractData(ResultSet rs) throws SQLException, DataAccessException {

			   int count = 0;
			    while (rs.next()) {
			     count++;
			     if(count==1){
			    	 break;
			     }
			    }
			    if(count==1){
			    	return "success";
			    }else {
			    	return "failure";
			    }
			   }
			  });
	}
	
	public boolean addRequest(final int bookId,final String emailId) {
		String sql = "Use Library; update book set allocated = 1 where bookId = "+bookId;
		jdbcTemplate.update(sql,bookId);
		String sql1 = "Use Library; insert into requests (bookId,emailId) values(?,?)";
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>(){

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, bookId);
				ps.setString(2, emailId);
				
				return ps.execute();
			}
			
		});
		
	}
	
	public int deleteRequest(final int requestId) {
		String sql = "USE Library; delete from dao.requests where requestId = "+requestId;
		return jdbcTemplate.update(sql,requestId);
	}
	
	public int rejectRequest(final int requestId){
		String sql = "USE Library; update requests set requestStatus = 1 where requestId ="+requestId;
		
		return jdbcTemplate.update(sql,requestId);
	}


}
