package com.student.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {

	private static HikariDataSource dataSource;
	private static String DB_URL;
	private static String USER;
	private static String PASS;

	static {
		try {
			// Load database config from .env
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

			// Configure HikariCP
			HikariConfig config = new HikariConfig();
			config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			config.setJdbcUrl(DB_URL);
			config.setUsername(USER);
			config.setPassword(PASS);
			
			// Pool settings
			config.setMaximumPoolSize(10); // Tối đa 10 connection
			config.setMinimumIdle(2); // Tối thiểu 2 connection sẵn sàng
			config.setConnectionTimeout(10000); // 10 giây timeout
			config.setIdleTimeout(300000); // 5 phút idle
			config.setMaxLifetime(600000); // 10 phút max lifetime
			
			// Performance settings
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			dataSource = new HikariDataSource(config);
			
			System.out.println("✓ HikariCP Connection Pool initialized successfully!");
			System.out.println("  - Pool size: 2-10 connections");
			System.out.println("  - Database: " + DB_URL);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi khởi tạo DBConnection: " + e.getMessage());
		}
	}

	public static Connection getNewConnection() throws SQLException {
		if (dataSource == null) {
			throw new SQLException("DataSource chưa được khởi tạo!");
		}
		
		long startTime = System.currentTimeMillis();
		Connection conn = dataSource.getConnection();
		long endTime = System.currentTimeMillis();
		
		System.out.println("DB Connection from pool in " + (endTime - startTime) + "ms");
		
		return conn;
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // Trả connection về pool, không đóng thật
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Shutdown pool khi application stop
	public static void shutdown() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
			System.out.println("✓ HikariCP Connection Pool closed");
		}
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
		} finally {
			shutdown();
		}
	}
}