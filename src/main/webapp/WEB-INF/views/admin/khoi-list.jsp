<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<%-- 
  File này nằm ở: /WEB-INF/views/admin/khoi-list.jsp
  Nhúng header (đường dẫn tuyệt đối từ root)
--%>
<jsp:include page="/WEB-INF/includes/header.jsp" />

<main id="main" class="main">

    <%-- 1. TIÊU ĐỀ TRANG (BREADCRUMBS) --%>
    <div class="pagetitle">
      <h1>Quản lý Khối</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${baseURL}/">Trang chủ</a></li>
          <li class="breadcrumb-item">Cấu hình</li>
          <li class="breadcrumb-item active">Quản lý Khối</li>
        </ol>
      </nav>
    </div>
    <section class="section">
      <div class="row">
        <div class="col-lg-12">

          <div class="card">
            <div class="card-body">
              <h5 class="card-title">
                Danh sách các Khối
                <a href="#" class="btn btn-primary btn-sm float-end" 
				   data-bs-toggle="modal" 
				   data-bs-target="#modalThemMoi">
				    <i class="bi bi-plus-circle"></i> Thêm mới
				</a>
              </h5>
              
              <%-- 2. THANH ĐIỀU KHIỂN (Entries & Search) - CẬP NHẬT --%>
				<div class="row mb-3 align-items-center">
				  <div class="col-md-5 col-lg-4">
				    <%-- Form chọn số lượng (Cập nhật 'action' và 'selected') --%>
				    <form action="${baseURL}/admin/khoi-list" method="GET">
				      <label class="me-2">Hiển thị</label>
				      <select name="entries" class="form-select d-inline-block" style="width: auto;" onchange="this.form.submit()">
				          <%-- Dùng biến 'pageSize' để chọn --%>
				          <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
				          <option value="25" ${pageSize == 25 ? 'selected' : ''}>25</option>
				          <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
				      </select>
				      <span class="ms-1">mục</span>
				      <%-- Luôn gửi kèm 'searchKey' hiện tại khi đổi số lượng --%>
				      <input type="hidden" name="searchKey" value="${param.searchKey}">
				    </form>
				  </div>
				  
				  <div class="col-md-7 col-lg-8 ms-auto">
				      <%-- Form tìm kiếm (Giữ nguyên, không đổi) --%>
				      <form action="${baseURL}/admin/khoi-list" method="GET" class="d-flex justify-content-end">
					    <div class="input-group" style="width: 300px;">
					      <input type="text" 
					             name="searchKey" 
					             class="form-control" 
					             placeholder="Tìm kiếm theo tên khối..." 
					             value="${param.searchKey}">
					      		<button class="btn btn-outline-secondary" type="submit">
					        	<i class="bi bi-search"></i>
					      </button>			      
					    </div> 
					</form>
				  </div>
				</div>

              <%-- 3. BẢNG DỮ LIỆU --%>
              <table class="table table-striped table-hover">
                <thead>
                  <tr>
                    <th scope="col">Mã Khối</th>
                    <th scope="col">Tên Khối</th>
                    <th scope="col">Hiển thị</th> <%-- Cột mới: Toggle --%>
                    <th scope="col">Hành động</th> <%-- Cột mới: Sửa/Xóa --%>
                  </tr>
                </thead>
                <tbody>
                    <%-- Vẫn dùng JSTL để lặp qua 'dsKhoi' mà Controller gửi sang --%>
                    
                    <c:if test="${empty dsKhoi}">
                        <tr>
                            <td colspan="4" class="text-center">Không có dữ liệu nào.</td>
                        </tr>
                    </c:if>

                    <c:forEach var="khoi" items="${dsKhoi}">
                        <tr>
                          <td>${khoi.maKhoi}</td>
                          <td>${khoi.tenKhoi}</td>
                          <td>
                            <%-- 
                              Nút Toggle (chưa có logic) 
                              Giả sử tất cả đang "bật"
                            --%>
                            <div class="form-check form-switch">
                              <input class="form-check-input" type="checkbox" 
                                     id="switch-${khoi.maKhoi}" checked>
                            </div>
                          </td>
                          <td>
                            <button type="button" class="btn btn-warning btn-sm" title="Sửa"
					          data-bs-toggle="modal" 
					          data-bs-target="#modalSua"
					          data-id="${khoi.maKhoi}"
					          data-ten="${khoi.tenKhoi}"> 
					    	  <i class="bi bi-pencil-square"></i>
					  		</button>
                            <a href="${baseURL}/admin/khoi-delete?id=${khoi.maKhoi}" class="btn btn-danger btn-sm" title="Xóa"
                               onclick="return confirm('Bạn có chắc muốn xóa khối ${khoi.tenKhoi} không?');">
                              <i class="bi bi-trash"></i>
                            </a>
                          </td>
                        </tr>
                    </c:forEach>
                </tbody>
              </table>
              <%-- 4. THÔNG TIN PHÂN TRANG & ĐIỀU HƯỚNG - CẬP NHẬT --%>
				<div class="row align-items-center">
				  <div class="col-md-6">
				      <%-- Thông tin hiển thị (Cập nhật) --%>
				      <span class="text-muted">
				          <%-- Tính toán số mục bắt đầu và kết thúc --%>
				          <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
				          <c:set var="endItem" value="${currentPage * pageSize}" />
				          <c:if test="${endItem > totalItems}">
				              <c:set var="endItem" value="${totalItems}" />
				          </c:if>
				          
				          <c:if test="${totalItems > 0}">
				             Hiển thị ${startItem} đến ${endItem} trong ${totalItems} mục
				          </c:if>
				          <c:if test="${totalItems == 0}">
				             Không có mục nào
				          </c:if>
				      </span>
				  </div>
				  <div class="col-md-6">
				      <%-- Các nút phân trang (Cập nhật) --%>
				      <nav aria-label="Page navigation">
				          <ul class="pagination justify-content-end">
				              <%-- Nút TRƯỚC --%>
				              <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
				                <a class="page-link" href="${baseURL}/admin/khoi-list?page=${currentPage - 1}&entries=${pageSize}&searchKey=${param.searchKey}">Trước</a>
				              </li>
				              
				              <%-- 
				                Các nút SỐ TRANG
				                (Chúng ta sẽ dùng một vòng lặp đơn giản từ 1 đến totalPages)
				              --%>
				              <c:forEach var="i" begin="1" end="${totalPages}">
				                  <li class="page-item ${i == currentPage ? 'active' : ''}">
				                    <a class="page-link" href="${baseURL}/admin/khoi-list?page=${i}&entries=${pageSize}&searchKey=${param.searchKey}">${i}</a>
				                  </li>
				              </c:forEach>
				              
				              <%-- Nút SAU --%>
				              <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
				                <a class="page-link" href="${baseURL}/admin/khoi-list?page=${currentPage + 1}&entries=${pageSize}&searchKey=${param.searchKey}">Sau</a>
				              </li>
				          </ul>
				      </nav>
				  </div>
				</div>
              </div>
          </div>

        </div>
      </div>
    </section>
    
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1100">

  <%-- Toast THÀNH CÔNG (Nếu có message) --%>
  <c:if test="${not empty sessionScope.message}">
    <div id="toastSuccess" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
      <div class="toast-header bg-success text-white">
        <i class="bi bi-check-circle-fill me-2"></i>
        <strong class="me-auto">Thành công!</strong>
        <small>Vừa xong</small>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
      <div class="toast-body">
        ${sessionScope.message}
      </div>
    </div>
    <%-- Xóa message khỏi session --%>
    <c:remove var="message" scope="session" />
  </c:if>

  <%-- Toast LỖI (Nếu có error) --%>
  <c:if test="${not empty sessionScope.error}">
    <div id="toastError" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
      <div class="toast-header bg-danger text-white">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <strong class="me-auto">Lỗi!</strong>
        <small>Vừa xong</small>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
      </div>
      <div class="toast-body">
        ${sessionScope.error}
      </div>
    </div>
    <%-- Xóa error khỏi session --%>
    <c:remove var="error" scope="session" />
  </c:if>

