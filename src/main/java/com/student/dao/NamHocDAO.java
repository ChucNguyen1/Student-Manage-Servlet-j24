package com.student.dao;

import java.util.List;

import com.student.model.NamHoc;

public interface NamHocDAO {
	List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(NamHoc nh);

	boolean update(NamHoc nh);

	boolean delete(String maNH); 

	NamHoc findById(String maNH); 

	boolean checkExist(String maNH);

	boolean updateStatus(String maNH, boolean status);

	boolean isUsed(String maNH);

	List<NamHoc> findAll();
}