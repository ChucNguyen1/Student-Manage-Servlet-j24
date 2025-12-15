package com.student.service;

import java.util.List;

import com.student.model.NamHoc;

public interface NamHocService {


	List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize);


	int count(String searchKey);

	boolean insert(NamHoc nh);

	boolean update(NamHoc nh);

	String delete(String maNH);

	NamHoc findById(String maNH);

	boolean updateStatus(String maNH, boolean newStatus);

	List<NamHoc> findAll();

}