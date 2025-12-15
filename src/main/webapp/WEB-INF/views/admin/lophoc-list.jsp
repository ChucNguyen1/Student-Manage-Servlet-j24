<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Lớp học</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Nghiệp vụ</li>
          <li class="breadcrumb-item active">Lớp học</li>
        </ol>
      </nav>
    </div>

    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100">
      <c:if test="${not empty sessionScope.message}">
        <div id="toastSuccess" class="toast" role="alert"><div class="toast-header bg-success text-white"><strong>Thành công</strong><button type="button" class="btn-close" data-bs-dismiss="toast"></button></div><div class="toast-body">${sessionScope.message}</div></div>
        <c:remove var="message" scope="session" />
      </c:if>
      <c:if test="${not empty sessionScope.error}">
        <div id="toastError" class="toast" role="alert"><div class="toast-header bg-danger text-white"><strong>Lỗi</strong><button type="button" class="btn-close" data-bs-dismiss="toast"></button></div><div class="toast-body">${sessionScope.error}</div></div>
        <c:remove var="error" scope="session" />
      </c:if>
    </div>

    <section class="section">
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              
              <h5 class="card-title">
                Danh sách Lớp học
                <button type="button" class="btn btn-primary btn-sm float-end" 
                        data-bs-toggle="modal" data-bs-target="#modalThemMoi">
                  <i class="bi bi-plus-circle"></i> Thêm mới
                </button>
              </h5>

              <%-- SEARCH BAR --%>
              <div class="row mb-3">
                <div class="col-12">
                    <form action="${baseURL}/admin/lophoc-list" method="GET" class="d-flex justify-content-end">
                       <div class="input-group" style="width: 350px;">
                         <input type="text" name="searchKey" class="form-control" 
                                placeholder="Tìm theo tên lớp hoặc GVCN..." value="${param.searchKey}">
                         <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i></button>
                       </div>
                    </form>
                </div>
              </div>

              <%-- TABLE --%>
              <div class="table-responsive">
                  <table class="table table-striped table-hover align-middle">
                    <thead>
                      <tr>
                        <th>Tên Lớp</th>
                        <th>Khối</th>
                        <th>Năm Học</th>
                        <th>GV Chủ Nhiệm</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty dsLopHoc}">
                            <tr><td colspan="6" class="text-center">Không có dữ liệu.</td></tr>
                        </c:if>

                        <c:forEach var="lop" items="${dsLopHoc}">
                            <tr>
                              <td><strong>${lop.tenLop}</strong></td>
                              <td><span class="badge bg-secondary">${lop.tenKhoi}</span></td>
                              <td>${lop.tenNamHoc}</td>
                              
                              <td>
                                <c:choose>
                                    <c:when test="${not empty lop.tenGVCN}">
                                        <i class="bi bi-person-badge"></i> ${lop.tenGVCN}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted fst-italic">-- Chưa phân công --</span>
                                    </c:otherwise>
                                </c:choose>
                              </td>

                              <td>
                                <div class="form-check form-switch">
                                  <input class="form-check-input" type="checkbox" 
                                         id="switch-${lop.maLop}" 
                                         ${lop.trangThai ? 'checked' : ''}
                                         onchange="toggleStatus('${lop.maLop}', this)">
                                  <label class="form-check-label" for="switch-${lop.maLop}">
                                      ${lop.trangThai ? 'HĐ' : 'Khóa'}
                                  </label>
                                </div>
                              </td>

                              <td>

                                <button type="button" class="btn btn-warning btn-sm" title="Sửa"
                                        data-bs-toggle="modal" data-bs-target="#modalSua"
                                        data-id="${lop.maLop}"
                                        data-ten="${lop.tenLop}"
                                        data-makhoi="${lop.maKhoi}"
                                        data-manh="${lop.maNH}"
                                        data-magvcn="${lop.maGVCN}">
                                  <i class="bi bi-pencil-square"></i>
                                </button>


                                <a href="${baseURL}/admin/lophoc-delete?id=${lop.maLop}" class="btn btn-danger btn-sm" title="Xóa"
                                   onclick="return confirm('Bạn có chắc muốn xóa lớp ${lop.tenLop} không?');">
                                  <i class="bi bi-trash"></i>
                                </a>
                              </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                  </table>
              </div>

              <%-- PAGINATION --%>
              <c:set var="pageSize" value="10" />
              <div class="row align-items-center mt-3">
                  <div class="col-md-6">
                      <span class="text-muted">
                          <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
                          <c:set var="endItem" value="${currentPage * pageSize}" />
                          <c:if test="${endItem > totalItems}"><c:set var="endItem" value="${totalItems}" /></c:if>
                          <c:if test="${totalItems > 0}">Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> lớp</c:if>
                      </span>
                  </div>
                  <div class="col-md-6">
                      <nav aria-label="Page navigation">
                          <ul class="pagination justify-content-end mb-0">
                              <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/lophoc-list?page=${currentPage - 1}&searchKey=${param.searchKey}">Trước</a>
                              </li>
                              <c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}">
                                  <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${baseURL}/admin/lophoc-list?page=${i}&searchKey=${param.searchKey}">${i}</a>
                                  </li>
                              </c:forEach>
                              <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${baseURL}/admin/lophoc-list?page=${currentPage + 1}&searchKey=${param.searchKey}">Sau</a>
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
          <form action="${baseURL}/admin/lophoc-add" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Thêm mới Lớp học</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              
              <div class="mb-3">
                  <label class="form-label">Tên Lớp <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" name="tenLop" required placeholder="Ví dụ: 10A1">
              </div>

              <div class="mb-3">
                  <label class="form-label">Thuộc Khối <span class="text-danger">*</span></label>
                  <select class="form-select" name="maKhoi" required>
                      <option value="" selected disabled>-- Chọn Khối --</option>
                      <c:forEach var="k" items="${dsKhoi}">
                          <option value="${k.maKhoi}">${k.tenKhoi}</option>
                      </c:forEach>
                  </select>
              </div>

              <div class="mb-3">
                  <label class="form-label">Năm Học <span class="text-danger">*</span></label>
                  <select class="form-select" name="maNH" required>
                      <option value="" selected disabled>-- Chọn Năm Học --</option>
                      <c:forEach var="nh" items="${dsNamHoc}">
                          <option value="${nh.maNH}">${nh.tenNH}</option>
                      </c:forEach>
                  </select>
              </div>

              <div class="mb-3">
                  <label class="form-label">GV Chủ Nhiệm</label>
                  <select class="form-select" name="maGVCN">
                      <option value="" selected>-- Chưa phân công --</option>
                      <c:forEach var="gv" items="${dsGiaoVien}">
                          <option value="${gv.maGV}">${gv.hoTen} - ${gv.tenMonHocChuyenMon}</option>
                      </c:forEach>
                  </select>
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
          <form action="${baseURL}/admin/lophoc-edit" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật Lớp học</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <input type="hidden" id="maLop_edit" name="maLop_edit">
              
              <div class="mb-3">
                  <label class="form-label">Tên Lớp <span class="text-danger">*</span></label>
                  <input type="text" class="form-control" id="tenLop_edit" name="tenLop_edit" required>
              </div>

              <div class="mb-3">
                  <label class="form-label">Thuộc Khối <span class="text-danger">*</span></label>
                  <select class="form-select" id="maKhoi_edit" name="maKhoi_edit" required>
                      <option value="" disabled>-- Chọn Khối --</option>
                      <c:forEach var="k" items="${dsKhoi}">
                          <option value="${k.maKhoi}">${k.tenKhoi}</option>
                      </c:forEach>
                  </select>
              </div>

              <div class="mb-3">
                  <label class="form-label">Năm Học <span class="text-danger">*</span></label>
                  <select class="form-select" id="maNH_edit" name="maNH_edit" required>
                      <option value="" disabled>-- Chọn Năm Học --</option>
                      <c:forEach var="nh" items="${dsNamHoc}">
                          <option value="${nh.maNH}">${nh.tenNH}</option>
                      </c:forEach>
                  </select>
              </div>

              <div class="mb-3">
                  <label class="form-label">GV Chủ Nhiệm</label>
                  <select class="form-select" id="maGVCN_edit" name="maGVCN_edit">
                      <option value="0">-- Chưa phân công --</option>
                      <c:forEach var="gv" items="${dsGiaoVien}">
                          <option value="${gv.maGV}">${gv.hoTen} - ${gv.tenMonHocChuyenMon}</option>
                      </c:forEach>
                  </select>
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
  function toggleStatus(id, cb) {
      const s = cb.checked;
      cb.nextElementSibling.textContent = s ? 'HĐ' : 'Khóa';
      fetch('${baseURL}/admin/lophoc-status?id=' + id + '&status=' + s);
  }

  document.addEventListener('DOMContentLoaded', (event) => {
    // Toast
    const ts = document.getElementById('toastSuccess'); if(ts) new bootstrap.Toast(ts, {delay: 5000}).show();
    const te = document.getElementById('toastError'); if(te) new bootstrap.Toast(te, {delay: 5000}).show();

    // Modal Sửa
    const modalSua = document.getElementById('modalSua');
    if(modalSua) {
        modalSua.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            
            // Lấy dữ liệu
            const id = button.getAttribute('data-id');
            const ten = button.getAttribute('data-ten');
            const makhoi = button.getAttribute('data-makhoi');
            const manh = button.getAttribute('data-manh');
            const magvcn = button.getAttribute('data-magvcn');

            // Điền form
            modalSua.querySelector('#maLop_edit').value = id;
            modalSua.querySelector('#tenLop_edit').value = ten;
            modalSua.querySelector('#maKhoi_edit').value = makhoi;
            modalSua.querySelector('#maNH_edit').value = manh;
            
            // Xử lý chọn GVCN (nếu chưa có thì chọn 0)
            const gvcnSelect = modalSua.querySelector('#maGVCN_edit');
            gvcnSelect.value = (magvcn && magvcn != '0') ? magvcn : '0';
        });
    }
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />