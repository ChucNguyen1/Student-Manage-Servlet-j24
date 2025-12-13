IF DB_ID('db_quanlyhocsinh_v2') IS NOT NULL
BEGIN
    ALTER DATABASE db_quanlyhocsinh_v2 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE db_quanlyhocsinh_v2;
END
GO

CREATE DATABASE db_quanlyhocsinh_v2;
GO

USE db_quanlyhocsinh_v2;
GO

/* ==========================================================================
   1. NHÓM DANH MỤC & HỆ THỐNG
   ========================================================================== */

/* Bảng Vai trò */
CREATE TABLE Roles (
    roleID INT PRIMARY KEY IDENTITY(1,1),
    roleName NVARCHAR(50) NOT NULL UNIQUE, -- Admin, GiaoVien, HocSinh, PhuHuynh
    trangThai BIT DEFAULT 1
);
GO

/* Bảng Người dùng */
CREATE TABLE Users (
    userID INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    avatar NVARCHAR(255) DEFAULT 'default.png',
    roleID INT NOT NULL,
    trangThai BIT DEFAULT 1, -- 1: Active, 0: Blocked
    FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);
GO

/* Bảng Năm học */
CREATE TABLE NamHoc (
    maNH VARCHAR(10) PRIMARY KEY, -- VD: '2024-2025'
    tenNH NVARCHAR(100) NOT NULL,
    ngayBatDau DATE,
    ngayKetThuc DATE,
    trangThai BIT DEFAULT 1
);
GO

/* Bảng Học kỳ */
CREATE TABLE HocKy (
    maHK INT PRIMARY KEY IDENTITY(1,1),
    tenHK NVARCHAR(50) NOT NULL, -- Học kỳ 1, Học kỳ 2
    heSo INT DEFAULT 1,
    maNH VARCHAR(10) NOT NULL,
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNH) REFERENCES NamHoc(maNH)
);
GO

/* Bảng Khối */
CREATE TABLE Khoi (
    maKhoi INT PRIMARY KEY IDENTITY(1,1),
    tenKhoi NVARCHAR(50) NOT NULL UNIQUE, -- Khối 10, 11, 12
    trangThai BIT DEFAULT 1
);
GO

/* Bảng Môn học */
CREATE TABLE MonHoc (
    maMH INT PRIMARY KEY IDENTITY(1,1),
    tenMH NVARCHAR(100) NOT NULL,
    soTiet INT DEFAULT 45, -- Số tiết quy định trong phân phối chương trình
    heSoMon INT DEFAULT 1, -- Hệ số môn (ví dụ Toán văn Anh hệ số 2)
    trangThai BIT DEFAULT 1
);
GO

/* ==========================================================================
   2. NHÓM NHÂN SỰ & LỚP HỌC
   ========================================================================== */

/* Bảng Giáo viên */
CREATE TABLE GiaoVien (
    maGV INT PRIMARY KEY IDENTITY(1,1),
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    sdt VARCHAR(15) UNIQUE,
    email VARCHAR(100) UNIQUE,
    diaChi NVARCHAR(MAX),
    maMonHocChuyenMon INT, -- Giáo viên chuyên dạy môn gì (để gợi ý phân công)
    userID INT UNIQUE, -- Liên kết tài khoản
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (userID) REFERENCES Users(userID),
    FOREIGN KEY (maMonHocChuyenMon) REFERENCES MonHoc(maMH)
);
GO

/* Bảng Lớp học */
CREATE TABLE LopHoc (
    maLop INT PRIMARY KEY IDENTITY(1,1),
    tenLop NVARCHAR(50) NOT NULL,
    maKhoi INT NOT NULL,
    maNH VARCHAR(10) NOT NULL,
    maGVCN INT, -- Giáo viên chủ nhiệm (QUAN TRỌNG)
    trangThai BIT DEFAULT 1,
    
    FOREIGN KEY (maKhoi) REFERENCES Khoi(maKhoi),
    FOREIGN KEY (maNH) REFERENCES NamHoc(maNH),
    FOREIGN KEY (maGVCN) REFERENCES GiaoVien(maGV),
    
    -- Ràng buộc: Trong 1 năm học, tên lớp không được trùng
    CONSTRAINT UQ_TenLop_NamHoc UNIQUE (tenLop, maNH)
);
GO

/* ==========================================================================
   3. NHÓM HỒ SƠ HỌC SINH (MỞ RỘNG)
   ========================================================================== */

/* Bảng Học sinh - Hồ sơ chi tiết */
CREATE TABLE HocSinh (
    maHS INT PRIMARY KEY IDENTITY(1,1),
    -- Thông tin cá nhân
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    noiSinh NVARCHAR(100), -- Mới
    danToc NVARCHAR(50),   -- Mới
    tonGiao NVARCHAR(50),  -- Mới
    diaChi NVARCHAR(MAX),
    
    -- Thông tin liên lạc
    email VARCHAR(100),
    sdtCaNhan VARCHAR(15),
    
    -- Thông tin gia đình (Mới - Để liên lạc)
    hoTenCha NVARCHAR(100),
    ngheNghiepCha NVARCHAR(100),
    sdtCha VARCHAR(15),
    hoTenMe NVARCHAR(100),
    ngheNghiepMe NVARCHAR(100),
    sdtMe VARCHAR(15),
    
    -- Thông tin học vụ
    maLop INT, -- Lớp hiện tại đang học
    userID INT UNIQUE,
    trangThaiHocTap NVARCHAR(50) DEFAULT N'Đang học', -- Đang học, Bảo lưu, Đuổi học, Tốt nghiệp
    trangThai BIT DEFAULT 1, -- Xóa mềm
    
    FOREIGN KEY (maLop) REFERENCES LopHoc(maLop),
    FOREIGN KEY (userID) REFERENCES Users(userID)
);
GO

