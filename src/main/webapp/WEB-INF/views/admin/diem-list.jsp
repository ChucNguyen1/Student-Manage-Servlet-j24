<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
      <h1>Quản lý Điểm số</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item active">Sổ điểm</li>
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
        
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Chọn lớp cần nhập điểm</h5>
              
              <form action="${baseURL}/admin/diem-list" method="GET" id="filterForm">
                
                <div class="row g-3">
                    <div class="col-md-2">
                      <label class="form-label fw-bold">Năm học</label>
                      <select class="form-select" name="maNH" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn năm --</option>
                        <c:forEach var="nh" items="${dsNamHoc}">
                           <option value="${nh.maNH}" ${param.maNH == nh.maNH ? 'selected' : ''}>${nh.tenNH}</option>
                        </c:forEach>
                      </select>
                    </div>
    
                    <div class="col-md-2">
                      <label class="form-label fw-bold">Học kỳ</label>
                      <select class="form-select" name="maHK">
                        <option value="" disabled selected>-- Chọn kỳ --</option>
                        <c:forEach var="hk" items="${dsHocKy}">
                           <option value="${hk.maHK}" ${param.maHK == hk.maHK ? 'selected' : ''}>${hk.tenHK}</option>
                        </c:forEach>
                      </select>
                    </div>
    
                    <div class="col-md-2">
                      <label class="form-label fw-bold">Khối</label>
                      <select class="form-select" name="maKhoi" onchange="document.getElementById('filterForm').submit()">
                        <option value="" disabled selected>-- Chọn khối --</option>
                        <c:forEach var="k" items="${dsKhoi}">
                           <option value="${k.maKhoi}" ${param.maKhoi == k.maKhoi ? 'selected' : ''}>${k.tenKhoi}</option>
                        </c:forEach>
                      </select>
                    </div>
    
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Lớp</label>
                      <select class="form-select" name="maLop">
                        <option value="" disabled selected>-- Chọn lớp --</option>
                        <c:forEach var="lop" items="${dsLopHoc}">
                           <option value="${lop.maLop}" ${param.maLop == lop.maLop ? 'selected' : ''}>${lop.tenLop}</option>
                        </c:forEach>
                      </select>
                      <c:if test="${empty dsLopHoc && (empty param.maNH || empty param.maKhoi)}">
                          <small class="text-danger">*Chọn Năm & Khối trước</small>
                      </c:if>
                    </div>
    
                    <div class="col-md-3">
                      <label class="form-label fw-bold">Môn học</label>
                      <select class="form-select" name="maMH">
                        <option value="" disabled selected>-- Chọn môn --</option>
                        <c:forEach var="mh" items="${dsMonHoc}">
                           <option value="${mh.maMH}" ${param.maMH == mh.maMH ? 'selected' : ''}>${mh.tenMH}</option>
                        </c:forEach>
                      </select>
                    </div>
                </div>

                <div class="text-center mt-4">
                  <button type="submit" class="btn btn-primary px-5">
                    <i class="bi bi-funnel-fill"></i> Lọc Bảng Điểm
                  </button>
                </div>
              </form>

            </div>
          </div>
        </div>

        <c:if test="${not empty dsDiem}">
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Bảng điểm chi tiết</h5>

              <form action="${baseURL}/admin/diem-save" method="POST">
                
                <input type="hidden" name="maLop" value="${param.maLop}">
                <input type="hidden" name="maMH" value="${param.maMH}">
                <input type="hidden" name="maHK" value="${param.maHK}">

                <div class="table-responsive">
                  <table class="table table-bordered table-hover align-middle text-center">
                    <thead class="table-light">
                      <tr>
                        <th rowspan="2" class="align-middle" style="width: 5%">STT</th>
                        <th rowspan="2" class="align-middle" style="width: 20%; text-align: left;">Họ và Tên</th>
                        <th colspan="3">Điểm Miệng (hs1)</th>
                        <th colspan="3">15 Phút (hs1)</th>
                        <th colspan="2">1 Tiết (hs2)</th>
                        <th rowspan="2" class="align-middle" style="width: 8%">Thi (hs3)</th>
                        <th rowspan="2" class="align-middle" style="width: 8%">TBM</th>
                      </tr>
                      <tr>
                        <th>1</th><th>2</th><th>3</th>
                        <th>1</th><th>2</th><th>3</th>
                        <th>1</th><th>2</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="d" items="${dsDiem}" varStatus="status">
                        <tr>
                          <td>${status.count}</td>
                          <td style="text-align: left; font-weight: bold;">
                              ${d.tenHocSinh}
                              <input type="hidden" name="maHS_list" value="${d.maHS}">
                          </td>
                          
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diemMieng1_${d.maHS}" value="${d.diemMieng1}"></td>
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diemMieng2_${d.maHS}" value="${d.diemMieng2}"></td>
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diemMieng3_${d.maHS}" value="${d.diemMieng3}"></td>
                          
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diem15p1_${d.maHS}" value="${d.diem15p1}"></td>
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diem15p2_${d.maHS}" value="${d.diem15p2}"></td>
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diem15p3_${d.maHS}" value="${d.diem15p3}"></td>
                          
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diem1Tiet1_${d.maHS}" value="${d.diem1Tiet1}"></td>
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center" name="diem1Tiet2_${d.maHS}" value="${d.diem1Tiet2}"></td>
                          
                          <td><input type="number" step="0.1" min="0" max="10" class="form-control form-control-sm px-1 text-center fw-bold text-primary" name="diemThi_${d.maHS}" value="${d.diemThi}"></td>
                          
                          <td class="fw-bold ${d.diemTBM >= 5.0 ? 'text-success' : 'text-danger'}">
                              ${d.diemTBM != null ? d.diemTBM : '-'}
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>

                <div class="text-center mt-3">
                  <button type="submit" class="btn btn-success btn-lg">
                    <i class="bi bi-save"></i> Lưu bảng điểm
                  </button>
                </div>

              </form>
            </div>
          </div>
        </div>
        </c:if>
        
        <c:if test="${empty dsDiem and not empty param.maLop}">
            <div class="col-12">
                <div class="alert alert-warning text-center">
                    Không tìm thấy học sinh nào trong lớp này hoặc dữ liệu chưa được khởi tạo.
                </div>
            </div>
        </c:if>

      </div>
    </section>

</main>

<script>
  document.addEventListener('DOMContentLoaded', (event) => {
    const ts = document.getElementById('toastSuccess'); if(ts) new bootstrap.Toast(ts, {delay:5000}).show();
    const te = document.getElementById('toastError'); if(te) new bootstrap.Toast(te, {delay:5000}).show();
  });
</script>

<jsp:include page="/WEB-INF/includes/footer.jsp" />