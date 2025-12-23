<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %> <%@ taglib
uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Quản lý Thông báo</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
        <li class="breadcrumb-item active">Thông báo</li>
      </ol>
    </nav>
  </div>

  <div
    class="toast-container position-fixed top-0 end-0 p-3"
    style="z-index: 1100"
  >
    <c:if test="${not empty sessionScope.message}">
      <div
        id="toastSuccess"
        class="toast"
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
      >
        <div class="toast-header bg-success text-white">
          <strong class="me-auto">Thành công</strong>
          <button
            type="button"
            class="btn-close btn-close-white"
            data-bs-dismiss="toast"
          ></button>
        </div>
        <div class="toast-body">${sessionScope.message}</div>
      </div>
      <c:remove var="message" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.error}">
      <div
        id="toastError"
        class="toast"
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
      >
        <div class="toast-header bg-danger text-white">
          <strong class="me-auto">Lỗi</strong>
          <button
            type="button"
            class="btn-close btn-close-white"
            data-bs-dismiss="toast"
          ></button>
        </div>
        <div class="toast-body">${sessionScope.error}</div>
      </div>
      <c:remove var="error" scope="session" />
    </c:if>
  </div>

  <section class="section">
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              Danh sách Thông báo
              <button
                type="button"
                class="btn btn-primary btn-sm float-end"
                data-bs-toggle="modal"
                data-bs-target="#modalThem"
              >
                <i class="bi bi-plus-circle"></i> Thêm mới
              </button>
            </h5>

            <%-- THANH TÌM KIẾM --%>
            <div class="row mb-3">
              <div class="col-12">
                <form
                  action="${baseURL}/admin/thongbao-list"
                  method="GET"
                  class="d-flex justify-content-end"
                >
                  <div class="input-group" style="width: 350px">
                    <input
                      type="text"
                      name="searchKey"
                      class="form-control"
                      placeholder="Tìm theo tiêu đề..."
                      value="${param.searchKey}"
                    />
                    <button class="btn btn-outline-secondary" type="submit">
                      <i class="bi bi-search"></i>
                    </button>
                  </div>
                </form>
              </div>
            </div>

            <%-- BẢNG DỮ LIỆU --%>
            <div class="table-responsive">
              <table class="table table-striped table-hover align-middle">
                <thead>
                  <tr>
                    <th style="width: 5%">ID</th>
                    <th style="width: 25%">Tiêu đề</th>
                    <th style="width: 35%">Nội dung tóm tắt</th>
                    <th style="width: 15%">Người đăng</th>
                    <th style="width: 10%">Ngày đăng</th>
                    <th style="width: 10%">Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  <c:if test="${empty dsThongBao}">
                    <tr>
                      <td colspan="6" class="text-center">
                        Không có thông báo nào.
                      </td>
                    </tr>
                  </c:if>

                  <c:forEach var="tb" items="${dsThongBao}">
                    <tr>
                      <td>${tb.maTB}</td>
                      <td><strong>${tb.tieuDe}</strong></td>
                      <td>
                        <%-- Cắt chuỗi nội dung nếu dài quá 100 ký tự --%>
                        <c:choose>
                          <c:when test="${fn:length(tb.noiDung) > 100}">
                            ${fn:substring(tb.noiDung, 0, 100)}...
                          </c:when>
                          <c:otherwise>${tb.noiDung}</c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <span class="badge bg-info text-dark">
                          <i class="bi bi-person"></i> ${tb.tenNguoiTao}
                        </span>
                      </td>
                      <td>
                        <fmt:formatDate
                          value="${tb.ngayDang}"
                          pattern="dd/MM/yyyy HH:mm"
                        />
                      </td>
                      <td>
                        <button
                          type="button"
                          class="btn btn-warning btn-sm"
                          title="Sửa"
                          data-bs-toggle="modal"
                          data-bs-target="#modalSua"
                          data-id="${tb.maTB}"
                          data-tieude="${tb.tieuDe}"
                          data-noidung="${tb.noiDung}"
                        >
                          <i class="bi bi-pencil-square"></i>
                        </button>
                        <a
                          href="${baseURL}/admin/thongbao-delete?id=${tb.maTB}"
                          class="btn btn-danger btn-sm"
                          title="Xóa"
                          onclick="return confirm('Bạn có chắc muốn xóa thông báo này không?');"
                        >
                          <i class="bi bi-trash"></i>
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <%-- PHÂN TRANG --%>
            <c:set var="pageSize" value="10" />
            <div class="row align-items-center mt-3">
              <div class="col-md-6">
                <span class="text-muted">
                  Trang ${currentPage} / ${totalPages}
                </span>
              </div>
              <div class="col-md-6">
                <nav aria-label="Page navigation">
                  <ul class="pagination justify-content-end mb-0">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                      <a
                        class="page-link"
                        href="${baseURL}/admin/thongbao-list?page=${currentPage - 1}&searchKey=${param.searchKey}"
                        >Trước</a
                      >
                    </li>
                    <c:forEach
                      var="i"
                      begin="1"
                      end="${totalPages > 0 ? totalPages : 1}"
                    >
                      <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a
                          class="page-link"
                          href="${baseURL}/admin/thongbao-list?page=${i}&searchKey=${param.searchKey}"
                          >${i}</a
                        >
                      </li>
                    </c:forEach>
                    <li
                      class="page-item ${currentPage >= totalPages ? 'disabled' : ''}"
                    >
                      <a
                        class="page-link"
                        href="${baseURL}/admin/thongbao-list?page=${currentPage + 1}&searchKey=${param.searchKey}"
                        >Sau</a
                      >
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <%-- ==================== MODAL THÊM MỚI ==================== --%>
  <div class="modal fade" id="modalThem" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <form action="${baseURL}/admin/thongbao-add" method="POST">
          <div class="modal-header">
            <h5 class="modal-title">Đăng thông báo mới</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label"
                >Tiêu đề <span class="text-danger">*</span></label
              >
              <input type="text" name="tieuDe" class="form-control" required />
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Nội dung <span class="text-danger">*</span></label
              >
              <textarea
                name="noiDung"
                class="form-control"
                rows="6"
                required
              ></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Hủy
            </button>
            <button type="submit" class="btn btn-primary">Đăng ngay</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <%-- ==================== MODAL SỬA (Cập nhật) ==================== --%>
  <div class="modal fade" id="modalSua" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <form action="${baseURL}/admin/thongbao-edit" method="POST">
          <div class="modal-header">
            <h5 class="modal-title">Chỉnh sửa thông báo</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <%-- ID ẩn để biết đang sửa bài nào --%>
            <input type="hidden" id="maTB_edit" name="maTB" />

            <div class="mb-3">
              <label class="form-label"
                >Tiêu đề <span class="text-danger">*</span></label
              >
              <input
                type="text"
                id="tieuDe_edit"
                name="tieuDe"
                class="form-control"
                required
              />
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Nội dung <span class="text-danger">*</span></label
              >
              <textarea
                id="noiDung_edit"
                name="noiDung"
                class="form-control"
                rows="6"
                required
              ></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Hủy
            </button>
            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</main>

<%-- SCRIPT --%>
<script>
  document.addEventListener("DOMContentLoaded", (event) => {
    // 1. Kích hoạt Toast
    const toastSuccessEl = document.getElementById("toastSuccess");
    if (toastSuccessEl)
      new bootstrap.Toast(toastSuccessEl, { delay: 3000 }).show();

    const toastErrorEl = document.getElementById("toastError");
    if (toastErrorEl) new bootstrap.Toast(toastErrorEl, { delay: 3000 }).show();

    // 2. Xử lý Modal Sửa (Đổ dữ liệu vào form)
    const modalSua = document.getElementById("modalSua");
    if (modalSua) {
      modalSua.addEventListener("show.bs.modal", function (event) {
        // Nút nào đã kích hoạt modal
        const button = event.relatedTarget;

        // Lấy dữ liệu từ data-attribute của nút đó
        const id = button.getAttribute("data-id");
        const tieuDe = button.getAttribute("data-tieude");
        const noiDung = button.getAttribute("data-noidung");

        // Điền vào form
        modalSua.querySelector("#maTB_edit").value = id;
        modalSua.querySelector("#tieuDe_edit").value = tieuDe;
        modalSua.querySelector("#noiDung_edit").value = noiDung;
      });
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
