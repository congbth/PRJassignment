<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f0f2f5;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 500px;
            margin: auto;
        }

        h2 {
            color: #2c3e50;
        }

        .info {
            margin-top: 20px;
            line-height: 1.6;
        }

        .btn {
            display: inline-block;
            margin-top: 25px;
            padding: 10px 20px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .btn:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>
    <div class="card">
        <h2>Chào mừng, <%= user.getFullname() %>!</h2>

        <div class="info">
            <p><strong>Tên đăng nhập:</strong> <%= user.getUsername() %></p>
            <p><strong>Vai trò:</strong> <%= user.getRoleName() %></p>
            <p><strong>Phòng ban:</strong> <%= user.getDepartmentId() %></p>
        </div>

        <a href="logout" class="btn">Đăng xuất</a>
    </div>
</body>
</html>
