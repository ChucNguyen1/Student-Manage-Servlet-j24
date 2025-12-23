USE [master]
GO
/****** Object:  Database [db_quanlyhocsinh_v2]    Script Date: 12/15/2025 7:56:34 PM ******/
CREATE DATABASE [db_quanlyhocsinh_v2]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'db_quanlyhocsinh_v2', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.NGUYENCHUC\MSSQL\DATA\db_quanlyhocsinh_v2.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'db_quanlyhocsinh_v2_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.NGUYENCHUC\MSSQL\DATA\db_quanlyhocsinh_v2_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [db_quanlyhocsinh_v2].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ARITHABORT OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET  ENABLE_BROKER 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET  MULTI_USER 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET DB_CHAINING OFF 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET QUERY_STORE = ON
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [db_quanlyhocsinh_v2]
GO
/****** Object:  Table [dbo].[ToBoMon]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ToBoMon](
	[maTo] [int] IDENTITY(1,1) NOT NULL,
	[tenTo] [nvarchar](100) NOT NULL,
	[moTa] [nvarchar](max) NULL,
	[maToTruong] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maTo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhanCong]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhanCong](
	[maLop] [int] NOT NULL,
	[maMonHoc] [int] NOT NULL,
	[maHocKy] [int] NOT NULL,
	[maGV] [int] NOT NULL,
 CONSTRAINT [PK_PhanCong] PRIMARY KEY CLUSTERED 
(
	[maLop] ASC,
	[maMonHoc] ASC,
	[maHocKy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HocKy]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HocKy](
	[maHK] [int] IDENTITY(1,1) NOT NULL,
	[tenHK] [nvarchar](50) NOT NULL,
	[heSo] [int] NULL,
	[maNH] [varchar](10) NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maHK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MonHoc]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MonHoc](
	[maMH] [int] IDENTITY(1,1) NOT NULL,
	[tenMH] [nvarchar](100) NOT NULL,
	[soTiet] [int] NULL,
	[heSoMon] [int] NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GiaoVien]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GiaoVien](
	[maGV] [int] IDENTITY(1,1) NOT NULL,
	[hoTen] [nvarchar](100) NOT NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](10) NULL,
	[sdt] [varchar](15) NULL,
	[email] [varchar](100) NULL,
	[diaChi] [nvarchar](max) NULL,
	[maMonHocChuyenMon] [int] NULL,
	[userID] [int] NULL,
	[trangThai] [bit] NULL,
	[maTo] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maGV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LopHoc]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LopHoc](
	[maLop] [int] IDENTITY(1,1) NOT NULL,
	[tenLop] [nvarchar](50) NOT NULL,
	[maKhoi] [int] NOT NULL,
	[maNH] [varchar](10) NOT NULL,
	[maGVCN] [int] NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maLop] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vw_PhanCong]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[vw_PhanCong] AS
SELECT 
    pc.maLop,
    lh.tenLop,
    pc.maMonHoc,
    mh.tenMH,
    pc.maHocKy,
    hk.tenHK,
    pc.maGV,
    gv.hoTen AS tenGiaoVien,
    gv.email AS emailGV,
    tb.tenTo AS tenToBoMon
FROM PhanCong pc
INNER JOIN LopHoc lh ON pc.maLop = lh.maLop
INNER JOIN MonHoc mh ON pc.maMonHoc = mh.maMH
INNER JOIN HocKy hk ON pc.maHocKy = hk.maHK
INNER JOIN GiaoVien gv ON pc.maGV = gv.maGV
LEFT JOIN ToBoMon tb ON gv.maTo = tb.maTo;
GO
/****** Object:  Table [dbo].[ThoiKhoaBieu]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ThoiKhoaBieu](
	[maTKB] [int] IDENTITY(1,1) NOT NULL,
	[maLop] [int] NOT NULL,
	[maHocKy] [int] NOT NULL,
	[maMonHoc] [int] NOT NULL,
	[maGV] [int] NOT NULL,
	[thu] [int] NOT NULL,
	[tiet] [int] NOT NULL,
	[phongHoc] [nvarchar](50) NULL,
	[createdAt] [datetime] NULL,
	[updatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[maTKB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[vw_ThoiKhoaBieu]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 5. Tạo View để dễ dàng query (JOIN các bảng)
CREATE VIEW [dbo].[vw_ThoiKhoaBieu] AS
SELECT 
    tkb.maTKB,
    tkb.maLop,
    lh.tenLop,
    tkb.maHocKy,
    hk.tenHK,
    tkb.maMonHoc,
    mh.tenMH,
    tkb.maGV,
    gv.hoTen AS tenGV,
    tkb.thu,
    tkb.tiet,
    tkb.phongHoc,
    tkb.createdAt,
    tkb.updatedAt,
    -- Thêm tên thứ để dễ hiển thị
    CASE tkb.thu
        WHEN 2 THEN N'Thứ Hai'
        WHEN 3 THEN N'Thứ Ba'
        WHEN 4 THEN N'Thứ Tư'
        WHEN 5 THEN N'Thứ Năm'
        WHEN 6 THEN N'Thứ Sáu'
        WHEN 7 THEN N'Thứ Bảy'
    END AS tenThu
FROM ThoiKhoaBieu tkb
INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop
INNER JOIN HocKy hk ON tkb.maHocKy = hk.maHK
INNER JOIN MonHoc mh ON tkb.maMonHoc = mh.maMH
INNER JOIN GiaoVien gv ON tkb.maGV = gv.maGV;
GO
/****** Object:  Table [dbo].[DiemChiTiet]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DiemChiTiet](
	[maDiem] [int] IDENTITY(1,1) NOT NULL,
	[maHS] [int] NOT NULL,
	[maMonHoc] [int] NOT NULL,
	[maHocKy] [int] NOT NULL,
	[diemMieng_1] [float] NULL,
	[diemMieng_2] [float] NULL,
	[diemMieng_3] [float] NULL,
	[diem15p_1] [float] NULL,
	[diem15p_2] [float] NULL,
	[diem15p_3] [float] NULL,
	[diem1Tiet_1] [float] NULL,
	[diem1Tiet_2] [float] NULL,
	[diemThi] [float] NULL,
	[diemTBM] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[maDiem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HocSinh]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HocSinh](
	[maHS] [int] IDENTITY(1,1) NOT NULL,
	[hoTen] [nvarchar](100) NOT NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](10) NULL,
	[noiSinh] [nvarchar](100) NULL,
	[danToc] [nvarchar](50) NULL,
	[tonGiao] [nvarchar](50) NULL,
	[diaChi] [nvarchar](max) NULL,
	[email] [varchar](100) NULL,
	[sdtCaNhan] [varchar](15) NULL,
	[hoTenCha] [nvarchar](100) NULL,
	[ngheNghiepCha] [nvarchar](100) NULL,
	[sdtCha] [varchar](15) NULL,
	[hoTenMe] [nvarchar](100) NULL,
	[ngheNghiepMe] [nvarchar](100) NULL,
	[sdtMe] [varchar](15) NULL,
	[maLop] [int] NULL,
	[userID] [int] NULL,
	[trangThaiHocTap] [nvarchar](50) NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maHS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Khoi]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Khoi](
	[maKhoi] [int] IDENTITY(1,1) NOT NULL,
	[tenKhoi] [nvarchar](50) NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maKhoi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NamHoc]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NamHoc](
	[maNH] [varchar](10) NOT NULL,
	[tenNH] [nvarchar](100) NOT NULL,
	[ngayBatDau] [date] NULL,
	[ngayKetThuc] [date] NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maNH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[roleID] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [nvarchar](50) NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[maTK] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[role] [nvarchar](20) NOT NULL,
	[maGV] [int] NULL,
	[maHS] [int] NULL,
	[isActive] [bit] NOT NULL,
	[createdAt] [datetime] NOT NULL,
	[updatedAt] [datetime] NOT NULL,
 CONSTRAINT [PK_TaiKhoan] PRIMARY KEY CLUSTERED 
(
	[maTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ThongBao]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ThongBao](
	[maTB] [int] IDENTITY(1,1) NOT NULL,
	[tieuDe] [nvarchar](255) NOT NULL,
	[noiDung] [nvarchar](max) NOT NULL,
	[ngayDang] [datetime] NULL,
	[maNguoiTao] [int] NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maTB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TongKetHocKy]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TongKetHocKy](
	[maTongKet] [int] IDENTITY(1,1) NOT NULL,
	[maHS] [int] NOT NULL,
	[maHocKy] [int] NOT NULL,
	[diemTrungBinhHocKy] [float] NULL,
	[hanhKiem] [nvarchar](20) NULL,
	[hocLuc] [nvarchar](20) NULL,
	[nhanXetCuaGVCN] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[maTongKet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 12/15/2025 7:56:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password_hash] [varchar](255) NOT NULL,
	[avatar] [nvarchar](255) NULL,
	[roleID] [int] NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[GiaoVien] ON 

INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (1, N'Nguyễn Văn An', CAST(N'1985-03-15' AS Date), N'Nam', N'0912345671', N'nva@school.edu.vn', N'123 Lê Lợi, Q1, HCM', 1, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (2, N'Trần Thị Bình', CAST(N'1988-07-20' AS Date), N'Nữ', N'0912345672', N'ttb@school.edu.vn', N'456 Nguyễn Huệ, Q1, HCM', 1, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (3, N'Lê Văn Cường', CAST(N'1982-11-10' AS Date), N'Nam', N'0912345673', N'lvc@school.edu.vn', N'789 Điện Biên Phủ, Q3, HCM', 2, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (4, N'Phạm Thị Dung', CAST(N'1987-05-12' AS Date), N'Nữ', N'0912345674', N'ptd@school.edu.vn', N'321 CMT8, Q10, HCM', 2, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (5, N'Hoàng Văn Em', CAST(N'1990-09-25' AS Date), N'Nam', N'0912345675', N'hve@school.edu.vn', N'654 Lý Thường Kiệt, Q5, HCM', 2, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (6, N'Võ Thị Phượng', CAST(N'1989-02-14' AS Date), N'Nữ', N'0912345676', N'vtp@school.edu.vn', N'987 Võ Văn Tần, Q3, HCM', 3, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (7, N'Đặng Văn Giang', CAST(N'1986-12-30' AS Date), N'Nam', N'0912345677', N'dvg@school.edu.vn', N'147 Pasteur, Q1, HCM', 3, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (8, N'Ngô Thị Hoa', CAST(N'1984-08-18' AS Date), N'Nữ', N'0912345678', N'nth@school.edu.vn', N'258 Hai Bà Trưng, Q1, HCM', 4, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (9, N'Bùi Văn Hùng', CAST(N'1983-04-22' AS Date), N'Nam', N'0912345679', N'bvh@school.edu.vn', N'369 Trần Hưng Đạo, Q1, HCM', 4, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (10, N'Đinh Thị Lan', CAST(N'1991-06-08' AS Date), N'Nữ', N'0912345680', N'dtl@school.edu.vn', N'741 NTMK, Q3, HCM', 6, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (11, N'Trương Văn Minh', CAST(N'1988-10-05' AS Date), N'Nam', N'0912345681', N'tvm@school.edu.vn', N'852 Lê Văn Sỹ, Q3, HCM', 7, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (12, N'Mai Thị Nga', CAST(N'1992-01-20' AS Date), N'Nữ', N'0912345682', N'mtn@school.edu.vn', N'963 Phan Đình Phùng, Q1, HCM', 8, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (13, N'Lý Văn Phong', CAST(N'1987-07-15' AS Date), N'Nam', N'0912345683', N'lvp@school.edu.vn', N'159 Võ Thị Sáu, Q3, HCM', 11, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (14, N'Phan Thị Quỳnh', CAST(N'1990-03-28' AS Date), N'Nữ', N'0912345684', N'ptq@school.edu.vn', N'357 NKKN, Q1, HCM', 11, NULL, 1, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (15, N'Huỳnh Văn Sơn', CAST(N'1985-11-11' AS Date), N'Nam', N'0912345685', N'hvs@school.edu.vn', N'468 BTX, Q3, HCM', 10, NULL, 0, NULL)
INSERT [dbo].[GiaoVien] ([maGV], [hoTen], [ngaySinh], [gioiTinh], [sdt], [email], [diaChi], [maMonHocChuyenMon], [userID], [trangThai], [maTo]) VALUES (16, N'Nguyễn Văn Tuấn', CAST(N'2000-01-11' AS Date), N'Nam', N'1234567890', N'123abc@gmail.com', N'Vinh, Nghệ An', NULL, NULL, 1, 4)
SET IDENTITY_INSERT [dbo].[GiaoVien] OFF
GO
SET IDENTITY_INSERT [dbo].[HocKy] ON 

INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (1, N'Học kỳ 1', 1, N'2023-2024', 1)
INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (2, N'Học kỳ 2', 1, N'2023-2024', 1)
INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (3, N'Học kỳ 1', 1, N'2024-2025', 1)
INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (4, N'Học kỳ 2', 1, N'2024-2025', 1)
INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (5, N'Học kỳ 1', 1, N'2025-2026', 1)
INSERT [dbo].[HocKy] ([maHK], [tenHK], [heSo], [maNH], [trangThai]) VALUES (6, N'Học kỳ 2', 1, N'2025-2026', 1)
SET IDENTITY_INSERT [dbo].[HocKy] OFF
GO
SET IDENTITY_INSERT [dbo].[HocSinh] ON 

INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (1, N'Nguyễn Văn Anh', CAST(N'2009-01-15' AS Date), N'Nam', NULL, NULL, NULL, N'HCM', N'nva.hs@edu.vn', N'0901234561', N'Cha 1', NULL, N'09001', NULL, NULL, NULL, 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (2, N'Trần Thị Bích', CAST(N'2009-03-20' AS Date), N'Nữ', NULL, NULL, NULL, N'HCM', N'ttb.hs@edu.vn', N'0901234562', N'Cha 2', NULL, N'09002', NULL, NULL, NULL, 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (3, N'Lê Văn Cường', CAST(N'2009-05-10' AS Date), N'Nam', NULL, NULL, NULL, N'HCM', N'lvc.hs@edu.vn', N'0901234563', N'Cha 3', NULL, N'09003', NULL, NULL, NULL, 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (4, N'Phạm Thị Dung', CAST(N'2009-07-25' AS Date), N'Nữ', NULL, NULL, NULL, N'HCM', N'ptd.hs@edu.vn', N'0901234564', N'Cha 4', NULL, N'09004', NULL, NULL, NULL, 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (5, N'Hoàng Văn Em', CAST(N'2009-09-12' AS Date), N'Nam', NULL, NULL, NULL, N'HCM', N'hve.hs@edu.vn', N'0901234565', N'Cha 5', NULL, N'09005', NULL, NULL, NULL, 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (6, N'Trương Văn Minh', CAST(N'2009-01-20' AS Date), N'Nam', N'', N'', N'', N'HCM', N'tvm.hs@edu.vn', N'0901234571', N'Cha 6', N'', N'09006', N'', N'', N'', 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (7, N'Mai Thị Nga', CAST(N'2009-03-15' AS Date), N'Nữ', N'', N'', N'', N'HCM', N'mtn.hs@edu.vn', N'0901234572', N'Cha 7', N'', N'09007', N'', N'', N'', 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (8, N'Lý Văn Phong', CAST(N'2009-05-28' AS Date), N'Nam', N'', N'', N'', N'HCM', N'lvp.hs@edu.vn', N'0901234573', N'Cha 8', N'', N'09008', N'', N'', N'', 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (9, N'Nguyễn Minh Anh', CAST(N'2008-02-12' AS Date), N'Nam', N'', N'', N'', N'HCM', N'nma.hs@edu.vn', N'0901234581', N'Cha 9', N'', N'09009', N'', N'', N'', 1, NULL, N'Đang học', 1)
INSERT [dbo].[HocSinh] ([maHS], [hoTen], [ngaySinh], [gioiTinh], [noiSinh], [danToc], [tonGiao], [diaChi], [email], [sdtCaNhan], [hoTenCha], [ngheNghiepCha], [sdtCha], [hoTenMe], [ngheNghiepMe], [sdtMe], [maLop], [userID], [trangThaiHocTap], [trangThai]) VALUES (10, N'Võ Đức Anh', CAST(N'2007-03-10' AS Date), N'Nam', N'Nghệ An', N'Kinh', N'không', N'HCM', N'vda.hs@edu.vn', N'0901234591', N'Cha 10', N'', N'09010', N'', N'', N'', 1, NULL, N'Đang học', 1)
SET IDENTITY_INSERT [dbo].[HocSinh] OFF
GO
SET IDENTITY_INSERT [dbo].[Khoi] ON 

INSERT [dbo].[Khoi] ([maKhoi], [tenKhoi], [trangThai]) VALUES (1, N'Khối 10', 1)
INSERT [dbo].[Khoi] ([maKhoi], [tenKhoi], [trangThai]) VALUES (2, N'Khối 11', 1)
INSERT [dbo].[Khoi] ([maKhoi], [tenKhoi], [trangThai]) VALUES (3, N'Khối 12', 1)
SET IDENTITY_INSERT [dbo].[Khoi] OFF
GO
SET IDENTITY_INSERT [dbo].[LopHoc] ON 

INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (1, N'10A1', 1, N'2024-2025', 1, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (2, N'10A2', 1, N'2024-2025', 2, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (3, N'10A3', 1, N'2024-2025', 4, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (4, N'11A1', 2, N'2024-2025', 6, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (5, N'11A2', 2, N'2024-2025', 8, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (6, N'12A1', 3, N'2024-2025', 11, 1)
INSERT [dbo].[LopHoc] ([maLop], [tenLop], [maKhoi], [maNH], [maGVCN], [trangThai]) VALUES (7, N'12A2', 3, N'2024-2025', 13, 1)
SET IDENTITY_INSERT [dbo].[LopHoc] OFF
GO
SET IDENTITY_INSERT [dbo].[MonHoc] ON 

INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (1, N'Toán', 5, 2, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (2, N'Ngữ văn', 5, 2, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (3, N'Tiếng Anh', 3, 2, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (4, N'Vật lý', 3, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (5, N'Hóa học', 3, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (6, N'Sinh học', 2, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (7, N'Lịch sử', 2, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (8, N'Địa lý', 2, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (9, N'GDCD', 1, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (10, N'Tin học', 2, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (11, N'Thể dục', 2, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (12, N'Công nghệ', 1, 1, 1)
INSERT [dbo].[MonHoc] ([maMH], [tenMH], [soTiet], [heSoMon], [trangThai]) VALUES (13, N'Toán 12', 50, 1, 1)
SET IDENTITY_INSERT [dbo].[MonHoc] OFF
GO
INSERT [dbo].[NamHoc] ([maNH], [tenNH], [ngayBatDau], [ngayKetThuc], [trangThai]) VALUES (N'2023-2024', N'Năm học 2023-2024', CAST(N'2023-09-01' AS Date), CAST(N'2024-05-31' AS Date), 1)
INSERT [dbo].[NamHoc] ([maNH], [tenNH], [ngayBatDau], [ngayKetThuc], [trangThai]) VALUES (N'2024-2025', N'Năm học 2024-2025', CAST(N'2024-09-01' AS Date), CAST(N'2025-05-31' AS Date), 1)
INSERT [dbo].[NamHoc] ([maNH], [tenNH], [ngayBatDau], [ngayKetThuc], [trangThai]) VALUES (N'2025-2026', N'Năm học 2025-2026', CAST(N'2025-09-01' AS Date), CAST(N'2026-05-31' AS Date), 1)
GO
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 1, 3, 1)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (2, 1, 3, 2)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 10, 3, 3)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 2, 3, 4)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (2, 2, 3, 5)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 3, 3, 6)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (2, 3, 3, 7)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 4, 3, 8)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 5, 3, 9)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (2, 4, 3, 9)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 6, 3, 10)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 7, 3, 11)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 9, 3, 11)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 8, 3, 12)
INSERT [dbo].[PhanCong] ([maLop], [maMonHoc], [maHocKy], [maGV]) VALUES (1, 11, 3, 13)
GO
SET IDENTITY_INSERT [dbo].[Roles] ON 

INSERT [dbo].[Roles] ([roleID], [roleName], [trangThai]) VALUES (1, N'Admin', 1)
INSERT [dbo].[Roles] ([roleID], [roleName], [trangThai]) VALUES (2, N'GiaoVien', 1)
INSERT [dbo].[Roles] ([roleID], [roleName], [trangThai]) VALUES (3, N'HocSinh', 1)
INSERT [dbo].[Roles] ([roleID], [roleName], [trangThai]) VALUES (4, N'PhuHuynh', 1)
INSERT [dbo].[Roles] ([roleID], [roleName], [trangThai]) VALUES (5, N'GiaoVu', 1)
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[TaiKhoan] ON 

INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (1, N'admin', N'admin123', N'ADMIN', NULL, NULL, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (2, N'giaovu', N'giaovu123', N'ADMIN', NULL, NULL, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (3, N'gv1', N'gv123', N'GIAOVIEN', 1, NULL, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (4, N'gv2', N'gv123', N'GIAOVIEN', 2, NULL, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (5, N'hs1', N'hs123', N'HOCSINH', NULL, 1, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[TaiKhoan] ([maTK], [username], [password], [role], [maGV], [maHS], [isActive], [createdAt], [updatedAt]) VALUES (6, N'hs2', N'hs123', N'HOCSINH', NULL, 2, 1, CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
SET IDENTITY_INSERT [dbo].[TaiKhoan] OFF
GO
SET IDENTITY_INSERT [dbo].[ThoiKhoaBieu] ON 

INSERT [dbo].[ThoiKhoaBieu] ([maTKB], [maLop], [maHocKy], [maMonHoc], [maGV], [thu], [tiet], [phongHoc], [createdAt], [updatedAt]) VALUES (16, 1, 3, 1, 1, 2, 1, N'A101', CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[ThoiKhoaBieu] ([maTKB], [maLop], [maHocKy], [maMonHoc], [maGV], [thu], [tiet], [phongHoc], [createdAt], [updatedAt]) VALUES (17, 1, 3, 1, 1, 2, 2, N'A101', CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[ThoiKhoaBieu] ([maTKB], [maLop], [maHocKy], [maMonHoc], [maGV], [thu], [tiet], [phongHoc], [createdAt], [updatedAt]) VALUES (18, 1, 3, 2, 4, 2, 3, N'A101', CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
INSERT [dbo].[ThoiKhoaBieu] ([maTKB], [maLop], [maHocKy], [maMonHoc], [maGV], [thu], [tiet], [phongHoc], [createdAt], [updatedAt]) VALUES (19, 1, 3, 3, 6, 2, 4, N'A101', CAST(N'2025-12-14T16:08:27.723' AS DateTime), CAST(N'2025-12-14T16:08:27.723' AS DateTime))
SET IDENTITY_INSERT [dbo].[ThoiKhoaBieu] OFF
GO
SET IDENTITY_INSERT [dbo].[ThongBao] ON 

INSERT [dbo].[ThongBao] ([maTB], [tieuDe], [noiDung], [ngayDang], [maNguoiTao], [trangThai]) VALUES (1, N'Thông báo Khai giảng', N'Lịch khai giảng năm học mới vào ngày 05/09...', CAST(N'2025-11-29T22:08:53.970' AS DateTime), 1, 1)
INSERT [dbo].[ThongBao] ([maTB], [tieuDe], [noiDung], [ngayDang], [maNguoiTao], [trangThai]) VALUES (2, N'Lịch nghỉ Tết', N'Học sinh toàn trường nghỉ tết từ ngày...', CAST(N'2025-11-29T22:08:53.970' AS DateTime), 1, 1)
INSERT [dbo].[ThongBao] ([maTB], [tieuDe], [noiDung], [ngayDang], [maNguoiTao], [trangThai]) VALUES (3, N'Đăng ký thi đua', N'Các lớp nộp danh sách thi đua trước ngày...', CAST(N'2025-11-29T22:08:53.970' AS DateTime), 1, 1)
INSERT [dbo].[ThongBao] ([maTB], [tieuDe], [noiDung], [ngayDang], [maNguoiTao], [trangThai]) VALUES (4, N'Họp phụ huynh đầu năm', N'Kính mời phụ huynh học sinh khối 10...', CAST(N'2025-11-29T22:08:53.970' AS DateTime), 1, 1)
INSERT [dbo].[ThongBao] ([maTB], [tieuDe], [noiDung], [ngayDang], [maNguoiTao], [trangThai]) VALUES (5, N'Thay đổi thời khóa biểu', N'Từ tuần sau TKB sẽ thay đổi như sau...', CAST(N'2025-11-29T22:08:53.970' AS DateTime), 1, 1)
SET IDENTITY_INSERT [dbo].[ThongBao] OFF
GO
SET IDENTITY_INSERT [dbo].[ToBoMon] ON 

INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (1, N'Tổ Toán - Lý', N'Tổ chuyên môn Toán học và Vật lý', 1)
INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (2, N'Tổ Ngữ Văn - Sử - Địa', N'Tổ chuyên môn Khoa học Xã hội', 12)
INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (3, N'Tổ Ngoại ngữ', N'Tổ chuyên môn tiếng Anh và các ngôn ngữ khác', 3)
INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (4, N'Tổ Hóa - Sinh', N'Tổ chuyên môn Hóa học và Sinh học', 10)
INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (5, N'Tổ Tin học - Công nghệ', N'Tổ chuyên môn Tin học và Công nghệ', 7)
INSERT [dbo].[ToBoMon] ([maTo], [tenTo], [moTa], [maToTruong]) VALUES (6, N'Tổ Thể dục - Nghệ thuật', N'Tổ chuyên môn Thể dục và Nghệ thuật', 9)
SET IDENTITY_INSERT [dbo].[ToBoMon] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([userID], [username], [password_hash], [avatar], [roleID], [trangThai]) VALUES (1, N'admin', N'123', N'default.png', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password_hash], [avatar], [roleID], [trangThai]) VALUES (2, N'gv_toan', N'123', N'default.png', 2, 1)
INSERT [dbo].[Users] ([userID], [username], [password_hash], [avatar], [roleID], [trangThai]) VALUES (3, N'gv_van', N'123', N'default.png', 2, 1)
INSERT [dbo].[Users] ([userID], [username], [password_hash], [avatar], [roleID], [trangThai]) VALUES (4, N'hs_hung', N'123', N'default.png', 3, 1)
INSERT [dbo].[Users] ([userID], [username], [password_hash], [avatar], [roleID], [trangThai]) VALUES (5, N'hs_lan', N'123', N'default.png', 3, 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
/****** Object:  Index [UQ_Diem_HocSinh]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[DiemChiTiet] ADD  CONSTRAINT [UQ_Diem_HocSinh] UNIQUE NONCLUSTERED 
(
	[maHS] ASC,
	[maMonHoc] ASC,
	[maHocKy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_GiaoVien_Email]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_GiaoVien_Email] ON [dbo].[GiaoVien]
(
	[email] ASC
)
WHERE ([email] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_GiaoVien_SDT]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_GiaoVien_SDT] ON [dbo].[GiaoVien]
(
	[sdt] ASC
)
WHERE ([sdt] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ_GiaoVien_UserID]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_GiaoVien_UserID] ON [dbo].[GiaoVien]
(
	[userID] ASC
)
WHERE ([userID] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_HocSinh_Email]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_HocSinh_Email] ON [dbo].[HocSinh]
(
	[email] ASC
)
WHERE ([email] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ_HocSinh_UserID]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UQ_HocSinh_UserID] ON [dbo].[HocSinh]
(
	[userID] ASC
)
WHERE ([userID] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Khoi__35C7DE7090247F39]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[Khoi] ADD UNIQUE NONCLUSTERED 
(
	[tenKhoi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_TenLop_NamHoc]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[LopHoc] ADD  CONSTRAINT [UQ_TenLop_NamHoc] UNIQUE NONCLUSTERED 
(
	[tenLop] ASC,
	[maNH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_PhanCong_GiaoVien]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_PhanCong_GiaoVien] ON [dbo].[PhanCong]
(
	[maGV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_PhanCong_Lop]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_PhanCong_Lop] ON [dbo].[PhanCong]
(
	[maLop] ASC,
	[maHocKy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_PhanCong_MonHoc]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_PhanCong_MonHoc] ON [dbo].[PhanCong]
(
	[maMonHoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Roles__B19478611283F9AC]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[Roles] ADD UNIQUE NONCLUSTERED 
(
	[roleName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_TaiKhoan_Username]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[TaiKhoan] ADD  CONSTRAINT [UQ_TaiKhoan_Username] UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_TaiKhoan_GiaoVien]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TaiKhoan_GiaoVien] ON [dbo].[TaiKhoan]
(
	[maGV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_TaiKhoan_HocSinh]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TaiKhoan_HocSinh] ON [dbo].[TaiKhoan]
(
	[maHS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_TaiKhoan_Role]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TaiKhoan_Role] ON [dbo].[TaiKhoan]
(
	[role] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_TaiKhoan_Username]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TaiKhoan_Username] ON [dbo].[TaiKhoan]
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UK_TKB_Lop_Time]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[ThoiKhoaBieu] ADD  CONSTRAINT [UK_TKB_Lop_Time] UNIQUE NONCLUSTERED 
(
	[maLop] ASC,
	[maHocKy] ASC,
	[thu] ASC,
	[tiet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_TKB_GiaoVien]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TKB_GiaoVien] ON [dbo].[ThoiKhoaBieu]
(
	[maGV] ASC,
	[maHocKy] ASC,
	[thu] ASC,
	[tiet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_TKB_LopHocKy]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TKB_LopHocKy] ON [dbo].[ThoiKhoaBieu]
(
	[maLop] ASC,
	[maHocKy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_TKB_Time]    Script Date: 12/15/2025 7:56:35 PM ******/
