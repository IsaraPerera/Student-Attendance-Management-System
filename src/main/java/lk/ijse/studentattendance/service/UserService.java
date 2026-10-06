/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.service;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.User;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public User authenticateUser(String username, String password, String role) throws SQLException {
        String query = "SELECT user_id, username, role FROM users WHERE username = ? AND password = ? AND role = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("role"));
            }
        }
        return null;
    }

    public boolean registerUser(String username, String password, String role) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            return stmt.executeUpdate() > 0;
        }
    }
}