/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.service;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.ClassSession;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClassSessionService {

    public List<ClassSession> getAllSessions() throws SQLException {
        List<ClassSession> sessions = new ArrayList<>();
        String query = "SELECT * FROM class_sessions";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                sessions.add(new ClassSession(
                        rs.getInt("session_id"),
                        rs.getInt("course_id"),
                        rs.getInt("lecturer_id"),
                        rs.getDate("session_date").toLocalDate(),
                        rs.getTime("session_time").toLocalTime(),
                        rs.getString("room")
                ));
            }
        }
        return sessions;
    }

    public boolean addClassSession(int courseId, int lecturerId, LocalDate date, LocalTime time, String room) throws SQLException {
        String query = "INSERT INTO class_sessions (course_id, lecturer_id, session_date, session_time, room) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, lecturerId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setTime(4, Time.valueOf(time));
            stmt.setString(5, room);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateClassSession(int sessionId, int courseId, int lecturerId, LocalDate date, LocalTime time, String room) throws SQLException {
        String query = "UPDATE class_sessions SET course_id = ?, lecturer_id = ?, session_date = ?, session_time = ?, room = ? WHERE session_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, lecturerId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setTime(4, Time.valueOf(time));
            stmt.setString(5, room);
            stmt.setInt(6, sessionId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteClassSession(int sessionId) throws SQLException {
        String query = "DELETE FROM class_sessions WHERE session_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sessionId);
            return stmt.executeUpdate() > 0;
        }
    }
}