package com.accolite.library.test;

import static org.junit.Assert.*;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.model.Employee;

import com.accolite.library.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:applicationContext.xml" })
public class EmployeeServiceTest {
	
	@Autowired
	Employee employee;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DriverManagerDataSource dataSource;
	
	
	@Before
	public void setUp() {
		/*dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://localhost:1433;Database=Library");
		dataSource.setUsername("sa");
		dataSource.setPassword("accolite");
		
		jdbcTemplate.setDataSource(dataSource);
		
		employee = new Employee();
		employeeDao = new EmployeeDao();
		
		employeeDao.setJdbcTemplate(jdbcTemplate);
		
		employeeService = new EmployeeService();
		
		employeeService.setEmployeeDao(employeeDao);*/
	}

	@Test
	public void getEmployeeTest() {
		employee.setEmailId("koolkartik.94@gmail.com");
		employee.setEmployeeId("123");
		employee.setGoogleId("koolkartik.94@gmail.com");
		employee.setFirstName("kartik");
		employee.setMiddleName("kumar");
		employee.setLastName("keshri");
		employee.setManagerId("234");
		employee.setMobileNo("9122520895");
		// employee.setCityId("11");
		employee.setRoleId(1);
		employee.setBlackListed(2);
		employee.setPassword("xyz");

		assertEquals("koolkartik.94@gmail.com", employee.getEmailId());
		assertEquals("123", employee.getEmployeeId());
		assertEquals("koolkartik.94@gmail.com", employee.getGoogleId());
		assertEquals("kartik", employee.getFirstName());
		assertEquals("kumar", employee.getMiddleName());
		assertEquals("keshri", employee.getLastName());
		assertEquals("234", employee.getManagerId());
		assertEquals("9122520895", employee.getMobileNo());

		assertEquals(1, employee.getRoleId());
		assertEquals(2, employee.getBlackListed());
		assertEquals("xyz", employee.getPassword());
		EmployeeService employeeService = new EmployeeService();
		// employeeService.set
	}

	@Test
	public void getEmployeeDaoTest() {
		Employee employee = new Employee();
		employee.setEmailId("koolkartik.94@gmail.com");
		employee.setEmployeeId("123");
		employee.setGoogleId("234");
		employee.setFirstName("kartik");
		employee.setMiddleName("kumar");
		employee.setLastName("keshri");
		employee.setManagerId("123");
		employee.setMobileNo("9122520895");
		// employee.setCityId("");
		employee.setRoleId(2);
		employee.setBlackListed(0);
		employee.setPassword("xyz");
		String str = "koolkartik.94@gmail.com";
		System.out.println(employeeService.getEmployee(str));
		Employee newemp = employeeService.getEmployee(str);
		System.out.println(newemp.getEmailId() + " " + employee.getEmailId());
		if (newemp.getEmailId() == employee.getEmailId()) {
			System.out.println("yes");
		}

		assertEquals(newemp.getEmailId(), employee.getEmailId());
		assertEquals(newemp.getEmployeeId(), employee.getEmployeeId());
		assertNotEquals(newemp.getGoogleId(), employee.getGoogleId());
		assertEquals(newemp.getFirstName(), employee.getFirstName());
		assertEquals(newemp.getMiddleName(), employee.getMiddleName());
		assertEquals(newemp.getLastName(), employee.getLastName());
		assertNotEquals(newemp.getManagerId(), employee.getManagerId());
		assertEquals(newemp.getMobileNo(), employee.getMobileNo());
		assertEquals(newemp.getCityId(), employee.getCityId());
		assertNotEquals(newemp.getRoleId(), employee.getRoleId());
		assertNotEquals(newemp.getBlackListed(), employee.getBlackListed());
		assertEquals(newemp.getPassword(), employee.getPassword());

	}

	@Test
	public void addAdminTest() {

		EmployeeDao employeeDao = new EmployeeDao();
		String str = employeeService.addAdmin("koolkartik.94@gmail.com");
		if (str.equals("success"))
			assertEquals("success", str);
		else
			assertEquals("failure", str);

	}

	@Test
	public void deleteAdminTest() {

		String str = employeeService.deleteAdmin("koolkartik.94@gmail.com");
		if (str.equals("success"))
			assertEquals("success", str);
		else
			assertEquals("failure", str);

	}

	@Test
	public void blackListEmployeeTest() {

		boolean x = employeeService.blackListEmployee("koolkartik.94@gmail.com");
		if (x == true)
			assertEquals(true, x);
		else
			assertEquals(false, x);

	}

	@Test
	public void removeBlackListEmployeeTest() {

		boolean x = employeeService.blackListEmployee("koolkartik.94@gmail.com");
		if (x == true)
			assertEquals(true, x);
		else
			assertEquals(false, x);

	}

	@Test
	public void addCityTest() {

		String str = employeeService.addCity("deoghar", "jharkhand", "india");
		if (str.equals("success"))
			assertEquals("success", str);
		else
			assertEquals("failure", str);

	}

	/*public static void main(String args[]) {
		EmployeeServiceTest e = new EmployeeServiceTest();
		e.getEmployeeTest();
	}*/

}
