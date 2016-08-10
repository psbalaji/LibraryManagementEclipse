package com.accolite.library.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{
	
	private MyThreadClass myThread;// = new MyThreadClass();
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		/* if ((myThread == null) || (!myThread.isAlive())) {
	            myThread = new MyThreadClass();
	            myThread.start();
	        }*/
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			//System.out.println("kartik1");
			//myThread.setDaemon(true);
            //myThread.interrupt();
			myThread = new MyThreadClass();
            myThread.start();
        } catch (Exception ex) {
        }
    }
}
	
