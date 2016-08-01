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

import com.accolite.library.model.Book;

@Repository
public class BookDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Boolean InsertBook(final Book book){
		System.out.println(book.getBookId());
	  String sql = "use Library ; insert into dbo.book (topicId,allocated,locationId) values(?,?,?)";
      return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
      public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
      ps.setInt(2, book.getTitleId());
      ps.setInt(3, book.getAllocated());
      ps.setInt(4, book.getLocationId());
      return ps.execute();
     }

   });
  }

 public int RemoveBook(int bookId){
	
	  String sql = "delete from dbo.book where bookId =" + bookId + ";";
       return jdbcTemplate.update(sql);
     }

  public List<Book> getAllbooks(int LocationId) {

		  return jdbcTemplate.query(" use Library ; select * from dbo.book where locationId =" + LocationId , new ResultSetExtractor<List<Book>>() {

		   public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {

		    List<Book> list = new ArrayList<Book>();

		    while (rs.next()) {
		     Book book = new Book();
		     book.setBookId(rs.getInt(1));
		     book.setTitleId(rs.getInt(2));
		     book.setAllocated(rs.getInt(3));
		     book.setLocationId(rs.getInt(4));
		     
		     list.add(book);
		    }

		    return list;
		   }
		  });
	
	

}
}
