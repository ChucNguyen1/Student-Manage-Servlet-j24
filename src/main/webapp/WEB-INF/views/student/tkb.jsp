<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Thời khóa biểu</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${baseURL}/student/home">Trang chủ</a></li>
                <li class="breadcrumb-item active">Thời khóa biểu</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <i class="bi bi-calendar3 text-primary"></i>
                            Lịch học của lớp: <strong class="text-primary">${tenLop}</strong>

                            <div class="float-end">
                                <form action="${baseURL}/student/tkb" method="GET" class="d-inline">
                                    <label class="me-2">Năm học:</label>
                                    <select name="maNH" class="form-select d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                        <c:forEach var="nh" items="${dsNamHoc}">
                                            <option value="${nh.maNH}" ${nh.maNH == maNHHienTai ? 'selected' : ''}>
                                                ${nh.tenNH}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    
                                    <c:if test="${not empty dsHocKy}">
                                        <label class="ms-3 me-2">Học kỳ:</label>
                                        <select name="maHocKy" class="form-select d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                            <c:forEach var="hk" items="${dsHocKy}">
                                                <option value="${hk.maHK}" ${hk.maHK == maHocKyHienTai ? 'selected' : ''}>
                                                    ${hk.tenHK}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </form>
                            </div>
                        </h5>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle me-1"></i>
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <div class="alert alert-info">
                            <i class="bi bi-person-badge me-1"></i>
                            <strong>Thông tin:</strong>
                            <span class="ms-2">
                                Họ tên: <strong>${hocSinh.hoTen}</strong> | 
                                Lớp: <strong>${tenLop}</strong>
                            </span>
                        </div>

                        <div class="table-responsive">
                            <table class="table table-bordered text-center">
                                <thead class="table-primary">
                                    <tr>
                                        <th style="width: 8%;">Tiết</th>
                                        <th style="width: 15%;">Thứ Hai</th>
                                        <th style="width: 15%;">Thứ Ba</th>
                                        <th style="width: 15%;">Thứ Tư</th>
                                        <th style="width: 15%;">Thứ Năm</th>
                                        <th style="width: 15%;">Thứ Sáu</th>
                                        <th style="width: 15%;">Thứ Bảy</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="tiet" begin="1" end="12">
                                        <tr>
                                            <td class="align-middle">
                                                <strong>Tiết ${tiet}</strong>
                                                <br/>
                                                <small class="text-muted">
                                                    <c:choose>
                                                        <c:when test="${tiet == 1}">7:00-8:00</c:when>
                                                        <c:when test="${tiet == 2}">8:00-9:00</c:when>
                                                        <c:when test="${tiet == 3}">9:00-10:00</c:when>
                                                        <c:when test="${tiet == 4}">10:00-11:00</c:when>
                                                        <c:when test="${tiet == 5}">11:00-12:00</c:when>
                                                        <c:when test="${tiet == 6}">13:00-14:00</c:when>
                                                        <c:when test="${tiet == 7}">14:00-15:00</c:when>
                                                        <c:when test="${tiet == 8}">15:00-16:00</c:when>
                                                        <c:when test="${tiet == 9}">16:00-17:00</c:when>
                                                        <c:when test="${tiet == 10}">17:00-18:00</c:when>
                                                        <c:when test="${tiet == 11}">18:00-19:00</c:when>
                                                        <c:when test="${tiet == 12}">19:00-20:00</c:when>
                                                    </c:choose>
                                                </small>
                                            </td>

                                            <c:forEach var="thu" begin="2" end="7">
                                                <td class="align-middle">
                                                    <%-- Tìm tiết học tương ứng trong dsTKB --%>
                                                    <c:set var="found" value="false" />
                                                    <c:forEach var="tkb" items="${dsTKB}">
                                                        <c:if test="${tkb.thu == thu && tkb.tiet == tiet}">
                                                            <c:set var="found" value="true" />
                                                            <div class="p-2 bg-success bg-opacity-10 border border-success rounded">
                                                                <strong class="text-success">${tkb.tenMonHoc}</strong><br/>
                                                                <small class="text-muted">
                                                                    <i class="bi bi-person"></i> ${tkb.tenGiaoVien}
                                                                </small>
                                                                <c:if test="${not empty tkb.phongHoc}">
                                                                    <br/><small class="text-muted">
                                                                        <i class="bi bi-door-closed"></i> ${tkb.phongHoc}
                                                                    </small>
                                                                </c:if>
                                                            </div>
                                                        </c:if>
                                                    </c:forEach>

                                                    <c:if test="${!found}">
                                                        <span class="text-muted">---</span>
                                                    </c:if>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <c:if test="${empty dsTKB}">
                            <div class="alert alert-warning mt-3">
                                <i class="bi bi-exclamation-circle me-1"></i>
                                Lớp của bạn chưa có lịch học trong học kỳ này.
                            </div>
                        </c:if>

                        <div class="mt-4">
                            <h6><i class="bi bi-info-circle text-primary"></i> Ghi chú:</h6>
                            <ul class="mb-0">
                                <li>Lịch học có thể thay đổi, vui lòng theo dõi thông báo từ nhà trường</li>
                                <li>Nếu có thắc mắc, vui lòng liên hệ với giáo viên chủ nhiệm</li>
                                <li>Thời gian mỗi tiết học: 45 phút</li>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>

</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />

<style>
/* Highlight current time slot */
.current-time {
    background-color: rgba(255, 193, 7, 0.1);
    border-left: 3px solid #ffc107;
}

/* Hover effect */
.table-bordered td:hover {
    background-color: rgba(13, 110, 253, 0.05);
}

/* Responsive */
@media (max-width: 768px) {
    .table-responsive {
        font-size: 0.85rem;
    }
    
    .table-bordered th,
    .table-bordered td {
        padding: 0.5rem 0.25rem;
    }
}
</style>
