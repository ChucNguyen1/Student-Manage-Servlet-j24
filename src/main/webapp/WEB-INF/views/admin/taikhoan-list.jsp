<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1><i class="bi bi-shield-lock"></i> Quản Lý Tài Khoản & Phân Quyền</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/admin/dashboard">Dashboard</a>
        </li>
        <li class="breadcrumb-item active">Quản Lý Tài Khoản</li>
      </ol>
    </nav>
  </div>

  <!-- Toast Thông Báo -->
  <c:if test="${not empty sessionScope.message}">
    <div
      class="toast align-items-center text-white bg-success border-0 position-fixed top-0 end-0 m-3"
      role="alert"
      style="z-index: 9999"
      id="successToast"
    >
      <div class="d-flex">
        <div class="toast-body">
          <i class="bi bi-check-circle me-2"></i>${sessionScope.message}
        </div>
        <button
          type="button"
          class="btn-close btn-close-white me-2 m-auto"
          data-bs-dismiss="toast"
        ></button>
      </div>
    </div>
    <c:remove var="message" scope="session" />
  </c:if>

  <c:if test="${not empty sessionScope.error}">
    <div
      class="toast align-items-center text-white bg-danger border-0 position-fixed top-0 end-0 m-3"
      role="alert"
      style="z-index: 9999"
      id="errorToast"
    >
      <div class="d-flex">
        <div class="toast-body">
          <i class="bi bi-exclamation-triangle me-2"></i>${sessionScope.error}
        </div>
        <button
          type="button"
          class="btn-close btn-close-white me-2 m-auto"
          data-bs-dismiss="toast"
        ></button>
      </div>
    </div>
    <c:remove var="error" scope="session" />
  </c:if>

  <c:if test="${not empty sessionScope.info}">
    <div
      class="toast align-items-center text-white bg-info border-0 position-fixed top-0 end-0 m-3"
      role="alert"
      style="z-index: 9999"
      id="infoToast"
    >
      <div class="d-flex">
        <div class="toast-body">
          <i class="bi bi-info-circle me-2"></i>${sessionScope.info}
        </div>
        <button
          type="button"
          class="btn-close btn-close-white me-2 m-auto"
          data-bs-dismiss="toast"
        ></button>
      </div>
    </div>
    <c:remove var="info" scope="session" />
  </c:if>

  <section class="section">
    <div class="row">
      <div class="col-lg-12">
        <!-- Auto Provision Cards -->
        <div class="row mb-4">
          <div class="col-md-6">
            <div class="card border-primary">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-person-plus-fill text-primary"></i> Sinh Tài
                  Khoản Học Sinh
                </h5>
                <p class="card-text">
                  Tự động tạo tài khoản cho tất cả học sinh chưa có tài
                  khoản.<br />
                  <small class="text-muted">
                    <i class="bi bi-info-circle"></i>
                    Username: <code>HS{mã HS}</code> | Password:
                    <code>123456</code>
                  </small>
                </p>
                <a
                  href="${baseURL}/admin/taikhoan-auto-provision-students"
                  class="btn btn-primary"
                  onclick="return confirm('Bạn có chắc muốn tạo tài khoản tự động cho tất cả học sinh chưa có tài khoản?\n\nUsername = HS{mã HS}\nPassword = 123456');"
                >
                  <i class="bi bi-lightning-fill"></i> Sinh Tự Động
                </a>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card border-success">
              <div class="card-body">
                <h5 class="card-title">
                  <i class="bi bi-person-plus-fill text-success"></i> Sinh Tài
                  Khoản Giáo Viên
                </h5>
                <p class="card-text">
                  Tự động tạo tài khoản cho tất cả giáo viên chưa có tài
                  khoản.<br />
                  <small class="text-muted">
                    <i class="bi bi-info-circle"></i>
                    Username: <code>GV{mã GV}</code> | Password:
                    <code>123456</code>
                  </small>
                </p>
                <a
                  href="${baseURL}/admin/taikhoan-auto-provision-teachers"
                  class="btn btn-success"
                  onclick="return confirm('Bạn có chắc muốn tạo tài khoản tự động cho tất cả giáo viên chưa có tài khoản?\n\nUsername = GV{mã GV}\nPassword = 123456');"
                >
                  <i class="bi bi-lightning-fill"></i> Sinh Tự Động
                </a>
              </div>
            </div>
          </div>
        </div>

        <!-- Danh Sách Tài Khoản -->
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h5 class="card-title mb-0">
                <i class="bi bi-list-ul"></i> Danh Sách Tài Khoản
                <span class="badge bg-secondary">${totalItems}</span>
              </h5>
              <form
                action="${baseURL}/admin/taikhoan-list"
                method="get"
                class="d-flex"
              >
                <input
                  type="text"
                  class="form-control form-control-sm me-2"
                  name="searchKey"
                  placeholder="Tìm kiếm..."
                  value="${param.searchKey}"
                  style="width: 250px"
                />
                <button type="submit" class="btn btn-sm btn-primary">
                  <i class="bi bi-search"></i> Tìm
                </button>
              </form>
            </div>

            <div class="table-responsive">
              <table class="table table-hover table-bordered">
                <thead class="table-light">
                  <tr class="text-center">
                    <th width="5%">Mã</th>
                    <th width="15%">Username</th>
                    <th width="20%">Họ Tên</th>
                    <th width="12%">Vai Trò</th>
                    <th width="12%">Trạng Thái</th>
                    <th width="15%">Ngày Tạo</th>
                    <th width="21%">Thao Tác</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="tk" items="${dsTaiKhoan}">
                    <tr>
                      <td class="text-center">
                        <strong>${tk.maTK}</strong>
                      </td>
                      <td>
                        <code>${tk.username}</code>
                      </td>
                      <td>${tk.hoTenHienThi}</td>
                      <td class="text-center">
                        <c:choose>
                          <c:when test="${tk.role == 'ADMIN'}">
                            <span class="badge bg-danger">
                              <i class="bi bi-shield-fill-check"></i> Admin
                            </span>
                          </c:when>
                          <c:when test="${tk.role == 'GIAOVIEN'}">
                            <span class="badge bg-success">
                              <i class="bi bi-person-badge"></i> Giáo Viên
                            </span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge bg-primary">
                              <i class="bi bi-person"></i> Học Sinh
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td class="text-center">
                        <c:choose>
                          <c:when test="${tk.active}">
                            <span class="badge bg-success">
                              <i class="bi bi-unlock-fill"></i> Hoạt động
                            </span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge bg-danger">
                              <i class="bi bi-lock-fill"></i> Đã khóa
                            </span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td class="text-center">
                        <small
                          ><fmt:formatDate
                            value="${tk.createdAt}"
                            pattern="dd/MM/yyyy HH:mm"
                        /></small>
                      </td>
                      <td class="text-center">
                        <!-- Toggle Status -->
                        <c:choose>
                          <c:when test="${tk.active}">
                            <a
                              href="${baseURL}/admin/taikhoan-toggle-status?id=${tk.maTK}&status=false"
                              class="btn btn-sm btn-warning"
                              title="Khóa tài khoản"
                              onclick="return confirm('Bạn có chắc muốn KHÓA tài khoản ${tk.username}?\nNgười dùng sẽ không thể đăng nhập!');"
                            >
                              <i class="bi bi-lock"></i> Khóa
                            </a>
                          </c:when>
                          <c:otherwise>
                            <a
                              href="${baseURL}/admin/taikhoan-toggle-status?id=${tk.maTK}&status=true"
                              class="btn btn-sm btn-success"
                              title="Mở khóa tài khoản"
                              onclick="return confirm('Bạn có chắc muốn MỞ KHÓA tài khoản ${tk.username}?');"
                            >
                              <i class="bi bi-unlock"></i> Mở
                            </a>
                          </c:otherwise>
                        </c:choose>

                        <!-- Reset Password -->
                        <a
                          href="${baseURL}/admin/taikhoan-reset-password?id=${tk.maTK}"
                          class="btn btn-sm btn-info"
                          title="Reset mật khẩu"
                          onclick="return confirm('Bạn có chắc muốn RESET mật khẩu tài khoản ${tk.username}?\nMật khẩu sẽ được đặt về: 123456');"
                        >
                          <i class="bi bi-key"></i> Reset
                        </a>
                      </td>
                    </tr>
                  </c:forEach>

                  <c:if test="${empty dsTaiKhoan}">
                    <tr>
                      <td colspan="7" class="text-center text-muted">
                        <i class="bi bi-inbox"></i> Không có dữ liệu
                      </td>
                    </tr>
                  </c:if>
                </tbody>
              </table>
            </div>

            <!-- Phân Trang -->
            <c:if test="${totalPages > 1}">
              <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                  <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a
                      class="page-link"
                      href="${baseURL}/admin/taikhoan-list?page=${currentPage - 1}&searchKey=${param.searchKey}"
                      >Trước</a
                    >
                  </li>
                  <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                      <a
                        class="page-link"
                        href="${baseURL}/admin/taikhoan-list?page=${i}&searchKey=${param.searchKey}"
                        >${i}</a
                      >
                    </li>
                  </c:forEach>
                  <li
                    class="page-item ${currentPage == totalPages ? 'disabled' : ''}"
                  >
                    <a
                      class="page-link"
                      href="${baseURL}/admin/taikhoan-list?page=${currentPage + 1}&searchKey=${param.searchKey}"
                      >Sau</a
                    >
                  </li>
                </ul>
              </nav>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<%@ include file="/WEB-INF/includes/footer.jsp" %>

<script src="${baseURL}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${baseURL}/assets/js/main.js"></script>

<script>
  // Auto show toasts
  document.addEventListener("DOMContentLoaded", function () {
    var successToast = document.getElementById("successToast");
    var errorToast = document.getElementById("errorToast");
    var infoToast = document.getElementById("infoToast");

    if (successToast) {
      new bootstrap.Toast(successToast, { delay: 5000 }).show();
    }
    if (errorToast) {
      new bootstrap.Toast(errorToast, { delay: 5000 }).show();
    }
    if (infoToast) {
      new bootstrap.Toast(infoToast, { delay: 5000 }).show();
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
