<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Quản lý Học kỳ</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
        <li class="breadcrumb-item">Cấu hình</li>
        <li class="breadcrumb-item active">Học kỳ</li>
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
              Danh sách Học kỳ
              <button
                type="button"
                class="btn btn-primary btn-sm float-end"
                data-bs-toggle="modal"
                data-bs-target="#modalThemMoi"
              >
                <i class="bi bi-plus-circle"></i> Thêm mới
              </button>
            </h5>

            <div class="row mb-3">
              <div class="col-12">
                <form
                  action="${baseURL}/admin/hocky-list"
                  method="GET"
                  class="d-flex justify-content-end"
                >
                  <div class="input-group" style="width: 350px">
                    <input
                      type="text"
                      name="searchKey"
                      class="form-control"
                      placeholder="Tìm theo tên học kỳ hoặc năm học..."
                      value="${param.searchKey}"
                    />
                    <button class="btn btn-outline-secondary" type="submit">
                      <i class="bi bi-search"></i>
                    </button>
                  </div>
                </form>
              </div>
            </div>

            <div class="table-responsive">
              <table class="table table-striped table-hover align-middle">
                <thead>
                  <tr>
                    <th>Tên Học Kỳ</th>
                    <th>Hệ Số</th>
                    <th>Thuộc Năm Học</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  <c:if test="${empty dsHocKy}">
                    <tr>
                      <td colspan="5" class="text-center">Không có dữ liệu.</td>
                    </tr>
                  </c:if>

                  <c:forEach var="hk" items="${dsHocKy}">
                    <tr>
                      <td><strong>${hk.tenHK}</strong></td>
                      <td>
                        <span class="badge bg-info text-dark">x${hk.heSo}</span>
                      </td>
                      <td>
                        <i class="bi bi-calendar-event"></i> ${hk.tenNamHoc}
                      </td>
                      <td>
                        <div class="form-check form-switch">
                          <input class="form-check-input" type="checkbox"
                          id="switch-${hk.maHK}" ${hk.trangThai ? 'checked' :
                          ''} onchange="toggleStatus('${hk.maHK}', this)">
                          <label
                            class="form-check-label"
                            for="switch-${hk.maHK}"
                          >
                            ${hk.trangThai ? 'Hiển thị' : 'Ẩn'}
                          </label>
                        </div>
                      </td>

                      <td>
                        <button
                          type="button"
                          class="btn btn-warning btn-sm"
                          title="Sửa"
                          data-bs-toggle="modal"
                          data-bs-target="#modalSua"
                          data-id="${hk.maHK}"
                          data-ten="${hk.tenHK}"
                          data-heso="${hk.heSo}"
                          data-manh="${hk.maNH}"
                        >
                          <i class="bi bi-pencil-square"></i>
                        </button>

                        <a
                          href="${baseURL}/admin/hocky-delete?id=${hk.maHK}"
                          class="btn btn-danger btn-sm"
                          title="Xóa vĩnh viễn"
                          onclick="return confirm('CẢNH BÁO: Bạn có chắc chắn xóa học kỳ này không?');"
                        >
                          <i class="bi bi-trash"></i>
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <c:set var="pageSize" value="10" />
            <div class="row align-items-center mt-3">
              <div class="col-md-6">
                <span class="text-muted">
                  <c:set
                    var="startItem"
                    value="${(currentPage - 1) * pageSize + 1}"
                  />
                  <c:set var="endItem" value="${currentPage * pageSize}" />
                  <c:if test="${endItem > totalItems}"
                    ><c:set var="endItem" value="${totalItems}"
                  /></c:if>
                  <c:if test="${totalItems > 0}"
                    >Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong
                    <b>${totalItems}</b> học kỳ</c:if
                  >
                </span>
              </div>
              <div class="col-md-6">
                <nav aria-label="Page navigation">
                  <ul class="pagination justify-content-end mb-0">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                      <a
                        class="page-link"
                        href="${baseURL}/admin/hocky-list?page=${currentPage - 1}&searchKey=${param.searchKey}"
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
                          href="${baseURL}/admin/hocky-list?page=${i}&searchKey=${param.searchKey}"
                          >${i}</a
                        >
                      </li>
                    </c:forEach>
                    <li
                      class="page-item ${currentPage >= totalPages ? 'disabled' : ''}"
                    >
                      <a
                        class="page-link"
                        href="${baseURL}/admin/hocky-list?page=${currentPage + 1}&searchKey=${param.searchKey}"
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
  <div class="modal fade" id="modalThemMoi" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="${baseURL}/admin/hocky-add" method="POST">
          <div class="modal-header">
            <h5 class="modal-title">Thêm mới Học kỳ</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label class="form-label"
                >Tên Học Kỳ <span class="text-danger">*</span></label
              >
              <input
                type="text"
                class="form-control"
                name="tenHK"
                required
                placeholder="Ví dụ: Học kỳ 1"
              />
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Hệ Số <span class="text-danger">*</span></label
              >
              <input
                type="number"
                class="form-control"
                name="heSo"
                value="1"
                min="1"
                required
              />
              <div class="form-text">Thường là 1 cho HK1, 2 cho HK2</div>
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Thuộc Năm Học <span class="text-danger">*</span></label
              >
              <select class="form-select" name="maNH" required>
                <option value="" selected disabled>-- Chọn Năm Học --</option>
                <%-- VÒNG LẶP ĐỔ DỮ LIỆU NĂM HỌC VÀO DROPDOWN --%>
                <c:forEach var="nh" items="${dsNamHoc}">
                  <option value="${nh.maNH}">${nh.tenNH}</option>
                </c:forEach>
              </select>
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
            <button type="submit" class="btn btn-primary">Lưu lại</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <%-- ==================== MODAL SỬA ==================== --%>
  <div class="modal fade" id="modalSua" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="${baseURL}/admin/hocky-edit" method="POST">
          <div class="modal-header">
            <h5 class="modal-title">Cập nhật Học kỳ</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <input type="hidden" id="maHK_edit" name="maHK_edit" />

            <div class="mb-3">
              <label class="form-label"
                >Tên Học Kỳ <span class="text-danger">*</span></label
              >
              <input
                type="text"
                class="form-control"
                id="tenHK_edit"
                name="tenHK_edit"
                required
              />
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Hệ Số <span class="text-danger">*</span></label
              >
              <input
                type="number"
                class="form-control"
                id="heSo_edit"
                name="heSo_edit"
                min="1"
                required
              />
            </div>
            <div class="mb-3">
              <label class="form-label"
                >Thuộc Năm Học <span class="text-danger">*</span></label
              >
              <select
                class="form-select"
                id="maNH_edit"
                name="maNH_edit"
                required
              >
                <option value="" disabled>-- Chọn Năm Học --</option>
                <c:forEach var="nh" items="${dsNamHoc}">
                  <option value="${nh.maNH}">${nh.tenNH}</option>
                </c:forEach>
              </select>
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

