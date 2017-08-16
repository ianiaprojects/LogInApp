package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	/**
	 * Don't forget to change database url, username and password... I guess it won't work otherwise.
	 */
	private static final String database_url = "jdbc:mysql://localhost:3306/database?autoReconnect=true&useSSL=false";
	private static final String database_username = "root";
	private static final String database_password = "javasql#19083";

	private static Connection conn = null;
	private static DBUtil instance = null;
	
	private DBUtil() {
		
		connectToDatabase();
		
	}

	public static Connection getConnection() {
		
		if (instance == null) {
			instance = new DBUtil();
		}
		return conn;
		
	}

	public static void connectToDatabase() {

		try {

			conn = DriverManager.getConnection(database_url, database_username, database_password);

		} catch (SQLException e) {
			
			e.printStackTrace();
	
		} finally {
			
			try {
				
				if (!conn.isClosed()) {
					//System.out.println("JDBC successfully connected to server!");
				}
			} catch (SQLException e) {
		
				e.printStackTrace();
			
			}
		}//try catch finally
		
	}//connectToDatabase
	
	public static void displaySQLExceptions(SQLException exception) {
		
		while (exception != null) {
		
			System.out.println("Message: " + exception.getMessage());

			Throwable cause = exception.getCause();
			while (cause != null) {
				
				System.out.println("Cause:" + cause);
				cause = cause.getCause();
			
			}
			exception = exception.getNextException();
		}
		
	}//displaySQLException
	
}
