<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<style>
  body {
    display: flex !important;
    flex-direction: column !important;
    min-height: 100vh;
  }

  #main {
    flex-grow: 1 !important;
  }
</style>

<!-- ======= Footer ======= -->
<footer id="footer" class="footer">
  <div class="copyright">
    &copy; Copyright <strong><span>Quản lý Học sinh</span></strong>. All Rights Reserved
  </div>
  <div class="credits">
    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
  </div>
</footer>

<!-- JS Files -->
<script src="${baseURL}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${baseURL}/assets/js/main.js"></script>

<!-- Chatbox Widget - Chỉ hiển thị cho học sinh -->
<c:if test="${sessionScope.role == 'HOCSINH'}">
  <link rel="stylesheet" href="${baseURL}/assets/css/chatbox.css">
  <script src="${baseURL}/assets/js/chatbox.js"></script>
</c:if>

</body>
</html>

