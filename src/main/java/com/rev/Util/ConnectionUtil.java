package com.rev.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

	private static Connection c = null;
//	private static final Logger l = LogManager.getLogger(ConnectionUtil.class);
	
	private ConnectionUtil() {}
	
	public static Connection getConnection() {
		if(c != null) {
			return c;
		}
		
		InputStream i = null;
		
		try {
			Properties p = new Properties();
			i = new FileInputStream("src/main/resources/connection.properties");
			p.load(i);
			
			//
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String end = p.getProperty("jdbc.url");
			String username = p.getProperty("jdbc.username");
			String password = p.getProperty("jdbc.password");
			
			c = DriverManager.getConnection(end, username, password);
			return c;
			
		}catch(FileNotFoundException e){
			System.out.println("Error reading file internallyFile\nPlease try again");
//			l.catching(e);
		}catch(ClassNotFoundException e) {
			System.out.println("Error reading file internallyClass\nPlease try again");
//			l.catching(e);
		}catch(IOException e) {
			System.out.println("Error reading file internallyIO\nPlease try again");
//			l.catching(e);
		}catch(SQLException e) {
			System.out.println("Error reading file internallySQL\nPlease try again");
			System.out.println(e.getErrorCode());
//			l.catching(e);
		}
		finally {
			try {
				i.close();
			}catch(IOException e) {
//				l.error("Error with file" + e.getMessage());
			}
		}
		
		return null;
	}
}

