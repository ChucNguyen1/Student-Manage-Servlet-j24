<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Thông tin cá nhân</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/student/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item active">Thông tin cá nhân</li>
      </ol>
    </nav>
  </div>

  <section class="section profile">
    <div class="row">
      <div class="col-xl-4">
        <div class="card">
          <div
            class="card-body profile-card pt-4 d-flex flex-column align-items-center"
          >
            <img
              src="${baseURL}/assets/img/profile-img.jpg"
              alt="Profile"
              class="rounded-circle"
              style="width: 120px; height: 120px"
            />
            <h2 class="mt-3">${hocSinh.hoTen}</h2>
            <h3>Học sinh</h3>
            <div class="mt-2">
              <span class="badge bg-primary">${hocSinh.tenLop}</span>
            </div>
          </div>
        </div>

        <div class="card">
          <div class="card-body pt-3">
            <h5 class="card-title">Thông tin học tập</h5>
            <div class="row mb-2">
              <div class="col-lg-5 col-md-4 label fw-bold">Mã học sinh:</div>
              <div class="col-lg-7 col-md-8">${hocSinh.maHS}</div>
            </div>
            <div class="row mb-2">
              <div class="col-lg-5 col-md-4 label fw-bold">Lớp:</div>
              <div class="col-lg-7 col-md-8">
                ${hocSinh.tenLop != null ? hocSinh.tenLop : 'Chưa xếp lớp'}
              </div>
            </div>
            <div class="row mb-2">
              <div class="col-lg-5 col-md-4 label fw-bold">Trạng thái:</div>
              <div class="col-lg-7 col-md-8">
                <span class="badge bg-success"
                  >${hocSinh.trangThaiHocTap != null ? hocSinh.trangThaiHocTap :
                  'Đang học'}</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xl-8">
        <div class="card">
          <div class="card-body pt-3">
            <ul class="nav nav-tabs nav-tabs-bordered">
              <li class="nav-item">
                <button
                  class="nav-link active"
                  data-bs-toggle="tab"
                  data-bs-target="#profile-overview"
                >
                  Tổng quan
                </button>
              </li>
              <li class="nav-item">
                <button
                  class="nav-link"
                  data-bs-toggle="tab"
                  data-bs-target="#profile-edit"
                >
                  Chỉnh sửa thông tin
                </button>
              </li>
              <li class="nav-item">
                <button
                  class="nav-link"
                  data-bs-toggle="tab"
                  data-bs-target="#profile-change-password"
                >
                  Đổi mật khẩu
                </button>
              </li>
            </ul>

            <div class="tab-content pt-3">
              <!-- Profile Overview -->
              <div
                class="tab-pane fade show active profile-overview"
                id="profile-overview"
              >
                <h5 class="card-title">Thông tin cá nhân</h5>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Họ và tên:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.hoTen}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Ngày sinh:</div>
                  <div class="col-lg-9 col-md-8">
                    <fmt:formatDate
                      value="${hocSinh.ngaySinh}"
                      pattern="dd/MM/yyyy"
                    />
                  </div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Giới tính:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.gioiTinh}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Nơi sinh:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.noiSinh}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Dân tộc:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.danToc}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Tôn giáo:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.tonGiao}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Email:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.email}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">
                    Số điện thoại:
                  </div>
                  <div class="col-lg-9 col-md-8">${hocSinh.sdtCaNhan}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Địa chỉ:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.diaChi}</div>
                </div>

                <h5 class="card-title mt-4">Thông tin gia đình</h5>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Họ tên cha:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.hoTenCha}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">
                    Nghề nghiệp:
                  </div>
                  <div class="col-lg-9 col-md-8">${hocSinh.ngheNghiepCha}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">SĐT cha:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.sdtCha}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">Họ tên mẹ:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.hoTenMe}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">
                    Nghề nghiệp:
                  </div>
                  <div class="col-lg-9 col-md-8">${hocSinh.ngheNghiepMe}</div>
                </div>

                <div class="row mb-2">
                  <div class="col-lg-3 col-md-4 label fw-bold">SĐT mẹ:</div>
                  <div class="col-lg-9 col-md-8">${hocSinh.sdtMe}</div>
                </div>
              </div>

              <!-- Edit Profile -->
              <div class="tab-pane fade profile-edit pt-3" id="profile-edit">
                <c:if test="${not empty successMessage}">
                  <div
                    class="alert alert-success alert-dismissible fade show"
                    role="alert"
                  >
                    <i class="bi bi-check-circle me-1"></i>
                    ${successMessage}
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="alert"
                      aria-label="Close"
                    ></button>
                  </div>
                </c:if>

                <c:if test="${not empty errorMessage}">
                  <div
                    class="alert alert-danger alert-dismissible fade show"
                    role="alert"
                  >
                    <i class="bi bi-exclamation-octagon me-1"></i>
                    ${errorMessage}
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="alert"
                      aria-label="Close"
                    ></button>
                  </div>
                </c:if>

                <form method="post" action="${baseURL}/student/profile">
                  <div class="row mb-3">
                    <label for="email" class="col-md-4 col-lg-3 col-form-label"
                      >Email</label
                    >
                    <div class="col-md-8 col-lg-9">
                      <input
                        name="email"
                        type="email"
                        class="form-control"
                        id="email"
                        value="${hocSinh.email}"
                        required
                      />
                    </div>
                  </div>

                  <div class="row mb-3">
                    <label
                      for="sdtCaNhan"
                      class="col-md-4 col-lg-3 col-form-label"
                      >Số điện thoại</label
                    >
                    <div class="col-md-8 col-lg-9">
                      <input
                        name="sdtCaNhan"
                        type="text"
                        class="form-control"
                        id="sdtCaNhan"
                        value="${hocSinh.sdtCaNhan}"
                        pattern="[0-9]{10,11}"
                      />
                      <small class="text-muted">Nhập 10-11 chữ số</small>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <label for="diaChi" class="col-md-4 col-lg-3 col-form-label"
                      >Địa chỉ</label
                    >
                    <div class="col-md-8 col-lg-9">
                      <textarea
                        name="diaChi"
                        class="form-control"
                        id="diaChi"
                        rows="3"
                      >
${hocSinh.diaChi}</textarea
                      >
                    </div>
                  </div>

                  <div class="text-center">
                    <button type="submit" class="btn btn-primary">
                      <i class="bi bi-save me-1"></i>Lưu thay đổi
                    </button>
                  </div>
                </form>
              </div>

              <!-- Change Password -->
              <div class="tab-pane fade pt-3" id="profile-change-password">
                <p class="text-muted">
                  Để đổi mật khẩu, vui lòng truy cập trang
                  <a href="${baseURL}/student/change-password">Đổi mật khẩu</a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />
