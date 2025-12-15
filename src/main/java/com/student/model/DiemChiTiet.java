package com.student.model;

public class DiemChiTiet {
	private int maDiem;

	private int maHS;
	private int maMonHoc;
	private int maHocKy;

	private Double diemMieng1;
	private Double diemMieng2;
	private Double diemMieng3;

	private Double diem15p1;
	private Double diem15p2;
	private Double diem15p3;

	private Double diem1Tiet1;
	private Double diem1Tiet2;

	private Double diemThi;
	private Double diemTBM; 

	private String tenHocSinh;
	private String tenMonHoc;
	private String tenHocKy;

	public DiemChiTiet() {
	}

	// --- GETTERS & SETTERS ---

	public int getMaDiem() {
		return maDiem;
	}

	public void setMaDiem(int maDiem) {
		this.maDiem = maDiem;
	}

	public int getMaHS() {
		return maHS;
	}

	public void setMaHS(int maHS) {
		this.maHS = maHS;
	}

	public int getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(int maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public int getMaHocKy() {
		return maHocKy;
	}

	public void setMaHocKy(int maHocKy) {
		this.maHocKy = maHocKy;
	}

	public Double getDiemMieng1() {
		return diemMieng1;
	}

	public void setDiemMieng1(Double diemMieng1) {
		this.diemMieng1 = diemMieng1;
	}

	public Double getDiemMieng2() {
		return diemMieng2;
	}

	public void setDiemMieng2(Double diemMieng2) {
		this.diemMieng2 = diemMieng2;
	}

	public Double getDiemMieng3() {
		return diemMieng3;
	}

	public void setDiemMieng3(Double diemMieng3) {
		this.diemMieng3 = diemMieng3;
	}

	public Double getDiem15p1() {
		return diem15p1;
	}

	public void setDiem15p1(Double diem15p1) {
		this.diem15p1 = diem15p1;
	}

	public Double getDiem15p2() {
		return diem15p2;
	}

	public void setDiem15p2(Double diem15p2) {
		this.diem15p2 = diem15p2;
	}

	public Double getDiem15p3() {
		return diem15p3;
	}

	public void setDiem15p3(Double diem15p3) {
		this.diem15p3 = diem15p3;
	}

	public Double getDiem1Tiet1() {
		return diem1Tiet1;
	}

	public void setDiem1Tiet1(Double diem1Tiet1) {
		this.diem1Tiet1 = diem1Tiet1;
	}

	public Double getDiem1Tiet2() {
		return diem1Tiet2;
	}

	public void setDiem1Tiet2(Double diem1Tiet2) {
		this.diem1Tiet2 = diem1Tiet2;
	}

	public Double getDiemThi() {
		return diemThi;
	}

	public void setDiemThi(Double diemThi) {
		this.diemThi = diemThi;
	}

	public Double getDiemTBM() {
		return diemTBM;
	}

	public void setDiemTBM(Double diemTBM) {
		this.diemTBM = diemTBM;
	}

	// Getters/Setters cho trường phụ
	public String getTenHocSinh() {
		return tenHocSinh;
	}

	public void setTenHocSinh(String tenHocSinh) {
		this.tenHocSinh = tenHocSinh;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public String getTenHocKy() {
		return tenHocKy;
	}

	public void setTenHocKy(String tenHocKy) {
		this.tenHocKy = tenHocKy;
	}
}