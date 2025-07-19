package dao;

import java.sql.*;
import model.Request;
import util.DBConnection;

public class RequestDAO {
    public static boolean insertRequest(Request req) {
        String sql = """
            INSERT INTO LeaveRequest (title, from_date, to_date, reason, status, created_by)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, req.getTitle());
            stmt.setDate(2, req.getFromDate());
            stmt.setDate(3, req.getToDate());
            stmt.setString(4, req.getReason());
            stmt.setString(5, req.getStatus());
            stmt.setInt(6, req.getCreatedBy());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
