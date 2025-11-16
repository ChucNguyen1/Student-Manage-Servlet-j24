<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> <%-- Thư viện để format ngày tháng --%>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <%-- 1. TIÊU ĐỀ & BREADCRUMBS --%>
    <div class="pagetitle">
      <h1>Quản lý Giáo viên</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Nghiệp vụ</li>
          <li class="breadcrumb-item active">Giáo viên</li>
        </ol>
      </nav>
    </div>

    <%-- 2. TOAST THÔNG BÁO (Góc trên phải) --%>
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
                Danh sách Giáo viên
                <button type="button" class="btn btn-primary btn-sm float-end" 
                        data-bs-toggle="modal" data-bs-target="#modalThemMoi">
                  <i class="bi bi-plus-circle"></i> Thêm mới
                </button>
              </h5>

              <%-- THANH TÌM KIẾM --%>
              <div class="row mb-3">
                <div class="col-12">
                    <form action="${baseURL}/admin/giaovien-list" method="GET" class="d-flex justify-content-end">
                       <div class="input-group" style="width: 350px;">
                         <input type="text" name="searchKey" class="form-control" 
                                placeholder="Tìm theo tên hoặc email..." value="${param.searchKey}">
                         <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i></button>
                       </div>
                    </form>
                </div>
              </div>

              <%-- BẢNG DỮ LIỆU --%>
              <div class="table-responsive"> <%-- Thêm class này để bảng cuộn ngang nếu màn hình nhỏ --%>
                  <table class="table table-striped table-hover align-middle">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Họ và Tên</th>
                        <th>Giới tính</th>
                        <th>Ngày sinh</th>
                        <th>Chuyên môn</th>
                        <th>Liên hệ</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty dsGiaoVien}">
                            <tr><td colspan="7" class="text-center">Không có dữ liệu.</td></tr>
                        </c:if>

                        <c:forEach var="gv" items="${dsGiaoVien}">
                            <tr>
                              <td>${gv.maGV}</td>
                              <td><strong>${gv.hoTen}</strong></td>
                              <td>
                                <c:choose>
                                    <c:when test="${gv.gioiTinh == 'Nam'}"><span class="badge bg-info">Nam</span></c:when>
                                    <c:when test="${gv.gioiTinh == 'Nữ'}"><span class="badge bg-danger">Nữ</span></c:when>
                                    <c:otherwise>${gv.gioiTinh}</c:otherwise>
                                </c:choose>
                              </td>
                              <td><fmt:formatDate value="${gv.ngaySinh}" pattern="dd/MM/yyyy"/></td>
                              <td>${gv.chuyenMon}</td>
                              <td>
                                <small><i class="bi bi-envelope"></i> ${gv.email}</small><br>
                                <small><i class="bi bi-telephone"></i> ${gv.sdt}</small>
                              </td>
                              <td>
                                <%-- NÚT SỬA: Lưu trữ tất cả dữ liệu vào data-attributes --%>
                                <button type="button" class="btn btn-warning btn-sm" title="Sửa"
                                        data-bs-toggle="modal" data-bs-target="#modalSua"
                                        data-id="${gv.maGV}"
                                        data-ten="${gv.hoTen}"
                                        data-ngaysinh="${gv.ngaySinh}" <%-- Output format: yyyy-MM-dd --%>
                                        data-gioitinh="${gv.gioiTinh}"
                                        data-chuyenmon="${gv.chuyenMon}"
                                        data-email="${gv.email}"
                                        data-sdt="${gv.sdt}"
                                        data-diachi="${gv.diaChi}">
                                  <i class="bi bi-pencil-square"></i>
                                </button>

                                <a href="${baseURL}/admin/giaovien-delete?id=${gv.maGV}" class="btn btn-danger btn-sm" title="Xóa"
                                   onclick="return confirm('Bạn có chắc muốn xóa giáo viên ${gv.hoTen} không?');">
                                  <i class="bi bi-trash"></i>
                                </a>
                              </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                  </table>
              </div>

