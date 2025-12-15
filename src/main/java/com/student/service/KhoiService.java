package com.student.service;

import java.util.List;

import com.student.model.Khoi;

public interface KhoiService {

	int count(String searchKey);

	List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	boolean insert(Khoi khoi);

	boolean delete(int maKhoi);

	boolean update(Khoi khoi);

	List<Khoi> findAll();
}