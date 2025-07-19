<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!user.getRoleName().equalsIgnoreCase("Nhân viên")) {
%>
    <html>
    <head>
        <title>Truy cập bị từ chối</title>
        <style>
            body {
                font-family: Arial;
                background: #fce4e4;
                padding: 50px;
                text-align: center;
            }
            .warning {
                color: red;
                font-size: 20px;
                font-weight: bold;
                background: #fff0f0;
                display: inline-block;
                padding: 20px;
                border: 2px solid red;
                border-radius: 10px;
            }
            a {
                display: block;
                margin-top: 20px;
                text-decoration: none;
                color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="warning">🚫 Bạn không có quyền truy cập chức năng này.</div>
        <a href="home.jsp">Quay lại trang chủ</a>
    </body>
    </html>
<%
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Tạo đơn nghỉ phép</title>
    <style>
        body {
            font-family: Arial;
            background: #f4f4f4;
            padding: 30px;
        }

        .form-container {
            max-width: 500px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-top: 15px;
        }

        input[type="text"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        textarea {
            resize: vertical;
        }

        input[type="submit"] {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background: #27ae60;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background: #1e8449;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Tạo đơn nghỉ phép</h2>

        <% String error = (String) request.getAttribute("errorMessage"); %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <form action="createRequest" method="post">
            <label>Tiêu đề:</label>
            <input type="text" name="title" required>

            <label>Từ ngày:</label>
            <input type="date" name="from_date" required>

            <label>Đến ngày:</label>
            <input type="date" name="to_date" required>

            <label>Lý do nghỉ:</label>
            <textarea name="reason" rows="4" required></textarea>

            <input type="submit" value="Gửi đơn">
        </form>
    </div>
</body>
</html>