</div>
    
    <%-- ====================================================== --%>
<%-- 
  Đây là HTML cho form pop-up.
  Nó bị ẩn theo mặc định và chỉ hiện khi nút "Thêm mới" được click.
--%>
<div class="modal fade" id="modalThemMoi" tabindex="-1" aria-labelledby="modalThemMoiLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <%-- 
        Form sẽ gửi POST request đến Controller.
        action="${baseURL}/admin/khoi-add"
        method="POST"
      --%>
      <form action="${baseURL}/admin/khoi-add" method="POST">
        <div class="modal-header">
          <h5 class="modal-title" id="modalThemMoiLabel">Thêm mới Khối</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        
        <div class="modal-body">
          <%-- Nội dung form --%>
          <div class="row mb-3">
            <label for="tenKhoi" class="col-sm-4 col-form-label">Tên Khối:</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="tenKhoi" name="tenKhoi" 
                     placeholder="Ví dụ: Khối 11" required>
            </div>
          </div>
        </div>
        
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            <i class="bi bi-x-circle"></i> Hủy
          </button>
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-save"></i> Lưu lại	
          </button>
        </div>
      </form>
      
    </div>
  </div>
</div>

<div class="modal fade" id="modalSua" tabindex="-1" aria-labelledby="modalSuaLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">

      <%-- Form sẽ POST đến /admin/khoi-edit --%>
      <form action="${baseURL}/admin/khoi-edit" method="POST">
        <div class="modal-header">
          <h5 class="modal-title" id="modalSuaLabel">Cập nhật Khối</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">

          <%-- 
            Ô QUAN TRỌNG: Ô ẩn để chứa ID. 
            Chúng ta sẽ điền giá trị vào đây bằng JavaScript.
          --%>
          <input type="hidden" id="maKhoi_edit" name="maKhoi_edit">

          <%-- Ô Tên Khối --%>
          <div class="row mb-3">
            <label for="tenKhoi_edit" class="col-sm-4 col-form-label">Tên Khối:</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" id="tenKhoi_edit" name="tenKhoi_edit" required>
            </div>
          </div>

        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            <i class="bi bi-x-circle"></i> Hủy
          </button>
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-save"></i> Lưu thay đổi
          </button>
        </div>
      </form>

    </div>
  </div>
