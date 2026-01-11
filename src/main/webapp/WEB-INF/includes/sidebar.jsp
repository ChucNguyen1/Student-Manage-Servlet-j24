<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %> <%@ page
isELIgnored="false" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">
  <ul class="sidebar-nav" id="sidebar-nav">
    <li class="nav-item">
      <a class="nav-link" href="${baseURL}/">
        <i class="bi bi-grid"></i>
        <span>Trang chủ</span>
      </a>
    </li>

    <c:if test="${sessionScope.role == 'ADMIN'}">
      <!-- Menu Admin -->
      <li class="nav-heading">Quản lý Hệ thống đào tạo</li>

      <li class="nav-item">
        <a
          class="nav-link collapsed"
          data-bs-target="#hethong-nav"
          data-bs-toggle="collapse"
          href="#"
        >
          <i class="bi bi-gear-wide-connected"></i><span>Cấu hình</span
          ><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul
          id="hethong-nav"
          class="nav-content collapse"
          data-bs-parent="#sidebar-nav"
        >
          <li>
            <a href="${baseURL}/admin/khoi-list">
              <i class="bi bi-circle"></i><span>Quản lý Khối</span>
            </a>
          </li>
          <li>
            <a href="${baseURL}/admin/namhoc-list">
              <i class="bi bi-circle"></i><span>Quản lý Năm học</span>
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
          <li>
            <a href="${baseURL}/admin/tobomon-list">
              <i class="bi bi-circle"></i><span>Quản lý tổ bộ môn</span>
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
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/lophoc-list">
          <i class="bi bi-building"></i>
          <span>Quản lý Lớp học</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/hocsinh-list">
          <i class="bi bi-people-fill"></i>
          <span>Quản lý Học sinh</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/diem">
          <i class="bi bi-card-checklist"></i>
          <span>Quản lý Điểm số</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/thongbao-list">
          <i class="bi bi-bell-fill"></i>
          <span>Quản lý Thông báo</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/phancong">
          <i class="bi bi-calendar-check"></i>
          <span>Quản lý Phân công</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/tkb">
          <i class="bi bi-calendar3"></i>
          <span>Quản lý Thời khóa biểu</span>
        </a>
      </li>

      <li class="nav-heading">Quản lý Hệ thống</li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/admin/taikhoan-list">
          <i class="bi bi-person-lock"></i>
          <span>Quản lý Tài khoản</span>
        </a>
      </li>
    </c:if>

    <c:if test="${sessionScope.role == 'GIAOVIEN'}">
      <!-- Menu Giáo viên -->
      <li class="nav-heading">Nghiệp vụ Giảng dạy</li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/teacher/lich-day">
          <i class="bi bi-calendar3"></i>
          <span>Thời khóa biểu</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/teacher/danh-sach-lop">
          <i class="bi bi-building"></i>
          <span>Lớp học phụ trách</span>
        </a>
      </li>
    </c:if>

    <c:if test="${sessionScope.role == 'HOCSINH'}">
      <!-- Menu Học sinh -->
      <li class="nav-heading">Thông tin học tập</li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/student/tkb">
          <i class="bi bi-calendar3"></i>
          <span>Thời khóa biểu</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/student/xem-diem">
          <i class="bi bi-card-checklist"></i>
          <span>Xem điểm</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="#">
          <i class="bi bi-building"></i>
          <span>Lớp học</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/student/thong-bao">
          <i class="bi bi-bell-fill"></i>
          <span>Thông báo</span>
        </a>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="${baseURL}/student/profile">
          <i class="bi bi-person-circle"></i>
          <span>Thông tin cá nhân</span>
        </a>
      </li>
    </c:if>
  </ul>
</aside>
<!-- End Sidebar-->
