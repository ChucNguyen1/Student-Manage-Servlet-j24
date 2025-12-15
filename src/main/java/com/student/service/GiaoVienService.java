package com.student.service;

import java.util.List;

import com.student.model.GiaoVien;

public interface GiaoVienService {

	List<GiaoVien> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(GiaoVien gv);

	boolean update(GiaoVien gv);

	boolean delete(int maGV);

	GiaoVien findById(int maGV);

	List<GiaoVien> findAll();

	List<GiaoVien> findByToBoMon(int maTo);
	
	List<GiaoVien> findByChuyenMon(int maMonHoc);
}