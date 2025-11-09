<%-- 
  File này nằm ở root: /webapp/index.jsp
  Nó sẽ là trang chủ của bạn (ví dụ: http://localhost:8080/student-management-j24/)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 
  Nhúng header.jsp
  Đường dẫn TUYỆT ĐỐI bắt đầu từ root (/), trỏ vào thư mục WEB-INF.
--%>
<jsp:include page="/WEB-INF/includes/header.jsp" />


  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Trang chủ</h1>
      <nav>
        <ol class="breadcrumb">
          <%-- 
            Lưu ý: Bạn có thể cần định nghĩa biến 'baseURL' hoặc dùng JSTL 
            để đường dẫn này chính xác. 
            Cách an toàn là: href="${pageContext.request.contextPath}/"
          --%>
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
          <li class="breadcrumb-item active">Tổng quan</li>
        </ol>
      </nav>
    </div><section class="section dashboard">
      <div class="row">

        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Chào mừng đến với Trang quản trị</h5>
              <p>Đây là nội dung của file <code>/index.jsp</code>.</p>
              <p>Các file <code>header.jsp</code> và <code>footer.jsp</code> đã được nhúng thành công từ <code>/WEB-INF/includes/</code>.</p>
            </div>
          </div>
        </div>
        
      </div>
    </section>

  </main><%-- 
  Nhúng footer.jsp
  Đường dẫn TUYỆT ĐỐI.
--%>
 <jsp:include page="/WEB-INF/includes/footer.jsp" />