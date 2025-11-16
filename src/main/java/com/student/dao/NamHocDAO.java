package com.student.dao;

import java.util.List;

import com.student.model.NamHoc;

public interface NamHocDAO {
	List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(NamHoc nh);

	boolean update(NamHoc nh);

	boolean delete(String maNH); // Xóa theo String ID

	NamHoc findById(String maNH); // Tìm theo String ID

	// Kiểm tra mã tồn tại (để validate khi thêm mới)
	boolean checkExist(String maNH);

	boolean updateStatus(String maNH, boolean status);

	boolean isUsed(String maNH);

	List<NamHoc> findAll();
}