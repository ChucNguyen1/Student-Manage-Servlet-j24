package com.student.service;

import com.student.model.TaiKhoan;
import java.util.List;
import java.util.Map;

public interface TaiKhoanService {
    
    TaiKhoan login(String username, String password);
    
    TaiKhoan findByUsername(String username);
    

    TaiKhoan findById(int maTK);
    
    boolean createAccount(TaiKhoan taiKhoan);
    
    boolean updateAccount(TaiKhoan taiKhoan);
    
    boolean changePassword(int maTK, String oldPassword, String newPassword);
    
    boolean deactivateAccount(int maTK);
    
    boolean activateAccount(int maTK);
    
    /**
     * @param username Tên đăng nhập
     * @return true nếu đã tồn tại, false nếu chưa
     */
    boolean isUsernameExists(String username);
    
    /**
     * Lấy danh sách tài khoản với phân trang
     */
    List<TaiKhoan> findAllWithPagination(String searchKey, int page, int pageSize);
    
    /**
     * Đếm tổng số tài khoản
     */
    int count(String searchKey);
    
    /**
     * Sinh tài khoản tự động cho tất cả học sinh chưa có tài khoản
     * @param defaultPassword Mật khẩu mặc định
     * @return Số tài khoản được tạo
     */
    Map<String, Object> autoProvisionStudentAccounts(String defaultPassword);
    
    /**
     * Sinh tài khoản tự động cho tất cả giáo viên chưa có tài khoản
     * @param defaultPassword Mật khẩu mặc định
     * @return Số tài khoản được tạo
     */
    Map<String, Object> autoProvisionTeacherAccounts(String defaultPassword);
    
    /**
     * Reset mật khẩu về mặc định
     */
    boolean resetPasswordToDefault(int maTK, String defaultPassword);
    
    /**
     * Khóa/Mở khóa tài khoản
     */
    boolean toggleAccountStatus(int maTK, boolean isActive);
}
