package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection CreateConnection() {
	
			Connection conn = null;
			String url = "jdbc:mysql://localhost:3306/webtoiec";
			String username = "root";
			String password = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url,username,password);
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			 catch (SQLException e) {
				
				e.printStackTrace();
			}
			return conn;
		
		
	}

}
