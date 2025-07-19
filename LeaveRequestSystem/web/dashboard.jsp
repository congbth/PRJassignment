<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" %>
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
        body { font-family: Arial; padding: 50px; background: #f8f9fa; }
        .box {
            background: white; padding: 30px;
            max-width: 500px; margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 { color: #333; }
    </style>
</head>
<body>
<div class="box">
    <h2>Chào, <%= fullName %></h2>
    <p>Vai trò của bạn: <strong><%= role %></strong></p>
    <a href="logout.jsp">Đăng xuất</a>
</div>
</body>
</html>
