package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false); // false nếu chưa có session
        if (session != null) {
            session.invalidate(); // Hủy session
        }

        response.sendRedirect("login.jsp"); // Quay về trang đăng nhập
    }
}
