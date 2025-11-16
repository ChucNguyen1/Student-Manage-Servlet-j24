<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <%-- 1. TIÊU ĐỀ & BREADCRUMBS --%>
    <div class="pagetitle">
      <h1>Quản lý Năm học</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Cấu hình</li>
          <li class="breadcrumb-item active">Năm học</li>
        </ol>
      </nav>
    </div>

    <%-- 2. TOAST THÔNG BÁO --%>
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100">
      <c:if test="${not empty sessionScope.message}">
        <div id="toastSuccess" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="toast-header bg-success text-white">
            <strong class="me-auto">Thành công</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.message}</div>
        </div>
        <c:remove var="message" scope="session" />
      </c:if>

      <c:if test="${not empty sessionScope.error}">
        <div id="toastError" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="toast-header bg-danger text-white">
            <strong class="me-auto">Lỗi</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.error}</div>
        </div>
        <c:remove var="error" scope="session" />
      </c:if>
    </div>

    <%-- 3. NỘI DUNG CHÍNH --%>
    <section class="section">
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              
              <h5 class="card-title">
                Danh sách Năm học
                <button type="button" class="btn btn-primary btn-sm float-end" 
                        data-bs-toggle="modal" data-bs-target="#modalThemMoi">
                  <i class="bi bi-plus-circle"></i> Thêm mới
                </button>
              </h5>

              <%-- THANH TÌM KIẾM --%>
              <div class="row mb-3">
                <div class="col-12">
                    <form action="${baseURL}/admin/namhoc-list" method="GET" class="d-flex justify-content-end">
                       <div class="input-group" style="width: 350px;">
                         <input type="text" name="searchKey" class="form-control" 
                                placeholder="Nhập mã hoặc tên năm học..." value="${param.searchKey}">
                         <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i></button>
                       </div>
                    </form>
                </div>
              </div>

              <%-- BẢNG DỮ LIỆU --%>
              <div class="table-responsive">
                  <table class="table table-striped table-hover align-middle">
                    <thead>
                      <tr>
                        <th>Mã Năm Học</th>
                        <th>Tên Năm Học</th>
                        <th>Thời gian</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty dsNamHoc}">
                            <tr><td colspan="5" class="text-center">Không có dữ liệu.</td></tr>
                        </c:if>

                        <c:forEach var="nh" items="${dsNamHoc}">
                            <tr>
                              <td><strong>${nh.maNH}</strong></td>
                              <td>${nh.tenNH}</td>
                              <td>
                                <%-- Format ngày tháng --%>
                                <small class="text-muted">Từ:</small> <fmt:formatDate value="${nh.ngayBatDau}" pattern="dd/MM/yyyy"/> <br>
                                <small class="text-muted">Đến:</small> <fmt:formatDate value="${nh.ngayKetThuc}" pattern="dd/MM/yyyy"/>
                              </td>
                              <%-- CỘT TRẠNG THÁI (SWITCH) --%>
                              <td>
                                <div class="form-check form-switch">
                                  <%-- 
                                    onchange: Gọi hàm JS toggleStatus khi bấm
                                    checked: Nếu status = true thì bật
                                  --%>
                                  <input class="form-check-input" type="checkbox" 
                                         id="switch-${nh.maNH}" 
                                         ${nh.trangThai ? 'checked' : ''}
                                         onchange="toggleStatus('${nh.maNH}', this)">
                                  <label class="form-check-label" for="switch-${nh.maNH}">
                                      ${nh.trangThai ? 'Hiển thị' : 'Ẩn'}
                                  </label>
                                </div>
                              </td>
                              <td>
                                <%-- NÚT SỬA --%>
                                <button type="button" class="btn btn-warning btn-sm" title="Sửa"
                                        data-bs-toggle="modal" data-bs-target="#modalSua"
                                        data-id="${nh.maNH}"
                                        data-ten="${nh.tenNH}"
                                        data-start="${nh.ngayBatDau}"
                                        data-end="${nh.ngayKetThuc}">
                                  <i class="bi bi-pencil-square"></i>
                                </button>

                                <%-- NÚT XÓA --%>
                                <a href="${baseURL}/admin/namhoc-delete?id=${nh.maNH}" class="btn btn-danger btn-sm" title="Xóa vĩnh viễn"
                                   onclick="return confirm('CẢNH BÁO: Hành động này sẽ xóa vĩnh viễn năm học [${nh.tenNH}].\nNếu năm học đã có dữ liệu, bạn sẽ không thể xóa.\nBạn có chắc chắn không?');">
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
                          <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
                          <c:set var="endItem" value="${currentPage * pageSize}" />
                          <c:if test="${endItem > totalItems}"><c:set var="endItem" value="${totalItems}" /></c:if>
                          <c:if test="${totalItems > 0}">Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> năm học</c:if>
                          <c:if test="${totalItems == 0}">Chưa có dữ liệu</c:if>
                      </span>
                  </div>
                  <div class="col-md-6">
                      <nav aria-label="Page navigation">
                          <ul class="pagination justify-content-end mb-0">
                              <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/namhoc-list?page=${currentPage - 1}&searchKey=${param.searchKey}">Trước</a>
                              </li>
                              <c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}">
                                  <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${baseURL}/admin/namhoc-list?page=${i}&searchKey=${param.searchKey}">${i}</a>
                                  </li>
                              </c:forEach>
                              <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/namhoc-list?page=${currentPage + 1}&searchKey=${param.searchKey}">Sau</a>
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
          <form action="${baseURL}/admin/namhoc-add" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Thêm mới Năm học</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <div class="mb-3">
                  <label class="form-label">Mã Năm Học (Ví dụ: 2024-2025) <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" name="maNH" required placeholder="Nhập mã định danh...">
              </div>
              <div class="mb-3">
                  <label class="form-label">Tên Năm Học <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" name="tenNH" required placeholder="Ví dụ: Năm học 2024-2025">
              </div>
              <div class="row">
                  <div class="col-6 mb-3">
                      <label class="form-label">Ngày Bắt Đầu</label>
                      <input type="date" class="form-control" name="ngayBatDau">
                  </div>
                  <div class="col-6 mb-3">
                      <label class="form-label">Ngày Kết Thúc</label>
                      <input type="date" class="form-control" name="ngayKetThuc">
                  </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
          <form action="${baseURL}/admin/namhoc-edit" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật Năm học</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              
              <%-- MÃ NĂM HỌC KHÔNG ĐƯỢC SỬA --%>
              <div class="mb-3">
                  <label class="form-label">Mã Năm Học</label>
                  <%-- Input hiển thị (Disabled) --%>
                  <input type="text" class="form-control bg-light" id="maNH_display" disabled>
                  <%-- Input ẩn để gửi dữ liệu --%>
                  <input type="hidden" id="maNH_edit" name="maNH_edit">
              </div>

              <div class="mb-3">
                  <label class="form-label">Tên Năm Học <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" id="tenNH_edit" name="tenNH_edit" required>
              </div>
              <div class="row">
                  <div class="col-6 mb-3">
                      <label class="form-label">Ngày Bắt Đầu</label>
                      <input type="date" class="form-control" id="ngayBatDau_edit" name="ngayBatDau_edit">
                  </div>
                  <div class="col-6 mb-3">
                      <label class="form-label">Ngày Kết Thúc</label>
                      <input type="date" class="form-control" id="ngayKetThuc_edit" name="ngayKetThuc_edit">
                  </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </div>
          </form>
        </div>
      </div>
    </div>

