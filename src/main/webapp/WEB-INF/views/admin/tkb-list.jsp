<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1><i class="bi bi-calendar3"></i> Sắp xếp Thời Khóa Biểu</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Đào tạo</li>
          <li class="breadcrumb-item active">Thời khóa biểu</li>
        </ol>
      </nav>
    </div>

    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100">
      <c:if test="${not empty sessionScope.message}">
        <div id="toastSuccess" class="toast" role="alert">
          <div class="toast-header bg-success text-white">
            <strong><i class="bi bi-check-circle-fill"></i> Thành công</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.message}</div>
        </div>
        <c:remove var="message" scope="session" />
      </c:if>
      
      <c:if test="${not empty sessionScope.error}">
        <div id="toastError" class="toast" role="alert">
          <div class="toast-header bg-danger text-white">
            <strong><i class="bi bi-x-circle-fill"></i> Lỗi</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.error}</div>
        </div>
        <c:remove var="error" scope="session" />
      </c:if>
      
      <c:if test="${not empty sessionScope.warning}">
        <div id="toastWarning" class="toast" role="alert">
          <div class="toast-header bg-warning text-dark">
            <strong><i class="bi bi-exclamation-triangle-fill"></i> Cảnh báo</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.warning}</div>
        </div>
        <c:remove var="warning" scope="session" />
      </c:if>
      
      <c:if test="${not empty sessionScope.info}">
        <div id="toastInfo" class="toast" role="alert">
          <div class="toast-header bg-info text-white">
            <strong><i class="bi bi-info-circle-fill"></i> Thông tin</strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
          </div>
          <div class="toast-body">${sessionScope.info}</div>
        </div>
        <c:remove var="info" scope="session" />
      </c:if>
    </div>

    <section class="section">
      <div class="row">
        
        <%-- 1. BỘ LỌC --%>
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title"><i class="bi bi-funnel-fill"></i> Chọn lớp cần xếp TKB</h5>
              
              <form action="${baseURL}/admin/tkb-list" method="GET" id="filterForm">
                <div class="row g-3">
                    <%-- Năm học --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Năm học <span class="text-danger">*</span></label>
                      <select class="form-select" name="maNH" required onchange="document.getElementById('filterForm').submit()">
                        <option value="">-- Chọn năm học --</option>
                        <c:forEach var="nh" items="${dsNamHoc}">
                           <option value="${nh.maNH}" ${param.maNH == nh.maNH ? 'selected' : ''}>${nh.tenNH}</option>
                        </c:forEach>
                      </select>
                    </div>
                    
                    <%-- Học kỳ --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Học kỳ <span class="text-danger">*</span></label>
                      <select class="form-select" name="maHK" required ${empty param.maNH ? 'disabled' : ''} onchange="document.getElementById('filterForm').submit()">
                        <option value="">-- Chọn học kỳ --</option>
                        <c:forEach var="hk" items="${dsHocKy}">
                           <option value="${hk.maHK}" ${param.maHK == hk.maHK ? 'selected' : ''}>${hk.tenHK}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <%-- Khối --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Khối <span class="text-danger">*</span></label>
                      <select class="form-select" name="maKhoi" required onchange="document.getElementById('filterForm').submit()">
                        <option value="">-- Chọn khối --</option>
                        <c:forEach var="k" items="${dsKhoi}">
                           <option value="${k.maKhoi}" ${param.maKhoi == k.maKhoi ? 'selected' : ''}>${k.tenKhoi}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <%-- Lớp --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Lớp <span class="text-danger">*</span></label>
                      <select class="form-select" name="maLop" required ${empty param.maKhoi or empty param.maNH ? 'disabled' : ''} onchange="document.getElementById('filterForm').submit()">
                        <option value="">-- Chọn lớp --</option>
                        <c:forEach var="l" items="${dsLopHoc}">
                           <option value="${l.maLop}" ${param.maLop == l.maLop ? 'selected' : ''}>${l.tenLop}</option>
                        </c:forEach>
                      </select>
                    </div>
                </div>
                
                <div class="alert alert-info mt-3 mb-0" role="alert">
                  <i class="bi bi-lightbulb-fill"></i> 
                  <strong>Hướng dẫn:</strong> Chọn đầy đủ thông tin → Hệ thống sẽ hiển thị lưới 5 tiết x 6 ngày để bạn xếp lịch.
                </div>
              </form>

            </div>
          </div>
        </div>

        <c:if test="${not empty dsPhanCong and not empty tkbGrid}">
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <i class="bi bi-calendar2-week"></i> Thời khóa biểu lớp: <strong class="text-primary">${tenLop}</strong>
                <span class="badge bg-secondary ms-2">${countScheduled}/${totalSlots} tiết đã xếp</span>

                <button type="button" class="btn btn-sm btn-danger float-end" onclick="resetTKB()">
                  <i class="bi bi-arrow-counterclockwise"></i> Reset TKB
                </button>
              </h5>

              <form action="${baseURL}/admin/tkb-save" method="POST" id="tkbForm" onsubmit="return confirmSave()">

                <input type="hidden" name="maNH" value="${param.maNH}">
                <input type="hidden" name="maKhoi" value="${param.maKhoi}">
                <input type="hidden" name="maLop" value="${param.maLop}">
                <input type="hidden" name="maHK" value="${param.maHK}">


                <div class="table-responsive">
                  <table class="table table-bordered table-hover align-middle text-center" style="font-size: 0.9rem;">
                    <thead class="table-dark">
                      <tr>
                        <th style="width: 8%">Tiết \ Thứ</th>
                        <th style="width: 15.33%">Thứ Hai</th>
                        <th style="width: 15.33%">Thứ Ba</th>
                        <th style="width: 15.33%">Thứ Tư</th>
                        <th style="width: 15.33%">Thứ Năm</th>
                        <th style="width: 15.33%">Thứ Sáu</th>
                        <th style="width: 15.33%">Thứ Bảy</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="tiet" begin="1" end="5">
                        <tr>
                          <td class="fw-bold bg-light">Tiết ${tiet}</td>
                          
                          <c:forEach var="thu" begin="2" end="7">
                            <c:set var="key" value="${thu}_${tiet}" />
                            <c:set var="tkb" value="${tkbGrid[key]}" />
                            
                            <td>
                              <select class="form-select form-select-sm mb-1" name="mon_${key}">
                                <option value="0">-- Trống --</option>
                                <c:forEach var="pc" items="${dsPhanCong}">
                                  <c:set var="value" value="${pc.maMonHoc}_${pc.maGV}" />
                                  <option value="${value}" ${tkb != null and tkb.maMonHoc == pc.maMonHoc ? 'selected' : ''}>
                                    ${pc.tenMonHoc}
                                  </option>
                                </c:forEach>
                              </select>
                              
                              <input type="text" class="form-control form-control-sm" 
                                     name="phong_${key}" 
                                     placeholder="Phòng..." 
                                     value="${tkb != null ? tkb.phongHoc : ''}"
                                     maxlength="20">
                              
                              <c:if test="${tkb != null}">
                                <small class="text-muted d-block mt-1">
                                  <i class="bi bi-person-fill"></i> ${tkb.tenGiaoVien}
                                </small>
                              </c:if>
                            </td>
                          </c:forEach>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>

                <div class="text-center mt-4">
                  <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-save-fill"></i> Lưu Thời Khóa Biểu
                  </button>
                  <button type="button" class="btn btn-secondary btn-lg ms-2" onclick="window.location.reload()">
                    <i class="bi bi-arrow-clockwise"></i> Làm mới
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        </c:if>
        
        <c:if test="${empty dsPhanCong and not empty param.maLop}">
          <div class="col-12">
            <div class="alert alert-danger" role="alert">
              <i class="bi bi-exclamation-octagon-fill"></i> 
              <strong>Chưa thể xếp TKB!</strong> Lớp này chưa có môn học nào được phân công giáo viên. 
              Vui lòng vào <a href="${baseURL}/admin/phancong-list" class="alert-link">Phân công giảng dạy</a> trước.
            </div>
          </div>
        </c:if>

      </div>
    </section>

</main>

<script>
// Show toasts
document.addEventListener('DOMContentLoaded', () => {
    const ts = document.getElementById('toastSuccess'); 
    if(ts) new bootstrap.Toast(ts, {delay: 4000}).show();
    
    const te = document.getElementById('toastError'); 
    if(te) new bootstrap.Toast(te, {delay: 5000}).show();
    
    const tw = document.getElementById('toastWarning'); 
    if(tw) new bootstrap.Toast(tw, {delay: 4000}).show();
    
    const ti = document.getElementById('toastInfo'); 
    if(ti) new bootstrap.Toast(ti, {delay: 4000}).show();
});

// Confirm trước khi save
function confirmSave() {
    return confirm('Xác nhận lưu Thời Khóa Biểu? Hệ thống sẽ kiểm tra trùng lịch giáo viên tự động.');
}

// Reset TKB
function resetTKB() {
    if (confirm(' XÓA TOÀN BỘ TKB và xếp lại từ đầu?')) {
        const params = new URLSearchParams(window.location.search);
        window.location.href = '${baseURL}/admin/tkb-reset?maLop=' + params.get('maLop') + '&maHK=' + params.get('maHK');
    }
}
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
