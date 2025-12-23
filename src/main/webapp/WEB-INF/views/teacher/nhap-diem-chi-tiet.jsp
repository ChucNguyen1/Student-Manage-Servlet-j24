<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/header.jsp" />
<jsp:include page="/WEB-INF/includes/sidebar.jsp" />

<link href="${baseURL}/assets/css/nhap-diem.css" rel="stylesheet" />

<main id="main" class="main">
  <div class="pagetitle">
    <h1>Nhập điểm chi tiết</h1>
    <nav>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="${baseURL}/teacher/home">Trang chủ</a>
        </li>
        <li class="breadcrumb-item">
          <a href="${baseURL}/teacher/danh-sach-lop?maHocKy=${maHocKy}"
            >Danh sách lớp</a
          >
        </li>
        <li class="breadcrumb-item active">Nhập điểm</li>
      </ol>
    </nav>
  </div>

  <section class="section">
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              <i class="bi bi-pencil-square text-primary"></i>
              Nhập điểm cho lớp:
              <strong class="text-primary">${lopHoc.tenLop}</strong> - Môn:
              <strong class="text-success">${monHoc.tenMH}</strong>
            </h5>

            <c:if test="${not empty error}">
              <div
                class="alert alert-danger alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-exclamation-triangle me-1"></i>
                ${error}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                ></button>
              </div>
            </c:if>

            <c:if test="${not empty sessionScope.success}">
              <div
                class="alert alert-success alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-check-circle me-1"></i>
                ${sessionScope.success}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                ></button>
              </div>
              <c:remove var="success" scope="session" />
            </c:if>

            <c:if test="${not empty sessionScope.warning}">
              <div
                class="alert alert-warning alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-exclamation-circle me-1"></i>
                ${sessionScope.warning}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                ></button>
              </div>
              <c:remove var="warning" scope="session" />
            </c:if>

            <div class="alert alert-info">
              <i class="bi bi-info-circle me-1"></i>
              <strong>Hướng dẫn:</strong>
              <ul class="mb-0 mt-2">
                <li>
                  Nhập điểm vào các ô tương ứng (0-10, cho phép 1 chữ số thập
                  phân)
                </li>
                <li>
                  <strong>Điểm Trung Bình (ĐTB)</strong> sẽ tự động tính khi bạn
                  nhập điểm
                </li>
                <li>
                  Công thức:
                  <code>ĐTB = (Miệng + 15p + 1Tiết×2 + Thi×3) ÷ 7</code>
                </li>
                <li>
                  Sau khi nhập xong, bấm nút <strong>"Lưu tất cả"</strong> ở
                  cuối trang
                </li>
              </ul>
            </div>

            <c:if test="${not empty dsBangDiem}">
              <form
                action="${baseURL}/teacher/nhap-diem-chi-tiet"
                method="POST"
                id="formNhapDiem"
              >
                <input type="hidden" name="maLop" value="${maLop}" />
                <input type="hidden" name="maMonHoc" value="${maMonHoc}" />
                <input type="hidden" name="maHocKy" value="${maHocKy}" />

                <div class="table-responsive">
                  <table class="table table-bordered table-hover align-middle">
                    <thead class="table-primary text-center">
                      <tr>
                        <th style="width: 5%">STT</th>
                        <th style="width: 25%">Họ và Tên</th>
                        <th style="width: 12%">
                          Miệng<br /><small class="text-muted">(hs1)</small>
                        </th>
                        <th style="width: 12%">
                          15 Phút<br /><small class="text-muted">(hs1)</small>
                        </th>
                        <th style="width: 12%">
                          1 Tiết<br /><small class="text-muted">(hs2)</small>
                        </th>
                        <th style="width: 12%">
                          Thi<br /><small class="text-muted">(hs3)</small>
                        </th>
                        <th style="width: 15%">
                          ĐTB<br /><small class="text-muted">(Tự động)</small>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach
                        var="hs"
                        items="${dsBangDiem}"
                        varStatus="status"
                      >
                        <tr>
                          <td class="text-center">${status.index + 1}</td>

                          <td>
                            <strong>${hs.tenHocSinh}</strong>
                            <%-- Hidden field chứa maHS --%>
                            <input
                              type="hidden"
                              name="maHS"
                              value="${hs.maHS}"
                            />
                          </td>

                          <td>
                            <input
                              type="number"
                              class="form-control form-control-sm text-center"
                              id="mieng_${hs.maHS}"
                              name="mieng_${hs.maHS}"
                              value="${hs.diemMieng1 != null ? hs.diemMieng1 : ''}"
                              min="0"
                              max="10"
                              step="0.1"
                              placeholder="0-10"
                            />
                          </td>

                          <td>
                            <input
                              type="number"
                              class="form-control form-control-sm text-center"
                              id="p15_${hs.maHS}"
                              name="p15_${hs.maHS}"
                              value="${hs.diem15p1 != null ? hs.diem15p1 : ''}"
                              min="0"
                              max="10"
                              step="0.1"
                              placeholder="0-10"
                            />
                          </td>

                          <td>
                            <input
                              type="number"
                              class="form-control form-control-sm text-center"
                              id="tiet1_${hs.maHS}"
                              name="tiet1_${hs.maHS}"
                              value="${hs.diem1Tiet1 != null ? hs.diem1Tiet1 : ''}"
                              min="0"
                              max="10"
                              step="0.1"
                              placeholder="0-10"
                            />
                          </td>

                          <td>
                            <input
                              type="number"
                              class="form-control form-control-sm text-center"
                              id="thi_${hs.maHS}"
                              name="thi_${hs.maHS}"
                              value="${hs.diemThi != null ? hs.diemThi : ''}"
                              min="0"
                              max="10"
                              step="0.1"
                              placeholder="0-10"
                            />
                          </td>

                          <td class="text-center">
                            <span id="dtb_${hs.maHS}" class="dtb-display">
                              ${hs.diemTBM != null ? hs.diemTBM : '---'}
                            </span>
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>

                <div
                  class="d-flex justify-content-between align-items-center mt-3"
                >
                  <a
                    href="${baseURL}/teacher/danh-sach-lop?maHocKy=${maHocKy}"
                    class="btn btn-secondary"
                  >
                    <i class="bi bi-arrow-left"></i> Quay lại
                  </a>

                  <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-save"></i> Lưu tất cả
                  </button>
                </div>
              </form>
            </c:if>

            <c:if test="${empty dsBangDiem}">
              <div class="alert alert-warning">
                <i class="bi bi-exclamation-triangle me-1"></i>
                Lớp này chưa có học sinh nào.
              </div>
              <a
                href="${baseURL}/teacher/danh-sach-lop?maHocKy=${maHocKy}"
                class="btn btn-secondary"
              >
                <i class="bi bi-arrow-left"></i> Quay lại
              </a>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </section>
