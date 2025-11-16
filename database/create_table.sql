IF DB_ID('db_quanlyhocsinh') IS NOT NULL
BEGIN
    ALTER DATABASE db_quanlyhocsinh SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE db_quanlyhocsinh;
END
GO

CREATE DATABASE db_quanlyhocsinh;
GO

USE db_quanlyhocsinh;
GO


/* QUẢN LÝ KHỐI */
CREATE TABLE Khoi (
    maKhoi INT PRIMARY KEY IDENTITY(1,1),
    tenKhoi NVARCHAR(50) NOT NULL UNIQUE
);
GO

/* QUẢN LÝ NĂM HỌC */
CREATE TABLE NamHoc (
    maNH VARCHAR(10) PRIMARY KEY, 
    tenNH NVARCHAR(100) NOT NULL,
    ngayBatDau DATE,
    ngayKetThuc DATE
);
GO

/*QUẢN LÝ MÔN HỌC */
CREATE TABLE MonHoc (
    maMH INT PRIMARY KEY IDENTITY(1,1),
    tenMH NVARCHAR(100) NOT NULL,
    soTiet INT
);
GO

/* QUẢN LÝ PHÂN QUYỀN (ROLES) */
CREATE TABLE Roles (
    roleID INT PRIMARY KEY IDENTITY(1,1),
    roleName NVARCHAR(50) NOT NULL UNIQUE 
);
GO

/* QUẢN LÝ GIÁO VIÊN */
CREATE TABLE GiaoVien (
    maGV INT PRIMARY KEY IDENTITY(1,1),
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    chuyenMon NVARCHAR(100),
    email VARCHAR(100) UNIQUE,
    sdt VARCHAR(15) UNIQUE,
    diaChi NVARCHAR(MAX)
);
GO

CREATE TABLE HocKy (
    maHK INT PRIMARY KEY IDENTITY(1,1),
    tenHK NVARCHAR(50) NOT NULL, 
    heSo INT DEFAULT 1,
    maNH VARCHAR(10) NOT NULL,
    FOREIGN KEY (maNH) REFERENCES NamHoc(maNH)
);
GO

/* QUẢN LÝ NGƯỜI DÙNG (USERS) */
CREATE TABLE Users (
    userID INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    roleID INT NOT NULL,
    FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);
GO

/*QUẢN LÝ LỚP HỌC */
CREATE TABLE LopHoc (
    maLop INT PRIMARY KEY IDENTITY(1,1),
    tenLop NVARCHAR(50) NOT NULL,
    maKhoi INT NOT NULL,
    maNH VARCHAR(10) NOT NULL,
    maGVCN INT,
    FOREIGN KEY (maKhoi) REFERENCES Khoi(maKhoi),
    FOREIGN KEY (maNH) REFERENCES NamHoc(maNH),
    FOREIGN KEY (maGVCN) REFERENCES GiaoVien(maGV) ON DELETE SET NULL
);
GO

/*QUẢN LÝ HỌC SINH */
CREATE TABLE HocSinh (
    maHS INT PRIMARY KEY IDENTITY(1,1),
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10), 
    diaChi NVARCHAR(MAX),
    email VARCHAR(100) UNIQUE,
    sdtPhuHuynh VARCHAR(15),
    maLop INT,
    FOREIGN KEY (maLop) REFERENCES LopHoc(maLop) ON DELETE SET NULL
);
GO

/* QUẢN LÝ THÔNG BÁO */
CREATE TABLE ThongBao (
    maTB INT PRIMARY KEY IDENTITY(1,1),
    tieuDe NVARCHAR(255) NOT NULL,
    noiDung NVARCHAR(MAX) NOT NULL,
    ngayDang DATETIME DEFAULT GETDATE(),
    maNguoiTao INT NOT NULL, 
    FOREIGN KEY (maNguoiTao) REFERENCES Users(userID)
);
GO

/* QUẢN LÝ ĐIỂM */
CREATE TABLE BangDiem (
    maHS INT NOT NULL,
    maMonHoc INT NOT NULL,
    maHocKy INT NOT NULL,
    diemMieng FLOAT,
    diem15p_1 FLOAT,
    diem15p_2 FLOAT,
    diem1Tiet_1 FLOAT,
    diem1Tiet_2 FLOAT,
    diemThi FLOAT,
    diemTBMon FLOAT, 
    PRIMARY KEY (maHS, maMonHoc, maHocKy), 
    FOREIGN KEY (maHS) REFERENCES HocSinh(maHS) ON DELETE CASCADE,
    FOREIGN KEY (maMonHoc) REFERENCES MonHoc(maMH) ON DELETE CASCADE,
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK) ON DELETE CASCADE
);
GO

