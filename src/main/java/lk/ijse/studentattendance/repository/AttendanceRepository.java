/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.repository;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AttendanceRepository {

    public boolean saveOrUpdateAttendance(int sessionId, int studentId, String status, LocalDate date) throws SQLException {
        String query = "INSERT INTO attendance (session_id, student_id, status, marked_date) " +
                       "VALUES (?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE status = VALUES(status), marked_date = VALUES(marked_date)";
                       
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sessionId);
            stmt.setInt(2, studentId);
            stmt.setString(3, status);
            stmt.setDate(4, Date.valueOf(date));
            return stmt.executeUpdate() > 0;
        }
    }
}