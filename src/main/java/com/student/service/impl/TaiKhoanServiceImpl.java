package com.student.service.impl;

import com.student.dao.TaiKhoanDAO;
import com.student.dao.HocSinhDAO;
import com.student.dao.GiaoVienDAO;
import com.student.dao.impl.TaiKhoanDAOImpl;
import com.student.dao.impl.HocSinhDAOImpl;
import com.student.dao.impl.GiaoVienDAOImpl;
import com.student.model.TaiKhoan;
import com.student.model.HocSinh;
import com.student.model.GiaoVien;
import com.student.service.TaiKhoanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaiKhoanServiceImpl implements TaiKhoanService {

    private final TaiKhoanDAO taiKhoanDAO;
    private final HocSinhDAO hocSinhDAO;
    private final GiaoVienDAO giaoVienDAO;

    public TaiKhoanServiceImpl() {
        this.taiKhoanDAO = new TaiKhoanDAOImpl();
        this.hocSinhDAO = new HocSinhDAOImpl();
        this.giaoVienDAO = new GiaoVienDAOImpl();
    }

    /**
     * Đăng nhập
     * 
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return TaiKhoan object hoặc null
     */
    @Override
    public TaiKhoan login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Username không được để trống");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.err.println("Password không được để trống");
            return null;
        }

        System.out.println("=== BẮT ĐẦU LOGIN ===");
        System.out.println("Username: " + username);
        long startTime = System.currentTimeMillis();
        
        TaiKhoan taiKhoan = taiKhoanDAO.checkLogin(username.trim(), password);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Login query completed in " + (endTime - startTime) + "ms");
        
        if (taiKhoan != null) {
            System.out.println("Đăng nhập thành công: " + taiKhoan.getUsername() + 
                             " (" + taiKhoan.getRole() + ")");
        } else {
            System.out.println("Đăng nhập thất bại: Sai username hoặc password");
        }
        System.out.println("=== KẾT THÚC LOGIN ===");
        
        return taiKhoan;
    }


    @Override
    public TaiKhoan findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return taiKhoanDAO.findByUsername(username.trim());
    }


    @Override
    public TaiKhoan findById(int maTK) {
        if (maTK <= 0) {
            return null;
        }
        return taiKhoanDAO.findById(maTK);
    }

    @Override
    public boolean createAccount(TaiKhoan taiKhoan) {
        if (taiKhoan == null) {
            System.err.println("TaiKhoan object không được null");
            return false;
        }
        
        if (taiKhoan.getUsername() == null || taiKhoan.getUsername().trim().isEmpty()) {
            System.err.println("Username không được để trống");
            return false;
        }
        
        if (taiKhoan.getPassword() == null || taiKhoan.getPassword().trim().isEmpty()) {
            System.err.println("Password không được để trống");
            return false;
        }
        if (isUsernameExists(taiKhoan.getUsername())) {
            System.err.println("Username đã tồn tại: " + taiKhoan.getUsername());
            return false;
        }

        String role = taiKhoan.getRole();
        if (role == null || role.trim().isEmpty()) {
            System.err.println("Role không được để trống");
            return false;
        }
        
        role = role.toUpperCase();
        if (!role.equals("ADMIN") && !role.equals("GIAOVIEN") && !role.equals("HOCSINH")) {
            System.err.println("Role không hợp lệ. Chỉ chấp nhận: ADMIN, GIAOVIEN, HOCSINH");
            return false;
        }
        
        taiKhoan.setRole(role);

        if (role.equals("GIAOVIEN")) {
            if (taiKhoan.getMaGV() == null || taiKhoan.getMaGV() <= 0) {
                System.err.println("maGV phải có giá trị khi role = GIAOVIEN");
                return false;
            }
            taiKhoan.setMaHS(null); 
        } else if (role.equals("HOCSINH")) {
            if (taiKhoan.getMaHS() == null || taiKhoan.getMaHS() <= 0) {
                System.err.println("maHS phải có giá trị khi role = HOCSINH");
                return false;
            }
            taiKhoan.setMaGV(null); 
        } else if (role.equals("ADMIN")) {
            taiKhoan.setMaGV(null);
            taiKhoan.setMaHS(null);
        }

        return taiKhoanDAO.insert(taiKhoan);
    }


    @Override
    public boolean updateAccount(TaiKhoan taiKhoan) {
        if (taiKhoan == null || taiKhoan.getMaTK() <= 0) {
            System.err.println("TaiKhoan không hợp lệ");
            return false;
        }
        
        String role = taiKhoan.getRole();
        if (role != null) {
            role = role.toUpperCase();
            taiKhoan.setRole(role);
            
            if (role.equals("GIAOVIEN")) {
                taiKhoan.setMaHS(null);
            } else if (role.equals("HOCSINH")) {
                taiKhoan.setMaGV(null);
            }
        }
        
        return taiKhoanDAO.update(taiKhoan);
    }

    @Override
    public boolean changePassword(int maTK, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            System.err.println("Mật khẩu cũ không được để trống");
            return false;
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.err.println("Mật khẩu mới không được để trống");
            return false;
        }

        TaiKhoan taiKhoan = taiKhoanDAO.findById(maTK);
        if (taiKhoan == null) {
            System.err.println("Không tìm thấy tài khoản với maTK: " + maTK);
            return false;
        }

        if (!taiKhoan.getPassword().equals(oldPassword)) {
            System.err.println("Mật khẩu cũ không đúng");
            return false;
        }

        return taiKhoanDAO.updatePassword(maTK, newPassword);
    }

    @Override
    public boolean deactivateAccount(int maTK) {
        if (maTK <= 0) {
            return false;
        }
        return taiKhoanDAO.deactivate(maTK);
    }


    @Override
    public boolean activateAccount(int maTK) {
        if (maTK <= 0) {
            return false;
        }
        return taiKhoanDAO.activate(maTK);
    }


    @Override
    public boolean isUsernameExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        TaiKhoan tk = taiKhoanDAO.findByUsername(username.trim());
        return tk != null;
    }
    
    @Override
    public List<TaiKhoan> findAllWithPagination(String searchKey, int page, int pageSize) {
        return taiKhoanDAO.findAllWithPagination(searchKey, page, pageSize);
    }
    
    @Override
    public int count(String searchKey) {
        return taiKhoanDAO.count(searchKey);
    }
    
    @Override
    public Map<String, Object> autoProvisionStudentAccounts(String defaultPassword) {
        Map<String, Object> result = new HashMap<>();
        
        if (defaultPassword == null || defaultPassword.trim().isEmpty()) {
            defaultPassword = "123456"; // Mật khẩu mặc định
        }
        
        // Lấy danh sách học sinh chưa có tài khoản
        List<Integer> studentIds = taiKhoanDAO.getStudentsWithoutAccount();
        
        if (studentIds.isEmpty()) {
            result.put("success", true);
            result.put("count", 0);
            result.put("message", "Tất cả học sinh đã có tài khoản");
            return result;
        }
        
        // Tạo danh sách tài khoản mới
        List<TaiKhoan> newAccounts = new ArrayList<>();
        for (Integer maHS : studentIds) {
            HocSinh hs = hocSinhDAO.findById(maHS);
            if (hs != null) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername("HS" + maHS); // Username = HS + Mã học sinh
                tk.setPassword(defaultPassword);
                tk.setRole("HOCSINH");
                tk.setMaHS(maHS);
                tk.setActive(true);
                newAccounts.add(tk);
            }
        }
        
        // Insert batch
        int successCount = taiKhoanDAO.batchInsert(newAccounts);
        
        result.put("success", successCount > 0);
        result.put("count", successCount);
        result.put("total", studentIds.size());
        result.put("message", "Đã tạo " + successCount + "/" + studentIds.size() + " tài khoản học sinh");
        
        return result;
    }
    
    @Override
    public Map<String, Object> autoProvisionTeacherAccounts(String defaultPassword) {
        Map<String, Object> result = new HashMap<>();
        
        if (defaultPassword == null || defaultPassword.trim().isEmpty()) {
            defaultPassword = "123456"; // Mật khẩu mặc định
        }
        
        // Lấy danh sách giáo viên chưa có tài khoản
        List<Integer> teacherIds = taiKhoanDAO.getTeachersWithoutAccount();
        
        if (teacherIds.isEmpty()) {
            result.put("success", true);
            result.put("count", 0);
            result.put("message", "Tất cả giáo viên đã có tài khoản");
            return result;
        }
        
        // Tạo danh sách tài khoản mới
        List<TaiKhoan> newAccounts = new ArrayList<>();
        for (Integer maGV : teacherIds) {
            GiaoVien gv = giaoVienDAO.findById(maGV);
            if (gv != null) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername("GV" + maGV); // Username = GV + Mã giáo viên
                tk.setPassword(defaultPassword);
                tk.setRole("GIAOVIEN");
                tk.setMaGV(maGV);
                tk.setActive(true);
                newAccounts.add(tk);
            }
        }
        
        // Insert batch
        int successCount = taiKhoanDAO.batchInsert(newAccounts);
        
        result.put("success", successCount > 0);
        result.put("count", successCount);
        result.put("total", teacherIds.size());
        result.put("message", "Đã tạo " + successCount + "/" + teacherIds.size() + " tài khoản giáo viên");
        
        return result;
    }
    
    @Override
    public boolean resetPasswordToDefault(int maTK, String defaultPassword) {
        if (maTK <= 0) {
            return false;
        }
        
        if (defaultPassword == null || defaultPassword.trim().isEmpty()) {
            defaultPassword = "123456"; // Mật khẩu mặc định
        }
        
        return taiKhoanDAO.resetPassword(maTK, defaultPassword);
    }
    
    @Override
    public boolean toggleAccountStatus(int maTK, boolean isActive) {
        if (maTK <= 0) {
            return false;
        }
        
        if (isActive) {
            return taiKhoanDAO.activate(maTK);
        } else {
            return taiKhoanDAO.deactivate(maTK);
        }
    }
}