</main>

<%-- SCRIPT XỬ LÝ --%>
<script>
  document.addEventListener('DOMContentLoaded', (event) => {
    // Kích hoạt Toast
    const toastSuccessEl = document.getElementById('toastSuccess');
    if (toastSuccessEl) new bootstrap.Toast(toastSuccessEl, {delay: 5000}).show();
    
    const toastErrorEl = document.getElementById('toastError');
    if (toastErrorEl) new bootstrap.Toast(toastErrorEl, {delay: 5000}).show();

    // Xử lý Modal Sửa
    const modalSua = document.getElementById('modalSua');
    if(modalSua) {
        modalSua.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            
            const id = button.getAttribute('data-id');
            const ten = button.getAttribute('data-ten');
            const start = button.getAttribute('data-start');
            const end = button.getAttribute('data-end');

            modalSua.querySelector('#maNH_display').value = id; // Hiển thị
            modalSua.querySelector('#maNH_edit').value = id;    // Gửi đi
            modalSua.querySelector('#tenNH_edit').value = ten;
            modalSua.querySelector('#ngayBatDau_edit').value = start;
            modalSua.querySelector('#ngayKetThuc_edit').value = end;
        });
    }
  });
  
  function toggleStatus(maNH, checkbox) {
      const newStatus = checkbox.checked; // true hoặc false
      const label = checkbox.nextElementSibling; // Thẻ label bên cạnh
      
      // Cập nhật giao diện ngay lập tức cho mượt
      label.textContent = newStatus ? 'Hiển thị' : 'Ẩn';

      // Gửi AJAX request về Server
      // URL: /admin/namhoc-status?id=2024-2025&status=true
      fetch('${baseURL}/admin/namhoc-status?id=' + maNH + '&status=' + newStatus, {
          method: 'GET'
      })
      .then(response => {
          if (response.ok) {
              // Thành công: Hiện Toast nhỏ (Tùy chọn)
              console.log("Cập nhật trạng thái thành công");
          } else {
              // Thất bại: Báo lỗi và revert cái switch lại
              alert("Lỗi kết nối! Không thể cập nhật trạng thái.");
              checkbox.checked = !newStatus;
              label.textContent = !newStatus ? 'Hiển thị' : 'Ẩn';
          }
      })
      .catch(error => {
          console.error('Error:', error);
          checkbox.checked = !newStatus;
      });
  }
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />