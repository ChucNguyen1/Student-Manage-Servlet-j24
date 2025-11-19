<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="vi">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Trang quản trị - Quản lý Học sinh</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${baseURL}/assets/img/favicon.png" rel="icon">
  <link href="${baseURL}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <link href="${baseURL}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${baseURL}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  
  <link href="${baseURL}/assets/css/style.css" rel="stylesheet">
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
      <a href="${baseURL}/" class="logo d-flex align-items-center">
        <img src="${baseURL}/assets/img/logo.png" alt="">
        <span class="d-none d-lg-block">QL Học sinh</span>
      </a>
      <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <div class="search-bar">
      <form class="search-form d-flex align-items-center" method="POST" action="#">
        <input type="text" name="query" placeholder="Tìm kiếm" title="Nhập từ khóa tìm kiếm">
        <button type="submit" title="Search"><i class="bi bi-search"></i></button>
      </form>
    </div><!-- End Search Bar -->

    <nav class="header-nav ms-auto">
      <ul class="d-flex align-items-center">

        <li class="nav-item d-block d-lg-none">
          <a class="nav-link nav-icon search-bar-toggle " href="#">
            <i class="bi bi-search"></i>
          </a>
        </li><!-- End Search Icon-->

        <li class="nav-item dropdown pe-3">
          <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
            <img src="${baseURL}/assets/img/profile-img.jpg" alt="Profile" class="rounded-circle">
            <span class="d-none d-md-block dropdown-toggle ps-2">Tên Người Dùng</span>
          </a><!-- End Profile Iamge Icon -->

          <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
            <li class="dropdown-header">
              <h6>Tên Người Dùng</h6>
              <span>Vai trò (Admin)</span>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>
            <li>
              <a class="dropdown-item d-flex align-items-center" href="#">
                <i class="bi bi-person"></i>
                <span>Thông tin tài khoản</span>
              </a>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>
            <li>
              <a class="dropdown-item d-flex align-items-center" href="${baseURL}/logout">
                <i class="bi bi-box-arrow-right"></i>
                <span>Đăng xuất</span>
              </a>
            </li>
          </ul><!-- End Profile Dropdown Items -->
        </li><!-- End Profile Nav -->

      </ul>
    </nav><!-- End Icons Navigation -->

  </header><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

      <li class="nav-item">
        <a class="nav-link " href="${baseURL}/">
          <i class="bi bi-grid"></i>
          <span>Trang chủ</span>
        </a>
      </li><!-- End Dashboard Nav -->

      <li class="nav-heading">Quản lý Hệ thống</li>

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#hethong-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-gear-wide-connected"></i><span>Cấu hình</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="hethong-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${baseURL}/admin/khoi-list"> 
              <i class="bi bi-circle"></i><span>Quản lý Khối</span>
            </a>
          </li>
          <li>
			<a href="${baseURL}/admin/namhoc-list">
              <i class="bi bi-circle">
              </i><span>Quản lý Năm học</span>
            </a>
          </li>
          <li>
            <a href="${baseURL}/admin/hocky-list">
              <i class="bi bi-circle"></i>
              <span>Quản lý Học kỳ</span>
            </a>
          </li>
          <li>
             <a href="${baseURL}/admin/monhoc-list">
              <i class="bi bi-circle"></i><span>Quản lý Môn học</span>
            </a>
          </li>
        </ul>
      </li>

      <li class="nav-heading">Quản lý Nghiệp vụ</li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/giaovien-list">
          <i class="bi bi-person-video3"></i>
          <span>Quản lý Giáo viên</span>
        </a>
      </li><!-- End Giao Vien Nav -->
      
      <li class="nav-item">
        <a class="nav-link collapsed" href="#"> 
          <i class="bi bi-building"></i>
          <span>Quản lý Lớp học</span>
        </a>
      </li><!-- End Lop Hoc Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" href="#">
          <i class="bi bi-people-fill"></i>
          <span>Quản lý Học sinh</span>
        </a>
      </li><!-- End Hoc Sinh Nav -->
      
      <li class="nav-item">
        <a class="nav-link collapsed" href="#"> 
          <i class="bi bi-card-checklist"></i>
          <span>Quản lý Điểm số</span>
        </a>
      </li><!-- End Diem Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/thongbao-list"> 
          <i class="bi bi-bell-fill"></i>
          <span>Quản lý Thông báo</span>
        </a>
      </li><!-- End Thong Bao Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" href="#"> 
          <i class="bi bi-person-lines-fill"></i>
          <span>Phân quyền</span>
        </a>
      </li>

    </ul>

  </aside>

