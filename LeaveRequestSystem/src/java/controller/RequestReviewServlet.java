package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import model.DBConnection;

@WebServlet("/review-requests")
public class RequestReviewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"Trưởng phòng".equals(role)) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        int managerId = (int) session.getAttribute("user_id");
        List<Map<String, String>> requests = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
                SELECT r.request_id, r.from_date, r.to_date, r.reason, r.status,
                       u.full_name AS employee_name
                FROM LeaveRequests r
                JOIN Users u ON r.created_by = u.user_id
                WHERE r.created_by IN (
                    SELECT user_id FROM Users WHERE department_id = (
                        SELECT department_id FROM Users WHERE user_id = ?
                    ) AND user_id != ?
                ) AND r.status = 'Inprogress'
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, managerId);
            ps.setInt(2, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("id", rs.getString("request_id"));
                row.put("from", rs.getString("from_date"));
                row.put("to", rs.getString("to_date"));
                row.put("reason", rs.getString("reason"));
                row.put("status", rs.getString("status"));
                row.put("employee", rs.getString("employee_name"));
                requests.add(row);
            }

            request.setAttribute("requests", requests);
            request.getRequestDispatcher("request_review.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi lấy đơn của cấp dưới", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"Trưởng phòng".equals(role)) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        int requestId = Integer.parseInt(request.getParameter("request_id"));
        String action = request.getParameter("action");
        int managerId = (int) session.getAttribute("user_id");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE LeaveRequests SET status = ?, processed_by = ? WHERE request_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, action.equals("approve") ? "Approved" : "Rejected");
            ps.setInt(2, managerId);
            ps.setInt(3, requestId);
            ps.executeUpdate();

            response.sendRedirect("review-requests");

        } catch (Exception e) {
            throw new ServletException("Lỗi khi duyệt đơn", e);
        }
    }
}