/* PHÂN CÔNG GIẢNG DẠY */
CREATE TABLE PhanCong (
    maPhanCong INT PRIMARY KEY IDENTITY(1,1),
    maGV INT NOT NULL,
    maLop INT NOT NULL,
    maMonHoc INT NOT NULL,
    maHocKy INT NOT NULL,
    CONSTRAINT uq_phancong UNIQUE (maGV, maLop, maMonHoc, maHocKy), -- Đảm bảo không trùng lặp
    FOREIGN KEY (maGV) REFERENCES GiaoVien(maGV),
    FOREIGN KEY (maLop) REFERENCES LopHoc(maLop),
    FOREIGN KEY (maMonHoc) REFERENCES MonHoc(maMH),
    FOREIGN KEY (maHocKy) REFERENCES HocKy(maHK)
);
GO


ALTER TABLE HocSinh
ADD userID INT NULL,
CONSTRAINT fk_hocsinh_user
    FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE SET NULL;
GO

ALTER TABLE GiaoVien
ADD userID INT NULL,
CONSTRAINT fk_giaovien_user
    FOREIGN KEY (userID) REFERENCES Users(userID) ON DELETE SET NULL;
GO


-- =================================================================
-- =================================================================
-- THÊM DỮ LIỆU MẪU 
-- =================================================================
-- =================================================================

SET IDENTITY_INSERT Khoi ON;
INSERT INTO Khoi (maKhoi, tenKhoi) VALUES
(6, N'Khối 6'),
(7, N'Khối 7'),
(8, N'Khối 8'),
(9, N'Khối 9'),
(10, N'Khối 10');
SET IDENTITY_INSERT Khoi OFF;
GO

INSERT INTO NamHoc (maNH, tenNH, ngayBatDau, ngayKetThuc) VALUES
('2022-2023', N'Năm học 2022-2023', '2022-09-05', '2023-05-31'),
('2023-2024', N'Năm học 2023-2024', '2023-09-05', '2024-05-31'),
('2024-2025', N'Năm học 2024-2025', '2024-09-05', '2025-05-31'),
('2025-2026', N'Năm học 2025-2026', '2025-09-05', '2026-05-31'),
('2026-2027', N'Năm học 2026-2027', '2026-09-05', '2027-05-31');
GO

INSERT INTO MonHoc (tenMH, soTiet) VALUES
(N'Toán', 120),
(N'Vật lý', 80),
(N'Hóa học', 80),
(N'Ngữ văn', 120),
(N'Tiếng Anh', 100);
GO

SET IDENTITY_INSERT Roles ON;
INSERT INTO Roles (roleID, roleName) VALUES
(1, N'Admin'),
(2, N'GiaoVien'),
(3, N'HocSinh');
SET IDENTITY_INSERT Roles OFF;
GO

INSERT INTO Users (username, password_hash, roleID) VALUES
('admin', '123', 1), 
('gv01', '123', 2),
('gv02', '123', 2),
('hs01', '123', 3),
('hs02', '123', 3);
GO

SET IDENTITY_INSERT GiaoVien ON;
INSERT INTO GiaoVien (maGV, hoTen, ngaySinh, gioiTinh, chuyenMon, email, sdt, diaChi, userID) VALUES
(101, N'Nguyễn Văn An', '1980-05-20', N'Nam', N'Toán', 'an.nv@email.com', '0912345601', N'Hà Nội', 2),
(102, N'Trần Thị Bình', '1985-10-15', N'Nữ', N'Ngữ văn', 'binh.tt@email.com', '0912345602', N'Hải Phòng', 3),
(103, N'Lê Văn Cường', '1990-01-30', N'Nam', N'Vật lý', 'cuong.lv@email.com', '0912345603', N'Đà Nẵng', NULL),
(104, N'Phạm Thị Dung', '1988-11-02', N'Nữ', N'Tiếng Anh', 'dung.pt@email.com', '0912345604', N'TP. HCM', NULL),
(105, N'Hoàng Minh Em', '1992-07-12', N'Nam', N'Hóa học', 'em.hm@email.com', '0912345605', N'Cần Thơ', NULL);
SET IDENTITY_INSERT GiaoVien OFF;
GO

INSERT INTO HocKy (tenHK, heSo, maNH) VALUES
(N'Học kỳ 1', 1, '2024-2025'),
(N'Học kỳ 2', 2, '2024-2025'),
(N'Học kỳ 1', 1, '2023-2024'),
(N'Học kỳ 2', 2, '2023-2024'),
(N'Học kỳ 1', 1, '2022-2023');
GO

