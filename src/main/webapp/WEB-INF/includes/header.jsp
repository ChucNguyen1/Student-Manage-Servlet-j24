<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />

    <title>Trang quản trị - Quản lý Học sinh</title>
    <meta content="" name="description" />
    <meta content="" name="keywords" />

    <!-- Favicons -->
    <link href="${baseURL}/assets/img/favicon.png" rel="icon" />
    <link
      href="${baseURL}/assets/img/apple-touch-icon.png"
      rel="apple-touch-icon"
    />

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect" />
    <link
      href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
      rel="stylesheet"
    />

    <link
      href="${baseURL}/assets/vendor/bootstrap/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="${baseURL}/assets/vendor/bootstrap-icons/bootstrap-icons.css"
      rel="stylesheet"
    />

    <link href="${baseURL}/assets/css/style.css" rel="stylesheet" />
  </head>

  <body>
    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top d-flex align-items-center">
      <div class="d-flex align-items-center justify-content-between">
        <a href="${baseURL}/" class="logo d-flex align-items-center">
          <img src="${baseURL}/assets/img/logo.png" alt="" />
          <span class="d-none d-lg-block">QL Học sinh</span>
        </a>
        <i class="bi bi-list toggle-sidebar-btn"></i>
      </div>
      <!-- End Logo -->

      <div class="search-bar">
        <form
          class="search-form d-flex align-items-center"
          method="POST"
          action="#"
        >
          <input
            type="text"
            name="query"
            placeholder="Tìm kiếm"
            title="Nhập từ khóa tìm kiếm"
          />
          <button type="submit" title="Search">
            <i class="bi bi-search"></i>
          </button>
        </form>
      </div>
      <!-- End Search Bar -->

      <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">
          <li class="nav-item d-block d-lg-none">
            <a class="nav-link nav-icon search-bar-toggle" href="#">
              <i class="bi bi-search"></i>
            </a>
          </li>
          <!-- End Search Icon-->

          <li class="nav-item dropdown pe-3">
            <a
              class="nav-link nav-profile d-flex align-items-center pe-0"
              href="#"
              data-bs-toggle="dropdown"
            >
              <img
                src="${baseURL}/assets/img/profile-img.jpg"
                alt="Profile"
                class="rounded-circle"
              />
              <span class="d-none d-md-block dropdown-toggle ps-2">
                <c:choose>
                  <c:when test="${not empty sessionScope.hoTenHienThi}">
                    ${sessionScope.hoTenHienThi}
                  </c:when>
                  <c:otherwise> Tên Người Dùng </c:otherwise>
                </c:choose>
              </span> </a
            ><!-- End Profile Iamge Icon -->

            <ul
              class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile"
            >
              <li class="dropdown-header">
                <h6>
                  <c:choose>
                    <c:when test="${not empty sessionScope.hoTenHienThi}">
                      ${sessionScope.hoTenHienThi}
                    </c:when>
                    <c:otherwise> Tên Người Dùng </c:otherwise>
                  </c:choose>
                </h6>
                <span>
                  <c:choose>
                    <c:when test="${sessionScope.role == 'ADMIN'}">
                      Quản trị viên
                    </c:when>
                    <c:when test="${sessionScope.role == 'GIAOVIEN'}">
                      Giáo viên
                    </c:when>
                    <c:when test="${sessionScope.role == 'HOCSINH'}">
                      Học sinh
                    </c:when>
                    <c:otherwise> Vai trò </c:otherwise>
                  </c:choose>
                </span>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>
              <li>
                <c:choose>
                  <c:when test="${sessionScope.role == 'HOCSINH'}">
                    <a
                      class="dropdown-item d-flex align-items-center"
                      href="${baseURL}/student/profile"
                    >
                      <i class="bi bi-person"></i>
                      <span>Thông tin tài khoản</span>
                    </a>
                  </c:when>
                  <c:when test="${sessionScope.role == 'GIAOVIEN'}">
                    <a class="dropdown-item d-flex align-items-center" href="#">
                      <i class="bi bi-person"></i>
                      <span>Thông tin tài khoản</span>
                    </a>
                  </c:when>
                  <c:otherwise>
                    <a class="dropdown-item d-flex align-items-center" href="#">
                      <i class="bi bi-person"></i>
                      <span>Thông tin tài khoản</span>
                    </a>
                  </c:otherwise>
                </c:choose>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>
              <li>
                <a
                  class="dropdown-item d-flex align-items-center"
                  href="${baseURL}/logout"
                >
                  <i class="bi bi-box-arrow-right"></i>
                  <span>Đăng xuất</span>
                </a>
              </li>
            </ul>
            <!-- End Profile Dropdown Items -->
          </li>
          <!-- End Profile Nav -->
        </ul>
      </nav>
      <!-- End Icons Navigation -->
    </header>
    <!-- End Header -->
  </body>
</html>
