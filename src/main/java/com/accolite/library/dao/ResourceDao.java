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
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.accolite.library.model.*;

@Repository
public class ResourceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean isAllocated(final int resourceId) {
		String sql = "select allocated from resource where resourceId = ?";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, resourceId);

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
				int status = 0;
				while (rs.next()) {
					status = (rs.getInt(1)); 
				}
				if(status == 0)
					return false;
				else
					return true;
			}

		});
	}

	public Boolean InsertBook(final Resource resource) {
		System.out.println(resource.getResourceId());
		String sql = "use Library ; insert into dbo.resource (titleId,allocated,locationId, typeId) values(?,?,?,?)";
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, resource.getTitleId());
				ps.setInt(2, resource.getAllocated());
				ps.setInt(3, resource.getLocationId());
				ps.setInt(4, resource.getTypeId());
				ps.execute();
				return true;
			}

		});
	}

	public int removeResource(int resourceId) {

		String sql = "delete from dbo.resource where resourceId =" + resourceId + ";";
		return jdbcTemplate.update(sql);
	}

	public ArrayList<Resource> getAllBooksLocation(final String locationName) {

		final String escapeLocationName = locationName
			    .replace("!", "!!")
			    .replace("%", "!%")
			    .replace("_", "!_")
			    .replace("[", "![");
		String sql = "use Library ; select resourceId, t.titleId as titleId, titleName, allocated, cityName, locationId, typeId from title as t join (select resourceId, titleId, allocated, city.cityName, locationId, typeId from resource join city on resource.locationId = city.cityId where city.cityName like ? ESCAPE '!') as r on t.titleId = r.titleId";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, '%'+escapeLocationName+'%');

			}

		}, new ResultSetExtractor<ArrayList<Resource>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Resource> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Resource> arrayList = new ArrayList<Resource>();
				Resource resource = null;
				while (rs.next()) {
					resource = new Resource();
					resource.setResourceId(rs.getInt("resourceId"));
					resource.setTitleId(rs.getInt("titleId"));
					resource.setTitleName(rs.getString("titleName"));
					resource.setAllocated(rs.getInt("allocated"));
					resource.setCityName(rs.getString("cityName"));
					resource.setLocationId(rs.getInt("locationId"));
					resource.setTypeId(rs.getInt("typeId"));
					arrayList.add(resource);
					System.out.println(resource.toString());
				}
				
				
				
				return arrayList;
			}

		});

	}

	public ArrayList<Resource> getAllBooksTitle(String titleName) {

		final String escapeTitleName = titleName
			    .replace("!", "!!")
			    .replace("%", "!%")
			    .replace("_", "!_")
			    .replace("[", "![");
		String sql = "use Library ; Select resourceId , resource.titleId, titleName, allocated from resource join title on resource.titleId=title.titleId where title.titleName like ? ESCAPE '!'";

		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, '%'+escapeTitleName+'%');

			}

		}, new ResultSetExtractor<ArrayList<Resource>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Resource> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Resource> arrayList = new ArrayList<Resource>();
				Resource resource = null;
				while (rs.next()) {
					resource = new Resource();
					resource.setResourceId(rs.getInt(1));
					resource.setTitleId(rs.getInt(2));
					resource.setTitleName(rs.getString(3));
					resource.setAllocated(rs.getInt(4));
					arrayList.add(resource);
				}
				return arrayList;
			}

		});

	}

	public ArrayList<Resource> getAllBooksTopic(final String topicName) {

		final String escapeTopicName = topicName
			    .replace("!", "!!")
			    .replace("%", "!%")
			    .replace("_", "!_")
			    .replace("[", "![");
		
		String sql = "select r.resourceId, t.titleId, t.titleName, r.allocated from resource as r join (select t.titleId, t.titleName from title as t join topic on t.topicId = topic.topicId where topic.topicName like ? escape '!') as t on t.titleId = r.titleId";
		return jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, '%'+escapeTopicName+'%');

			}

		}, new ResultSetExtractor<ArrayList<Resource>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Resource> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Resource> arrayList = new ArrayList<Resource>();
				Resource resource = null;
				while (rs.next()) {
					resource = new Resource();
					resource.setResourceId(rs.getInt(1));
					resource.setTitleId(rs.getInt(2));
					resource.setTitleName(rs.getString(3));
					resource.setAllocated(rs.getInt(4));
					arrayList.add(resource);
				}
				return arrayList;
			}

		});

	}

	public Boolean insertTitle(final Title title) {

		String sql = "use Library ; insert into dbo.title (titleName,authorId,topicId) values(?,?,?)";
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, title.getTitleName());
				ps.setInt(2, title.getAuthorId());
				ps.setInt(3, title.getTopicId());
				return ps.execute();
			}

		});
	}

	public boolean insertAuthor(final Author author) {
		String sql = "use Library; insert into author (authorName) values(?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, author.getAuthorName());
				ps.execute();
				return true;
			}
		});
	}

	public ArrayList<Author> getAllAuthors() {
		String sql = "select * from author order by authorName asc";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Author>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Author> arrayList = new ArrayList<Author>();
				while (rs.next()) {
					Author author = new Author();
					author.setAuthorId(rs.getInt(1));
					author.setAuthorName(rs.getString(2));
					arrayList.add(author);
				}
				return arrayList;

			}

		});
	}

	public int updateReview(final Review review) {

		String sql = "use Library ; update dbo.review set review=" + review.getReview() + "rating ="
				+ review.getRating() + ";";
		return jdbcTemplate.update(sql);
	}

	public List<Integer> getAllReviewTitleId(String emailId) {

		return jdbcTemplate.query(" use Library ; Select titleId from dbo.review where emailId= " + emailId,
				new ResultSetExtractor<List<Integer>>() {
					public List<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<Integer> list = new ArrayList<Integer>();
						while (rs.next()) {
							list.add(rs.getInt(1));
						}
						return list;
					}
				});
	}

	public Boolean insertReview(final Review review) {

		String sql = "use Library ; insert into dbo.review (reviewId,titleId,emailId,rating,review) values(?,?,?,?,?)";
		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, review.getReviewId());
				ps.setInt(2, review.getTitleId());
				ps.setString(3, review.getEmail());
				ps.setInt(4, review.getRating());
				ps.setString(5, review.getReview());
				return ps.execute();
			}

		});
	}

	public Boolean insertResourceType(final ResourceType resourceType) {
		String sql = "use Library; insert into resourceType (resourceName) values(?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, resourceType.getResourceName());
				ps.execute();
				return true;
			}
		});
	}

	public ArrayList<ResourceType> getAllResourceTypes() {

		String sql = "select * from resourceType order by resourceName asc";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<ResourceType>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<ResourceType> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<ResourceType> arrayList = new ArrayList<ResourceType>();
				while (rs.next()) {
					ResourceType resourceType = new ResourceType();
					resourceType.setTypeId(rs.getInt(1));
					resourceType.setResourceName(rs.getString(2));
					arrayList.add(resourceType);
				}
				return arrayList;

			}

		});
	}

	public ArrayList<Title> getAllTitles() {
		String sql = "select * from title order by titleName asc";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Title>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Title> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Title> arrayList = new ArrayList<Title>();
				while (rs.next()) {
					Title title = new Title();
					title.setTitleId(rs.getInt(1));
					title.setTitleName(rs.getString(2));
					title.setAuthorId(rs.getInt(3));
					title.setTopicId(rs.getInt(4));
					arrayList.add(title);
				}
				return arrayList;

			}

		});

	}

	public Boolean insertTopic(final Topic topic) {
		String sql = "use Library; insert into topic (topicName) values(?)";

		return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, topic.getTopicName());
				ps.execute();
				return true;
			}
		});

	}

	public ArrayList<Topic> getAllTopics() {
		String sql = "select * from topic order by topicName asc";

		return jdbcTemplate.query(sql, new ResultSetExtractor<ArrayList<Topic>>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.springframework.jdbc.core.ResultSetExtractor#extractData(java
			 * .sql.ResultSet)
			 */
			public ArrayList<Topic> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ArrayList<Topic> arrayList = new ArrayList<Topic>();
				while (rs.next()) {
					Topic topic = new Topic();
					topic.setTopicId(rs.getInt(1));
					topic.setTopicName(rs.getString(2));
					arrayList.add(topic);
				}
				return arrayList;

			}

		});
	}
	


}
