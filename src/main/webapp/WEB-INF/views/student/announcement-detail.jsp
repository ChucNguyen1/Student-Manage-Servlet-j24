<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Chi tiết thông báo</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/thong-bao">Thông báo</a>
        </li>
        <li class="breadcrumb-item active">Chi tiết</li>
      </ol>
    </nav>
  </div>

  <section class="section">
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <c:if test="${not empty announcement}">
              <h3 class="card-title">
                <i class="bi bi-bell-fill text-warning"></i>
                ${announcement.tieuDe}
              </h3>

              <div class="mb-3">
                <small class="text-muted">
                  <i class="bi bi-person-circle"></i>
                  <strong>Người đăng:</strong> ${announcement.tenNguoiTao}
                  &nbsp;&nbsp;|&nbsp;&nbsp;
                  <i class="bi bi-calendar-event"></i>
                  <strong>Ngày đăng:</strong>
                  <fmt:formatDate
                    value="${announcement.ngayDang}"
                    pattern="dd/MM/yyyy HH:mm"
                  />
                </small>
              </div>

              <hr />

              <div
                class="announcement-content"
                style="
                  white-space: pre-wrap;
                  line-height: 1.8;
                  font-size: 1.05em;
                "
              >
                ${announcement.noiDung}
              </div>

              <hr />

              <div class="text-center mt-4">
                <a
                  href="${baseURL}/student/thong-bao"
                  class="btn btn-secondary"
                >
                  <i class="bi bi-arrow-left"></i> Quay lại danh sách
                </a>
              </div>
            </c:if>

            <c:if test="${empty announcement}">
              <div class="alert alert-warning">
                <i class="bi bi-exclamation-triangle"></i>
                Không tìm thấy thông báo này.
              </div>
              <div class="text-center">
                <a href="${baseURL}/student/thong-bao" class="btn btn-primary">
                  <i class="bi bi-arrow-left"></i> Về danh sách thông báo
                </a>
              </div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
