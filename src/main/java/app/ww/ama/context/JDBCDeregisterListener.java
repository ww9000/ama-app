package app.ww.ama.context;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JDBCDeregisterListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("Context closed: " + event.getServletContext().getContextPath());
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				System.out.println("Deregistered driver: " + driver.getClass().getName());
			} catch (SQLException e) {
				System.err.println("Error deregistering driver: " + driver.getClass().getName());
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
