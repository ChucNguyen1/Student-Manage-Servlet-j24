package com.student.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	// Khai báo các biến cấu hình
	private static String DB_URL;
	private static String USER;
	private static String PASS;
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// Khối static: Chạy 1 lần duy nhất khi ứng dụng khởi động
	static {
		try {
			// 1. Nạp Driver
			Class.forName(DRIVER);

			// 2. Đọc file .env từ thư mục resources
			// getClassLoader().getResourceAsStream sẽ tìm file trong thư mục đã build
			// (WEB-INF/classes)
			try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(".env")) {
				if (input == null) {
					System.out.println("❌ LỖI: Không tìm thấy file .env trong src/main/resources!");
					throw new RuntimeException("File .env not found");
				}

				Properties prop = new Properties();
				prop.load(input);

				// 3. Lấy giá trị từ file
				DB_URL = prop.getProperty("DB_URL");
				USER = prop.getProperty("DB_USERNAME");
				PASS = prop.getProperty("DB_PASSWORD");

				// Debug (Xóa đi khi chạy thật để bảo mật)
				System.out.println("✅ Đã nạp cấu hình Database. User: " + USER);
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

	// Hàm đóng kết nối (Helper)
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