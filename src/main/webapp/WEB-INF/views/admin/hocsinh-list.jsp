<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Hồ sơ Học sinh</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Nghiệp vụ</li>
          <li class="breadcrumb-item active">Học sinh</li>
        </ol>
      </nav>
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
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              
              <h5 class="card-title">
                Danh sách Học sinh
                <button type="button" class="btn btn-primary btn-sm float-end" 
                        data-bs-toggle="modal" data-bs-target="#modalThemMoi">
                  <i class="bi bi-person-plus-fill"></i> Tiếp nhận
                </button>
              </h5>

              <%-- SEARCH --%>
              <div class="row mb-3">
                <div class="col-12">
                    <form action="${baseURL}/admin/hocsinh-list" method="GET" class="d-flex justify-content-end">
                       <div class="input-group" style="width: 400px;">
                         <input type="text" name="searchKey" class="form-control" 
                                placeholder="Tìm tên, email hoặc mã HS..." value="${param.searchKey}">
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
                        <th>Mã HS</th>
                        <th>Họ và Tên</th>
                        <th>Thông tin</th>
                        <th>Lớp</th>
                        <th>Phụ huynh</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty dsHocSinh}">
                            <tr><td colspan="7" class="text-center">Không có dữ liệu.</td></tr>
                        </c:if>

                        <c:forEach var="hs" items="${dsHocSinh}">
                            <tr>
                              <td>${hs.maHS}</td>
                              <td>
                                  <strong>${hs.hoTen}</strong><br>
                                  <small class="text-muted"><fmt:formatDate value="${hs.ngaySinh}" pattern="dd/MM/yyyy"/></small>
                              </td>
                              <td>
                                  <small>${hs.gioiTinh} - ${hs.danToc}</small><br>
                                  <small><i class="bi bi-geo-alt"></i> ${hs.noiSinh}</small>
                              </td>
                              <td>
                                  <span class="badge bg-primary">${hs.tenLop}</span>
                              </td>
                              <td>
                                  <small>Cha: ${hs.hoTenCha}</small><br>
                                  <small>Mẹ: ${hs.hoTenMe}</small>
                              </td>
                              <td>
                                  <c:choose>
                                      <c:when test="${hs.trangThaiHocTap == 'Đang học'}"><span class="badge bg-success">Đang học</span></c:when>
                                      <c:when test="${hs.trangThaiHocTap == 'Bảo lưu'}"><span class="badge bg-warning text-dark">Bảo lưu</span></c:when>
                                      <c:otherwise><span class="badge bg-secondary">${hs.trangThaiHocTap}</span></c:otherwise>
                                  </c:choose>
                              </td>
                              <td>
                                <%-- BUTTON SỬA (Lưu rất nhiều data attribute) --%>
                                <button type="button" class="btn btn-warning btn-sm" title="Chi tiết / Sửa"
                                        data-bs-toggle="modal" data-bs-target="#modalSua"
                                        data-id="${hs.maHS}"
                                        data-hoten="${hs.hoTen}"
                                        data-ngaysinh="${hs.ngaySinh}"
                                        data-gioitinh="${hs.gioiTinh}"
                                        data-noisinh="${hs.noiSinh}"
                                        data-dantoc="${hs.danToc}"
                                        data-tongiao="${hs.tonGiao}"
                                        data-diachi="${hs.diaChi}"
                                        data-email="${hs.email}"
                                        data-sdt="${hs.sdtCaNhan}"
                                        data-cha="${hs.hoTenCha}"
                                        data-nghecha="${hs.ngheNghiepCha}"
                                        data-sdtcha="${hs.sdtCha}"
                                        data-me="${hs.hoTenMe}"
                                        data-ngheme="${hs.ngheNghiepMe}"
                                        data-sdtme="${hs.sdtMe}"
                                        data-malop="${hs.maLop}"
                                        data-trangthai="${hs.trangThaiHocTap}">
                                  <i class="bi bi-pencil-square"></i>
                                </button>

                                <a href="${baseURL}/admin/hocsinh-delete?id=${hs.maHS}" class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Bạn có chắc muốn xóa hồ sơ học sinh này?');">
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
                  <div class="col-md-6"><span class="text-muted">Trang ${currentPage}/${totalPages > 0 ? totalPages : 1} - Tổng ${totalItems} học sinh</span></div>
                  <div class="col-md-6">
                      <nav>
                          <ul class="pagination justify-content-end mb-0">
                              <li class="page-item ${currentPage<=1?'disabled':''}"><a class="page-link" href="${baseURL}/admin/hocsinh-list?page=${currentPage-1}&searchKey=${param.searchKey}">Trước</a></li>
                              <c:forEach var="i" begin="1" end="${totalPages > 0 ? totalPages : 1}">
                                  <li class="page-item ${i==currentPage?'active':''}"><a class="page-link" href="${baseURL}/admin/hocsinh-list?page=${i}&searchKey=${param.searchKey}">${i}</a></li>
                              </c:forEach>
                              <li class="page-item ${currentPage>=totalPages?'disabled':''}"><a class="page-link" href="${baseURL}/admin/hocsinh-list?page=${currentPage+1}&searchKey=${param.searchKey}">Sau</a></li>
                          </ul>
                      </nav>
                  </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </section>

    <%-- =================================================================== --%>
    <%-- MODAL THÊM MỚI (CÓ TABS) --%>
    <%-- =================================================================== --%>
    <div class="modal fade" id="modalThemMoi" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form action="${baseURL}/admin/hocsinh-add" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Tiếp nhận Học sinh mới</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              
              <ul class="nav nav-tabs" id="addTab" role="tablist">
                <li class="nav-item"><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#add-info" type="button">Cá nhân</button></li>
                <li class="nav-item"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#add-family" type="button">Gia đình & Liên lạc</button></li>
                <li class="nav-item"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#add-school" type="button">Học vụ</button></li>
              </ul>

              <div class="tab-content pt-3" id="addTabContent">
                
                <%-- TAB 1: CÁ NHÂN --%>
                <div class="tab-pane fade show active" id="add-info">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Họ tên <span class="text-danger">*</span></label><input type="text" class="form-control" name="hoTen" required></div>
                        <div class="col-md-6"><label class="form-label">Ngày sinh</label><input type="date" class="form-control" name="ngaySinh"></div>
                        <div class="col-md-4"><label class="form-label">Giới tính</label><select class="form-select" name="gioiTinh"><option value="Nam">Nam</option><option value="Nữ">Nữ</option></select></div>
                        <div class="col-md-4"><label class="form-label">Dân tộc</label><input type="text" class="form-control" name="danToc" value="Kinh"></div>
                        <div class="col-md-4"><label class="form-label">Tôn giáo</label><input type="text" class="form-control" name="tonGiao" value="Không"></div>
                        <div class="col-md-12"><label class="form-label">Nơi sinh (Tỉnh/TP)</label><input type="text" class="form-control" name="noiSinh"></div>
                        <div class="col-md-12"><label class="form-label">Địa chỉ thường trú</label><input type="text" class="form-control" name="diaChi"></div>
                    </div>
                </div>

                <%-- TAB 2: GIA ĐÌNH --%>
                <div class="tab-pane fade" id="add-family">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Email cá nhân</label><input type="email" class="form-control" name="email"></div>
                        <div class="col-md-6"><label class="form-label">SĐT Cá nhân</label><input type="text" class="form-control" name="sdtCaNhan"></div>
                        <hr>
                        <div class="col-md-4"><label class="form-label">Họ tên Cha</label><input type="text" class="form-control" name="hoTenCha"></div>
                        <div class="col-md-4"><label class="form-label">Nghề nghiệp Cha</label><input type="text" class="form-control" name="ngheNghiepCha"></div>
                        <div class="col-md-4"><label class="form-label">SĐT Cha</label><input type="text" class="form-control" name="sdtCha"></div>
                        <div class="col-md-4"><label class="form-label">Họ tên Mẹ</label><input type="text" class="form-control" name="hoTenMe"></div>
                        <div class="col-md-4"><label class="form-label">Nghề nghiệp Mẹ</label><input type="text" class="form-control" name="ngheNghiepMe"></div>
                        <div class="col-md-4"><label class="form-label">SĐT Mẹ</label><input type="text" class="form-control" name="sdtMe"></div>
                    </div>
                </div>

                <%-- TAB 3: HỌC VỤ --%>
                <div class="tab-pane fade" id="add-school">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Xếp vào Lớp</label>
                            <select class="form-select" name="maLop">
                                <option value="" selected>-- Chọn lớp --</option>
                                <c:forEach var="l" items="${dsLopHoc}">
                                    <option value="${l.maLop}">${l.tenLop}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Trạng thái</label>
                            <select class="form-select" name="trangThaiHocTap">
                                <option value="Đang học">Đang học</option>
                                <option value="Bảo lưu">Bảo lưu</option>
                                <option value="Thôi học">Thôi học</option>
                            </select>
                        </div>
                    </div>
                </div>

              </div></div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
              <button type="submit" class="btn btn-primary">Lưu hồ sơ</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <%-- =================================================================== --%>
    <%-- MODAL SỬA (CẤU TRÚC GIỐNG HỆT THÊM, CÓ ID) --%>
    <%-- =================================================================== --%>
    <div class="modal fade" id="modalSua" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form action="${baseURL}/admin/hocsinh-edit" method="POST">
            <div class="modal-header">
              <h5 class="modal-title">Cập nhật Hồ sơ</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <input type="hidden" id="maHS_edit" name="maHS_edit">
              
              <ul class="nav nav-tabs" id="editTab" role="tablist">
                <li class="nav-item"><button class="nav-link active" data-bs-toggle="tab" data-bs-target="#edit-info" type="button">Cá nhân</button></li>
                <li class="nav-item"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#edit-family" type="button">Gia đình & Liên lạc</button></li>
                <li class="nav-item"><button class="nav-link" data-bs-toggle="tab" data-bs-target="#edit-school" type="button">Học vụ</button></li>
              </ul>

              <div class="tab-content pt-3" id="editTabContent">
                <div class="tab-pane fade show active" id="edit-info">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Họ tên <span class="text-danger">*</span></label><input type="text" class="form-control" id="hoTen_edit" name="hoTen" required></div>
                        <div class="col-md-6"><label class="form-label">Ngày sinh</label><input type="date" class="form-control" id="ngaySinh_edit" name="ngaySinh"></div>
                        <div class="col-md-4"><label class="form-label">Giới tính</label><select class="form-select" id="gioiTinh_edit" name="gioiTinh"><option value="Nam">Nam</option><option value="Nữ">Nữ</option></select></div>
                        <div class="col-md-4"><label class="form-label">Dân tộc</label><input type="text" class="form-control" id="danToc_edit" name="danToc"></div>
                        <div class="col-md-4"><label class="form-label">Tôn giáo</label><input type="text" class="form-control" id="tonGiao_edit" name="tonGiao"></div>
                        <div class="col-md-12"><label class="form-label">Nơi sinh</label><input type="text" class="form-control" id="noiSinh_edit" name="noiSinh"></div>
                        <div class="col-md-12"><label class="form-label">Địa chỉ</label><input type="text" class="form-control" id="diaChi_edit" name="diaChi"></div>
                    </div>
                </div>
                <div class="tab-pane fade" id="edit-family">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Email</label><input type="email" class="form-control" id="email_edit" name="email"></div>
                        <div class="col-md-6"><label class="form-label">SĐT Cá nhân</label><input type="text" class="form-control" id="sdtCaNhan_edit" name="sdtCaNhan"></div>
                        <hr>
                        <div class="col-md-4"><label class="form-label">Họ tên Cha</label><input type="text" class="form-control" id="hoTenCha_edit" name="hoTenCha"></div>
                        <div class="col-md-4"><label class="form-label">Nghề Cha</label><input type="text" class="form-control" id="ngheNghiepCha_edit" name="ngheNghiepCha"></div>
                        <div class="col-md-4"><label class="form-label">SĐT Cha</label><input type="text" class="form-control" id="sdtCha_edit" name="sdtCha"></div>
                        <div class="col-md-4"><label class="form-label">Họ tên Mẹ</label><input type="text" class="form-control" id="hoTenMe_edit" name="hoTenMe"></div>
                        <div class="col-md-4"><label class="form-label">Nghề Mẹ</label><input type="text" class="form-control" id="ngheNghiepMe_edit" name="ngheNghiepMe"></div>
                        <div class="col-md-4"><label class="form-label">SĐT Mẹ</label><input type="text" class="form-control" id="sdtMe_edit" name="sdtMe"></div>
                    </div>
                </div>
                <div class="tab-pane fade" id="edit-school">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Lớp</label><select class="form-select" id="maLop_edit" name="maLop"><option value="">-- Chọn lớp --</option><c:forEach var="l" items="${dsLopHoc}"><option value="${l.maLop}">${l.tenLop}</option></c:forEach></select></div>
                        <div class="col-md-6"><label class="form-label">Trạng thái</label><select class="form-select" id="trangThaiHocTap_edit" name="trangThaiHocTap"><option value="Đang học">Đang học</option><option value="Bảo lưu">Bảo lưu</option><option value="Thôi học">Thôi học</option></select></div>
                    </div>
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