<%-- SCRIPT XỬ LÝ --%>
<script>
  function toggleStatus(maHK, checkbox) {
    const newStatus = checkbox.checked;
    const label = checkbox.nextElementSibling;
    label.textContent = newStatus ? "Hiển thị" : "Ẩn";

    fetch("${baseURL}/admin/hocky-status?id=" + maHK + "&status=" + newStatus, {
      method: "GET",
    }).then((response) => {
      if (!response.ok) {
        alert("Lỗi cập nhật trạng thái!");
        checkbox.checked = !newStatus;
        label.textContent = !newStatus ? "Hiển thị" : "Ẩn";
      }
    });
  }

  document.addEventListener("DOMContentLoaded", (event) => {
    const toastSuccessEl = document.getElementById("toastSuccess");
    if (toastSuccessEl)
      new bootstrap.Toast(toastSuccessEl, { delay: 5000 }).show();
    const toastErrorEl = document.getElementById("toastError");
    if (toastErrorEl) new bootstrap.Toast(toastErrorEl, { delay: 5000 }).show();

    const modalSua = document.getElementById("modalSua");
    if (modalSua) {
      modalSua.addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget;

        const id = button.getAttribute("data-id");
        const ten = button.getAttribute("data-ten");
        const heso = button.getAttribute("data-heso");
        const manh = button.getAttribute("data-manh");

        modalSua.querySelector("#maHK_edit").value = id;
        modalSua.querySelector("#tenHK_edit").value = ten;
        modalSua.querySelector("#heSo_edit").value = heso;

        modalSua.querySelector("#maNH_edit").value = manh;
      });
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
