package com.student.dao;

import java.util.List;

import com.student.model.Khoi;

public interface KhoiDAO {
	/**
	 * Lấy tổng số lượng Khối 
	 * 
	 * @param searchKey 
	 * @return Tổng số Khối
	 */
	int count(String searchKey);

	/**
	 * Lấy danh sách Khối 
	 * 
	 * @param searchKey  
	 * @param pageNumber 
	 * @param pageSize   
	 * @return Danh sách Khối
	 */
	List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	boolean insert(Khoi khoi);

	boolean delete(int maKhoi);

	boolean update(Khoi khoi);

	List<Khoi> getAll();

	List<Khoi> findAll();

}