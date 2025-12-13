package com.student.service.impl;

import java.util.List;

import com.student.dao.KhoiDAO;
import com.student.dao.impl.KhoiDAOImpl;
import com.student.model.Khoi;
import com.student.service.KhoiService;

public class KhoiServiceImpl implements KhoiService {

	// Khởi tạo DAO (Dependency Injection)
	// Trong các framework như Spring, việc này sẽ tự động (Autowired)
	// Nhưng trong project Servlet thuần, chúng ta khởi tạo thủ công.
	private KhoiDAO khoiDAO;

	public KhoiServiceImpl() {
		// Khởi tạo DAO implementation
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
		// (Hiện tại nghiệp vụ đơn giản, chỉ gọi DAO)
		// (Trong tương lai có thể thêm: kiểm tra trùng tên,
		// chuẩn hóa tên... trước khi gọi DAO)

		// Ví dụ: Kiểm tra nghiệp vụ cơ bản
		if (khoi.getTenKhoi() == null || khoi.getTenKhoi().isEmpty()) {
			return false; // Không thêm nếu tên rỗng
		}

		// Gọi DAO để lưu
		return khoiDAO.insert(khoi);
	}

	@Override
	public boolean delete(int maKhoi) {
		// (Trong tương lai có thể kiểm tra xem khối này có
		// học sinh không trước khi cho xóa...)

		return khoiDAO.delete(maKhoi);
	}

	@Override
	public boolean update(Khoi khoi) {
		// Kiểm tra nghiệp vụ
		if (khoi.getTenKhoi() == null || khoi.getTenKhoi().isEmpty()) {
			return false; // Không cho phép tên rỗng
		}

		return khoiDAO.update(khoi);
	}

	// Main method để test
	public static void main(String[] args) {
		// Khởi tạo Service
		KhoiService khoiService = new KhoiServiceImpl();

	}

	@Override
	public List<Khoi> findAll() {
		// TODO Auto-generated method stub
		return khoiDAO.findAll();
	}
}