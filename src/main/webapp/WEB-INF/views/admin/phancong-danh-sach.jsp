<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Phân Công</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item active">Danh sách lớp môn chưa phân công</li>
        </ol>
      </nav>
    </div>

    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100">
      <c:if test="${not empty sessionScope.message}">
        <div id="toastSuccess" class="toast" role="alert">
          <div class="toast-header bg-success text-white">
            <strong>Thành công</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.message}</div>
        </div>
        <c:remove var="message" scope="session" />
      </c:if>
      <c:if test="${not empty sessionScope.error}">
        <div id="toastError" class="toast" role="alert">
          <div class="toast-header bg-danger text-white">
            <strong>Lỗi</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.error}</div>
        </div>
        <c:remove var="error" scope="session" />
      </c:if>
    </div>

    <section class="section">
      <div class="row">
        
        <!-- Bộ lọc tìm kiếm -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <i class="bi bi-funnel-fill"></i> Bộ lọc tìm kiếm
              </h5>
              
              <form action="${baseURL}/admin/phancong" method="GET" id="filterForm">
                <div class="row g-3">
                  
                  <div class="col-md-3">
                    <label class="form-label fw-bold">Năm học</label>
                    <select class="form-select" name="maNH" id="selectNamHoc">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="nh" items="${dsNamHoc}">
                        <option value="${nh.maNH}" ${param.maNH == nh.maNH ? 'selected' : ''}>${nh.tenNH}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-3">
                    <label class="form-label fw-bold">Học kỳ</label>
                    <select class="form-select" name="maHK" id="selectHocKy">
                      <option value="">-- Chọn năm học trước --</option>
                      <c:forEach var="hk" items="${dsHocKy}">
                        <option value="${hk.maHK}" data-manh="${hk.maNH}" ${param.maHK == hk.maHK ? 'selected' : ''}>${hk.tenHK}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-3">
                    <label class="form-label fw-bold">Khối</label>
                    <select class="form-select" name="maKhoi" id="selectKhoi">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="k" items="${dsKhoi}">
                        <option value="${k.maKhoi}" ${param.maKhoi == k.maKhoi ? 'selected' : ''}>${k.tenKhoi}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-3">
                    <label class="form-label fw-bold">Lớp</label>
                    <select class="form-select" name="maLop" id="selectLop">
                      <option value="">-- Chọn năm học và khối --</option>
                      <c:forEach var="lop" items="${dsLopHoc}">
                        <option value="${lop.maLop}" ${param.maLop == lop.maLop ? 'selected' : ''}>${lop.tenLop}</option>
                      </c:forEach>
                    </select>
                  </div>

                </div>

                <div class="text-center mt-3">
                  <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Tìm kiếm
                  </button>
                  <a href="${baseURL}/admin/phancong" class="btn btn-secondary">
                    <i class="bi bi-arrow-clockwise"></i> Làm mới
                  </a>
                </div>
              </form>

            </div>
          </div>
        </div>

        <!-- Bảng danh sách lớp môn chưa phân công -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <i class="bi bi-table"></i> Danh sách lớp phân công
              </h5>

              <c:choose>
                <c:when test="${not empty danhSach}">
                  <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                      <thead class="table-primary">
                        <tr class="text-center">
                          <th style="width: 5%">STT</th>
                          <th style="width: 15%">Năm học</th>
                          <th style="width: 12%">Học kỳ</th>
                          <th style="width: 10%">Khối</th>
                          <th style="width: 15%">Lớp</th>
                          <th style="width: 18%">Phân công môn học</th>
                          <th style="width: 13%">Trạng thái</th>
                          <th style="width: 12%">Hành động</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="item" items="${danhSach}" varStatus="status">
                          <c:set var="offset" value="${(currentPage - 1) * 10}" />
                          <tr>
                            <td class="text-center">${offset + status.count}</td>
                            <td class="text-center">${item.tenNH}</td>
                            <td class="text-center">${item.tenHocKy}</td>
                            <td class="text-center">${item.tenKhoi}</td>
                            <td class="fw-bold">${item.tenLop}</td>
                            <td class="text-center">
                              <span class="badge bg-success">${item.soMonDaPhanCong}</span>
                              <span class="text-muted mx-1">/</span>
                              <span class="badge bg-secondary">${item.tongSoMon}</span>
                              <small class="text-muted d-block mt-1">
                                (${item.soMonChuaPhanCong} môn chưa phân công)
                              </small>
                            </td>
                            <td class="text-center">
                              <c:choose>
                                <c:when test="${item.soMonChuaPhanCong == 0}">
                                  <span class="badge bg-success">
                                    <i class="bi bi-check-circle-fill"></i> Đầy đủ
                                  </span>
                                </c:when>
                                <c:when test="${item.soMonDaPhanCong > 0}">
                                  <span class="badge bg-warning">
                                    <i class="bi bi-exclamation-triangle-fill"></i> Chưa đủ
                                  </span>
                                </c:when>
                                <c:otherwise>
                                  <span class="badge bg-danger">
                                    <i class="bi bi-x-circle-fill"></i> Chưa phân công
                                  </span>
                                </c:otherwise>
                              </c:choose>
                            </td>
                            <td class="text-center">
                              <a href="${baseURL}/admin/phancong-detail?maNH=${item.maNH}&maKhoi=${item.maKhoi}&maLop=${item.maLop}&maHK=${item.maHocKy}" 
                                 class="btn btn-primary btn-sm">
                                <i class="bi bi-pencil-square"></i> Chi tiết
                              </a>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                  
                  <!-- Phân trang -->
                  <div class="row align-items-center mt-3">
                    <div class="col-md-6">
                      <span class="text-muted">
                        <c:set var="startItem" value="${(currentPage - 1) * 10 + 1}" />
                        <c:set var="endItem" value="${currentPage * 10}" />
                        <c:if test="${endItem > totalItems}"><c:set var="endItem" value="${totalItems}" /></c:if>
                        <c:if test="${totalItems > 0}">
                          <i class="bi bi-info-circle"></i> 
                          Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> lớp
                        </c:if>
                        <c:if test="${totalItems == 0}">Chưa có lớp nào</c:if>
                      </span>
                    </div>
                    <div class="col-md-6">
                      <c:if test="${totalPages > 1}">
                        <nav aria-label="Phân trang">
                          <ul class="pagination justify-content-end mb-0">
                            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                              <a class="page-link" href="${baseURL}/admin/phancong?page=${currentPage - 1}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}">Trước</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                              <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${baseURL}/admin/phancong?page=${i}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}">${i}</a>
                              </li>
                            </c:forEach>
                            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                              <a class="page-link" href="${baseURL}/admin/phancong?page=${currentPage + 1}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}">Sau</a>
                            </li>
                          </ul>
                        </nav>
                      </c:if>
                    </div>
                  </div>
                  
                </c:when>
                <c:otherwise>
                  <div class="alert alert-info text-center">
                    <i class="bi bi-info-circle"></i>
                    <c:choose>
                      <c:when test="${empty param.maNH and empty param.maHK and empty param.maKhoi and empty param.maLop}">
                        Vui lòng sử dụng bộ lọc để tìm kiếm danh sách lớp.
                      </c:when>
                      <c:otherwise>
                        Không tìm thấy lớp nào phù hợp với điều kiện tìm kiếm.
                      </c:otherwise>
                    </c:choose>
                  </div>
                </c:otherwise>
              </c:choose>

            </div>
          </div>
        </div>

      </div>
    </section>

