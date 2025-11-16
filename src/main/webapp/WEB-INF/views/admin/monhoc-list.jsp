<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">
    <div class="pagetitle">
      <h1>Quản lý Môn học</h1>
      <nav><ol class="breadcrumb"><li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li><li class="breadcrumb-item active">Môn học</li></ol></nav>
    </div>

    <%-- TOAST --%>
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
      <div class="row"><div class="col-lg-12"><div class="card"><div class="card-body">
          <h5 class="card-title">Danh sách Môn học <button type="button" class="btn btn-primary btn-sm float-end" data-bs-toggle="modal" data-bs-target="#modalThemMoi"><i class="bi bi-plus-circle"></i> Thêm mới</button></h5>
          
          <div class="row mb-3"><div class="col-12"><form action="${baseURL}/admin/monhoc-list" method="GET" class="d-flex justify-content-end"><div class="input-group" style="width: 350px;"><input type="text" name="searchKey" class="form-control" placeholder="Tìm kiếm môn học..." value="${param.searchKey}"><button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i></button></div></form></div></div>

          <div class="table-responsive">
              <table class="table table-striped table-hover align-middle">
                <thead><tr><th>ID</th><th>Tên Môn Học</th><th>Số Tiết</th><th>Trạng thái</th><th>Hành động</th></tr></thead>
                <tbody>
                    <c:forEach var="mh" items="${dsMonHoc}">
                        <tr>
                          <td>${mh.maMH}</td>
                          <td><strong>${mh.tenMH}</strong></td>
                          <td>${mh.soTiet}</td>
                          <td>
                            <div class="form-check form-switch">
                              <input class="form-check-input" type="checkbox" id="switch-${mh.maMH}" ${mh.trangThai ? 'checked' : ''} onchange="toggleStatus('${mh.maMH}', this)">
                              <label class="form-check-label">${mh.trangThai ? 'Hiển thị' : 'Ẩn'}</label>
                            </div>
                          </td>
                          <td>
                            <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#modalSua" data-id="${mh.maMH}" data-ten="${mh.tenMH}" data-sotiet="${mh.soTiet}"><i class="bi bi-pencil-square"></i></button>
                            <a href="${baseURL}/admin/monhoc-delete?id=${mh.maMH}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa vĩnh viễn môn [${mh.tenMH}] không?');"><i class="bi bi-trash"></i></a>
                          </td>
                        </tr>
                    </c:forEach>
                </tbody>
              </table>
          </div>
          
          <%-- PHÂN TRANG --%>
          <c:set var="pageSize" value="10" />
          <div class="row align-items-center mt-3">
              <div class="col-md-6"><span class="text-muted"><c:set var="startItem" value="${(currentPage-1)*pageSize+1}"/><c:set var="endItem" value="${currentPage*pageSize}"/><c:if test="${endItem > totalItems}"><c:set var="endItem" value="${totalItems}"/></c:if><c:if test="${totalItems > 0}">Hiển thị ${startItem}-${endItem} trong ${totalItems} môn</c:if></span></div>
              <div class="col-md-6"><nav><ul class="pagination justify-content-end mb-0"><li class="page-item ${currentPage<=1?'disabled':''}"><a class="page-link" href="${baseURL}/admin/monhoc-list?page=${currentPage-1}&searchKey=${param.searchKey}">Trước</a></li><c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}"><li class="page-item ${i==currentPage?'active':''}"><a class="page-link" href="${baseURL}/admin/monhoc-list?page=${i}&searchKey=${param.searchKey}">${i}</a></li></c:forEach><li class="page-item ${currentPage>=totalPages?'disabled':''}"><a class="page-link" href="${baseURL}/admin/monhoc-list?page=${currentPage+1}&searchKey=${param.searchKey}">Sau</a></li></ul></nav></div>
          </div>
      </div></div></div></div>
    </section>

    <%-- MODAL THÊM --%>
    <div class="modal fade" id="modalThemMoi"><div class="modal-dialog"><div class="modal-content"><form action="${baseURL}/admin/monhoc-add" method="POST"><div class="modal-header"><h5 class="modal-title">Thêm Môn Học</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body">
        <div class="mb-3"><label>Tên Môn Học</label><input type="text" name="tenMH" class="form-control" required></div>
        <div class="mb-3"><label>Số Tiết</label><input type="number" name="soTiet" class="form-control" required min="1"></div>
    </div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button><button type="submit" class="btn btn-primary">Lưu lại</button></div></form></div></div></div>

    <%-- MODAL SỬA --%>
    <div class="modal fade" id="modalSua"><div class="modal-dialog"><div class="modal-content"><form action="${baseURL}/admin/monhoc-edit" method="POST"><div class="modal-header"><h5 class="modal-title">Cập nhật Môn Học</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body">
        <input type="hidden" id="maMH_edit" name="maMH_edit">
        <div class="mb-3"><label>Tên Môn Học</label><input type="text" id="tenMH_edit" name="tenMH_edit" class="form-control" required></div>
        <div class="mb-3"><label>Số Tiết</label><input type="number" id="soTiet_edit" name="soTiet_edit" class="form-control" required min="1"></div>
    </div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button><button type="submit" class="btn btn-primary">Lưu thay đổi</button></div></form></div></div></div>
</main>

<script>
function toggleStatus(id, cb) {
    const s = cb.checked; cb.nextElementSibling.textContent = s?'Hiển thị':'Ẩn';
    fetch('${baseURL}/admin/monhoc-status?id='+id+'&status='+s);
}
document.addEventListener('DOMContentLoaded', () => {
    const ts = document.getElementById('toastSuccess'); if(ts) new bootstrap.Toast(ts, {delay:5000}).show();
    const te = document.getElementById('toastError'); if(te) new bootstrap.Toast(te, {delay:5000}).show();
    const ms = document.getElementById('modalSua');
    if(ms) ms.addEventListener('show.bs.modal', e => {
        const b = e.relatedTarget;
        ms.querySelector('#maMH_edit').value = b.getAttribute('data-id');
        ms.querySelector('#tenMH_edit').value = b.getAttribute('data-ten');
        ms.querySelector('#soTiet_edit').value = b.getAttribute('data-sotiet');
    });
});
</script>
<jsp:include page="/WEB-INF/includes/footer.jsp" />