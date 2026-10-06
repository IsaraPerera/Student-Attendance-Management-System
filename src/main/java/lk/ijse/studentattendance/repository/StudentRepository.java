/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.repository;

/**
 *
 * @author Admin
 */

import lk.ijse.studentattendance.model.Student;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        (Integer) rs.getObject("user_id"),
                        rs.getString("registration_no"),
                        rs.getString("student_name"),
                        rs.getInt("course_id")
                ));
            }
        }
        return students;
    }

    public boolean save(Student student) throws SQLException {
        String query = "INSERT INTO students (registration_no, student_name, course_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getRegistrationNo());
            stmt.setString(2, student.getStudentName());
            stmt.setInt(3, student.getCourseId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(Student student) throws SQLException {
        String query = "UPDATE students SET registration_no = ?, student_name = ?, course_id = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getRegistrationNo());
            stmt.setString(2, student.getStudentName());
            stmt.setInt(3, student.getCourseId());
            stmt.setInt(4, student.getStudentId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int studentId) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            return stmt.executeUpdate() > 0;
        }
    }
}
