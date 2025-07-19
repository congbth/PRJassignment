package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=LeaveRequestDB;encrypt=true;trustServerCertificate=true";
        String username = "sa"; // Đổi thành user của bạn
        String password = "30102004"; // Đổi thành mật khẩu tương ứng

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, username, password);
    }
}
