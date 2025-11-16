package com.student.dao;

import java.util.List;

import com.student.model.MonHoc;

public interface MonHocDAO {
	List<MonHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(MonHoc mh);

	boolean update(MonHoc mh);

	boolean delete(int maMH);

	boolean updateStatus(int maMH, boolean status);

	boolean isUsed(int maMH); // Kiểm tra ràng buộc dữ liệu

	// Thêm hàm này để dùng cho dropdown sau này (khi phân công giảng dạy)
	List<MonHoc> findAll();
}