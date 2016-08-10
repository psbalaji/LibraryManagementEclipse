package com.accolite.library.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.accolite.library.dao.AuthorizationDao;
import com.accolite.library.model.Employee;
import com.accolite.library.service.AuthorizationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthorizationServiceTest {

	
	@Test
	public void setAuthorizationDaoTest() {
		AuthorizationService a = new AuthorizationService();
		AuthorizationDao authorizationDao = new AuthorizationDao();
		a.setAuthorizationDao(authorizationDao);
		assertEquals(authorizationDao, a.getAuthorizationDao());
	}
	@Test
	
	public void setEmployeeDaoTest(){
		AuthorizationService a = new AuthorizationService();
		Employee employee = new Employee();
		a.setEmployee(employee);
		Employee newemp = a.getEmployee();
		assertEquals(newemp.getEmailId(),employee.getEmailId());
		assertEquals(newemp.getEmployeeId(),employee.getEmployeeId());
		assertEquals(newemp.getGoogleId(), employee.getGoogleId());
		assertEquals(newemp.getFirstName(),employee.getFirstName());
		assertEquals(newemp.getMiddleName(),employee.getMiddleName());
		assertEquals(newemp.getLastName(),employee.getLastName());
		assertEquals(newemp.getManagerId(),employee.getManagerId());
		assertEquals(newemp.getMobileNo(),employee.getMobileNo());
		assertEquals(newemp.getCityId(),employee.getCityId());
		assertEquals(newemp.getRoleId(),employee.getRoleId());
		assertEquals(newemp.getBlackListed(),employee.getBlackListed());
		assertEquals(newemp.getPassword(),employee.getPassword());	
	}
	
}
