package com.student.service.impl;

import java.util.List;

import com.student.dao.GiaoVienDAO;
import com.student.dao.impl.GiaoVienDAOImpl;
import com.student.model.GiaoVien;
import com.student.service.GiaoVienService;

public class GiaoVienServiceImpl implements GiaoVienService {

	private GiaoVienDAO giaoVienDAO = new GiaoVienDAOImpl();

	@Override
	public List<GiaoVien> findAndPaginate(String searchKey, int pageNumber, int pageSize) {
		return giaoVienDAO.findAndPaginate(searchKey, pageNumber, pageSize);
	}

	@Override
	public int count(String searchKey) {
		return giaoVienDAO.count(searchKey);
	}

	@Override
	public boolean insert(GiaoVien gv) {
		if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
			System.out.println("Lỗi: Tên giáo viên không được để trống.");
			return false;
		}

		return giaoVienDAO.insert(gv);
	}

	@Override
	public boolean update(GiaoVien gv) {

		if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
			return false;
		}
		return giaoVienDAO.update(gv);
	}

	@Override
	public boolean delete(int maGV) {

		return giaoVienDAO.delete(maGV);
	}

	@Override
	public GiaoVien findById(int maGV) {
		return giaoVienDAO.findById(maGV);
	}

	public List<GiaoVien> findAll() {
		return giaoVienDAO.findAll();
	}

	@Override
	public List<GiaoVien> findByToBoMon(int maTo) {
		return giaoVienDAO.findByToBoMon(maTo);
	}

	@Override
	public List<GiaoVien> findByChuyenMon(int maMonHoc) {
		return giaoVienDAO.findByChuyenMon(maMonHoc);
	}

	public static void main(String[] args) {
		GiaoVienService service = new GiaoVienServiceImpl();

		System.out.println("--- TEST SERVICE LIST ---");
		List<GiaoVien> list = service.findAndPaginate(null, 1, 10);
		if (list.isEmpty()) {
			System.out.println("Danh sách trống.");
		} else {
			for (GiaoVien gv : list) {
				System.out.println("GV: " + gv.getHoTen() + " - " + gv.getEmail());
			}
		}
	}
}