<%-- SCRIPT: Bơm dữ liệu vào Modal Sửa --%>
<script>
document.addEventListener('DOMContentLoaded', () => {
    // Toast
    const ts = document.getElementById('toastSuccess'); if(ts) new bootstrap.Toast(ts).show();
    const te = document.getElementById('toastError'); if(te) new bootstrap.Toast(te).show();

    // Modal Edit Logic
    const ms = document.getElementById('modalSua');
    if(ms) {
        ms.addEventListener('show.bs.modal', e => {
            const b = e.relatedTarget;
            // Helper để set value cho gọn
            const setVal = (id, attr) => ms.querySelector(id).value = b.getAttribute(attr) || '';
            
            setVal('#maHS_edit', 'data-id');
            setVal('#hoTen_edit', 'data-hoten');
            setVal('#ngaySinh_edit', 'data-ngaysinh');
            setVal('#gioiTinh_edit', 'data-gioitinh');
            setVal('#danToc_edit', 'data-dantoc');
            setVal('#tonGiao_edit', 'data-tongiao');
            setVal('#noiSinh_edit', 'data-noisinh');
            setVal('#diaChi_edit', 'data-diachi');
            
            setVal('#email_edit', 'data-email');
            setVal('#sdtCaNhan_edit', 'data-sdt');
            
            setVal('#hoTenCha_edit', 'data-cha');
            setVal('#ngheNghiepCha_edit', 'data-nghecha');
            setVal('#sdtCha_edit', 'data-sdtcha');
            
            setVal('#hoTenMe_edit', 'data-me');
            setVal('#ngheNghiepMe_edit', 'data-ngheme');
            setVal('#sdtMe_edit', 'data-sdtme');
            
            setVal('#maLop_edit', 'data-malop');
            setVal('#trangThaiHocTap_edit', 'data-trangthai');
        });
    }
});
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />