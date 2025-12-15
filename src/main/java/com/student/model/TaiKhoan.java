package com.student.model;

import java.sql.Timestamp;

public class TaiKhoan {
    private int maTK;
    private String username;
    private String password;
    private String role; 
    private Integer maGV; 
    private Integer maHS; 
    private String hoTenHienThi; 
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TaiKhoan() {
        this.isActive = true;
    }

    public TaiKhoan(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = true;
    }

    // Getters and Setters
    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getMaGV() {
        return maGV;
    }

    public void setMaGV(Integer maGV) {
        this.maGV = maGV;
    }

    public Integer getMaHS() {
        return maHS;
    }

    public void setMaHS(Integer maHS) {
        this.maHS = maHS;
    }

    public String getHoTenHienThi() {
        return hoTenHienThi;
    }

    public void setHoTenHienThi(String hoTenHienThi) {
        this.hoTenHienThi = hoTenHienThi;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isGiaoVien() {
        return "GIAOVIEN".equalsIgnoreCase(role);
    }

    public boolean isHocSinh() {
        return "HOCSINH".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maTK=" + maTK +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", maGV=" + maGV +
                ", maHS=" + maHS +
                ", hoTenHienThi='" + hoTenHienThi + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
