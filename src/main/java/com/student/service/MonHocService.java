package com.student.service;

import java.util.List;

import com.student.model.MonHoc;

public interface MonHocService {

	List<MonHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(MonHoc mh);

	boolean update(MonHoc mh);

	String delete(int maMH);

	boolean updateStatus(int maMH, boolean status);

	List<MonHoc> findAll();
}