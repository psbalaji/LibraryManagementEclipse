package com.accolite.library.servlet;

import java.sql.Connection;
import java.util.Date;

import com.accolite.library.service.NotificationServices;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MyThreadClass extends Thread {

	public void run() {
		// TODO Auto-generated method stub
		
		
		while(true) 
		{
            try {
                
                //Implementation
                
        		PreparedStatement pstmt = null;
        		Connection conn = null;
        		Class.forName(Constants.SQL_SERVER_JDBC_DRIVER);
    			conn = DriverManager.getConnection(Constants.DB_URL,Constants.username,Constants.password);

        		try {
        			Date date = new Date();
        			String currentDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        			String sql = "select DATEADD(day,7,tr.issueDate) as returnDate,ti.titleName,tr.emailId, DATEDIFF(day,tr.issueDate,GETDATE()) from transactions as tr JOIN title as ti ON tr.titleId = ti.titleId where (tr.returnDate is null) and (tr.issueDate is not null) and (DATEDIFF(day,tr.issueDate,GETDATE()) > 7)";
        			//System.out.println("kartik");
        			pstmt = conn.prepareStatement(sql);
        			ResultSet rs = pstmt.executeQuery();

        			while (rs.next()) {
        				System.out.println("Inside rs.next");
        				Date returnDate = rs.getDate("returnDate");
        				String titleName = rs.getString("titleName");
        				String emailId = rs.getString("emailId");
        				//call notificationservice method
        				NotificationServices n = new NotificationServices();
        				n.returnBookNtfc(returnDate, titleName, emailId);
        			}
        			
        			
        			conn.close();
        		} catch (SQLException se) {
        			se.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            	System.out.println(" hello kartik");
            	Thread.sleep(86400*1000);
        		
                
            } catch (InterruptedException ie) {
            } catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}

}
