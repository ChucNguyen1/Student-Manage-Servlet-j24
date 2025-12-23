package com.student.dao;

public interface UsersDAO {
	boolean updatePassword(int userID, String newPasswordHash);
	String getPasswordHash(int userID);
}
