package com.student.service;

import java.util.List;

import com.student.model.ToBoMon;

public interface ToBoMonService {
	List<ToBoMon> findAll();

	List<ToBoMon> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	ToBoMon findById(int maTo);

	boolean themToBoMon(ToBoMon toBoMon);

	boolean capNhatToBoMon(ToBoMon toBoMon);

	boolean capNhatToTruong(int maTo, int maGV);

	boolean xoaToBoMon(int maTo);
}