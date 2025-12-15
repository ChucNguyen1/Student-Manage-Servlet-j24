package com.student.dao;

import java.util.List;

import com.student.model.ToBoMon;

public interface ToBoMonDAO {

	List<ToBoMon> getAllToBoMon();

	List<ToBoMon> findAndPaginate(String searchKey, int pageNumber, int pageSize);

	int count(String searchKey);

	ToBoMon findById(int maTo);

	boolean addToBoMon(ToBoMon toBoMon);

	boolean updateToBoMon(ToBoMon toBoMon);

	boolean updateToTruong(int maTo, int maGV);

	boolean deleteToBoMon(int maTo);
}