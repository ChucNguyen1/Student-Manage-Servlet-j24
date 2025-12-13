package com.student.service;

import java.util.List;

import com.student.model.HocSinh;

public interface HocSinhService {
	List<HocSinh> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	boolean insert(HocSinh hs);

	boolean update(HocSinh hs);

	boolean delete(int maHS);

	HocSinh findById(int maHS);

	List<HocSinh> findByLop(int maLop);
}