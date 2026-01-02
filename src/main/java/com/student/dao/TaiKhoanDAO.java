package com.student.dao;

import com.student.model.TaiKhoan;
import java.util.List;

public interface TaiKhoanDAO {
    
    /**
     * Kiểm tra đăng nhập
     * 
     * @param username 
     * @param password 
     * @return TaiKhoan object nếu đăng nhập thành công, null nếu thất bại
     */
    TaiKhoan checkLogin(String username, String password);
    
    /**
     * Tìm tài khoản theo username
     * 
     * @param username 
     * @return TaiKhoan object hoặc null
     */
    TaiKhoan findByUsername(String username);
    
    /**
     * Tìm tài khoản theo maTK
     * 
     * @param maTK 
     * @return TaiKhoan object hoặc null
     */
    TaiKhoan findById(int maTK);
    
    boolean insert(TaiKhoan taiKhoan);
    
    boolean update(TaiKhoan taiKhoan);
    
    boolean updatePassword(int maTK, String newPassword);
    
    boolean deactivate(int maTK);
    
    boolean activate(int maTK);
    
    /**
     * Lấy danh sách tất cả tài khoản với phân trang
     */
    List<TaiKhoan> findAllWithPagination(String searchKey, int page, int pageSize);
    
    /**
     * Đếm tổng số tài khoản
     */
    int count(String searchKey);
    
    /**
     * Lấy danh sách học sinh chưa có tài khoản
     */
    List<Integer> getStudentsWithoutAccount();
    
    /**
     * Lấy danh sách giáo viên chưa có tài khoản
     */
    List<Integer> getTeachersWithoutAccount();
    
    /**
     * Tạo nhiều tài khoản cùng lúc (batch insert)
     */
    int batchInsert(List<TaiKhoan> accounts);
    
    /**
     * Reset mật khẩu về mặc định
     */
    boolean resetPassword(int maTK, String defaultPassword);
}
