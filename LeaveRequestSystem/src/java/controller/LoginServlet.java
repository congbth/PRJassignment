package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import model.User;
import dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = UserDAO.checkLogin(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);  // Lưu user đã đăng nhập

            response.sendRedirect("home.jsp"); // Trang chính sau login
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu sai!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