</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />

<script>
  document.addEventListener('DOMContentLoaded', function () {
    const toastSuccessEl = document.getElementById('toastSuccess');
    if (toastSuccessEl) {
      const toastSuccess = new bootstrap.Toast(toastSuccessEl);
      toastSuccess.show();
    }

    const toastErrorEl = document.getElementById('toastError');
    if (toastErrorEl) {
      const toastError = new bootstrap.Toast(toastErrorEl);
      toastError.show();
    }

    // Cascade dropdown: Năm học -> Học kỳ
    const selectNamHoc = document.getElementById('selectNamHoc');
    const selectHocKy = document.getElementById('selectHocKy');
    const selectKhoi = document.getElementById('selectKhoi');
    const selectLop = document.getElementById('selectLop');
    const filterForm = document.getElementById('filterForm');

    // Lưu tất cả option học kỳ
    const allHocKyOptions = Array.from(selectHocKy.options).slice(1); // Bỏ option đầu tiên

    // Filter học kỳ theo năm học
    function filterHocKy() {
      const selectedNamHoc = selectNamHoc.value;
      
      // Xóa tất cả option hiện tại (trừ option đầu tiên)
      selectHocKy.innerHTML = '<option value="">-- Chọn năm học trước --</option>';
      
      if (selectedNamHoc) {
        // Thêm các học kỳ của năm học đã chọn
        allHocKyOptions.forEach(option => {
          if (option.getAttribute('data-manh') === selectedNamHoc) {
            selectHocKy.appendChild(option.cloneNode(true));
          }
        });
        selectHocKy.options[0].text = '-- Tất cả --';
      }
      
      // Restore selected value if exists
      const selectedHK = '${param.maHK}';
      if (selectedHK) {
        selectHocKy.value = selectedHK;
      }
    }

    // Auto submit khi chọn năm học hoặc khối
    selectNamHoc.addEventListener('change', function() {
      filterHocKy();
      filterForm.submit();
    });

    selectKhoi.addEventListener('change', function() {
      filterForm.submit();
    });

    // Initial filter on page load
    filterHocKy();
  });
</script>

</body>
</html>
