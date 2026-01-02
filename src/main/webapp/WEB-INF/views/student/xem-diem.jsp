<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<!-- External CSS -->
<link rel="stylesheet" href="${baseURL}/assets/css/xem-diem.css">
<!-- Meta tag for base URL (used by JavaScript) -->
<meta name="base-url" content="${baseURL}">

<main id="main" class="main">
    <div class="pagetitle">
        <h1>Bảng Điểm Cá Nhân</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${baseURL}/student/home">Trang chủ</a>
                </li>
                <li class="breadcrumb-item active">Xem điểm</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row mb-3">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title mb-0">
                            <i class="bi bi-person-badge text-primary"></i> ${hocSinh.hoTen}
                            <span class="ms-3"><i class="bi bi-bookmark text-primary"></i> Lớp: <span class="badge bg-primary">${hocSinh.tenLop}</span></span>
                        </h5>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row mb-3">
            <div class="col-12">
                <div class="card">
                    <div class="card-body py-3">
                        <form action="${baseURL}/student/xem-diem" method="GET" id="formChonHocKy">
                            <div class="row align-items-center g-3">
                                <div class="col-md-5">
                                    <div class="d-flex align-items-center">
                                        <label for="maNamHoc" class="form-label mb-0 me-2 text-nowrap">
                                            <i class="bi bi-calendar3"></i> Năm học:
                                        </label>
                                        <select name="maNamHoc" id="maNamHoc" class="form-select">
                                            <c:forEach var="nh" items="${dsNamHoc}">
                                                <option value="${nh.maNH}" ${nh.maNH == maNamHocHienTai ? 'selected' : ''}>
                                                    ${nh.tenNH}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <div class="d-flex align-items-center">
                                        <label for="maHocKy" class="form-label mb-0 me-2 text-nowrap">
                                            <i class="bi bi-calendar-range"></i> Học kỳ:
                                        </label>
                                        <select name="maHocKy" id="maHocKy" class="form-select" onchange="this.form.submit()">
                                            <c:forEach var="hk" items="${dsHocKy}">
                                                <option value="${hk.maHK}" 
                                                        data-ma-nh="${hk.maNH}"
                                                        ${hk.maHK == maHocKyHienTai ? 'selected' : ''}>
                                                    ${hk.tenHK}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <button type="submit" class="btn btn-primary w-100">
                                        <i class="bi bi-arrow-clockwise"></i> Tải lại
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <script>
            (function() {
                // Lọc và hiển thị học kỳ theo năm học được chọn
                function filterHocKy() {
                    var maNamHoc = document.getElementById('maNamHoc').value;
                    var selectHocKy = document.getElementById('maHocKy');
                    var allOptions = Array.from(selectHocKy.options);
                    
                    // Ẩn/hiện options dựa trên năm học
                    var hasVisibleOption = false;
                    var firstVisibleOption = null;
                    
                    allOptions.forEach(function(option) {
                        var optionMaNH = option.getAttribute('data-ma-nh');
                        if (optionMaNH === maNamHoc) {
                            option.style.display = '';
                            if (!firstVisibleOption) {
                                firstVisibleOption = option;
                            }
                            if (option.selected) {
                                hasVisibleOption = true;
                            }
                        } else {
                            option.style.display = 'none';
                        }
                    });
                    
                    // Nếu option hiện tại bị ẩn, chọn option đầu tiên visible
                    if (!hasVisibleOption && firstVisibleOption) {
                        firstVisibleOption.selected = true;
                    }
                }
                
                // Khởi tạo khi trang load
                document.addEventListener('DOMContentLoaded', function() {
                    filterHocKy();
                    
                    // Lắng nghe sự kiện thay đổi năm học
                    document.getElementById('maNamHoc').addEventListener('change', filterHocKy);
                });
            })();
        </script>

        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${empty dsDiem}">
                                <div class="alert alert-info m-3">
                                    <i class="bi bi-info-circle-fill"></i> 
                                    Hiện tại chưa có dữ liệu điểm cho học kỳ này.
                                </div>
                            </c:when>
                            <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-grades table-hover mb-0">
                                <thead>
                                    <tr>
                                        <th style="width: 5%;">STT</th>
                                        <th style="width: 20%;">Môn học</th>
                                        <th style="width: 12%;" title="Điểm Miệng (3 cột)">Miệng</th>
                                        <th style="width: 12%;" title="Điểm 15 phút (3 cột)">15p</th>
                                        <th style="width: 10%;" title="Điểm 1 Tiết (2 cột)">1 Tiết</th>
                                        <th style="width: 8%;" title="Điểm Thi">Thi</th>
                                        <th style="width: 10%;" title="Điểm Trung Bình Môn">ĐTB</th>
                                        <th style="width: 13%;">Xếp loại</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="diem" items="${dsDiem}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td class="subject-name">${diem.tenMonHoc}</td>
                                            
                                            <%-- Cột Điểm Miệng: Hiển thị 3 cột gộp --%>
                                            <td class="grade-cell">
                                                <c:choose>
                                                    <c:when test="${diem.diemMieng1 != null or diem.diemMieng2 != null or diem.diemMieng3 != null}">
                                                        <c:if test="${diem.diemMieng1 != null}">
                                                            <fmt:formatNumber value="${diem.diemMieng1}" pattern="#0.0" />
                                                        </c:if>
                                                        <c:if test="${diem.diemMieng2 != null}">
                                                            <c:if test="${diem.diemMieng1 != null}">, </c:if>
                                                            <fmt:formatNumber value="${diem.diemMieng2}" pattern="#0.0" />
                                                        </c:if>
                                                        <c:if test="${diem.diemMieng3 != null}">
                                                            <c:if test="${diem.diemMieng1 != null or diem.diemMieng2 != null}">, </c:if>
                                                            <fmt:formatNumber value="${diem.diemMieng3}" pattern="#0.0" />
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="grade-empty">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            
                                            <%-- Cột Điểm 15p --%>
                                            <td class="grade-cell">
                                                <c:choose>
                                                    <c:when test="${diem.diem15p1 != null or diem.diem15p2 != null or diem.diem15p3 != null}">
                                                        <c:if test="${diem.diem15p1 != null}">
                                                            <fmt:formatNumber value="${diem.diem15p1}" pattern="#0.0" />
                                                        </c:if>
                                                        <c:if test="${diem.diem15p2 != null}">
                                                            <c:if test="${diem.diem15p1 != null}">, </c:if>
                                                            <fmt:formatNumber value="${diem.diem15p2}" pattern="#0.0" />
                                                        </c:if>
                                                        <c:if test="${diem.diem15p3 != null}">
                                                            <c:if test="${diem.diem15p1 != null or diem.diem15p2 != null}">, </c:if>
                                                            <fmt:formatNumber value="${diem.diem15p3}" pattern="#0.0" />
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="grade-empty">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            
                                            <%-- Cột Điểm 1 Tiết --%>
                                            <td class="grade-cell">
                                                <c:choose>
                                                    <c:when test="${diem.diem1Tiet1 != null or diem.diem1Tiet2 != null}">
                                                        <c:if test="${diem.diem1Tiet1 != null}">
                                                            <fmt:formatNumber value="${diem.diem1Tiet1}" pattern="#0.0" />
                                                        </c:if>
                                                        <c:if test="${diem.diem1Tiet2 != null}">
                                                            <c:if test="${diem.diem1Tiet1 != null}">, </c:if>
                                                            <fmt:formatNumber value="${diem.diem1Tiet2}" pattern="#0.0" />
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="grade-empty">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            
                                            <%-- Cột Điểm Thi --%>
                                            <td class="grade-cell">
                                                <c:choose>
                                                    <c:when test="${diem.diemThi != null}">
                                                        <fmt:formatNumber value="${diem.diemThi}" pattern="#0.0" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="grade-empty">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            
                                            <%-- Cột ĐTB --%>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${diem.diemTBM != null}">
                                                        <span class="grade-average">
                                                            <fmt:formatNumber value="${diem.diemTBM}" pattern="#0.00" />
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="grade-empty">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            

                                            <td>
                                                <c:choose>
                                                    <c:when test="${diem.diemTBM != null}">
                                                        <c:choose>
                                                            <c:when test="${diem.diemTBM >= 8.0}">
                                                                <span class="rank-excellent">Giỏi</span>
                                                            </c:when>
                                                            <c:when test="${diem.diemTBM >= 6.5}">
                                                                <span class="rank-good">Khá</span>
                                                            </c:when>
                                                            <c:when test="${diem.diemTBM >= 5.0}">
                                                                <span class="rank-average">Trung Bình</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="rank-weak">Yếu</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="rank-none">Chưa có</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                
                                <%-- Footer: Tổng kết học kỳ --%>
                                <tfoot>
                                    <tr class="table-footer">
                                        <td colspan="6" class="text-end px-3">
                                            <i class="bi bi-trophy-fill"></i> 
                                            <strong>Điểm Trung Bình Học Kỳ (ĐTBHK):</strong>
                                        </td>
                                        <td colspan="2" class="text-center">
                                            <c:choose>
                                                <c:when test="${diemTBHK > 0}">
                                                    <span class="summary-grade">
                                                        <fmt:formatNumber value="${diemTBHK}" pattern="#0.00" />
                                                    </span>
                                                    <br/>
                                                    <small class="text-muted">
                                                        (Xếp loại: 
                                                        <c:choose>
                                                            <c:when test="${diemTBHK >= 8.0}">
                                                                <span class="rank-excellent">Giỏi</span>
                                                            </c:when>
                                                            <c:when test="${diemTBHK >= 6.5}">
                                                                <span class="rank-good">Khá</span>
                                                            </c:when>
                                                            <c:when test="${diemTBHK >= 5.0}">
                                                                <span class="rank-average">Trung Bình</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="rank-weak">Yếu</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        )
                                                    </small>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Chưa có điểm</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-info-circle"></i> Ghi chú</h5>
                <ul class="mb-0">
                    <li><strong>ĐTB (Điểm Trung Bình Môn):</strong> Được tính theo công thức: 
                        <code>(Miệng + 15p + 1Tiết×2 + Thi×3) ÷ 7</code>
                    </li>
                    <li><strong>ĐTBHK (Điểm Trung Bình Học Kỳ):</strong> Trung bình cộng của ĐTB các môn học.</li>
                    <li><strong>Xếp loại:</strong> 
                        <span class="rank-excellent">Giỏi (≥ 8.0)</span>, 
                        <span class="rank-good">Khá (6.5 - 7.9)</span>, 
                        <span class="rank-average">Trung Bình (5.0 - 6.4)</span>, 
                        <span class="rank-weak">Yếu (< 5.0)</span>
                    </li>
                    <li>Ký hiệu <span class="grade-empty">"-"</span> nghĩa là điểm chưa được nhập.</li>
                </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>


    
<jsp:include page="/WEB-INF/includes/footer.jsp" />
