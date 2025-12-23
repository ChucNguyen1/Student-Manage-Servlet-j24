<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Thông báo</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item active">Thông báo</li>
      </ol>
    </nav>
  </div>

  <section class="section">
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              <i class="bi bi-megaphone text-warning"></i> Danh sách thông báo
            </h5>

            <%-- Form tìm kiếm --%>
            <div class="row mb-3">
              <div class="col-md-6">
                <form
                  method="get"
                  action="${baseURL}/student/thong-bao"
                  class="d-flex"
                >
                  <input
                    type="text"
                    name="search"
                    class="form-control me-2"
                    placeholder="Tìm kiếm theo tiêu đề..."
                    value="${searchKey}"
                  />
                  <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Tìm
                  </button>
                </form>
              </div>
              <div class="col-md-6 text-end">
                <c:if test="${not empty searchKey}">
                  <a
                    href="${baseURL}/student/thong-bao"
                    class="btn btn-secondary"
                  >
                    <i class="bi bi-x-circle"></i> Xóa tìm kiếm
                  </a>
                </c:if>
              </div>
            </div>

            <%-- Hiển thị thông báo --%>
            <c:choose>
              <c:when test="${empty announcements}">
                <div class="alert alert-info">
                  <i class="bi bi-info-circle"></i> Chưa có thông báo nào.
                </div>
              </c:when>
              <c:otherwise>
                <div class="list-group">
                  <c:forEach var="announcement" items="${announcements}">
                    <a
                      href="${baseURL}/student/thong-bao/chi-tiet?id=${announcement.maTB}"
                      class="list-group-item list-group-item-action ${readStatusMap[announcement.maTB] ? '' : 'list-group-item-primary'}"
                      style="text-decoration: none; color: inherit"
                    >
                      <div
                        class="d-flex w-100 justify-content-between align-items-start"
                      >
                        <div class="flex-grow-1">
                          <h5 class="mb-2">
                            <c:choose>
                              <c:when
                                test="${readStatusMap[announcement.maTB]}"
                              >
                                <i class="bi bi-bell text-warning"></i>
                              </c:when>
                              <c:otherwise>
                                <i class="bi bi-bell-fill text-warning"></i>
                                <span class="badge bg-danger ms-2">Mới</span>
                              </c:otherwise>
                            </c:choose>
                            ${announcement.tieuDe}
                          </h5>
                          <p
                            class="mb-2 text-muted text-truncate"
                            style="max-width: 90%"
                          >
                            ${announcement.noiDung}
                          </p>
                          <small class="text-muted">
                            <i class="bi bi-person"></i>
                            ${announcement.tenNguoiTao} |
                            <i class="bi bi-calendar"></i>
                            <fmt:formatDate
                              value="${announcement.ngayDang}"
                              pattern="dd/MM/yyyy HH:mm"
                            />
                          </small>
                        </div>
                        <div>
                          <i class="bi bi-chevron-right"></i>
                        </div>
                      </div>
                    </a>
                  </c:forEach>
                </div>

                <%-- Phân trang --%>
                <c:if test="${totalPages > 1}">
                  <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                      <%-- Nút Previous --%>
                      <li
                        class="page-item ${currentPage == 1 ? 'disabled' : ''}"
                      >
                        <a
                          class="page-link"
                          href="${baseURL}/student/thong-bao?page=${currentPage - 1}<c:if test='${not empty searchKey}'>&search=${searchKey}</c:if>"
                        >
                          <i class="bi bi-chevron-left"></i>
                        </a>
                      </li>

                      <%-- Các số trang --%>
                      <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:if
                          test="${i == 1 || i == totalPages || (i >= currentPage - 2 && i <= currentPage + 2)}"
                        >
                          <li
                            class="page-item ${i == currentPage ? 'active' : ''}"
                          >
                            <a
                              class="page-link"
                              href="${baseURL}/student/thong-bao?page=${i}<c:if test='${not empty searchKey}'>&search=${searchKey}</c:if>"
                            >
                              ${i}
                            </a>
                          </li>
                        </c:if>
                        <c:if
                          test="${(i == currentPage - 3 && i > 1) || (i == currentPage + 3 && i < totalPages)}"
                        >
                          <li class="page-item disabled">
                            <span class="page-link">...</span>
                          </li>
                        </c:if>
                      </c:forEach>

                      <%-- Nút Next --%>
                      <li
                        class="page-item ${currentPage == totalPages ? 'disabled' : ''}"
                      >
                        <a
                          class="page-link"
                          href="${baseURL}/student/thong-bao?page=${currentPage + 1}<c:if test='${not empty searchKey}'>&search=${searchKey}</c:if>"
                        >
                          <i class="bi bi-chevron-right"></i>
                        </a>
                      </li>
                    </ul>
                  </nav>

                  <div class="text-center text-muted">
                    <small
                      >Trang ${currentPage} / ${totalPages} (Tổng
                      ${totalRecords} thông báo)</small
                    >
                  </div>
                </c:if>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
