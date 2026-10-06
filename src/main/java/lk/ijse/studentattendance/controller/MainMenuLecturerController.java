package lk.ijse.studentattendance.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.studentattendance.util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainMenuLecturerController {

    @FXML private Button scheduleButton;
    @FXML private Button attendanceButton;
    @FXML private Button reportButton;
    @FXML private Button logoutButton;

    @FXML private Label courseCountLabel;
    @FXML private Label studentCountLabel;
    @FXML private Label lecturerCountLabel;

    @FXML
    public void initialize() {
        loadDashboardCounts();
    }

    private void loadDashboardCounts() {
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Courses Count
            try (ResultSet rsCourses = stmt.executeQuery("SELECT COUNT(*) FROM course")) {
                if (rsCourses.next() && courseCountLabel != null) {
                    courseCountLabel.setText(String.valueOf(rsCourses.getInt(1)));
                }
            }

            // Students Count
            try (ResultSet rsStudents = stmt.executeQuery("SELECT COUNT(*) FROM student")) {
                if (rsStudents.next() && studentCountLabel != null) {
                    studentCountLabel.setText(String.valueOf(rsStudents.getInt(1)));
                }
            }

            // Lecturers Count
            try (ResultSet rsLecturers = stmt.executeQuery("SELECT COUNT(*) FROM lecturer")) {
                if (rsLecturers.next() && lecturerCountLabel != null) {
                    lecturerCountLabel.setText(String.valueOf(rsLecturers.getInt(1)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(ActionEvent event, String fxmlPath) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("FXML file not found at path: " + fxmlPath);
                return;
            }
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnScheduleOnAction(ActionEvent event) {
        navigateTo(event, "/lk/ijse/studentattendance/ClassScheduling.fxml");
    }

    @FXML
    void btnMarkAttendanceOnAction(ActionEvent event) {
        navigateTo(event, "/lk/ijse/studentattendance/AttendanceMarking.fxml");
    }

    @FXML
    void btnViewReportsOnAction(ActionEvent event) {
        navigateTo(event, "/lk/ijse/studentattendance/AttendanceReporting.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        navigateTo(event, "/lk/ijse/studentattendance/Login.fxml");
    }
}