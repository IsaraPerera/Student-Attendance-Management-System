/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.repository;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.ClassSession;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassSessionRepository {

    public boolean save(ClassSession session) throws SQLException {
        String sql = "INSERT INTO class_sessions (course_id, lecturer_id, session_date, session_time, room) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, session.getCourseId());
        pstm.setInt(2, session.getLecturerId());
        pstm.setDate(3, Date.valueOf(session.getSessionDate()));
        pstm.setTime(4, Time.valueOf(session.getSessionTime()));
        pstm.setString(5, session.getRoom());
        return pstm.executeUpdate() > 0;
    }

    public List<ClassSession> getAll() throws SQLException {
        String sql = "SELECT * FROM class_sessions";
        Connection conn = DBConnection.getInstance().getConnection();
        ResultSet resultSet = conn.createStatement().executeQuery(sql);
        List<ClassSession> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new ClassSession(
                resultSet.getInt("session_id"),
                resultSet.getInt("course_id"),
                resultSet.getInt("lecturer_id"),
                resultSet.getDate("session_date").toLocalDate(),
                resultSet.getTime("session_time").toLocalTime(),
                resultSet.getString("room")
            ));
        }
        return list;
    }
}
