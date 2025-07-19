package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=LeaveManagementDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa"; // đổi theo user của bạn
    private static final String PASSWORD = "30102004"; // đổi theo mật khẩu của bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
