package lk.ijse.studentattendance.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminDashboardController {

    @FXML private Button courseButton;
    @FXML private Button studentButton;
    @FXML private Button lecturerButton;
    @FXML private Button scheduleButton;
    @FXML private Button attendanceReportButton;
    @FXML private Button logoutButton;

    @FXML private Label courseCountLabel;
    @FXML private Label studentCountLabel;
    @FXML private Label lecturerCountLabel;

    @FXML
    public void initialize() {
        loadCounts();

        if (courseButton != null) {
            courseButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/CourseManagement.fxml", "Course Management"));
        }
        if (studentButton != null) {
            studentButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/StudentManagement.fxml", "Student Management"));
        }
        if (lecturerButton != null) {
            lecturerButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/LecturerManagement.fxml", "Lecturer Management"));
        }
        if (scheduleButton != null) {
            scheduleButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/ClassScheduling.fxml", "Class Scheduling"));
        }
        if (attendanceReportButton != null) {
            attendanceReportButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/AttendanceReporting.fxml", "Attendance Reports"));
        }
        if (logoutButton != null) {
            logoutButton.setOnAction(e -> handleLogout());
        }
    }

    private void handleLogout() {
        // Clear active session upon logging out
        UserSession.cleanUserSession();
        navigateTo("/lk/ijse/studentattendance/Login.fxml", "Login");
    }

    private void loadCounts() {
        try (Connection conn = DBConnection.getInstance().getConnection(); 
             Statement stmt = conn.createStatement()) {

            if (courseCountLabel != null) {
                ResultSet rsCourses = stmt.executeQuery("SELECT COUNT(*) FROM courses");
                if (rsCourses.next()) {
                    courseCountLabel.setText(String.valueOf(rsCourses.getInt(1)));
                }
            }

            if (studentCountLabel != null) {
                ResultSet rsStudents = stmt.executeQuery("SELECT COUNT(*) FROM students");
                if (rsStudents.next()) {
                    studentCountLabel.setText(String.valueOf(rsStudents.getInt(1)));
                }
            }

            if (lecturerCountLabel != null) {
                ResultSet rsLecturers = stmt.executeQuery("SELECT COUNT(*) FROM lecturers");
                if (rsLecturers.next()) {
                    lecturerCountLabel.setText(String.valueOf(rsLecturers.getInt(1)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("Error: FXML file not found at path -> " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}