package Helper;

import java.sql.*;

public class DBConnection {
	
	Connection conn = null;
	
	public DBConnection() {}
	
	public Connection connDb() {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yurt_camasirhanesi","root","1234");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	

}
