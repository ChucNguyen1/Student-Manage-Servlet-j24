package com.student.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	static {
		try {
			Class.forName(DRIVER);


			try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(".env")) {
				if (input == null) {
					System.out.println("LỖI: Không tìm thấy file .env trong src/main/resources!");
					throw new RuntimeException("File .env not found");
				}

				Properties prop = new Properties();
				prop.load(input);
				DB_URL = prop.getProperty("DB_URL");
				USER = prop.getProperty("DB_USERNAME");
				PASS = prop.getProperty("DB_PASSWORD");

				System.out.println("Đã nạp cấu hình Database. User: " + USER);
			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi khởi tạo DBConnection: " + e.getMessage());
		}
	}

	public static Connection getNewConnection() throws SQLException {
		if (DB_URL == null || USER == null || PASS == null) {
			throw new SQLException("Thiếu thông tin cấu hình Database! Kiểm tra file .env");
		}
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	// Main test
	public static void main(String[] args) {
		try (Connection conn = getNewConnection()) {
			if (conn != null) {
				System.out.println("TEST KẾT NỐI THÀNH CÔNG!");
				System.out.println("Database: " + conn.getMetaData().getDatabaseProductName());
			}
		} catch (SQLException e) {
			System.out.println("TEST KẾT NỐI THẤT BẠI!");
			e.printStackTrace();
		}
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
}