package com.student.service;

import com.student.model.TaiKhoan;

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
}
