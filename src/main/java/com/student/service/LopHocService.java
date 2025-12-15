package com.student.service;

import java.util.List;

import com.student.model.LopHoc;

public interface LopHocService {
	List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	List<LopHoc> findAll(); 

	int count(String searchKey);

	boolean insert(LopHoc lh);

	boolean update(LopHoc lh);

	String delete(int maLop); 

	boolean updateStatus(int maLop, boolean status);

	List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi);

	LopHoc findById(int maLop);
}