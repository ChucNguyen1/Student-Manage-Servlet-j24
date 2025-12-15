package com.student.service.impl;

import java.util.List;

import com.student.dao.NamHocDAO;
import com.student.dao.impl.NamHocDAOImpl;
import com.student.model.NamHoc;
import com.student.service.NamHocService;

public class NamHocServiceImpl implements NamHocService {

	private NamHocDAO namHocDAO = new NamHocDAOImpl();

	@Override
	public List<NamHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return namHocDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return namHocDAO.count(searchKey);
	}

	@Override
	public boolean insert(NamHoc nh) {
		if (namHocDAO.checkExist(nh.getMaNH())) {
			System.out.println("Lỗi: Mã năm học đã tồn tại!");
			return false;
		}

		if (nh.getNgayBatDau() != null && nh.getNgayKetThuc() != null) {
			if (nh.getNgayKetThuc().before(nh.getNgayBatDau())) {
				System.out.println("Lỗi: Ngày kết thúc phải sau ngày bắt đầu!");
				return false;
			}
		}

		return namHocDAO.insert(nh);
	}

	@Override
	public boolean update(NamHoc nh) {
		if (nh.getNgayBatDau() != null && nh.getNgayKetThuc() != null) {
			if (nh.getNgayKetThuc().before(nh.getNgayBatDau())) {
				return false;
			}
		}
		return namHocDAO.update(nh);
	}

	@Override
	public String delete(String maNH) {
		if (namHocDAO.isUsed(maNH)) {
			return "FOREIGN_KEY_ERROR"; 
		}

		boolean success = namHocDAO.delete(maNH);
		if (success) {
			return "SUCCESS";
		} else {
			return "SYSTEM_ERROR";
		}
	}

	@Override
	public NamHoc findById(String maNH) {
		return namHocDAO.findById(maNH);
	}

	@Override
	public boolean updateStatus(String maNH, boolean newStatus) {
		return false;
	}

	@Override
	public List<NamHoc> findAll() {
		return namHocDAO.findAll();
	}
}