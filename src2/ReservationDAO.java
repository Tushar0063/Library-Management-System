package dao;

import model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public static boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (user_id, book_id, reservation_date, due_date, returned) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservation.getUserId());
            stmt.setInt(2, reservation.getBookId());
            stmt.setDate(3, reservation.getReservationDate());
            stmt.setDate(4, reservation.getDueDate());
            stmt.setBoolean(5, reservation.isReturned());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean markAsReturned(int id) {
        String sql = "UPDATE reservations SET returned = 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Reservation> getDueSoonReservations(int days) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE due_date <= DATE_ADD(CURDATE(), INTERVAL ? DAY) AND returned = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, days);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Reservation> getOverdueReservations() {
        List<Reservation> overdueList = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE returned = 0 AND due_date < CURRENT_DATE";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                overdueList.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdueList;
    }



    private static Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("book_id"),
                rs.getDate("reservation_date"),
                rs.getDate("due_date"),
                rs.getBoolean("returned")
        );
    }
}


