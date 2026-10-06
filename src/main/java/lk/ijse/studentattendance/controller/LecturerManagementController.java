package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.Lecturer;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.studentattendance.util.NavigationUtil;

public class LecturerManagementController {
    
    @FXML
    private Button backButton;

    @FXML private TableView<Lecturer> lecturerTable;
    
    // Make sure these fx:ids match your FXML column IDs
    @FXML private TableColumn<Lecturer, Integer> colLecturerId;
    @FXML private TableColumn<Lecturer, String> colLecturerName;
    @FXML private TableColumn<Lecturer, String> colEmail;
    @FXML private TableColumn<Lecturer, String> colSubject;
    
    @FXML
    private void btnBackOnAction(javafx.event.ActionEvent event) {
    NavigationUtil.goBack(event);
}

    @FXML
    public void initialize() {
        // Property names MUST match the getter method suffixes in Lecturer.java
        // getLecturerId()   -> "lecturerId"
        // getLecturerName() -> "lecturerName"
        // getEmail()        -> "email"
        // getSubject()      -> "subject"
        
        colLecturerId.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        colLecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        loadAllLecturers();
    }

    private void loadAllLecturers() {
        ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList();
        
        // Adjust column and table names according to your sams_db schema
        String sql = "SELECT lecturer_id, user_id, lecturer_name, email, subject FROM lecturers";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int lecturerId = rs.getInt("lecturer_id");
                Integer userId = (Integer) rs.getObject("user_id"); // Handles NULL user_ids safely
                String name = rs.getString("lecturer_name");
                String email = rs.getString("email");
                String subject = rs.getString("subject");

                lecturerList.add(new Lecturer(lecturerId, userId, name, email, subject));
            }

            lecturerTable.setItems(lecturerList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database fetch failed: " + e.getMessage());
        }
    }
}