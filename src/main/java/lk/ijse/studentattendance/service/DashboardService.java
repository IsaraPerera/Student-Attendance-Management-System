/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.service;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardService {

    public int getCourseCount() throws SQLException {
        return getCount("courses");
    }

    public int getStudentCount() throws SQLException {
        return getCount("students");
    }

    public int getLecturerCount() throws SQLException {
        return getCount("lecturers");
    }

    private int getCount(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