</main>

<jsp:include page="/WEB-INF/includes/footer.jsp" />

<script>
  function tinhDiem(maHS) {
    var mieng = parseFloat(document.getElementById("mieng_" + maHS).value) || 0;
    var p15 = parseFloat(document.getElementById("p15_" + maHS).value) || 0;
    var tiet1 = parseFloat(document.getElementById("tiet1_" + maHS).value) || 0;
    var thi = parseFloat(document.getElementById("thi_" + maHS).value) || 0;

    var thiInput = document.getElementById("thi_" + maHS).value.trim();
    if (thiInput === "") {
      document.getElementById("dtb_" + maHS).textContent = "---";
      document.getElementById("dtb_" + maHS).classList.remove("text-success");
      document.getElementById("dtb_" + maHS).classList.add("text-muted");
      return;
    }

    var dtb = (mieng + p15 + tiet1 * 2 + thi * 3) / 7;

    dtb = Math.round(dtb * 100) / 100;

    var dtbElement = document.getElementById("dtb_" + maHS);
    dtbElement.textContent = dtb.toFixed(2);

    dtbElement.classList.remove(
      "text-primary",
      "text-success",
      "text-warning",
      "text-danger",
      "text-muted"
    );
    if (dtb >= 8.0) {
      dtbElement.classList.add("text-success");
    } else if (dtb >= 6.5) {
      dtbElement.classList.add("text-primary");
    } else if (dtb >= 5.0) {
      dtbElement.classList.add("text-warning");
    } else {
      dtbElement.classList.add("text-danger");
    }
  }

  document.addEventListener("DOMContentLoaded", function () {
    var maHSInputs = document.querySelectorAll('input[name="maHS"]');

    maHSInputs.forEach(function (input) {
      var maHS = input.value;
      tinhDiem(maHS);
    });
  });

  document
    .getElementById("formNhapDiem")
    ?.addEventListener("submit", function (e) {
      var confirmed = confirm(
        "Bạn có chắc chắn muốn lưu điểm cho tất cả học sinh?"
      );
      if (!confirmed) {
        e.preventDefault();
      }
    });
</script>

<script src="${baseURL}/assets/js/nhap-diem.js"></script>
