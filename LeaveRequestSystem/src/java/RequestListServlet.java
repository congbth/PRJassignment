package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import model.DBConnection;

@WebServlet("/my-requests")
public class RequestListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        List<Map<String, String>> requests = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT request_id, from_date, to_date, reason, status FROM LeaveRequests WHERE created_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("id", rs.getString("request_id"));
                row.put("from", rs.getString("from_date"));
                row.put("to", rs.getString("to_date"));
                row.put("reason", rs.getString("reason"));
                row.put("status", rs.getString("status"));
                requests.add(row);
            }

            request.setAttribute("requests", requests);
            RequestDispatcher rd = request.getRequestDispatcher("request_list.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi lấy danh sách đơn", e);
        }
    }
}
