<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Tổ Bộ Môn</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Nghiệp vụ</li>
          <li class="breadcrumb-item active">Tổ Bộ Môn</li>
        </ol>
      </nav>
    </div>

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

    <section class="section">
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              
              <h5 class="card-title">
                Danh sách Tổ Bộ Môn
                <button type="button" class="btn btn-primary btn-sm float-end" 
                        data-bs-toggle="modal" data-bs-target="#modalThemMoi">
                  <i class="bi bi-plus-circle"></i> Thêm mới
                </button>
              </h5>

              <%-- THANH TÌM KIẾM --%>
              <div class="row mb-3">
                <div class="col-12">
                    <form action="${baseURL}/admin/tobomon-list" method="GET" class="d-flex justify-content-end">
                       <div class="input-group" style="width: 350px;">
                         <input type="text" name="searchKey" class="form-control" 
                                placeholder="Tìm theo tên hoặc mô tả..." value="${param.searchKey}">
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
                        <th>Mã Tổ</th>
                        <th>Tên Tổ Bộ Môn</th>
                        <th>Mô tả</th>
                        <th>Tổ Trưởng</th>
                        <th>Số Giáo Viên</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty dsToBoMon}">
                            <tr><td colspan="6" class="text-center">Không có dữ liệu.</td></tr>
                        </c:if>

                        <c:forEach var="to" items="${dsToBoMon}">
                            <tr>
                              <td><strong>${to.maTo}</strong></td>
                              <td><span class="badge bg-primary">${to.tenTo}</span></td>
                              <td>${to.moTa}</td>
                              <td>
                                <c:choose>
                                  <c:when test="${not empty to.tenToTruong}">
                                    <span class="badge bg-info text-dark">${to.tenToTruong}</span>
                                  </c:when>
                                  <c:otherwise>
                                    <span class="text-muted">Chưa có</span>
                                  </c:otherwise>
                                </c:choose>
                              </td>
                              <td>
                                <span class="badge bg-secondary">${to.soLuongGiaoVien} GV</span>
                              </td>
                              <td>
                                <button type="button" class="btn btn-warning btn-sm" title="Sửa"
                                        data-bs-toggle="modal" data-bs-target="#modalSua"
                                        data-id="${to.maTo}"
                                        data-ten="${to.tenTo}"
                                        data-mota="${to.moTa}"
                                        data-matotruong="${to.maToTruong}">
                                  <i class="bi bi-pencil-square"></i>
                                </button>

                                <a href="${baseURL}/admin/tobomon-delete?id=${to.maTo}" 
                                   class="btn btn-danger btn-sm" title="Xóa"
                                   onclick="return confirm('Bạn có chắc muốn xóa tổ bộ môn ${to.tenTo} không?');">
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
                          <c:if test="${totalItems > 0}">Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> tổ bộ môn</c:if>
                          <c:if test="${totalItems == 0}">Chưa có tổ bộ môn nào</c:if>
                      </span>
                  </div>
                  <div class="col-md-6">
                      <nav aria-label="Page navigation">
                          <ul class="pagination justify-content-end mb-0">
                              <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/tobomon-list?page=${currentPage - 1}&searchKey=${param.searchKey}">Trước</a>
                              </li>
                              <c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}">
                                  <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${baseURL}/admin/tobomon-list?page=${i}&searchKey=${param.searchKey}">${i}</a>
                                  </li>
                              </c:forEach>
                              <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/tobomon-list?page=${currentPage + 1}&searchKey=${param.searchKey}">Sau</a>
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
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form action="${baseURL}/admin/tobomon-add" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Thêm mới Tổ Bộ Môn</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <div class="row g-3">
                  
                  <div class="col-md-6">
                    <label class="form-label">Tên Tổ Bộ Môn <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="tenTo" required 
                           placeholder="VD: Tổ Toán - Lý">
                  </div>

                  <div class="col-md-6">
                    <label class="form-label">Tổ Trưởng</label>
                    <select class="form-select" name="maToTruong">
                      <option value="">-- Chọn giáo viên làm tổ trưởng --</option>
                      <c:forEach var="gv" items="${dsGiaoVien}">
                        <option value="${gv.maGV}">${gv.hoTen}</option>
                      </c:forEach>
                    </select>
                    <small class="text-muted">Có thể để trống và cập nhật sau</small>
                  </div>

                  <div class="col-12">
                    <label class="form-label">Mô tả</label>
                    <textarea class="form-control" name="moTa" rows="3" 
                              placeholder="Mô tả về tổ bộ môn..."></textarea>
                  </div>

              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
              <button type="submit" class="btn btn-primary">Lưu</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <%-- ==================== MODAL SỬA ==================== --%>
    <div class="modal fade" id="modalSua" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form action="${baseURL}/admin/tobomon-edit" method="POST">
            <input type="hidden" name="maTo_edit" id="edit_maTo">
            
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật Tổ Bộ Môn</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <div class="row g-3">
                  
                  <div class="col-md-6">
                    <label class="form-label">Tên Tổ Bộ Môn <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="tenTo_edit" id="edit_tenTo" required>
                  </div>

                  <div class="col-md-6">
                    <label class="form-label">Tổ Trưởng</label>
                    <select class="form-select" name="maToTruong_edit" id="edit_maToTruong">
                      <option value="">-- Chọn giáo viên làm tổ trưởng --</option>
                      <c:forEach var="gv" items="${dsGiaoVien}">
                        <option value="${gv.maGV}">${gv.hoTen}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-12">
                    <label class="form-label">Mô tả</label>
                    <textarea class="form-control" name="moTa_edit" id="edit_moTa" rows="3"></textarea>
                  </div>

              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
              <button type="submit" class="btn btn-warning">Cập nhật</button>
            </div>
          </form>
        </div>
      </div>
    </div>

</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />

<script>
    // Hiển thị Toast
    window.addEventListener('DOMContentLoaded', () => {
        const toastSuccess = document.getElementById('toastSuccess');
        if (toastSuccess) {
            const toast = new bootstrap.Toast(toastSuccess);
            toast.show();
        }

        const toastError = document.getElementById('toastError');
        if (toastError) {
            const toast = new bootstrap.Toast(toastError);
            toast.show();
        }
    });

    // Xử lý Modal Sửa
    const modalSua = document.getElementById('modalSua');
    if (modalSua) {
        modalSua.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            
            const maTo = button.getAttribute('data-id');
            const tenTo = button.getAttribute('data-ten');
            const moTa = button.getAttribute('data-mota');
            const maToTruong = button.getAttribute('data-matotruong');

            document.getElementById('edit_maTo').value = maTo;
            document.getElementById('edit_tenTo').value = tenTo;
            document.getElementById('edit_moTa').value = moTa || '';
            
            // Set giá trị cho dropdown Tổ Trưởng
            const selectToTruong = document.getElementById('edit_maToTruong');
            if (selectToTruong && maToTruong) {
                selectToTruong.value = maToTruong;
            } else if (selectToTruong) {
                selectToTruong.value = '';
            }
        });
    }
</script>

</body>
</html>