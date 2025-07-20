<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = (String) session.getAttribute("role");
    if (!"Trưởng nhóm".equals(role) && !"Trưởng phòng".equals(role)) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    List<Map<String, String>> requests = (List<Map<String, String>>) request.getAttribute("requests");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xét duyệt đơn nghỉ phép</title>
    <style>
        body { font-family: Arial; padding: 40px; background: #f0f4f8; }
        table {
            width: 90%;
            margin: auto;
            background: white;
            border-collapse: collapse;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #eee;
            text-align: center;
        }
        th { background: #007bff; color: white; }
        tr:hover { background-color: #f1f1f1; }
        form { display: flex; gap: 10px; justify-content: center; }
        input[type="submit"] {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .approve { background: #28a745; color: white; }
        .reject { background: #dc3545; color: white; }
    </style>
</head>
<body>
<h2 style="text-align:center;">Xét duyệt đơn nghỉ phép</h2>
<table>
    <tr>
        <th>Nhân viên</th>
        <th>Từ ngày</th>
        <th>Đến ngày</th>
        <th>Lý do</th>
        <th>Trạng thái</th>
        <th>Duyệt</th>
    </tr>
    <%
        if (requests != null) {
            for (Map<String, String> req : requests) {
    %>
    <tr>
        <td><%= req.get("employee") %></td>
        <td><%= req.get("from") %></td>
        <td><%= req.get("to") %></td>
        <td><%= req.get("reason") %></td>
        <td><%= req.get("status") %></td>
        <td>
            <% if ("Inprogress".equals(req.get("status"))) { %>
            <form method="post" action="review-requests">
                <input type="hidden" name="request_id" value="<%= req.get("id") %>">
                <input type="submit" name="action" value="approve" class="approve">
                <input type="submit" name="action" value="reject" class="reject">
            </form>
            <% } else { %>
                (Đã xử lý)
            <% } %>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
