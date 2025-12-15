<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng nhập - Hệ thống Quản lý Học sinh</title>

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
        background: #f0f4f8;
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      }

      .login-container {
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        max-width: 420px;
        width: 100%;
        padding: 40px;
      }

      .login-header {
        text-align: center;
        margin-bottom: 30px;
      }

      .login-header i {
        font-size: 60px;
        color: #4a90e2;
        margin-bottom: 15px;
      }

      .login-header h1 {
        font-size: 1.8rem;
        font-weight: 600;
        color: #333;
        margin-bottom: 8px;
      }

      .login-header p {
        color: #666;
        margin-bottom: 0;
      }

      .form-label {
        font-weight: 500;
        color: #555;
        margin-bottom: 8px;
      }

      .form-control {
        height: 45px;
        border-radius: 8px;
        border: 1px solid #ddd;
        padding: 0 15px;
        font-size: 0.95rem;
        transition: all 0.2s;
      }

      .form-control:focus {
        border-color: #4a90e2;
        box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.1);
        outline: none;
      }

      .input-group-text {
        background: white;
        border: 1px solid #ddd;
        border-right: none;
        border-radius: 8px 0 0 8px;
        padding: 0 12px;
        color: #999;
      }

      .input-group .form-control {
        border-left: none;
        border-radius: 0 8px 8px 0;
      }

      .input-group:focus-within .input-group-text {
        border-color: #4a90e2;
      }

      .btn-login {
        width: 100%;
        height: 45px;
        border-radius: 8px;
        background: #4a90e2;
        border: none;
        color: white;
        font-size: 1rem;
        font-weight: 500;
        transition: background 0.2s;
      }

      .btn-login:hover {
        background: #357abd;
      }

      .alert {
        border-radius: 8px;
        border: none;
        font-size: 0.9rem;
      }

      .form-check-input:checked {
        background-color: #4a90e2;
        border-color: #4a90e2;
      }

      .demo-box {
        background: #f8f9fa;
        padding: 15px;
        border-radius: 8px;
        border-left: 3px solid #4a90e2;
      }

      .demo-box small {
        font-size: 0.85rem;
      }

      @media (max-width: 768px) {
        .login-container {
          padding: 30px 25px;
        }

        .login-right {
          padding: 40px 30px;
        }

        .login-left i {
          font-size: 60px;
          margin-bottom: 20px;
        }

        .login-left h1 {
          font-size: 1.5rem;
        }
      }
    </style>
  </head>
  <body>
    <div class="login-container">
      <!-- Login Header -->
      <div class="login-header">
        <i class="bi bi-mortarboard-fill"></i>
        <h1>Đăng nhập hệ thống</h1>
        <p>Quản lý học sinh - Giáo viên - Điểm số</p>
      </div>

      <!-- Error Message -->
      <c:if test="${not empty error}">
        <div
          class="alert alert-danger alert-dismissible fade show"
          role="alert"
        >
          <i class="bi bi-exclamation-triangle-fill me-2"></i>
          ${error}
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="alert"
          ></button>
        </div>
      </c:if>

      <!-- Login Form -->
      <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="mb-3">
          <label for="username" class="form-label">Tên đăng nhập</label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="bi bi-person-fill text-muted"></i>
            </span>
            <input
              type="text"
              class="form-control"
              id="username"
              name="username"
              placeholder="Nhập tên đăng nhập"
              value="${username}"
              required
              autofocus
            />
          </div>
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">Mật khẩu</label>
          <div class="input-group">
            <span class="input-group-text">
              <i class="bi bi-lock-fill text-muted"></i>
            </span>
            <input
              type="password"
              class="form-control"
              id="password"
              name="password"
              placeholder="Nhập mật khẩu"
              required
            />
          </div>
        </div>

        <div class="mb-4 form-check">
          <input type="checkbox" class="form-check-input" id="remember" />
          <label class="form-check-label" for="remember">
            Ghi nhớ đăng nhập
          </label>
        </div>

        <button type="submit" class="btn btn-login">
          <i class="bi bi-box-arrow-in-right me-2"></i>
          Đăng nhập
        </button>
      </form>

      <!-- Additional Links -->
      <div class="text-center mt-3">
        <small class="text-muted">
          <i class="bi bi-info-circle me-1"></i>
          Quên mật khẩu? Liên hệ quản trị viên
        </small>
      </div>

      <!-- Demo Accounts Info -->
      <div class="mt-3 demo-box">
        <small class="text-muted d-block mb-2">
          <strong><i class="bi bi-key-fill me-1"></i>Tài khoản demo:</strong>
        </small>
        <small class="text-muted d-block">
          • Admin: <code>admin</code> / <code>admin123</code>
        </small>
        <small class="text-muted d-block">
          • Giáo viên: <code>gv1</code> / <code>gv1123</code>
        </small>
        <small class="text-muted d-block">
          • Học sinh: <code>hs1</code> / <code>hs1123</code>
        </small>
      </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
