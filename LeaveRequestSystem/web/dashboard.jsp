<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String fullName = (String) session.getAttribute("full_name");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial;
            background: #f5f7fa;
            padding: 40px;
        }

        .container {
            background: white;
            padding: 30px;
            max-width: 600px;
            margin: auto;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }

        h2 {
            color: #333;
            text-align: center;
        }

        ul {
            list-style: none;
            padding: 0;
            margin-top: 20px;
        }

        li {
            margin: 12px 0;
            text-align: center;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        a:hover {
            background: #0056b3;
        }

        .logout {
            text-align: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Xin chào, <%= fullName %></h2>
    <p style="text-align:center;">Vai trò: <strong><%= role %></strong></p>

    <ul>
        <%-- Nhân viên và Trưởng nhóm có quyền tạo và xem đơn của mình --%>
        <% if ("Nhân viên".equals(role) || "Trưởng nhóm".equals(role)) { %>
            <li><a href="request_form.jsp">Tạo đơn nghỉ phép</a></li>
            <li><a href="my-requests">Xem đơn của tôi</a></li>
        <% } %>

        <%-- Trưởng nhóm có thể duyệt đơn của nhân viên dưới quyền --%>
        <% if ("Trưởng nhóm".equals(role)) { %>
            <li><a href="review-requests">Xét duyệt đơn của cấp dưới</a></li>
        <% } %>

        <%-- Trưởng phòng có toàn quyền --%>
        <% if ("Trưởng phòng".equals(role)) { %>
            <li><a href="review-requests">Xét duyệt đơn của phòng</a></li>
            <li><a href="agenda">Xem tình hình nghỉ toàn phòng</a></li>
        <% } %>
    </ul>

    <div class="logout">
        <a href="logout.jsp" style="background:#dc3545;">Đăng xuất</a>
    </div>
</div>
</body>
</html>
