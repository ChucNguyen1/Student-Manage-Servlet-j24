package com.student.service.impl;

import java.util.List;

import com.student.dao.LopHocDAO;
import com.student.dao.impl.LopHocDAOImpl;
import com.student.model.LopHoc;
import com.student.service.LopHocService;

public class LopHocServiceImpl implements LopHocService {

	private LopHocDAO lopHocDAO = new LopHocDAOImpl();

	@Override
	public List<LopHoc> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return lopHocDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public List<LopHoc> findAll() {
		return lopHocDAO.findAll();
	}

	@Override
	public int count(String searchKey) {
		return lopHocDAO.count(searchKey);
	}

	@Override
	public boolean insert(LopHoc lh) {
		if (lopHocDAO.checkDuplicate(lh.getTenLop(), lh.getMaNH())) {
			System.out.println("Lỗi: Lớp " + lh.getTenLop() + " đã tồn tại trong năm học này.");
			return false;
		}
		return lopHocDAO.insert(lh);
	}

	@Override
	public boolean update(LopHoc lh) {
		return lopHocDAO.update(lh);
	}

	@Override
	public String delete(int maLop) {
		if (lopHocDAO.isUsed(maLop)) {
			return "FOREIGN_KEY_ERROR"; 
		}
		return lopHocDAO.delete(maLop) ? "SUCCESS" : "SYSTEM_ERROR";
	}

	@Override
	public boolean updateStatus(int maLop, boolean status) {
		return lopHocDAO.updateStatus(maLop, status);
	}

	@Override
	public List<LopHoc> findByNamHocAndKhoi(String maNH, int maKhoi) {
		return lopHocDAO.findByNamHocAndKhoi(maNH, maKhoi);
	}

	@Override
	public LopHoc findById(int maLop) {
		return lopHocDAO.findById(maLop);
	}
}