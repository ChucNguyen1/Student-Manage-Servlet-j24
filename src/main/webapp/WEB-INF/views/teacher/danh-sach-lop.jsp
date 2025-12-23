<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Danh sách lớp phụ trách</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${baseURL}/teacher/home">Trang chủ</a></li>
                <li class="breadcrumb-item active">Danh sách lớp</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            Chọn lớp để nhập điểm

                            <c:if test="${not empty dsHocKy}">
                                <div class="float-end">
                                    <form action="${baseURL}/teacher/danh-sach-lop" method="GET" class="d-inline">
                                        <label class="me-2">Học kỳ:</label>
                                        <select name="maHocKy" class="form-select d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                            <c:forEach var="hk" items="${dsHocKy}">
                                                <option value="${hk.maHK}" ${hk.maHK == maHocKyHienTai ? 'selected' : ''}>
                                                    ${hk.tenHK}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </form>
                                </div>
                            </c:if>
                        </h5>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle me-1"></i>
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <p class="text-muted">
                            <i class="bi bi-info-circle"></i>
                            Bạn đang phụ trách <strong>${soLopDay}</strong> lớp trong học kỳ này. 
                            Chọn lớp để nhập điểm cho học sinh.
                        </p>

                        <c:if test="${not empty dsPhanCong}">
                            <div class="row">
                                <c:forEach var="pc" items="${dsPhanCong}" varStatus="status">
                                    <div class="col-xl-4 col-md-6 mb-4">
                                        <div class="card border-start border-primary border-4 shadow-sm h-100 hover-card">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-start mb-3">
                                                    <h5 class="card-title mb-0">
                                                        <i class="bi bi-book-fill text-primary"></i>
                                                        <strong>${pc.tenLop}</strong>
                                                    </h5>
                                                    <span class="badge bg-primary">${pc.tenHocKy}</span>
                                                </div>
                                                
                                                <div class="mb-3">
                                                    <div class="d-flex align-items-center">
                                                        <div class="flex-shrink-0">
                                                            <div class="avatar-icon bg-primary bg-opacity-10 rounded-circle p-3">
                                                                <i class="bi bi-journal-text text-primary fs-4"></i>
                                                            </div>
                                                        </div>
                                                        <div class="flex-grow-1 ms-3">
                                                            <h6 class="mb-1">${pc.tenMonHoc}</h6>
                                                            <small class="text-muted">
                                                                <i class="bi bi-mortarboard"></i> Môn học phụ trách
                                                            </small>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="mt-auto">
                                                    <a href="${baseURL}/teacher/nhap-diem-chi-tiet?maLop=${pc.maLop}&maMon=${pc.maMonHoc}&maHocKy=${maHocKyHienTai}" 
                                                       class="btn btn-primary w-100 btn-hover">
                                                        <i class="bi bi-pencil-square"></i> Nhập điểm
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <c:if test="${not empty dsPhanCong}">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead class="table-light">
                                        <tr>
                                            <th scope="col">STT</th>
                                            <th scope="col">Mã Lớp</th>
                                            <th scope="col">Tên Lớp</th>
                                            <th scope="col">Môn Học</th>
                                            <th scope="col">Học Kỳ</th>
                                            <th scope="col" class="text-center">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="pc" items="${dsPhanCong}" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${pc.maLop}</td>
                                                <td><strong>${pc.tenLop}</strong></td>
                                                <td>${pc.tenMonHoc}</td>
                                                <td>${pc.tenHocKy}</td>
                                                <td class="text-center">
                                                    <a href="${baseURL}/teacher/nhap-diem-chi-tiet?maLop=${pc.maLop}&maMon=${pc.maMonHoc}&maHocKy=${maHocKyHienTai}" 
                                                       class="btn btn-primary btn-sm">
                                                        <i class="bi bi-pencil-square"></i> Nhập điểm
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                        --%>

                        <c:if test="${empty dsPhanCong}">
                            <div class="alert alert-info">
                                <i class="bi bi-info-circle me-1"></i>
                                Bạn chưa được phân công giảng dạy lớp nào trong học kỳ này.
                            </div>
                        </c:if>

                    </div>
                </div>
            </div>
        </div>
    </section>

</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />

<style>
.hover-card {
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.hover-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;
}

/* Avatar icon */
.avatar-icon {
    width: 60px;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* Button hover effect */
.btn-hover {
    transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-hover:hover {
    transform: scale(1.02);
}
</style>
