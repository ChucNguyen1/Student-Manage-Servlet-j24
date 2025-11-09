//package com.student.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DBConnection {
//	private static final String URL = "jdbc:sqlserver://LAPTOP-VSRQFIKQ\\NGUYENCHUC:1433;databaseName=db_quanlyhocsinh;encrypt=true;trustServerCertificate=true;";
//	private static final String USER = "sa";
//	private static final String PASSWORD = "123456789";
//
//	public static Connection getConnection() {
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("✅ Kết nối SQL Server thành công!");
//		} catch (Exception e) {
//			System.out.println("❌ Lỗi kết nối SQL Server: " + e.getMessage());
//		}
//		return conn;
//	}
//}
package com.student.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = "jdbc:sqlserver://LAPTOP-VSRQFIKQ\\NGUYENCHUC:1433;databaseName=db_quanlyhocsinh;encrypt=true;trustServerCertificate=true;";
	private static final String USER = "sa";
	private static final String PASS = "123456789"; // Thay password của bạn vào đây
	// ===========================================

	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection == null) {
			synchronized (DBConnection.class) {
				if (connection == null) {
					try {
						Class.forName(DRIVER);
						connection = DriverManager.getConnection(DB_URL, USER, PASS);
						System.out.println("Database connection established successfully.");
					} catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
						System.out.println("Failed to establish database connection.");
						return null;
					}
				}
			}
		}
		return connection;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void loadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load JDBC driver", e);
		}
	}

	static {
		loadDriver();
	}

	public static Connection getNewConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public static void main(String[] args) {
		try (Connection conn = DBConnection.getNewConnection()) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("TEST: Connection Successful!");
				System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
				System.out.println("Version: " + conn.getMetaData().getDatabaseProductVersion());
			}
		} catch (SQLException e) {
			System.out.println("TEST: Connection Failed!");
			e.printStackTrace();
		}
	}
}