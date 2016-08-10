package com.accolite.library.service;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accolite.library.dao.EmployeeDao;
import com.accolite.library.model.Employee;

import sun.util.logging.resources.logging;

@Service
public class NotificationServices {

	@Autowired
	private EmployeeDao employeeDao;
	
	private Employee employee = null;
	
	public void returnBookNtfc(Date returnDate, String titleName,String emailId)
	{
		employeeDao = new EmployeeDao();
		System.out.println("hello returnBookNtfc");
		//employee = employeeDao.getEmployee(emailId);
		//String firstName=employee.getFirstName();

			final String username = "saibalaji.polakampalli@accoliteindia.com";
			final String password = "Sheldonlc@9";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("saibalaji.polakampally@accoliteindia.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailId));
				message.setSubject("Book return");
				message.setText("\n"+"Return date of the book" + " : "+returnDate+"\n"+"Title of the book" + " : "+titleName);
				//"Name" + " : "+ firstName+
				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	public void suggestBookNtfc(String semailId, String titleName,String remailId)
	{
		employee=employeeDao.getEmployee(semailId);
		String firstName=employee.getFirstName();

			final String username = "saibalaji.polakampalli@accoliteindia.com";
			final String password = "Sheldonlc@9";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("saibalaji.polakampalli@accoliteindia.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(remailId));
				message.setSubject("Suggestion for a book");
				message.setText("Adviser email" + " : "+ semailId+"\n"+"Book advised" + " : "+titleName);

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}	
}
