package dao;

import model.Reservation;
import model.User;
import java.sql.*;
import java.util.List;

public class UserDAO {

    // Login Method
    public static User login(String username, String password) {
        User user = null;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password); // 🔐 Use hashing in production
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Login error:");
            e.printStackTrace();
        }

        return user;
    }

    // Register Method
    public static boolean register(User newUser) {
        String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword()); // 🔐 Use hashing in production
            stmt.setString(3, newUser.getRole());
            stmt.setString(4, newUser.getEmail());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Registration error:");
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get User by ID
    public static User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToUser(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching user by ID:");
            e.printStackTrace();
        }

        return null;
    }

    public static long calculateUserFine(int userId) {
        List<Reservation> reservations = ReservationDAO.getAllReservations();
        long totalFine = 0;
        for (Reservation r : reservations) {
            if (r.getUserId() == userId && !r.isReturned()) {
                totalFine += r.calculateFine();
            }
        }
        return totalFine;
    }




    private static User mapRowToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getString("email")
        );
    }
}
