package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.Lecturer;
import lk.ijse.studentattendance.service.LecturerService;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.sql.SQLException;

public class LecturerManagementController {

    @FXML private TextField lectureridField;
    @FXML private TextField lecturernameField;
    @FXML private TextField emailField;
    @FXML private TextField subjectField;
    @FXML private TextField searchField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button searchButton;
    @FXML private Button backButton;

    @FXML private TableView<Lecturer> lecturerTable;
    @FXML private TableColumn<Lecturer, Integer> colLecturerId;
    @FXML private TableColumn<Lecturer, String> colLecturerName;
    @FXML private TableColumn<Lecturer, String> colEmail;
    @FXML private TableColumn<Lecturer, String> colSubject;

    private final LecturerService lecturerService = new LecturerService();
    private final ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadAllLecturers();

        if (backButton != null) backButton.setOnAction(NavigationUtil::goBack);
        if (addButton != null) addButton.setOnAction(e -> addLecturer());
        if (updateButton != null) updateButton.setOnAction(e -> updateLecturer());
        if (deleteButton != null) deleteButton.setOnAction(e -> deleteLecturer());
        if (clearButton != null) clearButton.setOnAction(e -> clearFields());
        if (searchButton != null) searchButton.setOnAction(e -> searchLecturer());

        lecturerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                lectureridField.setText(String.valueOf(newSel.getLecturerId()));
                lecturernameField.setText(newSel.getLecturerName());
                emailField.setText(newSel.getEmail());
                subjectField.setText(newSel.getSubject());
            }
        });
    }

    private void setupTableColumns() {
        colLecturerId.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        colLecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    }

    private void loadAllLecturers() {
        try {
            lecturerList.clear();
            lecturerList.addAll(lecturerService.getAllLecturers());
            lecturerTable.setItems(lecturerList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load lecturers.");
        }
    }

    private void addLecturer() {
        try {
            boolean success = lecturerService.addLecturer(
                    lecturernameField.getText(),
                    emailField.getText(),
                    subjectField.getText()
            );
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer added successfully!");
                loadAllLecturers();
                clearFields();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not add lecturer.");
        }
    }

    private void updateLecturer() {
        try {
            int id = Integer.parseInt(lectureridField.getText());
            boolean success = lecturerService.updateLecturer(
                    id,
                    lecturernameField.getText(),
                    emailField.getText(),
                    subjectField.getText()
            );
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer updated successfully!");
                loadAllLecturers();
                clearFields();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not update lecturer.");
        }
    }

    private void deleteLecturer() {
        try {
            int id = Integer.parseInt(lectureridField.getText());
            boolean success = lecturerService.deleteLecturer(id);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer deleted successfully!");
                loadAllLecturers();
                clearFields();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not delete lecturer.");
        }
    }

    private void searchLecturer() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            loadAllLecturers();
            return;
        }
        ObservableList<Lecturer> filtered = FXCollections.observableArrayList();
        for (Lecturer l : lecturerList) {
            if (l.getLecturerName().toLowerCase().contains(keyword) || l.getSubject().toLowerCase().contains(keyword)) {
                filtered.add(l);
            }
        }
        lecturerTable.setItems(filtered);
    }

    private void clearFields() {
        lectureridField.clear();
        lecturernameField.clear();
        emailField.clear();
        subjectField.clear();
    }

    @FXML
    private void btnBackOnAction(ActionEvent event) {
        NavigationUtil.goBack(event);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}