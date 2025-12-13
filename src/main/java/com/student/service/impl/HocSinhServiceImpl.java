package com.student.service.impl;

import java.util.List;

import com.student.dao.HocSinhDAO;
import com.student.dao.impl.HocSinhDAOImpl;
import com.student.model.HocSinh;
import com.student.service.HocSinhService;

public class HocSinhServiceImpl implements HocSinhService {

	private HocSinhDAO hocSinhDAO = new HocSinhDAOImpl();

	@Override
	public List<HocSinh> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return hocSinhDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return hocSinhDAO.count(searchKey);
	}

	@Override
	public boolean insert(HocSinh hs) {
		// Validate: Tên không được rỗng
		if (hs.getHoTen() == null || hs.getHoTen().trim().isEmpty()) {
			return false;
		}
		return hocSinhDAO.insert(hs);
	}

	@Override
	public boolean update(HocSinh hs) {
		if (hs.getHoTen() == null || hs.getHoTen().trim().isEmpty()) {
			return false;
		}
		return hocSinhDAO.update(hs);
	}

	@Override
	public boolean delete(int maHS) {
		// Có thể thêm kiểm tra điểm số trước khi xóa (nếu cần)
		return hocSinhDAO.delete(maHS);
	}

	@Override
	public HocSinh findById(int maHS) {
		return hocSinhDAO.findById(maHS);
	}

	@Override
	public List<HocSinh> findByLop(int maLop) {
		return hocSinhDAO.findByLop(maLop);
	}
}