package com.student.controller.admin;

import java.io.IOException;
import java.util.List;

import com.student.model.ThongBao;
import com.student.service.ThongBaoService;
import com.student.service.impl.ThongBaoServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Đường dẫn trên trình duyệt
@WebServlet(urlPatterns = { "/admin/thongbao-list", "/admin/thongbao-add", "/admin/thongbao-delete", "/admin/thongbao-edit" })
public class ThongBaoController extends HttpServlet {

    private ThongBaoService service = new ThongBaoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.contains("delete")) {
            // Xử lý xóa
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                service.delete(id);
                req.getSession().setAttribute("message", "Xóa thành công!");
            } catch (Exception e) {
                req.getSession().setAttribute("error", "Lỗi khi xóa!");
            }
            resp.sendRedirect(req.getContextPath() + "/admin/thongbao-list");
        } else {
            // Xử lý hiển thị danh sách (Mặc định)
            showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Để nhận tiếng Việt
        String path = req.getServletPath();
        HttpSession session = req.getSession();       
        // Xử lý thêm mới
        if (path.equals("/admin/thongbao-add")) {
        String tieuDe = req.getParameter("tieuDe");
        String noiDung = req.getParameter("noiDung");
        
        // Mặc định người đăng là Admin (ID=1) vì chưa làm chức năng đăng nhập hoàn chỉnh
        int maNguoiTao = 1; 

        ThongBao tb = new ThongBao();
        tb.setTieuDe(tieuDe);
        tb.setNoiDung(noiDung);
        tb.setMaNguoiTao(maNguoiTao);

        if (service.insert(tb)) {
            req.getSession().setAttribute("message", "Đăng thông báo thành công!");
        } else {
            req.getSession().setAttribute("error", "Đăng thất bại!");
        }
        }
     // --- XỬ LÝ CẬP NHẬT (EDIT) ---
        else if (path.equals("/admin/thongbao-edit")) {
            try {
                // Lấy ID từ input ẩn trong form
                int maTB = Integer.parseInt(req.getParameter("maTB"));
                String tieuDe = req.getParameter("tieuDe");
                String noiDung = req.getParameter("noiDung");

                ThongBao tb = new ThongBao();
                tb.setMaTB(maTB);
                tb.setTieuDe(tieuDe);
                tb.setNoiDung(noiDung);

                if (service.update(tb)) {
                    session.setAttribute("message", "Cập nhật thành công!");
                } else {
                    session.setAttribute("error", "Cập nhật thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Lỗi hệ thống!");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/thongbao-list");
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchKey = req.getParameter("searchKey");
        
        // Xử lý phân trang
        int page = 1;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) { page = 1; }

        // Lấy dữ liệu
        List<ThongBao> list = service.findAll(searchKey, page, 10);
        int totalItems = service.count(searchKey);
        int totalPages = (int) Math.ceil((double) totalItems / 10);

        // Gửi sang JSP (Chú ý tên biến: dsThongBao)
        req.setAttribute("dsThongBao", list);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);

        req.getRequestDispatcher("/WEB-INF/views/admin/thongbao-list.jsp").forward(req, resp);
    }
}