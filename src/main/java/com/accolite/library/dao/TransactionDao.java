package com.accolite.library.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.accolite.library.model.Topic;
import com.accolite.library.model.Transaction;
import com.sun.javafx.collections.MappingChange.Map;
import com.accolite.library.model.Location;
import com.accolite.library.model.Resource;
import com.accolite.library.model.Title;

@Repository
public class TransactionDao {
	/** The jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Transaction transaction;
	
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Autowired
	private Title title;

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
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

	public Boolean addRequest(final int titleId, final String emailId) {
		String sql = "insert into transactions (titleId, emailId) values(?,?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, titleId);
				ps.setString(2, emailId);
				ps.execute();
				return true;
			}

		});
	}

	public ArrayList<Topic> summaryByTopic() {

		System.out.println(jdbcTemplate);

		String sql = "select topic.topicId as topicId, topic.topicName as topicName, count(topic.topicName) as topicCount from topic join (select title.topicId as topicId from title join (select b.titleId as titleId from resource as b join transactions as t on t.resourceId = b.resourceId where t.status = 'approved' or t.status = 'return') as trans on trans.titleId = title.titleId ) as trans on trans.topicId = topic.topicId group by topic.topicId, topic.topicName";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Topic>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Topic> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Topic> t = new ArrayList();
				while (rs.next()) {
					Topic topic = new Topic();
					topic.setTopicId(rs.getInt(1));
					topic.setTopicName(rs.getString(2));
					topic.setCount(rs.getInt(3));
					t.add(topic);
				}
				return t;
			}

		});
	}

	public ArrayList<Location> summaryByLocation() {

		// System.out.println(jdbcTemplate);

		String sql = "select c.cityId, c.cityName,count(c.cityId) as locationCount from city as c join (select r.locationId as cityId from resource as r join transactions as t on r.resourceId = t.resourceId where t.status = 'approved' or t.status='return') as trans on trans.cityId = c.cityId group by c.cityName, c.cityId";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Location>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Location> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Location> arrayList = new ArrayList();
				while (rs.next()) {
					Location location = new Location();
					location.setLocationId(rs.getInt(1));
					location.setLocationName(rs.getString(2));
					location.setCount(rs.getInt(3));
					arrayList.add(location);
				}
				return arrayList;
			}

		});
	}

	public int approveRequest(int transactionId, int resourceId) {
		try {
			String sql = "update transactions set status = 'approved', issueDate = ?, resourceId = ? where transactionId = ?";
			Date date = new Date();
			java.sql.Date issueDate = new java.sql.Date(date.getTime());
			jdbcTemplate.update(sql, issueDate, resourceId, transactionId);
			sql = "update resource set allocated = 1 where resourceId = ?";
			jdbcTemplate.update(sql, resourceId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;

	}

	public int rejectRequest(int transactionId) {
		String sql = "update transactions set status = 'reject' where transactionId = ?";

		return jdbcTemplate.update(sql, transactionId);

	}

	public ArrayList<Transaction> getAllProcessingTransaction() {
		String sql = "use Library ; select * from transactions join title on transactions.titleId = title.titleId where status = 'processing'";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Transaction>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Transaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
				//Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					transaction.setTransactionId(rs.getInt("transactionId"));
					transaction.setResourceId(rs.getInt("resourceId"));
					transaction.setEmailId(rs.getString("emailId"));
					transaction.setIssueDate(rs.getDate("issueDate"));
					transaction.setReturnDate(rs.getDate("returnDate"));
					transaction.setRequestDate(rs.getDate("requestDate"));
					transaction.setStatus(rs.getString("status"));
					transaction.setTitleId(rs.getInt("titleId"));
					transaction.setTitleName(rs.getString("titleName"));
					arrayList.add(transaction);
				}
				return arrayList;
			}

		});

	}

	public int returnResource(int transactionId, int resourceId, String emailId) {
		Date date = new Date();
		java.sql.Date sqDate = new java.sql.Date(date.getTime());
		String sql = "update transactions set status = 'return', returnDate = ? where transactionId = ? and emailId = ?";
		jdbcTemplate.update(sql, sqDate,transactionId, emailId);
		sql = "update resource set allocated = 0 where resourceId = ?";
		jdbcTemplate.update(sql, resourceId);
		return 0;

	}

	public ArrayList<Transaction> borrowedList(final String emailId) {
		String sql = "use Library ; select * from transactions join title on transactions.titleId = title.titleId where status = 'approved' and emailId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, emailId);
			}

		}, new ResultSetExtractor<ArrayList<Transaction>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Transaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
				Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					System.out.println(rs.getInt("transactionId"));
					transaction.setTransactionId(rs.getInt("transactionId"));
					transaction.setResourceId(rs.getInt("resourceId"));
					transaction.setEmailId(rs.getString("emailId"));
					transaction.setIssueDate(rs.getDate("issueDate"));
					transaction.setReturnDate(rs.getDate("returnDate"));
					transaction.setRequestDate(rs.getDate("requestDate"));
					transaction.setStatus(rs.getString("status"));
					transaction.setTitleId(rs.getInt("titleId"));
					transaction.setTitleName(rs.getString("titleName"));
					System.out.println(transaction.getTransactionId());
					arrayList.add(transaction);
				}
				return arrayList;
			}

		});
	}

	public Transaction getTransactionDetails(final int transactionId) {
		String sql = "use Library ; select * from transactions where transactionId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, transactionId);
			}

		}, new ResultSetExtractor<Transaction>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public Transaction extractData(ResultSet rs) throws SQLException, DataAccessException {
				Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					transaction.setTransactionId(rs.getInt("transactionId"));
					transaction.setResourceId(rs.getInt("resourceId"));
					transaction.setEmailId(rs.getString("emailId"));
					transaction.setIssueDate(rs.getDate("issueDate"));
					transaction.setReturnDate(rs.getDate("returnDate"));
					transaction.setRequestDate(rs.getDate("requestDate"));
					transaction.setStatus(rs.getString("status"));
					transaction.setTitleId(rs.getInt("titleId"));
				}
				return transaction;
			}

		});
	}

	public int isBookAvailable(final int titleId) {
		String sql = "use Library ; select top(1) * from resource where titleId = ? and allocated = 0";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, titleId);
			}

		}, new ResultSetExtractor<Integer>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				int resourceId = -1;
				while (rs.next()) {
					resourceId = rs.getInt("resourceId");
				}
				return resourceId;
			}

		});

	}

	public ArrayList<Transaction> borrowListByTitle(String titleName) {
		final String escapeTitleName = titleName.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[",
				"![");

		String sql = "use library; select * from libraryUser as l, (select transactionId, title.titleId, title.titleName, emailId, issueDate, requestDate, returnDate, resourceId,status from title join transactions on title.titleId = transactions.titleId where title.titleName like ? and (transactions.status = 'return' or transactions.status='approved')) as t where l.emailId = t.emailId";
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, '%' + escapeTitleName + '%');

			}

		}, new ResultSetExtractor<ArrayList<Transaction>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Transaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
				Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					transaction = new Transaction();
					transaction.setEmailId(rs.getString("emailId"));
					transaction.setIssueDate(rs.getDate("issueDate"));
					transaction.setName(rs.getString("firstName"));
					transaction.setReturnDate(rs.getDate("returnDate"));
					transaction.setStatus(rs.getString("status"));
					transaction.setRequestDate(rs.getDate("requestDate"));
					transaction.setTransactionId(rs.getInt("transactionId"));
					transaction.setTitleName(rs.getString("titleName"));
					transaction.setResourceId(rs.getInt("resourceId"));
					arrayList.add(transaction);
				}
				return arrayList;
			}

		});

	}

	public ArrayList<Transaction> borrowListByLocation(String locationName) {
		final String escapeLocationName = locationName.replace("!", "!!").replace("%", "!%").replace("_", "!_")
				.replace("[", "![");

		String sql = "select transactionId ,trct.emailId as emailId, cityName, firstName, titleName, resourceId, status from libraryUser join (select transactionId, cityName, titleName, tr.resourceId, emailId, status from transactions as tr join (select titleName, resourceId, cityName from title as t join (select cityName, resourceId, titleId from city join resource on city.cityId = resource.locationId where city.cityName like ?) as c on c.titleId = t.titleId) as ct on ct.resourceId = tr.resourceId where tr.status = 'approved' or tr.status = 'return') as trct on libraryUser.emailId = trct.emailId";
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, '%' + escapeLocationName + '%');

			}

		}, new ResultSetExtractor<ArrayList<Transaction>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Transaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
				Transaction transaction = null;
				while (rs.next()) {
					transaction = new Transaction();
					transaction = new Transaction();
					transaction.setEmailId(rs.getString("emailId"));
					transaction.setName(rs.getString("firstName"));
					transaction.setStatus(rs.getString("status"));
					transaction.setTransactionId(rs.getInt("transactionId"));
					transaction.setTitleName(rs.getString("titleName"));
					transaction.setResourceId(rs.getInt("resourceId"));
					transaction.setLocationName(rs.getString("cityName"));
					arrayList.add(transaction);
				}
				return arrayList;
			}

		});
	}

	public ArrayList<Transaction> borrowListByDate(Date startDate, Date endDate) {

		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		return null;
	}

	public ArrayList<String> getTitleDemandCount() {
		String sql = "select count(title.titleId) as issueCount, title.titleId as titleId, title.titleName as titleName from transactions join title on transactions.titleId = title.titleId where requestDate between  DATEADD(day,-30,GETDATE()) and GETDATE() group by title.titleId,titleName order by issueCount desc";
		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<String>>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap<Title, Integer> counts = new HashMap<Title, Integer>();
				ArrayList<String> bod = new ArrayList<String>();
				while (rs.next()) {
					Title title = new Title();
					int titleCount = 0;
					title.setTitleName(rs.getString("titleName"));
					title.setTitleId(rs.getInt("titleId"));
					titleCount = rs.getInt("issueCount");
					bod.add(rs.getString("titleName"));
					counts.put(title, titleCount);
				}
				return bod;
			}

		});
	}

	public boolean didRequestEarlier(final int titleId, final String emailId) {
		System.out.println("in did request earlier");
		String sql = "select * from transactions where titleId = ? and emailId = ? and (status='processing' or status='approved')";
		
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, titleId);
				ps.setString(2, emailId);

			}

		},new ResultSetExtractor<Boolean>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				int count = 0;
				while(rs.next()){
					count += 1;
				}
				System.out.println(count);
				if(count > 0){
					return true;
				}
				else{
					return false;
				}
				
			}

		});
		
}

}
