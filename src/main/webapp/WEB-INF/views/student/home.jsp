<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Trang chủ Học sinh</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item active">Dashboard</li>
      </ol>
    </nav>
  </div>

  <section class="section dashboard">
    <c:if test="${not empty error}">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle me-1"></i>
        ${error}
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="alert"
        ></button>
      </div>
    </c:if>

    <div class="row">
      <div class="col-lg-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              <i class="bi bi-person-circle text-primary"></i> Thông tin cá nhân
            </h5>

            <c:if test="${not empty hocSinh}">
              <table class="table table-borderless">
                <tr>
                  <th style="width: 40%">Mã HS:</th>
                  <td><strong>${hocSinh.maHS}</strong></td>
                </tr>
                <tr>
                  <th>Họ và tên:</th>
                  <td><strong>${hocSinh.hoTen}</strong></td>
                </tr>
                <tr>
                  <th>Lớp:</th>
                  <td>
                    <span class="badge bg-primary">${hocSinh.tenLop}</span>
                  </td>
                </tr>
                <tr>
                  <th>Ngày sinh:</th>
                  <td>${hocSinh.ngaySinh}</td>
                </tr>
                <tr>
                  <th>Giới tính:</th>
                  <td>${hocSinh.gioiTinh}</td>
                </tr>
                <tr>
                  <th>Email:</th>
                  <td>${hocSinh.email}</td>
                </tr>
                <tr>
                  <th>SĐT:</th>
                  <td>${hocSinh.sdtCaNhan}</td>
                </tr>
              </table>
            </c:if>
          </div>
        </div>
      </div>

      <%-- MENU CHỨC NĂNG --%>
      <div class="col-lg-8">
        <div class="row">
          <%-- Xem thời khóa biểu --%>
          <div class="col-md-6 mb-4">
            <div class="card info-card sales-card">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-calendar3 text-primary"></i> Thời khóa biểu
                </h5>
                <div class="d-flex align-items-center">
                  <div
                    class="card-icon rounded-circle d-flex align-items-center justify-content-center"
                  >
                    <i class="bi bi-calendar-week text-primary"></i>
                  </div>
                  <div class="ps-3">
                    <p class="mb-3">Xem lịch học hàng tuần của lớp</p>
                    <a
                      href="${baseURL}/student/tkb"
                      class="btn btn-primary btn-sm"
                    >
                      <i class="bi bi-eye"></i> Xem lịch học
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <%-- Xem điểm --%>
          <div class="col-md-6 mb-4">
            <div class="card info-card revenue-card">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-journal-text text-success"></i> Xem điểm
                </h5>
                <div class="d-flex align-items-center">
                  <div
                    class="card-icon rounded-circle d-flex align-items-center justify-content-center"
                  >
                    <i class="bi bi-file-earmark-text text-success"></i>
                  </div>
                  <div class="ps-3">
                    <p class="mb-3">Tra cứu điểm số các môn học</p>
                    <a
                      href="${baseURL}/student/xem-diem"
                      class="btn btn-success btn-sm"
                    >
                      <i class="bi bi-eye"></i> Xem điểm
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-6 mb-4">
            <div class="card info-card customers-card">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-bell text-warning"></i> Thông báo
                  <c:if test="${unreadAnnouncementCount > 0}">
                    <span class="badge bg-danger badge-number"
                      >${unreadAnnouncementCount}</span
                    >
                  </c:if>
                </h5>
                <div class="d-flex align-items-center">
                  <div
                    class="card-icon rounded-circle d-flex align-items-center justify-content-center"
                  >
                    <i class="bi bi-megaphone text-warning"></i>
                  </div>
                  <div class="ps-3">
                    <p class="mb-3">
                      Xem thông báo từ nhà trường
                      <c:if test="${unreadAnnouncementCount > 0}">
                        <br />
                        <span class="badge bg-danger">
                          <i class="bi bi-envelope-exclamation"></i>
                          ${unreadAnnouncementCount} thông báo chưa đọc
                        </span>
                      </c:if>
                      <c:if
                        test="${newAnnouncementCount > 0 && unreadAnnouncementCount == 0}"
                      >
                        <br />
                        <span class="badge bg-info">
                          <i class="bi bi-info-circle"></i>
                          ${newAnnouncementCount} thông báo mới trong 7 ngày
                        </span>
                      </c:if>
                    </p>
                    <a
                      href="${baseURL}/student/thong-bao"
                      class="btn btn-warning btn-sm"
                    >
                      <i class="bi bi-eye"></i> Xem thông báo
                      <c:if test="${unreadAnnouncementCount > 0}">
                        <span class="badge bg-danger"
                          >${unreadAnnouncementCount}</span
                        >
                      </c:if>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-md-6 mb-4">
            <div class="card info-card">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-person-gear text-info"></i> Cập nhật thông tin
                </h5>
                <div class="d-flex align-items-center">
                  <div
                    class="card-icon rounded-circle d-flex align-items-center justify-content-center"
                  >
                    <i class="bi bi-person-badge text-info"></i>
                  </div>
                  <div class="ps-3">
                    <p class="mb-3">Cập nhật thông tin cá nhân</p>
                    <a
                      href="${baseURL}/student/profile"
                      class="btn btn-info btn-sm text-white"
                    >
                      <i class="bi bi-pencil-square"></i> Cập nhật
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              <i class="bi bi-info-circle text-primary"></i> Hướng dẫn sử dụng
            </h5>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">
                <i class="bi bi-check-circle text-success"></i>
                Kiểm tra lịch học hàng tuần để chuẩn bị bài tập và sách vở
              </li>
              <li class="list-group-item">
                <i class="bi bi-check-circle text-success"></i>
                Theo dõi điểm số thường xuyên để biết kết quả học tập
              </li>
              <li class="list-group-item">
                <i class="bi bi-check-circle text-success"></i>
                Đọc thông báo từ nhà trường mỗi ngày
              </li>
              <li class="list-group-item">
                <i class="bi bi-check-circle text-success"></i>
                Cập nhật thông tin liên lạc để nhà trường có thể liên hệ khi cần
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