/* ==========================================================================
   4. NHÓM THỜI KHÓA BIỂU & PHÂN CÔNG (MỚI)
   ========================================================================== */

/* Bảng Phân công giảng dạy (Ai dạy môn gì lớp nào) */
CREATE TABLE PhanCong (
    maPhanCong INT PRIMARY KEY IDENTITY(1,1),
    maGV INT NOT NULL,
    maLop INT NOT NULL,
    maMonHoc INT NOT NULL,
    maHocKy INT NOT NULL,
    trangThai BIT DEFAULT 1,
    
    FOREIGN KEY (maGV) REFERENCES GiaoVien(maGV),
    FOREIGN KEY (maLop) REFERENCES LopHoc(maLop),
    FOREIGN KEY (maMonHoc) REFERENCES MonHoc(maMH),
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK),
    
    -- Ràng buộc: Trong 1 học kỳ, 1 lớp, 1 môn chỉ có 1 giáo viên dạy chính
    CONSTRAINT UQ_PhanCong UNIQUE (maLop, maMonHoc, maHocKy)
);
GO

/* Bảng Thời khóa biểu (MỚI) */
CREATE TABLE ThoiKhoaBieu (
    maTKB INT PRIMARY KEY IDENTITY(1,1),
    maLop INT NOT NULL,
    maMonHoc INT NOT NULL,
    maGV INT NOT NULL, -- Giáo viên dạy tiết này (thường lấy từ bảng PhanCong sang)
    maHocKy INT NOT NULL,
    
    thu INT CHECK (thu BETWEEN 2 AND 8), -- Thứ 2 đến Chủ nhật (8)
    tiet INT CHECK (tiet BETWEEN 1 AND 12), -- Tiết 1 đến 12 (Sáng/Chiều)
    phongHoc NVARCHAR(50), -- Phòng học (nếu có thay đổi)
    
    FOREIGN KEY (maLop) REFERENCES LopHoc(maLop),
    FOREIGN KEY (maMonHoc) REFERENCES MonHoc(maMH),
    FOREIGN KEY (maGV) REFERENCES GiaoVien(maGV),
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK),
    
    -- Ràng buộc Logic:
    -- 1. Trong 1 học kỳ, vào thứ X tiết Y, Lớp Z chỉ học 1 môn
    CONSTRAINT UQ_TKB_Lop UNIQUE (maLop, maHocKy, thu, tiet),
    
    -- 2. Trong 1 học kỳ, vào thứ X tiết Y, Giáo viên Z chỉ dạy 1 lớp
    CONSTRAINT UQ_TKB_GiaoVien UNIQUE (maGV, maHocKy, thu, tiet)
);
GO

/* ==========================================================================
   5. NHÓM KẾT QUẢ HỌC TẬP & RÈN LUYỆN
   ========================================================================== */

/* Bảng Điểm chi tiết (Điểm môn học) */
CREATE TABLE DiemChiTiet (
    maDiem INT PRIMARY KEY IDENTITY(1,1),
    maHS INT NOT NULL,
    maMonHoc INT NOT NULL,
    maHocKy INT NOT NULL,
    
    -- Các cột điểm
    diemMieng_1 FLOAT, diemMieng_2 FLOAT, diemMieng_3 FLOAT,
    diem15p_1 FLOAT, diem15p_2 FLOAT, diem15p_3 FLOAT,
    diem1Tiet_1 FLOAT, diem1Tiet_2 FLOAT,
    diemThi FLOAT,
    
    -- Điểm trung bình môn (Tính toán tự động hoặc trigger)
    diemTBM FLOAT,
    
    FOREIGN KEY (maHS) REFERENCES HocSinh(maHS),
    FOREIGN KEY (maMonHoc) REFERENCES MonHoc(maMH),
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK),
    
    CONSTRAINT UQ_Diem_HocSinh UNIQUE (maHS, maMonHoc, maHocKy)
);
GO

/* Bảng Tổng kết học kỳ (Hạnh kiểm & Danh hiệu) - MỚI */
CREATE TABLE TongKetHocKy (
    maTongKet INT PRIMARY KEY IDENTITY(1,1),
    maHS INT NOT NULL,
    maHocKy INT NOT NULL,
    
    diemTrungBinhHocKy FLOAT, -- Tổng kết tất cả các môn
    hanhKiem NVARCHAR(20),    -- Tốt, Khá, Trung bình, Yếu
    hocLuc NVARCHAR(20),      -- Giỏi, Khá, Trung bình, Yếu, Kém
    nhanXetCuaGVCN NVARCHAR(MAX),
    
    FOREIGN KEY (maHS) REFERENCES HocSinh(maHS),
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK),
    
    CONSTRAINT UQ_TongKet UNIQUE (maHS, maHocKy)
);
GO

/* Bảng Thông báo */
CREATE TABLE ThongBao (
    maTB INT PRIMARY KEY IDENTITY(1,1),
    tieuDe NVARCHAR(255) NOT NULL,
    noiDung NVARCHAR(MAX) NOT NULL,
    ngayDang DATETIME DEFAULT GETDATE(),
    maNguoiTao INT NOT NULL, 
    trangThai BIT DEFAULT 1,
    FOREIGN KEY (maNguoiTao) REFERENCES Users(userID)
);
GO
