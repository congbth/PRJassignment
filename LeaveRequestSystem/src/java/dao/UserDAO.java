package dao;

import java.sql.*;
import model.User;
import util.DBConnection;

public class UserDAO {
    public static User checkLogin(String username, String password) {
        User user = null;

        String sql = """
            SELECT u.id, u.username, u.fullname, u.department_id, r.name AS role_name
            FROM [User] u
            JOIN UserRole ur ON u.id = ur.user_id
            JOIN Role r ON ur.role_id = r.id
            WHERE u.username = ? AND u.password = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("fullname"),
                    rs.getInt("department_id"),
                    rs.getString("role_name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
