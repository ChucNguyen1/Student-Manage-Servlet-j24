package com.student.service.impl;

import java.util.List;

import com.student.dao.MonHocDAO;
import com.student.dao.impl.MonHocDAOImpl;
import com.student.model.MonHoc;
import com.student.service.MonHocService;

public class MonHocServiceImpl implements MonHocService {

	private MonHocDAO monHocDAO = new MonHocDAOImpl();

	@Override
	public List<MonHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return monHocDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return monHocDAO.count(searchKey);
	}

	@Override
	public boolean insert(MonHoc mh) {
		if (mh.getSoTiet() <= 0)
			return false; 
		return monHocDAO.insert(mh);
	}

	@Override
	public boolean update(MonHoc mh) {
		if (mh.getSoTiet() <= 0)
			return false;
		return monHocDAO.update(mh);
	}

	@Override
	public String delete(int maMH) {
		if (monHocDAO.isUsed(maMH))
			return "FOREIGN_KEY_ERROR";
		return monHocDAO.delete(maMH) ? "SUCCESS" : "SYSTEM_ERROR";
	}

	@Override
	public boolean updateStatus(int maMH, boolean status) {
		return monHocDAO.updateStatus(maMH, status);
	}

	@Override
	public List<MonHoc> findAll() {
		return monHocDAO.findAll();
	}
}