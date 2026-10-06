/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.service;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.Course;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("subject"),
                        rs.getString("description")
                ));
            }
        }
        return courses;
    }

    public boolean addCourse(String courseCode, String courseName, String subject, String description) throws SQLException {
        String query = "INSERT INTO courses (course_code, course_name, subject, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseCode);
            stmt.setString(2, courseName);
            stmt.setString(3, subject);
            stmt.setString(4, description);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateCourse(int courseId, String courseName, String subject, String description) throws SQLException {
        String query = "UPDATE courses SET course_name = ?, subject = ?, description = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseName);
            stmt.setString(2, subject);
            stmt.setString(3, description);
            stmt.setInt(4, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCourse(int courseId) throws SQLException {
        String query = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Course> searchCourses(String keyword) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE course_name LIKE ? OR subject LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("subject"),
                        rs.getString("description")
                ));
            }
        }
        return courses;
    }
}
