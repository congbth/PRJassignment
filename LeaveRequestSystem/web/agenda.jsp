<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.time.*" %>
<%
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = (String) session.getAttribute("role");
    if (!"Trưởng phòng".equals(role)) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    List<LocalDate> days = (List<LocalDate>) request.getAttribute("days");
    Map<String, Map<LocalDate, Boolean>> agenda = (Map<String, Map<LocalDate, Boolean>>) request.getAttribute("agenda");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch nghỉ của phòng</title>
    <style>
        body { font-family: Arial; background: #eef3f7; padding: 40px; }
        .container { background: white; padding: 30px; max-width: 95%; margin: auto;
            border-radius: 12px; box-shadow: 0 0 15px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #007bff; color: white; }
        .work { background-color: #c8e6c9; }
        .off { background-color: #ffcdd2; }
        form { text-align: center; margin-bottom: 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2 style="text-align:center;">Tình hình nghỉ phòng ban</h2>

    <form method="post" action="agenda">
        Từ ngày:
        <input type="date" name="from_date" required>
        Đến ngày:
        <input type="date" name="to_date" required>
        <input type="submit" value="Xem lịch">
    </form>

    <% if (agenda != null && days != null) { %>
    <table>
        <tr>
            <th>Nhân viên</th>
            <% for (LocalDate d : days) { %>
                <th><%= d.toString() %></th>
            <% } %>
        </tr>
        <% for (Map.Entry<String, Map<LocalDate, Boolean>> entry : agenda.entrySet()) { %>
        <tr>
            <td><%= entry.getKey() %></td>
            <% for (LocalDate d : days) {
                boolean isWorking = entry.getValue().get(d);
            %>
            <td class="<%= isWorking ? "work" : "off" %>">
                <%= isWorking ? "✓" : "✕" %>
            </td>
            <% } %>
        </tr>
        <% } %>
    </table>
    <% } %>
</div>
</body>
</html>