INSERT INTO LopHoc (tenLop, maKhoi, maNH, maGVCN) VALUES
(N'10A1', 10, '2024-2025', 101),
(N'10A2', 10, '2024-2025', 103), 
(N'9A1', 9, '2024-2025', 102),  
(N'10A1', 10, '2023-2024', 101), 
(N'9A1', 9, '2023-2024', 104);  
GO

SET IDENTITY_INSERT HocSinh ON;
INSERT INTO HocSinh (maHS, hoTen, ngaySinh, gioiTinh, diaChi, email, sdtPhuHuynh, maLop, userID) VALUES
(1001, N'Nguyễn Văn Hùng', '2009-03-10', N'Nam', N'123 Đường A', 'hung.nv@email.com', '0987654321', 1, 4),
(1002, N'Trần Thị Lan', '2009-06-15', N'Nữ', N'456 Đường B', 'lan.tt@email.com', '0987654322', 1, 5),
(1003, N'Lê Văn Minh', '2009-09-20', N'Nam', N'789 Đường C', 'minh.lv@email.com', '0987654323', 2, NULL),
(1004, N'Phạm Thị Oanh', '2010-01-25', N'Nữ', N'321 Đường D', 'oanh.pt@email.com', '0987654324', 3, NULL),
(1005, N'Hoàng Văn Phúc', '2010-04-30', N'Nam', N'654 Đường E', 'phuc.hv@email.com', '0987654325', 3, NULL);
SET IDENTITY_INSERT HocSinh OFF;
GO

INSERT INTO PhanCong (maGV, maLop, maMonHoc, maHocKy) VALUES
(101, 1, 1, 1), 
(103, 1, 2, 1), 
(105, 1, 3, 1), 
(102, 1, 4, 1), 
(104, 1, 5, 1); 
GO

INSERT INTO ThongBao (tieuDe, noiDung, maNguoiTao) VALUES
(N'Thông báo nghỉ lễ 30/4', N'Học sinh toàn trường được nghỉ lễ 30/4-1/5.', 1), 
(N'Kế hoạch thi Học kỳ 1', N'Nhà trường thông báo kế hoạch thi HK1 năm 2024-2025...', 1), 
(N'Nộp bài tập Toán', N'Các em lớp 10A1 nộp bài tập Toán trước ngày 15/10.', 2), 
(N'Thay đổi giờ học Văn', N'Lớp 10A1 đổi giờ học Văn sang tiết 3 sáng thứ 4.', 3), 
(N'Học bổng tài năng', N'Thông báo về chương trình học bổng tài năng 2025...', 1); 
GO

INSERT INTO BangDiem (maHS, maMonHoc, maHocKy, diemMieng, diem15p_1, diem1Tiet_1, diemThi) VALUES
(1001, 1, 1, 8, 9, 8.5, 9),
(1001, 2, 1, 7, 8, 7.5, 8), 
(1001, 3, 1, 9, 9, 9.5, 9), 
(1002, 1, 1, 9, 10, 9.5, 10), 
(1002, 2, 1, 8, 8, 8.5, 8); 
GO

-- Cập nhật trạng thái cho csdl
USE db_quanlyhocsinh;
GO

-- 1. Cập nhật bảng KHOI
ALTER TABLE Khoi 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 2. Cập nhật bảng NAMHOC
ALTER TABLE NamHoc 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 3. Cập nhật bảng MONHOC
ALTER TABLE MonHoc 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 4. Cập nhật bảng ROLES
ALTER TABLE Roles 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 5. Cập nhật bảng HOCKY
ALTER TABLE HocKy 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 6. Cập nhật bảng USERS
ALTER TABLE Users 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 7. Cập nhật bảng GIAOVIEN
ALTER TABLE GiaoVien 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 8. Cập nhật bảng LOPHOC
ALTER TABLE LopHoc 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 9. Cập nhật bảng HOCSINH
ALTER TABLE HocSinh 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 10. Cập nhật bảng THONGBAO
ALTER TABLE ThongBao 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- 11. Cập nhật bảng PHANCONG
ALTER TABLE PhanCong 
ADD trangThai BIT NOT NULL DEFAULT 1;
GO

-- Kiểm tra lại kết quả
SELECT maGV, hoTen, trangThai FROM GiaoVien;
SELECT maKhoi, tenKhoi, trangThai FROM Khoi;
