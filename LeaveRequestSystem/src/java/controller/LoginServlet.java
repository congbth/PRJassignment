package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

import model.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, full_name FROM Users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");

                // Lấy role đầu tiên
                String role = "";
                PreparedStatement roleStmt = conn.prepareStatement(
                    "SELECT r.role_name FROM UserRoles ur JOIN Roles r ON ur.role_id = r.role_id WHERE ur.user_id = ?"
                );
                roleStmt.setInt(1, userId);
                ResultSet roleRs = roleStmt.executeQuery();
                if (roleRs.next()) {
                    role = roleRs.getString("role_name");
                }

                HttpSession session = request.getSession();
                session.setAttribute("user_id", userId);
                session.setAttribute("full_name", fullName);
                session.setAttribute("role", role);

                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("Lỗi đăng nhập", e);
        }
    }
}
