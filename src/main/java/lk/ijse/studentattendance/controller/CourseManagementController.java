package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.Course;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.sql.*;

public class CourseManagementController {

    @FXML private TextField courseidField;
    @FXML private TextField coursenameField;
    @FXML private TextField subjectField;
    @FXML private TextField descriptionField;
    @FXML private TextField searchField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button searchButton;
    @FXML private Button backButton;

    @FXML private TableView<Course> dataTable;

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadCourses();

        addButton.setOnAction(e -> addCourse());
        updateButton.setOnAction(e -> updateCourse());
        deleteButton.setOnAction(e -> deleteCourse());
        clearButton.setOnAction(e -> clearFields());
        searchButton.setOnAction(e -> searchCourse());

        // Standardize back button action
        if (backButton != null) {
            backButton.setOnAction(NavigationUtil::goBack);
        }

        dataTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                courseidField.setText(String.valueOf(newSelection.getCourseId()));
                coursenameField.setText(newSelection.getCourseName());
                subjectField.setText(newSelection.getSubject());
                descriptionField.setText(newSelection.getDescription());
            }
        });
    }

    @FXML
    private void btnBackOnAction(ActionEvent event) {
        NavigationUtil.goBack(event);
    }

    private void setupTableColumns() {
        if (dataTable.getColumns().size() >= 4) {
            TableColumn<Course, Integer> idCol = (TableColumn<Course, Integer>) dataTable.getColumns().get(0);
            TableColumn<Course, String> nameCol = (TableColumn<Course, String>) dataTable.getColumns().get(1);
            TableColumn<Course, String> subCol = (TableColumn<Course, String>) dataTable.getColumns().get(2);
            TableColumn<Course, String> descCol = (TableColumn<Course, String>) dataTable.getColumns().get(3);

            idCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            subCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        }
    }

    private void loadCourses() {
        courseList.clear();
        String query = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                courseList.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("subject"),
                        rs.getString("description")
                ));
            }
            dataTable.setItems(courseList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCourse() {
        String query = "INSERT INTO courses (course_code, course_name, subject, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "CRS-" + (System.currentTimeMillis() % 10000));
            stmt.setString(2, coursenameField.getText());
            stmt.setString(3, subjectField.getText());
            stmt.setString(4, descriptionField.getText());

            stmt.executeUpdate();
            loadCourses();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourse() {
        String query = "UPDATE courses SET course_name = ?, subject = ?, description = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, coursenameField.getText());
            stmt.setString(2, subjectField.getText());
            stmt.setString(3, descriptionField.getText());
            stmt.setInt(4, Integer.parseInt(courseidField.getText()));

            stmt.executeUpdate();
            loadCourses();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        String query = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(courseidField.getText()));
            stmt.executeUpdate();
            loadCourses();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCourse() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.trim().isEmpty()) {
            loadCourses();
            return;
        }

        courseList.clear();
        String query = "SELECT * FROM courses WHERE course_name LIKE ? OR subject LIKE ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courseList.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("subject"),
                        rs.getString("description")
                ));
            }
            dataTable.setItems(courseList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        courseidField.clear();
        coursenameField.clear();
        subjectField.clear();
        descriptionField.clear();
    }
}