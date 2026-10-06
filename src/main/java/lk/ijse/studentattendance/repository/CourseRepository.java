/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.repository;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.Course;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    public List<Course> findAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        }
        return courses;
    }

    public boolean save(Course course) throws SQLException {
        String query = "INSERT INTO courses (course_code, course_name, subject, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getSubject());
            stmt.setString(4, course.getDescription());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Course course) throws SQLException {
        String query = "UPDATE courses SET course_name = ?, subject = ?, description = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getSubject());
            stmt.setString(3, course.getDescription());
            stmt.setInt(4, course.getCourseId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int courseId) throws SQLException {
        String query = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("course_id"),
                rs.getString("course_code"),
                rs.getString("course_name"),
                rs.getString("subject"),
                rs.getString("description")
        );
    }
}
