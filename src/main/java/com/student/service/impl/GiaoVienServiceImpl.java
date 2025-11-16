package com.student.service.impl;

import java.util.List;

import com.student.dao.GiaoVienDAO;
import com.student.dao.impl.GiaoVienDAOImpl;
import com.student.model.GiaoVien;
import com.student.service.GiaoVienService;

public class GiaoVienServiceImpl implements GiaoVienService {

	// Khởi tạo DAO
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
		// --- LOGIC NGHIỆP VỤ: KIỂM TRA DỮ LIỆU ---
		if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
			System.out.println("Lỗi: Tên giáo viên không được để trống.");
			return false;
		}

		// Nếu email có nhập, có thể kiểm tra định dạng email ở đây...

		// Gọi DAO để lưu
		return giaoVienDAO.insert(gv);
	}

	@Override
	public boolean update(GiaoVien gv) {
		// Kiểm tra tương tự khi update
		if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
			return false;
		}
		return giaoVienDAO.update(gv);
	}

	@Override
	public boolean delete(int maGV) {
		// Có thể kiểm tra xem GV này có đang chủ nhiệm lớp nào không trước khi xóa
		return giaoVienDAO.delete(maGV);
	}

	@Override
	public GiaoVien findById(int maGV) {
		return giaoVienDAO.findById(maGV);
	}

	// Main test cho Service
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