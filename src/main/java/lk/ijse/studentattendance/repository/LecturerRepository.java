/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.repository;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.Lecturer;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerRepository {

    public List<Lecturer> findAll() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String query = "SELECT * FROM lecturers";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lecturers.add(new Lecturer(
                        rs.getInt("lecturer_id"),
                        (Integer) rs.getObject("user_id"),
                        rs.getString("lecturer_name"),
                        rs.getString("email"),
                        rs.getString("subject")
                ));
            }
        }
        return lecturers;
    }

    public boolean save(Lecturer lecturer) throws SQLException {
        String query = "INSERT INTO lecturers (lecturer_name, email, subject) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lecturer.getLecturerName());
            stmt.setString(2, lecturer.getEmail());
            stmt.setString(3, lecturer.getSubject());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int lecturerId) throws SQLException {
        String query = "DELETE FROM lecturers WHERE lecturer_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, lecturerId);
            return stmt.executeUpdate() > 0;
        }
    }
}