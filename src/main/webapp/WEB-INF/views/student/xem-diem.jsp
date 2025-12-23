<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xem Bảng Điểm | Student Management</title>
    
    <c:set var="baseURL" value="${pageContext.request.contextPath}" />
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .main-content {
            margin-top: 30px;
            margin-bottom: 50px;
        }
        
        .page-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            margin-bottom: 20px;
        }
        
        .card-title {
            color: #495057;
            font-weight: 600;
            margin-bottom: 20px;
        }
        
        /* Bảng điểm */
        .table-grades {
            background: white;
        }
        
        .table-grades thead {
            background-color: #667eea;
            color: white;
        }
        
        .table-grades thead th {
            font-weight: 600;
            text-align: center;
            vertical-align: middle;
            padding: 15px 8px;
            font-size: 0.9rem;
        }
        
        .table-grades tbody td {
            text-align: center;
            vertical-align: middle;
            padding: 12px 8px;
        }
        
        .table-grades tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        /* Cột môn học */
        .subject-name {
            font-weight: 600;
            color: #495057;
            text-align: left !important;
            padding-left: 15px !important;
        }
        
        /* Điểm số */
        .grade-cell {
            font-weight: 500;
            color: #212529;
        }
        
        .grade-empty {
            color: #adb5bd;
            font-weight: 400;
        }
        
        /* Điểm trung bình môn */
        .grade-average {
            font-weight: 700;
            font-size: 1.05rem;
        }
        
        /* Xếp loại */
        .rank-excellent {
            color: #28a745;
            font-weight: 700;
        }
        
        .rank-good {
            color: #17a2b8;
            font-weight: 700;
        }
        
        .rank-average {
            color: #ffc107;
            font-weight: 700;
        }
        
        .rank-weak {
            color: #dc3545;
            font-weight: 700;
        }
        
        .rank-none {
            color: #6c757d;
            font-style: italic;
        }
        
        /* Footer tổng kết */
        .table-footer {
            background-color: #e9ecef;
            font-weight: 700;
            font-size: 1.1rem;
        }
        
        .summary-grade {
            color: #667eea;
            font-size: 1.3rem;
        }
        
        /* Dropdown học kỳ */
        .semester-selector {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            margin-bottom: 20px;
        }
        
        .semester-selector label {
            font-weight: 600;
            color: #495057;
            margin-right: 10px;
        }
        
        .semester-selector select {
            min-width: 200px;
        }
        
        /* Alert */
        .alert-custom {
            border-left: 4px solid #667eea;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .table-grades {
                font-size: 0.85rem;
            }
            
            .table-grades thead th,
            .table-grades tbody td {
                padding: 8px 4px;
            }
            
            .page-header {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/header.jsp" />
    
    <div class="container main-content">
        <div class="page-header">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2><i class="bi bi-journal-text"></i> Bảng Điểm Cá Nhân</h2>
                    <p class="mb-0">
                        <i class="bi bi-person-badge"></i> ${hocSinh.hoTen} 
                        <span class="ms-3"><i class="bi bi-bookmark"></i> Lớp: ${hocSinh.tenLop}</span>
                    </p>
                </div>
                <div>
                    <a href="${baseURL}/student/home" class="btn btn-light">
                        <i class="bi bi-house-door"></i> Trang chủ
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="semester-selector">
            <form action="${baseURL}/student/xem-diem" method="GET" class="d-flex align-items-center">
                <label for="maHocKy"><i class="bi bi-calendar-range"></i> Học kỳ:</label>
                <select name="maHocKy" id="maHocKy" class="form-select" onchange="this.form.submit()">
                    <c:forEach var="hk" items="${dsHocKy}">
                        <option value="${hk.maHK}" ${hk.maHK == maHocKyHienTai ? 'selected' : ''}>
                            ${hk.tenHK}
                        </option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <div class="card">
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${empty dsDiem}">
                        <div class="alert alert-info alert-custom m-3">
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
                                        <th style="width: 8%;" title="Điểm Miệng">Miệng</th>
                                        <th style="width: 8%;" title="Điểm 15 phút">15p</th>
                                        <th style="width: 8%;" title="Điểm 1 Tiết">1 Tiết</th>
                                        <th style="width: 8%;" title="Điểm Thi">Thi</th>
                                        <th style="width: 10%;" title="Điểm Trung Bình Môn">ĐTB</th>
                                        <th style="width: 15%;">Xếp loại</th>
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
        
        <div class="card">
            <div class="card-body">
                <h6 class="card-title"><i class="bi bi-info-circle"></i> Ghi chú</h6>
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
    
    <jsp:include page="/WEB-INF/includes/footer.jsp" />
    
    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
