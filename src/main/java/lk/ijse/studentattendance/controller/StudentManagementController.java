/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.controller;

/**
 *
 * @author Admin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.*;
import lk.ijse.studentattendance.util.NavigationUtil;

public class StudentManagementController {

    @FXML private TextField studentidField;
    @FXML private TextField registrationnoField;
    @FXML private TextField studentnameField;
    @FXML private TextField courseField;
    @FXML private TextField searchField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button searchButton;
    @FXML private Button backButton;

    @FXML private TableView<Student> dataTable;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    
    @FXML
private void backButton(javafx.event.ActionEvent event) {
    NavigationUtil.goBack(event);
}

    @FXML
    public void initialize() {
        setupTableColumns();
        loadStudents();

        addButton.setOnAction(e -> addStudent());
        updateButton.setOnAction(e -> updateStudent());
        deleteButton.setOnAction(e -> deleteStudent());
        clearButton.setOnAction(e -> clearFields());
        backButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/AdminDashboard.fxml", "Admin Dashboard"));

        dataTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                studentidField.setText(String.valueOf(newSel.getStudentId()));
                registrationnoField.setText(newSel.getRegistrationNo());
                studentnameField.setText(newSel.getStudentName());
                courseField.setText(String.valueOf(newSel.getCourseId()));
            }
        });
    }

    private void setupTableColumns() {
        TableColumn<Student, Integer> idCol = (TableColumn<Student, Integer>) dataTable.getColumns().get(0);
        TableColumn<Student, String> regCol = (TableColumn<Student, String>) dataTable.getColumns().get(1);
        TableColumn<Student, String> nameCol = (TableColumn<Student, String>) dataTable.getColumns().get(2);
        TableColumn<Student, Integer> courseCol = (TableColumn<Student, Integer>) dataTable.getColumns().get(3);

        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        regCol.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
    }

    private void loadStudents() {
        studentList.clear();
        String query = "SELECT * FROM students";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                studentList.add(new Student(
                        rs.getInt("student_id"),
                        (Integer) rs.getObject("user_id"), // Fixed: replaced rs.getInteger() with (Integer) rs.getObject()
                        rs.getString("registration_no"),
                        rs.getString("student_name"),
                        rs.getInt("course_id")
                ));
            }
            dataTable.setItems(studentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addStudent() {
        String query = "INSERT INTO students (registration_no, student_name, course_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, registrationnoField.getText());
            stmt.setString(2, studentnameField.getText());
            stmt.setInt(3, Integer.parseInt(courseField.getText()));

            stmt.executeUpdate();
            loadStudents();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent() {
        String query = "UPDATE students SET registration_no = ?, student_name = ?, course_id = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, registrationnoField.getText());
            stmt.setString(2, studentnameField.getText());
            stmt.setInt(3, Integer.parseInt(courseField.getText()));
            stmt.setInt(4, Integer.parseInt(studentidField.getText()));

            stmt.executeUpdate();
            loadStudents();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(studentidField.getText()));
            stmt.executeUpdate();
            loadStudents();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        studentidField.clear();
        registrationnoField.clear();
        studentnameField.clear();
        courseField.clear();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Student {
        private final int studentId;
        private final Integer userId;
        private final String registrationNo;
        private final String studentName;
        private final int courseId;

        public Student(int studentId, Integer userId, String registrationNo, String studentName, int courseId) {
            this.studentId = studentId;
            this.userId = userId;
            this.registrationNo = registrationNo;
            this.studentName = studentName;
            this.courseId = courseId;
        }

        public int getStudentId() { return studentId; }
        public Integer getUserId() { return userId; }
        public String getRegistrationNo() { return registrationNo; }
        public String getStudentName() { return studentName; }
        public int getCourseId() { return courseId; }
    }
}