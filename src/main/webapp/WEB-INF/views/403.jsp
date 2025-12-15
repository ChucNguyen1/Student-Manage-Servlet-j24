<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>403 - Truy cập bị từ chối</title>

    <!-- Bootstrap 5 CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <!-- Bootstrap Icons -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
    />

    <style>
      body {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      }

      .error-container {
        background: white;
        border-radius: 20px;
        box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        padding: 60px 50px;
        max-width: 600px;
        text-align: center;
      }

      .error-icon {
        font-size: 120px;
        color: #dc3545;
        margin-bottom: 30px;
        animation: shake 0.5s;
      }

      @keyframes shake {
        0%,
        100% {
          transform: translateX(0);
        }
        25% {
          transform: translateX(-10px);
        }
        75% {
          transform: translateX(10px);
        }
      }

      .error-code {
        font-size: 80px;
        font-weight: 900;
        color: #333;
        margin-bottom: 10px;
        line-height: 1;
      }

      .error-title {
        font-size: 2rem;
        font-weight: 600;
        color: #333;
        margin-bottom: 20px;
      }

      .error-message {
        font-size: 1.1rem;
        color: #666;
        margin-bottom: 40px;
        line-height: 1.6;
      }

      .btn-home {
        padding: 12px 40px;
        border-radius: 10px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
        color: white;
        font-size: 1.1rem;
        font-weight: 600;
        text-decoration: none;
        display: inline-block;
        transition: transform 0.2s, box-shadow 0.2s;
      }

      .btn-home:hover {
        transform: translateY(-2px);
        box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
        color: white;
      }

      .btn-logout {
        padding: 12px 40px;
        border-radius: 10px;
        background: white;
        border: 2px solid #dc3545;
        color: #dc3545;
        font-size: 1.1rem;
        font-weight: 600;
        text-decoration: none;
        display: inline-block;
        transition: all 0.2s;
        margin-left: 10px;
      }

      .btn-logout:hover {
        background: #dc3545;
        color: white;
      }

      .user-info {
        background: #f8f9fa;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 30px;
        font-size: 0.95rem;
        color: #666;
      }

      .user-info strong {
        color: #333;
      }
    </style>
  </head>
  <body>
    <div class="error-container">
      <i class="bi bi-shield-exclamation error-icon"></i>

      <div class="error-code">403</div>

      <h1 class="error-title">Truy cập bị từ chối</h1>

      <p class="error-message">
        <c:choose>
          <c:when test="${not empty errorMessage}"> ${errorMessage} </c:when>
          <c:otherwise>
            Bạn không có quyền truy cập trang này. Vui lòng liên hệ quản trị
            viên nếu bạn cho rằng đây là lỗi.
          </c:otherwise>
        </c:choose>
      </p>

      <!-- Hiển thị thông tin người dùng hiện tại -->
      <c:if test="${not empty sessionScope.account}">
        <div class="user-info">
          <i class="bi bi-person-circle me-2"></i>
          Bạn đang đăng nhập với tài khoản:
          <strong>${sessionScope.hoTenHienThi}</strong>
          (<span class="text-muted">${sessionScope.role}</span>)
        </div>
      </c:if>

      <div class="mt-4">
        <!-- Nút quay về trang chủ -->
        <c:choose>
          <c:when test="${sessionScope.role == 'ADMIN'}">
            <a
              href="${pageContext.request.contextPath}/admin/dashboard"
              class="btn-home"
            >
              <i class="bi bi-house-door-fill me-2"></i>
              Về trang chủ
            </a>
          </c:when>
          <c:when test="${sessionScope.role == 'GIAOVIEN'}">
            <a
              href="${pageContext.request.contextPath}/teacher/home"
              class="btn-home"
            >
              <i class="bi bi-house-door-fill me-2"></i>
              Về trang chủ
            </a>
          </c:when>
          <c:when test="${sessionScope.role == 'HOCSINH'}">
            <a
              href="${pageContext.request.contextPath}/student/home"
              class="btn-home"
            >
              <i class="bi bi-house-door-fill me-2"></i>
              Về trang chủ
            </a>
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/login" class="btn-home">
              <i class="bi bi-box-arrow-in-right me-2"></i>
              Đăng nhập
            </a>
          </c:otherwise>
        </c:choose>

        <!-- Nút đăng xuất -->
        <c:if test="${not empty sessionScope.account}">
          <a
            href="${pageContext.request.contextPath}/logout"
            class="btn-logout"
          >
            <i class="bi bi-box-arrow-right me-2"></i>
            Đăng xuất
          </a>
        </c:if>
      </div>

      <!-- Thông tin hỗ trợ -->
      <div class="mt-4">
        <small class="text-muted">
          <i class="bi bi-info-circle me-1"></i>
          Nếu bạn nghĩ đây là lỗi, vui lòng liên hệ:
          <a href="mailto:admin@school.edu.vn">admin@school.edu.vn</a>
        </small>
      </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
