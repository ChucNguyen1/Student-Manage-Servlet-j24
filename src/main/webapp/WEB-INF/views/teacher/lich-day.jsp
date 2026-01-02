<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.css" rel="stylesheet" />

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Lịch dạy của tôi</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${baseURL}/teacher/home">Trang chủ</a></li>
                <li class="breadcrumb-item active">Lịch dạy</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            Lịch dạy
                            
                            <%-- Dropdown chọn năm học và học kỳ --%>
                            <div class="float-end">
                                <form action="${baseURL}/teacher/lich-day" method="GET" class="d-inline" id="filterForm">
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

                        <c:if test="${empty dsTKB}">
                            <div class="alert alert-info mt-3">
                                <i class="bi bi-info-circle me-1"></i>
                                Bạn chưa có lịch dạy nào trong học kỳ này.
                            </div>
                        </c:if>
                        <div id="calendar"></div>

                    </div>
                </div>
            </div>
        </div>
    </section>

</main>

<!-- Modal chi tiết lịch dạy -->
<div class="modal fade" id="scheduleDetailModal" tabindex="-1" aria-labelledby="scheduleDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="scheduleDetailModalLabel">
                    <i class="bi bi-calendar-event"></i> Chi tiết lịch dạy
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row mb-3">
                    <div class="col-4 fw-bold">Lớp:</div>
                    <div class="col-8" id="detailLop"></div>
                </div>
                <div class="row mb-3">
                    <div class="col-4 fw-bold">Môn học:</div>
                    <div class="col-8" id="detailMonHoc"></div>
                </div>
                <div class="row mb-3">
                    <div class="col-4 fw-bold">Phòng học:</div>
                    <div class="col-8" id="detailPhongHoc"></div>
                </div>
                <div class="row mb-3">
                    <div class="col-4 fw-bold">Thời gian:</div>
                    <div class="col-8" id="detailThoiGian"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle"></i> Đóng
                </button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/includes/footer.jsp" />


<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js"></script>


<script type="application/json" id="scheduleData">
[
<c:forEach var="tkb" items="${dsTKB}" varStatus="status">
{"thu":${tkb.thu},"tiet":${tkb.tiet},"tenLop":"${tkb.tenLop}","tenMonHoc":"${tkb.tenMonHoc}","phongHoc":"${not empty tkb.phongHoc ? tkb.phongHoc : ''}"}<c:if test="${!status.last}">,</c:if>
</c:forEach>
]
</script>

<script>

var rawData = JSON.parse(document.getElementById('scheduleData').textContent);

window.teacherScheduleData = rawData.map(function(item) {
    var tiet = parseInt(item.tiet);
    var startHour = 7 + (tiet - 1);
    var endHour = startHour + 1;
    
    return {
        title: item.tenLop + ' - ' + item.tenMonHoc,
        daysOfWeek: [parseInt(item.thu) - 1],
        startTime: (startHour < 10 ? '0' : '') + startHour + ':00:00',
        endTime: (endHour < 10 ? '0' : '') + endHour + ':00:00',
        extendedProps: {
            phongHoc: item.phongHoc || 'Chưa xác định',
            tenLop: item.tenLop,
            tenMonHoc: item.tenMonHoc
        },
        color: '#0d6efd'
    };
});
</script>

<script src="${baseURL}/assets/js/teacher-schedule.js"></script>
