package com.student.dao;

import com.student.model.TaiKhoan;

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
}
