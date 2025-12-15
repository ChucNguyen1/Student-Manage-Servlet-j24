package com.student.service.impl;

import java.util.List;

import com.student.dao.ToBoMonDAO;
import com.student.dao.impl.ToBoMonDAOImpl;
import com.student.model.ToBoMon;
import com.student.service.ToBoMonService;

public class ToBoMonServiceImpl implements ToBoMonService {

	private ToBoMonDAO toBoMonDAO = new ToBoMonDAOImpl();

	@Override
	public List<ToBoMon> findAll() {
		return toBoMonDAO.getAllToBoMon();
	}

	@Override
	public List<ToBoMon> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return toBoMonDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return toBoMonDAO.count(searchKey);
	}

	@Override
	public ToBoMon findById(int maTo) {
		return toBoMonDAO.findById(maTo);
	}

	@Override
	public boolean themToBoMon(ToBoMon toBoMon) {
		if (toBoMon.getTenTo() == null || toBoMon.getTenTo().trim().isEmpty()) {
			return false;
		}
		return toBoMonDAO.addToBoMon(toBoMon);
	}

	@Override
	public boolean capNhatToBoMon(ToBoMon toBoMon) {
		if (toBoMon.getTenTo() == null || toBoMon.getTenTo().trim().isEmpty()) {
			return false;
		}
		return toBoMonDAO.updateToBoMon(toBoMon);
	}

	@Override
	public boolean capNhatToTruong(int maTo, int maGV) {
		return toBoMonDAO.updateToTruong(maTo, maGV);
	}

	@Override
	public boolean xoaToBoMon(int maTo) {
		return toBoMonDAO.deleteToBoMon(maTo);
	}
}