</div>
</main><%-- 
  Nhúng footer (đường dẫn tuyệt đối từ root)
--%>
<script>
  // Chờ cho trang tải xong
  document.addEventListener('DOMContentLoaded', (event) => {
    
    // Tìm Toast thành công
    const toastSuccessEl = document.getElementById('toastSuccess');
    if (toastSuccessEl) {
      // Khởi tạo và hiển thị toast
      const toast = new bootstrap.Toast(toastSuccessEl, {
        delay: 5000 // Tự động ẩn sau 5 giây
      });
      toast.show();
    }
    
    // Tìm Toast lỗi
    const toastErrorEl = document.getElementById('toastError');
    if (toastErrorEl) {
      // Khởi tạo và hiển thị toast
      const toast = new bootstrap.Toast(toastErrorEl, {
        delay: 5000 // Tự động ẩn sau 5 giây
      });
      toast.show();
    }
    
    const modalSua = document.getElementById('modalSua');
    if(modalSua) {
        // Lắng nghe sự kiện "show.bs.modal" (khi modal CHUẨN BỊ hiện)
        modalSua.addEventListener('show.bs.modal', function (event) {

            // 1. Lấy NÚT BẤM đã kích hoạt modal
            const button = event.relatedTarget; 

            // 2. Lấy dữ liệu từ data-attributes của nút bấm
            const maKhoi = button.getAttribute('data-id');
            const tenKhoi = button.getAttribute('data-ten');

            // 3. Tìm các ô input trong Modal
            const inputMaKhoi = modalSua.querySelector('#maKhoi_edit');
            const inputTenKhoi = modalSua.querySelector('#tenKhoi_edit');

            // 4. "Bơm" dữ liệu vào các ô input
            inputMaKhoi.value = maKhoi;
            inputTenKhoi.value = tenKhoi;
        });
    }
  });
</script>
<jsp:include page="/WEB-INF/includes/footer.jsp" />