package com.student.service;

import java.util.List;

import com.student.model.Khoi;

public interface KhoiService {
	/**
	 * Lấy tất cả các Khối.
	 * 
	 * @return Danh sách các đối tượng Khoi
	 */
	int count(String searchKey);

	List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	boolean insert(Khoi khoi);

	boolean delete(int maKhoi);

	boolean update(Khoi khoi);
}