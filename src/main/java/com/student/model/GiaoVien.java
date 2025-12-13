package com.student.model;

import java.sql.Date;

public class GiaoVien {
	private int maGV;
	private String hoTen;
	private Date ngaySinh;
	private String gioiTinh;
	private String sdt;
	private String email;
	private String diaChi;

	// THAY ĐỔI: Chuyên môn giờ là ID (int)
	private int maMonHocChuyenMon;

	// THÊM: Tên môn học (để hiển thị ra bảng mà không cần query lại)
	private String tenMonHocChuyenMon;

	// THÊM: Trạng thái
	private boolean trangThai;

	private Integer userID; // Giữ nguyên

	public GiaoVien() {
	}

	// --- GETTERS & SETTERS (Tạo lại cho đầy đủ) ---

	public int getMaGV() {
		return maGV;
	}

	public void setMaGV(int maGV) {
		this.maGV = maGV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	// Getter/Setter cho ID Môn
	public int getMaMonHocChuyenMon() {
		return maMonHocChuyenMon;
	}

	public void setMaMonHocChuyenMon(int maMonHocChuyenMon) {
		this.maMonHocChuyenMon = maMonHocChuyenMon;
	}

	// Getter/Setter cho Tên Môn (DTO)
	public String getTenMonHocChuyenMon() {
		return tenMonHocChuyenMon;
	}

	public void setTenMonHocChuyenMon(String tenMonHocChuyenMon) {
		this.tenMonHocChuyenMon = tenMonHocChuyenMon;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}