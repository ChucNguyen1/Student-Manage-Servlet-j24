<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Trang chủ Giáo viên</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${baseURL}/teacher/home">Trang chủ</a></li>
                <li class="breadcrumb-item active">Thông tin giáo viên</li>
            </ol>
        </nav>
    </div>

    <section class="section dashboard">
        <div class="row">

            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thông tin cá nhân</h5>
                        
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle me-1"></i>
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty giaoVien}">
                            <div class="row">
                                <div class="col-md-6">
                                    <table class="table table-borderless">
                                        <tr>
                                            <th width="40%">Mã giáo viên:</th>
                                            <td>${giaoVien.maGV}</td>
                                        </tr>
                                        <tr>
                                            <th>Họ và tên:</th>
                                            <td><strong>${giaoVien.hoTen}</strong></td>
                                        </tr>
                                        <tr>
                                            <th>Ngày sinh:</th>
                                            <td>${giaoVien.ngaySinh}</td>
                                        </tr>
                                        <tr>
                                            <th>Giới tính:</th>
                                            <td>${giaoVien.gioiTinh}</td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="col-md-6">
                                    <table class="table table-borderless">
                                        <tr>
                                            <th width="40%">Số điện thoại:</th>
                                            <td>${giaoVien.sdt}</td>
                                        </tr>
                                        <tr>
                                            <th>Email:</th>
                                            <td>${giaoVien.email}</td>
                                        </tr>
                                        <tr>
                                            <th>Địa chỉ:</th>
                                            <td>${giaoVien.diaChi}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            Lớp được phân công giảng dạy
                            
                            <%-- Dropdown chọn học kỳ --%>
                            <c:if test="${not empty dsHocKy}">
                                <div class="float-end">
                                    <form action="${baseURL}/teacher/home" method="GET" class="d-inline">
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
                        
                        <p class="text-muted">
                            Hiện tại bạn đang được phân công giảng dạy <strong>${soLopDay}</strong> lớp.
                        </p>

                        <c:if test="${not empty dsPhanCong}">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">STT</th>
                                        <th scope="col">Lớp</th>
                                        <th scope="col">Môn học</th>
                                        <th scope="col">Học kỳ</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="pc" items="${dsPhanCong}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td><strong>${pc.tenLop}</strong></td>
                                            <td>${pc.tenMonHoc}</td>
                                            <td>${pc.tenHocKy}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        
                        <c:if test="${empty dsPhanCong}">
                            <div class="alert alert-info">
                                <i class="bi bi-info-circle me-1"></i>
                                Bạn chưa được phân công giảng dạy lớp nào trong học kỳ này.
                            </div>
                        </c:if>

                        <div class="text-center mt-4">
                            <a href="${baseURL}/teacher/lich-day" class="btn btn-primary">
                                <i class="bi bi-calendar3"></i> Xem lịch dạy của tôi
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>

</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
