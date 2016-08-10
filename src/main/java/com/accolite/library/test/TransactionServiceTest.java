package com.accolite.library.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.accolite.library.dao.TransactionDao;
import com.accolite.library.model.Location;
import com.accolite.library.model.Topic;
import com.accolite.library.model.Transaction;
import com.accolite.library.service.TransactionService;

public class TransactionServiceTest {

	@Autowired
	TransactionService transactionservice;
	
	@Autowired
	Transaction transaction;
	
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DriverManagerDataSource dataSource;
	
	
	public TransactionService getTransactionservice() {
		return transactionservice;
	}

	public void setTransactionservice(TransactionService transactionservice) {
		this.transactionservice = transactionservice;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void summaryByTopicTest(){
		ArrayList<Topic> a = new ArrayList<Topic>();
		ArrayList<Topic> a1 = transactionservice.summaryByTopic();
		//assertThat(a).isEqualToComparingFieldByField(transactionservice.summaryByTopic());
		//assertEquals(a, transactionservice.summaryByTopic());
		//mockito
		assertNotEquals(a.size(),a1.size());
		
	}
	
	@Test
	public void summaryByLocationTest(){
		ArrayList<Location> a = new ArrayList<Location>();
		TransactionService transactionservice = new TransactionService();
		//ArrayList<Location> a1 ;
		
		assertNotEquals(0,transactionservice.summaryByLocation().size());
	}
	
	@Test
	public void approveRequestTest(){
		TransactionService transactionservice = new TransactionService();
		String str = transactionservice.approveRequest(0, 0);
		assertEquals("unable to process request", str);
	}
	
	@Test
	public void getAllProcessingTransactionsTest(){
		ArrayList<Transaction> a = new ArrayList<Transaction>();
		TransactionService transactionservice = new TransactionService();
		ArrayList<Transaction> a1 = transactionservice.getAllProcessingTransactions();
		assertNotEquals(a.size(), a1.size());
	}

	@Test
	public void borrowedListTest(){
		ArrayList<Transaction> a = new ArrayList<Transaction>();
		ArrayList<Transaction> a1 = transactionservice.borrowedList("kk.com");
		assertNotEquals(a.size(), a1.size());
	}
	
	@Test
	public void onDemandBooksTest(){
		//ArrayList<String> a = new ArrayList<String>();
		System.out.println("transactionservice.onDemandBooks().size(): "+transactionservice.onDemandBooks().size());
		assertNotEquals(0, transactionservice.onDemandBooks().size());
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	

}
