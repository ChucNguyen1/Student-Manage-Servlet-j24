package com.student.service;

import java.util.List;

import com.student.model.HocKy;

public interface HocKyService {

	List<HocKy> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(HocKy hk);

	boolean update(HocKy hk);

	String delete(int maHK);

	boolean updateStatus(int maHK, boolean status);

	HocKy findById(int maHK);

	List<HocKy> findAll();

	List<HocKy> findByNamHoc(String maNH);

}