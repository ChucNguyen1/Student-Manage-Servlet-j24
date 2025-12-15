package com.student.service.impl;

import java.util.List;

import com.student.dao.KhoiDAO;
import com.student.dao.impl.KhoiDAOImpl;
import com.student.model.Khoi;
import com.student.service.KhoiService;

public class KhoiServiceImpl implements KhoiService {

	private KhoiDAO khoiDAO;

	public KhoiServiceImpl() {
		this.khoiDAO = new KhoiDAOImpl();
	}

	@Override
	public int count(String searchKey) {
		return khoiDAO.count(searchKey);
	}

	@Override
	public List<Khoi> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return khoiDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public boolean insert(Khoi khoi) {
		if (khoi.getTenKhoi() == null || khoi.getTenKhoi().isEmpty()) {
			return false; 
		}

		return khoiDAO.insert(khoi);
	}

	@Override
	public boolean delete(int maKhoi) {

		return khoiDAO.delete(maKhoi);
	}

	@Override
	public boolean update(Khoi khoi) {

		if (khoi.getTenKhoi() == null || khoi.getTenKhoi().isEmpty()) {
			return false; 
		}

		return khoiDAO.update(khoi);
	}

	public static void main(String[] args) {
		// Khởi tạo Service
		KhoiService khoiService = new KhoiServiceImpl();

	}

	@Override
	public List<Khoi> findAll() {
		return khoiDAO.findAll();
	}
}