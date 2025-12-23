<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Phân công Giảng dạy</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Đào tạo</li>
          <li class="breadcrumb-item active">Phân công</li>
        </ol>
      </nav>
    </div>

    <%-- TOAST NOTIFICATIONS --%>
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
              <h5 class="card-title"><i class="bi bi-funnel-fill"></i> Bộ lọc - Chọn Năm học, Học kỳ, Khối và Lớp</h5>
              
              <form action="${baseURL}/admin/phancong-list" method="GET" id="filterForm">
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
                      <c:if test="${empty param.maNH}">
                        <small class="text-muted">Vui lòng chọn năm học trước</small>
                      </c:if>
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
                      <c:if test="${empty param.maKhoi or empty param.maNH}">
                        <small class="text-muted">Chọn năm học và khối trước</small>
                      </c:if>
                    </div>
                </div>
                
                <%-- Hướng dẫn --%>
                <div class="alert alert-info mt-3 mb-0" role="alert">
                  <i class="bi bi-info-circle-fill"></i> 
                  <strong>Hướng dẫn:</strong> Chọn đầy đủ Năm học → Học kỳ → Khối → Lớp để xem bảng phân công môn học.
                </div>
              </form>

            </div>
          </div>
        </div>

        <%-- 2. BẢNG PHÂN CÔNG --%>
        <c:if test="${not empty dsPhanCong}">
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                <i class="bi bi-journal-text"></i> Phân công lớp: <strong class="text-primary">${tenLop}</strong>
                <span class="badge bg-secondary ms-2">${countPhanCong}/${totalMon} môn đã phân công</span>
              </h5>

              <form action="${baseURL}/admin/phancong-save" method="POST" id="phanCongForm" onsubmit="return validateForm()">
                <%-- Giữ lại các tham số lọc để redirect --%>
                <input type="hidden" name="maNH" value="${param.maNH}">
                <input type="hidden" name="maKhoi" value="${param.maKhoi}">
                <input type="hidden" name="maLop" value="${param.maLop}">
                <input type="hidden" name="maHK" value="${param.maHK}">

                <div class="table-responsive">
                  <table class="table table-bordered table-hover align-middle">
                    <thead class="table-dark">
                      <tr>
                        <th style="width: 5%" class="text-center">STT</th>
                        <th style="width: 25%">Môn học</th>
                        <th style="width: 15%">Tổ bộ môn</th>
                        <th style="width: 35%">Giáo viên giảng dạy</th>
                        <th style="width: 20%" class="text-center">Trạng thái</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="pc" items="${dsPhanCong}" varStatus="status">
                        <tr>
                          <td class="text-center fw-bold">${status.count}</td>
                          
                          <td>
                              <strong class="text-primary">${pc.tenMonHoc}</strong>
                              <input type="hidden" name="maMonHoc_list" value="${pc.maMonHoc}">
                          </td>
                          
                          <td>
                              <c:choose>
                                <c:when test="${not empty pc.maTo and pc.maTo > 0}">
                                  <span class="badge bg-info"><i class="bi bi-people-fill"></i> Tổ ${pc.maTo}</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="text-muted"><i class="bi bi-dash-circle"></i> Chưa có tổ</span>
                                </c:otherwise>
                              </c:choose>
                          </td>
                          
                          <td>
                              <%-- DROPDOWN CHỌN GIÁO VIÊN - Lọc theo Tổ Bộ Môn --%>
                              <select class="form-select" name="maGV_${pc.maMonHoc}" id="select_${pc.maMonHoc}">
                                  <option value="0">-- Chưa chọn giáo viên --</option>
                                  
                                  <c:if test="${not empty pc.maTo and pc.maTo > 0}">
                                      <%-- Lấy danh sách GV theo Tổ --%>
                                      <c:set var="listGVTheoTo" value="${mapGVTheoTo[pc.maTo]}" />
                                      <c:choose>
                                        <c:when test="${empty listGVTheoTo}">
                                          <option disabled>-- Tổ này chưa có giáo viên --</option>
                                        </c:when>
                                        <c:otherwise>
                                          <c:forEach var="gv" items="${listGVTheoTo}">
                                              <option value="${gv.maGV}" ${pc.maGV == gv.maGV ? 'selected' : ''}>
                                                  ${gv.hoTen}
                                              </option>
                                          </c:forEach>
                                        </c:otherwise>
                                      </c:choose>
                                  </c:if>
                                  
                                  <c:if test="${empty pc.maTo or pc.maTo == 0}">
                                      <option disabled>-- Môn học chưa thuộc tổ nào --</option>
                                  </c:if>
                              </select>
                          </td>

                          <td class="text-center">
                              <c:if test="${pc.trangThai}">
                                  <span class="badge bg-success"><i class="bi bi-check-circle-fill"></i> Đã phân công</span>
                              </c:if>
                              <c:if test="${!pc.trangThai}">
                                  <span class="badge bg-warning text-dark"><i class="bi bi-exclamation-triangle-fill"></i> Chưa có GV</span>
                              </c:if>
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>

                <div class="text-center mt-4">
                  <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-save-fill"></i> Lưu Phân công
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
        
        <c:if test="${empty dsPhanCong and not empty param.maNH and not empty param.maKhoi and not empty param.maLop and not empty param.maHK}">
          <div class="col-12">
            <div class="alert alert-warning" role="alert">
              <i class="bi bi-exclamation-triangle-fill"></i> 
              <strong>Không tìm thấy dữ liệu!</strong> Lớp này chưa có môn học nào để phân công.
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

// Validate form trước khi submit
function validateForm() {
    const form = document.getElementById('phanCongForm');
    const selects = form.querySelectorAll('select[name^="maGV_"]');
    let hasSelection = false;
    let countSelected = 0;
    
    for (let select of selects) {
        if (select.value && select.value !== "0") {
            hasSelection = true;
            countSelected++;
        }
    }
    
    if (!hasSelection) {
        alert(' Vui lòng chọn ít nhất một giáo viên để phân công!');
        return false;
    }
    
    return confirm(`Xác nhận lưu phân công cho ${countSelected} môn học?`);
}
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />