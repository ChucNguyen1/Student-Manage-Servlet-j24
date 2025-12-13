<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

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
        
        <%-- 1. BỘ LỌC --%>
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Chọn Lớp & Học kỳ</h5>
              
              <form action="${baseURL}/admin/phancong-list" method="GET" id="filterForm">
                <div class="row g-3">
                    <%-- Năm học --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Năm học</label>
                      <select class="form-select" name="maNH" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn năm --</option>
                        <c:forEach var="nh" items="${dsNamHoc}">
                           <option value="${nh.maNH}" ${param.maNH == nh.maNH ? 'selected' : ''}>${nh.tenNH}</option>
                        </c:forEach>
                      </select>
                    </div>
                    
                    <%-- Học kỳ --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Học kỳ</label>
                      <select class="form-select" name="maHK" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn kỳ --</option>
                        <c:forEach var="hk" items="${dsHocKy}">
                           <option value="${hk.maHK}" ${param.maHK == hk.maHK ? 'selected' : ''}>${hk.tenHK}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <%-- Khối --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Khối</label>
                      <select class="form-select" name="maKhoi" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn khối --</option>
                        <c:forEach var="k" items="${dsKhoi}">
                           <option value="${k.maKhoi}" ${param.maKhoi == k.maKhoi ? 'selected' : ''}>${k.tenKhoi}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <%-- Lớp --%>
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Lớp</label>
                      <select class="form-select" name="maLop" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn lớp --</option>
                        <c:forEach var="l" items="${dsLopHoc}">
                           <option value="${l.maLop}" ${param.maLop == l.maLop ? 'selected' : ''}>${l.tenLop}</option>
                        </c:forEach>
                      </select>
                    </div>
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
              <h5 class="card-title">Danh sách Phân công</h5>

              <form action="${baseURL}/admin/phancong-save" method="POST">
                <%-- Giữ lại các tham số lọc để redirect --%>
                <input type="hidden" name="maNH" value="${param.maNH}">
                <input type="hidden" name="maKhoi" value="${param.maKhoi}">
                <input type="hidden" name="maLop" value="${param.maLop}">
                <input type="hidden" name="maHK" value="${param.maHK}">

                <div class="table-responsive">
                  <table class="table table-bordered table-hover align-middle">
                    <thead class="table-light">
                      <tr>
                        <th style="width: 5%">STT</th>
                        <th style="width: 30%">Môn học</th>
                        <th style="width: 40%">Giáo viên giảng dạy</th>
                        <th style="width: 25%">Trạng thái</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="pc" items="${dsPhanCong}" varStatus="status">
                        <tr>
                          <td class="text-center">${status.count}</td>
                          <td>
                              <strong>${pc.tenMonHoc}</strong>
                              <input type="hidden" name="maMonHoc_list" value="${pc.maMonHoc}">
                          </td>
                          
                          <td>
                              <%-- DROPDOWN CHỌN GIÁO VIÊN --%>
                              <%-- Name: maGV_{ID_Môn} --%>
                              <select class="form-select" name="maGV_${pc.maMonHoc}">
                                  <option value="0">-- Chưa phân công --</option>
                                  <c:forEach var="gv" items="${dsGiaoVien}">
                                      <%-- Logic Selected: Nếu maGV của phân công trùng với maGV trong list --%>
                                      <option value="${gv.maGV}" ${pc.maGV == gv.maGV ? 'selected' : ''}>
                                          ${gv.hoTen} (${gv.tenMonHocChuyenMon})
                                      </option>
                                  </c:forEach>
                              </select>
                          </td>

                          <td class="text-center">
                              <c:if test="${pc.trangThai}">
                                  <span class="badge bg-success"><i class="bi bi-check-circle"></i> Đã phân công</span>
                              </c:if>
                              <c:if test="${!pc.trangThai}">
                                  <span class="badge bg-warning text-dark"><i class="bi bi-exclamation-triangle"></i> Chưa có GV</span>
                              </c:if>
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>

                <div class="text-center mt-3">
                  <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-save"></i> Lưu Phân công
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        </c:if>

      </div>
    </section>

</main>

<script>
document.addEventListener('DOMContentLoaded', () => {
    const ts = document.getElementById('toastSuccess'); if(ts) new bootstrap.Toast(ts).show();
    const te = document.getElementById('toastError'); if(te) new bootstrap.Toast(te).show();
});
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />