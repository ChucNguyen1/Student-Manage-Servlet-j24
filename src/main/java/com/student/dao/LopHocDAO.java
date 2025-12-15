package com.student.dao;

import java.util.List;

import com.student.model.LopHoc;

public interface LopHocDAO {
	List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	List<LopHoc> findAll(); 

	int count(String searchKey);

	boolean insert(LopHoc lh);

	boolean update(LopHoc lh);

	boolean delete(int maLop); 

	boolean updateStatus(int maLop, boolean status); 

	boolean isUsed(int maLop); 

	boolean checkDuplicate(String tenLop, String maNH);

	List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi);

	LopHoc findById(int maLop);
}