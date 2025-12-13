package com.student.service.impl;

import java.util.List;

import com.student.dao.HocKyDAO;
import com.student.dao.impl.HocKyDAOImpl;
import com.student.model.HocKy;
import com.student.service.HocKyService;

public class HocKyServiceImpl implements HocKyService {

	private HocKyDAO hocKyDAO = new HocKyDAOImpl();

	@Override
	public List<HocKy> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return hocKyDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return hocKyDAO.count(searchKey);
	}

	@Override
	public boolean insert(HocKy hk) {
		// Validate: Hệ số phải dương
		if (hk.getHeSo() < 1) {
			System.out.println("Lỗi: Hệ số học kỳ phải lớn hơn hoặc bằng 1.");
			return false;
		}
		// Validate: Tên không được rỗng
		if (hk.getTenHK() == null || hk.getTenHK().trim().isEmpty()) {
			return false;
		}

		return hocKyDAO.insert(hk);
	}

	@Override
	public boolean update(HocKy hk) {
		if (hk.getHeSo() < 1)
			return false;
		if (hk.getTenHK() == null || hk.getTenHK().trim().isEmpty())
			return false;

		return hocKyDAO.update(hk);
	}

	@Override
	public String delete(int maHK) {
		// 1. Kiểm tra xem học kỳ có đang được dùng trong Bảng Điểm hoặc Phân Công
		// không?
		if (hocKyDAO.isUsed(maHK)) {
			return "FOREIGN_KEY_ERROR"; // Không thể xóa cứng
		}

		// 2. Nếu không vướng dữ liệu -> Xóa cứng
		if (hocKyDAO.delete(maHK)) {
			return "SUCCESS";
		} else {
			return "SYSTEM_ERROR";
		}
	}

	@Override
	public boolean updateStatus(int maHK, boolean status) {
		return hocKyDAO.updateStatus(maHK, status);
	}

	@Override
	public HocKy findById(int maHK) {
		return hocKyDAO.findById(maHK);
	}

	@Override
	public List<HocKy> findAll() {
		return hocKyDAO.findAll();
	}

	@Override
	public List<HocKy> findByNamHoc(String maNH) {
		return hocKyDAO.findByNamHoc(maNH);
	}
}