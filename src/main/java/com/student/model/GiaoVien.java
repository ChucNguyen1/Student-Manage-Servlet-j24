package com.student.model;

import java.sql.Date; // <-- Quan trọng: Dùng java.sql.Date

public class GiaoVien {

	private int maGV;
	private String hoTen;
	private Date ngaySinh;
	private String gioiTinh;
	private String chuyenMon;
	private String email;
	private String sdt;
	private String diaChi;
	private Integer userID;

	// Constructors (Thêm 1 constructor rỗng và 1 đầy đủ)
	public GiaoVien() {
	}

	public GiaoVien(int maGV, String hoTen, Date ngaySinh, String gioiTinh, String chuyenMon, String email, String sdt,
			String diaChi, Integer userID) {
		this.maGV = maGV;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.chuyenMon = chuyenMon;
		this.email = email;
		this.sdt = sdt;
		this.diaChi = diaChi;
		this.userID = userID;
	}

	// Getters and Setters (Tạo cho tất cả các thuộc tính)
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

	public String getChuyenMon() {
		return chuyenMon;
	}

	public void setChuyenMon(String chuyenMon) {
		this.chuyenMon = chuyenMon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "GiaoVien{" + "maGV=" + maGV + ", hoTen=" + hoTen + '}';
	}
}