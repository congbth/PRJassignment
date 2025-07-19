<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Map<String, String>> requests = (List<Map<String, String>>) request.getAttribute("requests");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn nghỉ phép</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background: #f5f7fa;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <h2>Đơn nghỉ phép của tôi</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Từ ngày</th>
            <th>Đến ngày</th>
            <th>Lý do</th>
            <th>Trạng thái</th>
        </tr>
        <%
            if (requests != null) {
                for (Map<String, String> req : requests) {
        %>
        <tr>
            <td><%= req.get("id") %></td>
            <td><%= req.get("from") %></td>
            <td><%= req.get("to") %></td>
            <td><%= req.get("reason") %></td>
            <td><%= req.get("status") %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
