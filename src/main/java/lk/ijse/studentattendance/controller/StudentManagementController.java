package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.Student;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.sql.*;

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
    public void initialize() {
        setupTableColumns();
        loadStudents();

        if (addButton != null) addButton.setOnAction(e -> addStudent());
        if (updateButton != null) updateButton.setOnAction(e -> updateStudent());
        if (deleteButton != null) deleteButton.setOnAction(e -> deleteStudent());
        if (clearButton != null) clearButton.setOnAction(e -> clearFields());
        if (backButton != null) backButton.setOnAction(NavigationUtil::goBack);

        dataTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                studentidField.setText(String.valueOf(newSel.getStudentId()));
                registrationnoField.setText(newSel.getRegistrationNo());
                studentnameField.setText(newSel.getStudentName());
                courseField.setText(String.valueOf(newSel.getCourseId()));
            }
        });
    }

    @FXML
    private void btnBackOnAction(ActionEvent event) {
        NavigationUtil.goBack(event);
    }

    private void setupTableColumns() {
        if (dataTable.getColumns().size() >= 4) {
            TableColumn<Student, Integer> idCol = (TableColumn<Student, Integer>) dataTable.getColumns().get(0);
            TableColumn<Student, String> regCol = (TableColumn<Student, String>) dataTable.getColumns().get(1);
            TableColumn<Student, String> nameCol = (TableColumn<Student, String>) dataTable.getColumns().get(2);
            TableColumn<Student, Integer> courseCol = (TableColumn<Student, Integer>) dataTable.getColumns().get(3);

            idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            regCol.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            courseCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        }
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
                        (Integer) rs.getObject("user_id"),
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
}