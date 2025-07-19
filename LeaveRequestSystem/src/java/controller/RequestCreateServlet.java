package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

import model.DBConnection;

@WebServlet("/create-request")
public class RequestCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String fromDate = request.getParameter("from_date");
        String toDate = request.getParameter("to_date");
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO LeaveRequests (from_date, to_date, reason, status, created_by) VALUES (?, ?, ?, 'Inprogress', ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(fromDate));
            ps.setDate(2, Date.valueOf(toDate));
            ps.setString(3, reason);
            ps.setInt(4, userId);
            ps.executeUpdate();

            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            throw new ServletException("Lỗi tạo đơn nghỉ phép", e);
        }
    }
}
