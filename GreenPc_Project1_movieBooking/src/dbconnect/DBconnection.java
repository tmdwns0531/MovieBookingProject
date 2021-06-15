package dbconnect;

import java.sql.*;

public class DBconnection {

	private static DBconnection dc;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private String user = "green_movie";
	private String pwd = "green1234";
	private Connection conn;

	static public DBconnection getInstance() {
		if (dc == null) {
			dc = new DBconnection();
		}
		return dc;
	}

	public Connection getConnect() {

		try {
			Class.forName(driver);
			System.out.println("jdbc driver loading suc");
			conn = DriverManager.getConnection(url, user, pwd);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}

}
