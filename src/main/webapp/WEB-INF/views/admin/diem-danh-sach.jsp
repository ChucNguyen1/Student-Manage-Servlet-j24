<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Nhập Điểm</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item active">Danh sách lớp môn chưa nhập điểm</li>
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
              
              <form action="${baseURL}/admin/diem" method="GET" id="filterForm">
                <div class="row g-3">
                  
                  <div class="col-md-3">
                    <label class="form-label fw-bold">Năm học</label>
                    <select class="form-select" name="maNH" onchange="document.getElementById('filterForm').submit()">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="nh" items="${dsNamHoc}">
                        <option value="${nh.maNH}" ${param.maNH == nh.maNH ? 'selected' : ''}>${nh.tenNH}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-2">
                    <label class="form-label fw-bold">Học kỳ</label>
                    <select class="form-select" name="maHK">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="hk" items="${dsHocKy}">
                        <option value="${hk.maHK}" ${param.maHK == hk.maHK ? 'selected' : ''}>${hk.tenHK}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-2">
                    <label class="form-label fw-bold">Khối</label>
                    <select class="form-select" name="maKhoi" onchange="document.getElementById('filterForm').submit()">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="k" items="${dsKhoi}">
                        <option value="${k.maKhoi}" ${param.maKhoi == k.maKhoi ? 'selected' : ''}>${k.tenKhoi}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-2">
                    <label class="form-label fw-bold">Lớp</label>
                    <select class="form-select" name="maLop">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="lop" items="${dsLopHoc}">
                        <option value="${lop.maLop}" ${param.maLop == lop.maLop ? 'selected' : ''}>${lop.tenLop}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="col-md-3">
                    <label class="form-label fw-bold">Môn học</label>
                    <select class="form-select" name="maMH">
                      <option value="">-- Tất cả --</option>
                      <c:forEach var="mh" items="${dsMonHoc}">
                        <option value="${mh.maMH}" ${param.maMH == mh.maMH ? 'selected' : ''}>${mh.tenMH}</option>
                      </c:forEach>
                    </select>
                  </div>

                </div>

                <div class="text-center mt-3">
                  <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Tìm kiếm
                  </button>
                  <a href="${baseURL}/admin/diem" class="btn btn-secondary">
                    <i class="bi bi-arrow-clockwise"></i> Làm mới
                  </a>
                </div>
              </form>

            </div>
          </div>
        </div>

        <!-- Bảng danh sách lớp môn chưa nhập điểm -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <i class="bi bi-table"></i> Danh sách lớp môn chưa nhập điểm hoặc nhập chưa đầy đủ
              </h5>

              <c:choose>
                <c:when test="${not empty dsLopMon}">
                  <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                      <thead class="table-primary">
                        <tr class="text-center">
                          <th style="width: 5%">STT</th>
                          <th style="width: 12%">Năm học</th>
                          <th style="width: 10%">Học kỳ</th>
                          <th style="width: 8%">Khối</th>
                          <th style="width: 12%">Lớp</th>
                          <th style="width: 15%">Môn học</th>
                          <th style="width: 12%">Tiến độ</th>
                          <th style="width: 10%">Trạng thái</th>
                          <th style="width: 16%">Hành động</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="item" items="${dsLopMon}" varStatus="status">
                          <tr>
                            <td class="text-center">${status.count}</td>
                            <td class="text-center">${item.tenNH}</td>
                            <td class="text-center">${item.tenHocKy}</td>
                            <td class="text-center">${item.tenKhoi}</td>
                            <td class="fw-bold">${item.tenLop}</td>
                            <td>${item.tenMonHoc}</td>
                            <td class="text-center">
                              <c:set var="progressPercent" value="${item.soHocSinh > 0 ? (item.soHocSinhDaNhap * 100.0) / item.soHocSinh : 0}" />
                              <c:set var="progressInt" value="${Math.round(progressPercent)}" />
                              <c:set var="progressColor" value="${progressInt == 100 ? 'bg-success' : (progressInt >= 50 ? 'bg-warning' : 'bg-danger')}" />
                              <div class="progress" style="height: 25px">
                                <div class="progress-bar ${progressColor}" 
                                     role="progressbar" 
                                     data-width="${progressInt}"
                                     aria-valuenow="${progressInt}" 
                                     aria-valuemin="0" 
                                     aria-valuemax="100">
                                  <span>${item.soHocSinhDaNhap}/${item.soHocSinh}</span>
                                </div>
                              </div>
                            </td>
                            <td class="text-center">
                              <c:choose>
                                <c:when test="${item.daHoanThanh}">
                                  <span class="badge bg-success">Hoàn thành</span>
                                </c:when>
                                <c:when test="${item.soHocSinhDaNhap == 0}">
                                  <span class="badge bg-danger">Chưa nhập</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="badge bg-warning">Nhập một phần</span>
                                </c:otherwise>
                              </c:choose>
                            </td>
                            <td class="text-center">
                              <a href="${baseURL}/admin/diem-nhap?maNH=${item.maNH}&maKhoi=${item.maKhoi}&maLop=${item.maLop}&maMH=${item.maMonHoc}&maHK=${item.maHocKy}" 
                                 class="btn btn-primary btn-sm">
                                <i class="bi bi-pencil-square"></i> Nhập điểm
                              </a>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                  
                  <div class="row align-items-center mt-3">
                    <div class="col-md-6">
                      <span class="text-muted">
                        <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
                        <c:set var="endItem" value="${currentPage * pageSize}" />
                        <c:if test="${endItem > totalItems}"><c:set var="endItem" value="${totalItems}" /></c:if>
                        <c:if test="${totalItems > 0}">
                          <i class="bi bi-info-circle"></i> 
                          Hiển thị <b>${startItem}</b> đến <b>${endItem}</b> trong <b>${totalItems}</b> lớp-môn cần nhập điểm
                        </c:if>
                        <c:if test="${totalItems == 0}">Chưa có lớp-môn nào cần nhập điểm</c:if>
                      </span>
                    </div>
                    <div class="col-md-6">
                      <c:if test="${totalPages > 1}">
                        <nav aria-label="Phân trang">
                          <ul class="pagination justify-content-end mb-0">
                            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                              <a class="page-link" href="${baseURL}/admin/diem?page=${currentPage - 1}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}&maMH=${param.maMH}">Trước</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                              <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${baseURL}/admin/diem?page=${i}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}&maMH=${param.maMH}">${i}</a>
                              </li>
                            </c:forEach>
                            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                              <a class="page-link" href="${baseURL}/admin/diem?page=${currentPage + 1}&maNH=${param.maNH}&maHK=${param.maHK}&maKhoi=${param.maKhoi}&maLop=${param.maLop}&maMH=${param.maMH}">Sau</a>
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
                      <c:when test="${empty param.maNH and empty param.maHK and empty param.maKhoi and empty param.maLop and empty param.maMH}">
                        Vui lòng sử dụng bộ lọc để tìm kiếm lớp môn cần nhập điểm.
                      </c:when>
                      <c:otherwise>
                        Không tìm thấy lớp-môn nào chưa nhập điểm hoặc tất cả đã nhập đầy đủ.
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

<script>
  document.addEventListener('DOMContentLoaded', (event) => {
    const ts = document.getElementById('toastSuccess'); 
    if(ts) new bootstrap.Toast(ts, {delay:5000}).show();
    const te = document.getElementById('toastError'); 
    if(te) new bootstrap.Toast(te, {delay:5000}).show();
    
    // Set progress bar widths
    document.querySelectorAll('.progress-bar[data-width]').forEach(function(bar) {
      var width = bar.getAttribute('data-width');
      bar.style.width = width + '%';
    });
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
