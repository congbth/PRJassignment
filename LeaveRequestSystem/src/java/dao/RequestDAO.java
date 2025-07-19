package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Request;
import util.DBConnection;

public class RequestDAO {

    public boolean insertRequest(Request req) {
        String sql = "INSERT INTO requests (title, from_date, to_date, reason, status, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, req.getTitle());
            ps.setDate(2, req.getFromDate());
            ps.setDate(3, req.getToDate());
            ps.setString(4, req.getReason());
            ps.setString(5, req.getStatus());
            ps.setInt(6, req.getCreatedById());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Hàm mới: lấy danh sách đơn theo userId
    public List<Request> getRequestsByUserId(int userId) {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT r.id, r.title, r.from_date, r.to_date, r.status, " +
                     "u.fullname AS createdByName, " +
                     "p.fullname AS processedByName " +
                     "FROM requests r " +
                     "JOIN users u ON r.created_by = u.id " +
                     "LEFT JOIN users p ON r.processed_by = p.id " +
                     "WHERE r.created_by = ? " +
                     "ORDER BY r.id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Request req = new Request();
                req.setId(rs.getInt("id"));
                req.setTitle(rs.getString("title"));
                req.setFromDate(rs.getDate("from_date"));
                req.setToDate(rs.getDate("to_date"));
                req.setStatus(rs.getString("status"));
                req.setCreatedByName(rs.getString("createdByName"));
                req.setProcessedByName(rs.getString("processedByName"));
                list.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
