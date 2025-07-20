package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import model.DBConnection;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Giao diện chọn ngày
        request.getRequestDispatcher("agenda.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        int leaderId = (int) request.getSession().getAttribute("user_id");

        LocalDate from = LocalDate.parse(request.getParameter("from_date"));
        LocalDate to = LocalDate.parse(request.getParameter("to_date"));

        Map<String, Map<LocalDate, Boolean>> agenda = new LinkedHashMap<>();
        List<LocalDate> days = new ArrayList<>();
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            days.add(date);
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Lấy department_id của trưởng phòng
            PreparedStatement deptStmt = conn.prepareStatement("SELECT department_id FROM Users WHERE user_id = ?");
            deptStmt.setInt(1, leaderId);
            ResultSet deptRs = deptStmt.executeQuery();
            int deptId = deptRs.next() ? deptRs.getInt(1) : -1;

            // Lấy danh sách nhân viên trong phòng
            String userSql = "SELECT user_id, full_name FROM Users WHERE department_id = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setInt(1, deptId);
            ResultSet userRs = userStmt.executeQuery();

            while (userRs.next()) {
                int uid = userRs.getInt("user_id");
                String name = userRs.getString("full_name");

                Map<LocalDate, Boolean> map = new LinkedHashMap<>();
                for (LocalDate d : days) map.put(d, true); // true = đi làm

                // Kiểm tra đơn nghỉ được duyệt của user này
                String reqSql = "SELECT from_date, to_date FROM LeaveRequests WHERE created_by = ? AND status = 'Approved'";
                PreparedStatement reqStmt = conn.prepareStatement(reqSql);
                reqStmt.setInt(1, uid);
                ResultSet reqRs = reqStmt.executeQuery();

                while (reqRs.next()) {
                    LocalDate f = reqRs.getDate(1).toLocalDate();
                    LocalDate t = reqRs.getDate(2).toLocalDate();
                    for (LocalDate d : days) {
                        if (!d.isBefore(f) && !d.isAfter(t)) {
                            map.put(d, false); // nghỉ
                        }
                    }
                }

                agenda.put(name, map);
            }

            request.setAttribute("agenda", agenda);
            request.setAttribute("days", days);
            request.getRequestDispatcher("agenda.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Lỗi xử lý Agenda", e);
        }
    }
}
