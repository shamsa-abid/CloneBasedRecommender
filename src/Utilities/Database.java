package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Database uniqueInstance;
	 
	// other useful instance variables here
 
	private Database() {}
 
	public static Database getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Database();
		}
		return uniqueInstance;
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		String url = Constants.DATABASE_URL;			
		Connection conn = DriverManager.getConnection(url, "", "");
		return conn;	
		
	}

}
