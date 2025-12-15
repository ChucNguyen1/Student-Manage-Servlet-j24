package com.student.dao;

import java.util.List;

import com.student.model.HocKy;

public interface HocKyDAO {

	/**
	 * Lấy danh sách học kỳ có phân trang và tìm kiếm. 
	 * 
	 * @param searchKey  
	 * @param pageNumber 
	 * @param pageSize  
	 * @return Danh sách HocKy
	 */
	List<HocKy> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(HocKy hk);

	boolean update(HocKy hk);

	boolean delete(int maHK);

	boolean updateStatus(int maHK, boolean status);

	HocKy findById(int maHK);

	boolean isUsed(int maHK);

	List<HocKy> findAll();

	List<HocKy> findByNamHoc(String maNH);
}