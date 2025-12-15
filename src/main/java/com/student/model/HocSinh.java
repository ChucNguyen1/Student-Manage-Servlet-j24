package com.student.model;

import java.sql.Date;

public class HocSinh {
	private int maHS;
	private String hoTen;
	private Date ngaySinh;
	private String gioiTinh;
	private String noiSinh;
	private String danToc;
	private String tonGiao;
	private String diaChi;
	private String email;
	private String sdtCaNhan;
	private String hoTenCha;
	private String ngheNghiepCha;
	private String sdtCha;
	private String hoTenMe;
	private String ngheNghiepMe;
	private String sdtMe;


	private int maLop;
	private Integer userID; 
	private String trangThaiHocTap; 
	private boolean trangThai; 
	private String tenLop;

	public HocSinh() {
	}


	public int getMaHS() {
		return maHS;
	}

	public void setMaHS(int maHS) {
		this.maHS = maHS;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public int getMaLop() {
		return maLop;
	}

	public void setMaLop(int maLop) {
		this.maLop = maLop;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	// ... Bạn hãy generate hết nhé ...

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getNoiSinh() {
		return noiSinh;
	}

	public void setNoiSinh(String noiSinh) {
		this.noiSinh = noiSinh;
	}

	public String getDanToc() {
		return danToc;
	}

	public void setDanToc(String danToc) {
		this.danToc = danToc;
	}

	public String getTonGiao() {
		return tonGiao;
	}

	public void setTonGiao(String tonGiao) {
		this.tonGiao = tonGiao;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSdtCaNhan() {
		return sdtCaNhan;
	}

	public void setSdtCaNhan(String sdtCaNhan) {
		this.sdtCaNhan = sdtCaNhan;
	}

	public String getHoTenCha() {
		return hoTenCha;
	}

	public void setHoTenCha(String hoTenCha) {
		this.hoTenCha = hoTenCha;
	}

	public String getNgheNghiepCha() {
		return ngheNghiepCha;
	}

	public void setNgheNghiepCha(String ngheNghiepCha) {
		this.ngheNghiepCha = ngheNghiepCha;
	}

	public String getSdtCha() {
		return sdtCha;
	}

	public void setSdtCha(String sdtCha) {
		this.sdtCha = sdtCha;
	}

	public String getHoTenMe() {
		return hoTenMe;
	}

	public void setHoTenMe(String hoTenMe) {
		this.hoTenMe = hoTenMe;
	}

	public String getNgheNghiepMe() {
		return ngheNghiepMe;
	}

	public void setNgheNghiepMe(String ngheNghiepMe) {
		this.ngheNghiepMe = ngheNghiepMe;
	}

	public String getSdtMe() {
		return sdtMe;
	}

	public void setSdtMe(String sdtMe) {
		this.sdtMe = sdtMe;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getTrangThaiHocTap() {
		return trangThaiHocTap;
	}

	public void setTrangThaiHocTap(String trangThaiHocTap) {
		this.trangThaiHocTap = trangThaiHocTap;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
}