<%-- PHÂN TRANG (Giao diện giống Khối) --%>
              
              <%-- Đặt pageSize = 10 (vì trong Controller ta đang gán cứng là 10) --%>
              <c:set var="pageSize" value="10" />
              
              <div class="row align-items-center mt-3">
                  <%-- CỘT TRÁI: Hiển thị thông tin số lượng --%>
                  <div class="col-md-6">
                      <span class="text-muted">
                          <%-- Tính toán số mục bắt đầu và kết thúc --%>
                          <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
                          <c:set var="endItem" value="${currentPage * pageSize}" />
                          
                          <%-- Xử lý biên: Nếu trang cuối ít hơn 10 mục --%>
                          <c:if test="${endItem > totalItems}">
                              <c:set var="endItem" value="${totalItems}" />
                          </c:if>
                          
                          <%-- Hiển thị text --%>
                          <c:if test="${totalItems > 0}">
                             Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> giáo viên
                          </c:if>
                          <c:if test="${totalItems == 0}">
                             Chưa có giáo viên nào
                          </c:if>
                      </span>
                  </div>

                  <%-- CỘT PHẢI: Các nút bấm --%>
                  <div class="col-md-6">
                      <nav aria-label="Page navigation">
                          <ul class="pagination justify-content-end mb-0">
                              
                              <%-- Nút TRƯỚC --%>
                              <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/giaovien-list?page=${currentPage - 1}&searchKey=${param.searchKey}">Trước</a>
                              </li>
                              
                              <%-- Vòng lặp số trang --%>
                              <c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}">
                                  <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${baseURL}/admin/giaovien-list?page=${i}&searchKey=${param.searchKey}">${i}</a>
                                  </li>
                              </c:forEach>
                              
                              <%-- Nút SAU --%>
                              <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/giaovien-list?page=${currentPage + 1}&searchKey=${param.searchKey}">Sau</a>
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
      <div class="modal-dialog modal-lg"> <%-- modal-lg: Form rộng hơn --%>
        <div class="modal-content">
          <form action="${baseURL}/admin/giaovien-add" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Thêm mới Giáo viên</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <div class="row g-3">
                  <div class="col-md-6">
                      <label class="form-label">Họ và Tên <span class="text-danger">*</span></label>
                      <input type="text" class="form-control" name="hoTen" required>
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Ngày sinh</label>
                      <input type="date" class="form-control" name="ngaySinh">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Giới tính</label>
                      <select class="form-select" name="gioiTinh">
                          <option value="Nam">Nam</option>
                          <option value="Nữ">Nữ</option>
                      </select>
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Chuyên môn</label>
                      <input type="text" class="form-control" name="chuyenMon">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Email</label>
                      <input type="email" class="form-control" name="email">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Số điện thoại</label>
                      <input type="text" class="form-control" name="sdt">
                  </div>
                  <div class="col-12">
                      <label class="form-label">Địa chỉ</label>
                      <input type="text" class="form-control" name="diaChi">
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
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form action="${baseURL}/admin/giaovien-edit" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật thông tin</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <%-- INPUT ẨN CHỨA ID --%>
              <input type="hidden" id="maGV_edit" name="maGV_edit">
              
              <div class="row g-3">
                  <div class="col-md-6">
                      <label class="form-label">Họ và Tên <span class="text-danger">*</span></label>
                      <input type="text" class="form-control" id="hoTen_edit" name="hoTen_edit" required>
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Ngày sinh</label>
                      <input type="date" class="form-control" id="ngaySinh_edit" name="ngaySinh_edit">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Giới tính</label>
                      <select class="form-select" id="gioiTinh_edit" name="gioiTinh_edit">
                          <option value="Nam">Nam</option>
                          <option value="Nữ">Nữ</option>
                      </select>
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Chuyên môn</label>
                      <input type="text" class="form-control" id="chuyenMon_edit" name="chuyenMon_edit">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Email</label>
                      <input type="email" class="form-control" id="email_edit" name="email_edit">
                  </div>
                  <div class="col-md-6">
                      <label class="form-label">Số điện thoại</label>
                      <input type="text" class="form-control" id="sdt_edit" name="sdt_edit">
                  </div>
                  <div class="col-12">
                      <label class="form-label">Địa chỉ</label>
                      <input type="text" class="form-control" id="diaChi_edit" name="diaChi_edit">
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
    // 1. Kích hoạt Toast
    const toastSuccessEl = document.getElementById('toastSuccess');
    if (toastSuccessEl) new bootstrap.Toast(toastSuccessEl, {delay: 5000}).show();
    
    const toastErrorEl = document.getElementById('toastError');
    if (toastErrorEl) new bootstrap.Toast(toastErrorEl, {delay: 5000}).show();

    // 2. Xử lý Modal Sửa (Bơm dữ liệu)
    const modalSua = document.getElementById('modalSua');
    if(modalSua) {
        modalSua.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            
            // Lấy dữ liệu từ button
            const id = button.getAttribute('data-id');
            const ten = button.getAttribute('data-ten');
            const ngaysinh = button.getAttribute('data-ngaysinh');
            const gioitinh = button.getAttribute('data-gioitinh');
            const chuyenmon = button.getAttribute('data-chuyenmon');
            const email = button.getAttribute('data-email');
            const sdt = button.getAttribute('data-sdt');
            const diachi = button.getAttribute('data-diachi');

            // Điền vào form
            modalSua.querySelector('#maGV_edit').value = id;
            modalSua.querySelector('#hoTen_edit').value = ten;
            modalSua.querySelector('#ngaySinh_edit').value = ngaysinh;
            modalSua.querySelector('#gioiTinh_edit').value = gioitinh;
            modalSua.querySelector('#chuyenMon_edit').value = chuyenmon;
            modalSua.querySelector('#email_edit').value = email;
            modalSua.querySelector('#sdt_edit').value = sdt;
            modalSua.querySelector('#diaChi_edit').value = diachi;
        });
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />