<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Đổi mật khẩu</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/profile">Thông tin cá nhân</a>
        </li>
        <li class="breadcrumb-item active">Đổi mật khẩu</li>
      </ol>
    </nav>
  </div>

  <section class="section">
    <div class="row justify-content-center">
      <div class="col-lg-6">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Thay đổi mật khẩu</h5>

            <c:if test="${not empty successMessage}">
              <div
                class="alert alert-success alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-check-circle me-1"></i>
                ${successMessage}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                  aria-label="Close"
                ></button>
              </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
              <div
                class="alert alert-danger alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-exclamation-octagon me-1"></i>
                ${errorMessage}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                  aria-label="Close"
                ></button>
              </div>
            </c:if>

            <form
              method="post"
              action="${baseURL}/student/change-password"
              class="mt-3"
            >
              <div class="mb-3">
                <label for="currentPassword" class="form-label"
                  >Mật khẩu hiện tại <span class="text-danger">*</span></label
                >
                <input
                  type="password"
                  class="form-control"
                  id="currentPassword"
                  name="currentPassword"
                  required
                  minlength="6"
                />
              </div>

              <div class="mb-3">
                <label for="newPassword" class="form-label"
                  >Mật khẩu mới <span class="text-danger">*</span></label
                >
                <input
                  type="password"
                  class="form-control"
                  id="newPassword"
                  name="newPassword"
                  required
                  minlength="6"
                />
                <small class="text-muted"
                  >Mật khẩu phải có ít nhất 6 ký tự</small
                >
              </div>

              <div class="mb-3">
                <label for="confirmPassword" class="form-label"
                  >Xác nhận mật khẩu mới
                  <span class="text-danger">*</span></label
                >
                <input
                  type="password"
                  class="form-control"
                  id="confirmPassword"
                  name="confirmPassword"
                  required
                  minlength="6"
                />
              </div>

              <div class="alert alert-info">
                <h6 class="alert-heading">
                  <i class="bi bi-info-circle me-1"></i>Lưu ý:
                </h6>
                <ul class="mb-0">
                  <li>Mật khẩu mới phải có ít nhất 6 ký tự</li>
                  <li>Nên sử dụng kết hợp chữ hoa, chữ thường và số</li>
                  <li>Không chia sẻ mật khẩu với người khác</li>
                </ul>
              </div>

              <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary">
                  <i class="bi bi-shield-lock me-1"></i>Đổi mật khẩu
                </button>
                <a href="${baseURL}/student/profile" class="btn btn-secondary">
                  <i class="bi bi-arrow-left me-1"></i>Quay lại
                </a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