CREATE NONCLUSTERED INDEX [IX_TKB_Time] ON [dbo].[ThoiKhoaBieu]
(
	[thu] ASC,
	[tiet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ_TongKet]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[TongKetHocKy] ADD  CONSTRAINT [UQ_TongKet] UNIQUE NONCLUSTERED 
(
	[maHS] ASC,
	[maHocKy] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Users__F3DBC572DCA44114]    Script Date: 12/15/2025 7:56:35 PM ******/
ALTER TABLE [dbo].[Users] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[GiaoVien] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[HocKy] ADD  DEFAULT ((1)) FOR [heSo]
GO
ALTER TABLE [dbo].[HocKy] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[HocSinh] ADD  DEFAULT (N'Đang học') FOR [trangThaiHocTap]
GO
ALTER TABLE [dbo].[HocSinh] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[Khoi] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[LopHoc] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[MonHoc] ADD  DEFAULT ((45)) FOR [soTiet]
GO
ALTER TABLE [dbo].[MonHoc] ADD  DEFAULT ((1)) FOR [heSoMon]
GO
ALTER TABLE [dbo].[MonHoc] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[NamHoc] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[Roles] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT ((1)) FOR [isActive]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT (getdate()) FOR [createdAt]
GO
ALTER TABLE [dbo].[TaiKhoan] ADD  DEFAULT (getdate()) FOR [updatedAt]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] ADD  DEFAULT (getdate()) FOR [createdAt]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] ADD  DEFAULT (getdate()) FOR [updatedAt]
GO
ALTER TABLE [dbo].[ThongBao] ADD  DEFAULT (getdate()) FOR [ngayDang]
GO
ALTER TABLE [dbo].[ThongBao] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT ('default.png') FOR [avatar]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT ((1)) FOR [trangThai]
GO
ALTER TABLE [dbo].[DiemChiTiet]  WITH CHECK ADD FOREIGN KEY([maHocKy])
REFERENCES [dbo].[HocKy] ([maHK])
GO
ALTER TABLE [dbo].[DiemChiTiet]  WITH CHECK ADD FOREIGN KEY([maMonHoc])
REFERENCES [dbo].[MonHoc] ([maMH])
GO
ALTER TABLE [dbo].[DiemChiTiet]  WITH CHECK ADD FOREIGN KEY([maHS])
REFERENCES [dbo].[HocSinh] ([maHS])
GO
ALTER TABLE [dbo].[GiaoVien]  WITH CHECK ADD FOREIGN KEY([maMonHocChuyenMon])
REFERENCES [dbo].[MonHoc] ([maMH])
GO
ALTER TABLE [dbo].[GiaoVien]  WITH CHECK ADD FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[GiaoVien]  WITH CHECK ADD  CONSTRAINT [FK_GiaoVien_ToBoMon] FOREIGN KEY([maTo])
REFERENCES [dbo].[ToBoMon] ([maTo])
GO
ALTER TABLE [dbo].[GiaoVien] CHECK CONSTRAINT [FK_GiaoVien_ToBoMon]
GO
ALTER TABLE [dbo].[HocKy]  WITH CHECK ADD FOREIGN KEY([maNH])
REFERENCES [dbo].[NamHoc] ([maNH])
GO
ALTER TABLE [dbo].[HocSinh]  WITH CHECK ADD FOREIGN KEY([maLop])
REFERENCES [dbo].[LopHoc] ([maLop])
GO
ALTER TABLE [dbo].[HocSinh]  WITH CHECK ADD FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[LopHoc]  WITH CHECK ADD FOREIGN KEY([maGVCN])
REFERENCES [dbo].[GiaoVien] ([maGV])
GO
ALTER TABLE [dbo].[LopHoc]  WITH CHECK ADD FOREIGN KEY([maKhoi])
REFERENCES [dbo].[Khoi] ([maKhoi])
GO
ALTER TABLE [dbo].[LopHoc]  WITH CHECK ADD FOREIGN KEY([maNH])
REFERENCES [dbo].[NamHoc] ([maNH])
GO
ALTER TABLE [dbo].[PhanCong]  WITH CHECK ADD  CONSTRAINT [FK_PhanCong_GiaoVien] FOREIGN KEY([maGV])
REFERENCES [dbo].[GiaoVien] ([maGV])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PhanCong] CHECK CONSTRAINT [FK_PhanCong_GiaoVien]
GO
ALTER TABLE [dbo].[PhanCong]  WITH CHECK ADD  CONSTRAINT [FK_PhanCong_HocKy] FOREIGN KEY([maHocKy])
REFERENCES [dbo].[HocKy] ([maHK])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PhanCong] CHECK CONSTRAINT [FK_PhanCong_HocKy]
GO
ALTER TABLE [dbo].[PhanCong]  WITH CHECK ADD  CONSTRAINT [FK_PhanCong_LopHoc] FOREIGN KEY([maLop])
REFERENCES [dbo].[LopHoc] ([maLop])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PhanCong] CHECK CONSTRAINT [FK_PhanCong_LopHoc]
GO
ALTER TABLE [dbo].[PhanCong]  WITH CHECK ADD  CONSTRAINT [FK_PhanCong_MonHoc] FOREIGN KEY([maMonHoc])
REFERENCES [dbo].[MonHoc] ([maMH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PhanCong] CHECK CONSTRAINT [FK_PhanCong_MonHoc]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_TaiKhoan_GiaoVien] FOREIGN KEY([maGV])
REFERENCES [dbo].[GiaoVien] ([maGV])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_GiaoVien]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_TaiKhoan_HocSinh] FOREIGN KEY([maHS])
REFERENCES [dbo].[HocSinh] ([maHS])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_HocSinh]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD  CONSTRAINT [FK_TKB_GiaoVien] FOREIGN KEY([maGV])
REFERENCES [dbo].[GiaoVien] ([maGV])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] CHECK CONSTRAINT [FK_TKB_GiaoVien]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD  CONSTRAINT [FK_TKB_HocKy] FOREIGN KEY([maHocKy])
REFERENCES [dbo].[HocKy] ([maHK])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] CHECK CONSTRAINT [FK_TKB_HocKy]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD  CONSTRAINT [FK_TKB_LopHoc] FOREIGN KEY([maLop])
REFERENCES [dbo].[LopHoc] ([maLop])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] CHECK CONSTRAINT [FK_TKB_LopHoc]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD  CONSTRAINT [FK_TKB_MonHoc] FOREIGN KEY([maMonHoc])
REFERENCES [dbo].[MonHoc] ([maMH])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ThoiKhoaBieu] CHECK CONSTRAINT [FK_TKB_MonHoc]
GO
ALTER TABLE [dbo].[ThongBao]  WITH CHECK ADD FOREIGN KEY([maNguoiTao])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[ToBoMon]  WITH CHECK ADD FOREIGN KEY([maToTruong])
REFERENCES [dbo].[GiaoVien] ([maGV])
GO
ALTER TABLE [dbo].[TongKetHocKy]  WITH CHECK ADD FOREIGN KEY([maHocKy])
REFERENCES [dbo].[HocKy] ([maHK])
GO
ALTER TABLE [dbo].[TongKetHocKy]  WITH CHECK ADD FOREIGN KEY([maHS])
REFERENCES [dbo].[HocSinh] ([maHS])
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD FOREIGN KEY([roleID])
REFERENCES [dbo].[Roles] ([roleID])
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [CK_TaiKhoan_Role] CHECK  (([role]='HOCSINH' OR [role]='GIAOVIEN' OR [role]='ADMIN'))
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [CK_TaiKhoan_Role]
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD CHECK  (([tiet]>=(1) AND [tiet]<=(5)))
GO
ALTER TABLE [dbo].[ThoiKhoaBieu]  WITH CHECK ADD CHECK  (([thu]>=(2) AND [thu]<=(7)))
GO
/****** Object:  StoredProcedure [dbo].[sp_CheckGiaoVienConflict]    Script Date: 12/15/2025 7:56:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- 6. Tạo Stored Procedure để kiểm tra trùng lịch Giáo viên
CREATE PROCEDURE [dbo].[sp_CheckGiaoVienConflict]
    @maGV INT,
    @maHocKy INT,
    @thu INT,
    @tiet INT,
    @maLop INT = NULL -- NULL khi insert mới, có giá trị khi update
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Kiểm tra xem GV có lịch dạy tại thời điểm này ở lớp khác không
    SELECT COUNT(*) AS conflictCount,
           STRING_AGG(lh.tenLop, ', ') AS danhSachLop
    FROM ThoiKhoaBieu tkb
    INNER JOIN LopHoc lh ON tkb.maLop = lh.maLop
    WHERE tkb.maGV = @maGV
      AND tkb.maHocKy = @maHocKy
      AND tkb.thu = @thu
      AND tkb.tiet = @tiet
      AND (@maLop IS NULL OR tkb.maLop <> @maLop); -- Bỏ qua lớp hiện tại khi update
END
GO
USE [master]
GO
ALTER DATABASE [db_quanlyhocsinh_v2] SET  READ_WRITE 
GO
