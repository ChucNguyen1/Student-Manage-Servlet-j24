package com.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.dao.UsersDAO;
import com.student.utils.DBConnection;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public boolean updatePassword(int userID, String newPasswordHash) {
		String sql = "UPDATE Users SET password_hash = ? WHERE userID = ?";
		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, newPasswordHash);
			ps.setInt(2, userID);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getPasswordHash(int userID) {
		String sql = "SELECT password_hash FROM Users WHERE userID = ?";
		try (Connection conn = DBConnection.getNewConnection(); 
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userID);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("password_hash");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
