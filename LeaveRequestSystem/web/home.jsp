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
            background: #f4f4f4;
            text-align: center;
            padding: 50px;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
        }

        .actions {
            margin-top: 30px;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }

        .btn:hover {
            background: #2980b9;
        }

        .logout {
            background: #e74c3c;
        }

        .logout:hover {
            background: #c0392b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chào mừng, <%= user.getFullname() %>!</h2>
        <p>Vai trò: <strong><%= user.getRoleName() %></strong></p>
        <p>Phòng ban: <strong><%= user.getDepartmentId() %></strong></p>

        <div class="actions">
            <%-- Chỉ hiện nút này nếu là nhân viên --%>
            <% if (user.getRoleName().equalsIgnoreCase("Nhân viên")) { %>
                <a href="requestForm.jsp" class="btn">✍️ Viết đơn nghỉ phép</a>
            <% } %>

            <a href="logout" class="btn logout">Đăng xuất</a>
        </div>
    </div>
</body>
</html>
