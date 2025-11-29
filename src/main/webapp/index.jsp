<%-- 
  File này BÂY GIỜ được gọi bởi DashboardController.
  Nó nhận dữ liệu thống kê từ Controller.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<%-- 1. NHÚNG HEADER --%>
<jsp:include page="/WEB-INF/includes/header.jsp" />


<main id="main" class="main">

  <div class="pagetitle">
    <h1>Dashboard</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
        <li class="breadcrumb-item active">Dashboard</li>
      </ol>
    </nav>
  </div><section class="section dashboard">
    <div class="row">

      <div class="col-lg-12">
        <div class="row">

          <div class="col-xxl-3 col-md-6">
            <div class="card info-card sales-card"> <%-- Dùng style "sales-card" (màu xanh) --%>
              <div class="card-body">
                <h5 class="card-title">Giáo viên</h5>
                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-person-video3"></i>
                  </div>
                  <div class="ps-3">
                    <%-- Lấy dữ liệu từ Controller --%>
                    <h6>${totalGiaoVien}</h6>
                    <span class="text-muted small pt-2 ps-1">tổng số giáo viên</span>
                  </div>
                </div>
              </div>
            </div>
          </div><div class="col-xxl-3 col-md-6">
            <div class="card info-card revenue-card"> <%-- "revenue-card" (màu lá) --%>
              <div class="card-body">
                <h5 class="card-title">Môn học</h5>
                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-book-half"></i>
                  </div>
                  <div class="ps-3">
                    <h6>${totalMonHoc}</h6>
                    <span class="text-muted small pt-2 ps-1">môn học đang dạy</span>
                  </div>
                </div>
              </div>
            </div>
          </div><div class="col-xxl-3 col-md-6">
            <div class="card info-card customers-card"> <%-- "customers-card" (màu cam) --%>
              <div class="card-body">
                <h5 class="card-title">Năm học</h5>
                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-calendar-event"></i>
                  </div>
                  <div class="ps-3">
                    <h6>${totalNamHoc}</h6>
                    <span class="text-muted small pt-2 ps-1">năm học đang hoạt động</span>
                  </div>
                </div>
              </div>
            </div>
          </div><div class="col-xxl-3 col-md-6">
            <div class="card info-card revenue-card"> <%-- (Dùng lại màu lá) --%>
              <div class="card-body">
                <h5 class="card-title">Học kỳ</h5>
                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-flag-fill"></i>
                  </div>
                  <div class="ps-3">
                    <h6>${totalHocKy}</h6>
                    <span class="text-muted small pt-2 ps-1">học kỳ đang hoạt động</span>
                  </div>
                </div>
              </div>
            </div>
          </div></div>
      </div>
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Truy cập nhanh các chức năng</h5>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                <a class="btn btn-outline-primary" href="${baseURL}/admin/giaovien-list">
                  <i class="bi bi-person-video3 me-1"></i> Quản lý Giáo viên
                </a>
                <a class="btn btn-outline-secondary" href="${baseURL}/admin/lophoc-list">
                  <i class="bi bi-building me-1"></i> Quản lý Lớp học
                </a>
                <a class="btn btn-outline-success" href="${baseURL}/admin/hocsinh-list">
                  <i class="bi bi-people-fill me-1"></i> Quản lý Học sinh
                </a>
            </div>

            <hr>
            <h6 class="card-subtitle mb-2 text-muted">Cấu hình hệ thống</h6>
            <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                <a class="btn btn-outline-info btn-sm" href="${baseURL}/admin/namhoc-list">Quản lý Năm học</a>
                <a class="btn btn-outline-info btn-sm" href="${baseURL}/admin/hocky-list">Quản lý Học kỳ</a>
                <a class="btn btn-outline-info btn-sm" href="${baseURL}/admin/khoi-list">Quản lý Khối</a>
                <a class="btn btn-outline-info btn-sm" href="${baseURL}/admin/monhoc-list">Quản lý Môn học</a>
            </div>
            
          </div>
        </div>
      </div>
      </div>
  </section>

</main><%-- 3. NHÚNG FOOTER --%>
<jsp:include page="/WEB-INF/includes/footer.jsp" />