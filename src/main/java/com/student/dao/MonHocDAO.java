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

	boolean isUsed(int maMH); 

	List<MonHoc> findAll();

	MonHoc findById(int maMH);
}