package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.ClassSession;
import lk.ijse.studentattendance.model.AttendanceTM;
import lk.ijse.studentattendance.repository.AttendanceRepository;
import lk.ijse.studentattendance.service.ClassSessionService;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.sql.*;
import java.time.LocalDate;

public class AttendanceMarkingController {

    @FXML private Button backButton;
    @FXML private ComboBox<ClassSession> sessionComboBox;
    @FXML private DatePicker datePicker;
    @FXML private Button loadButton;
    @FXML private Button presentButton;
    @FXML private Button absentButton;
    @FXML private Button lateButton;
    @FXML private Button saveButton;
    @FXML private TableView<AttendanceTM> attendanceTable;

    private final ClassSessionService classSessionService = new ClassSessionService();
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();
    private final ObservableList<AttendanceTM> studentAttendanceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        datePicker.setValue(LocalDate.now());
        setupTableColumns();
        loadSessions();

        if (backButton != null) backButton.setOnAction(NavigationUtil::goBack);
        if (loadButton != null) loadButton.setOnAction(e -> loadStudentsForSession());
        if (presentButton != null) presentButton.setOnAction(e -> markSelectedStatus("PRESENT"));
        if (absentButton != null) absentButton.setOnAction(e -> markSelectedStatus("ABSENT"));
        if (lateButton != null) lateButton.setOnAction(e -> markSelectedStatus("LATE"));
    }

    private void setupTableColumns() {
        TableColumn<AttendanceTM, Integer> idCol = (TableColumn<AttendanceTM, Integer>) attendanceTable.getColumns().get(0);
        TableColumn<AttendanceTM, String> regCol = (TableColumn<AttendanceTM, String>) attendanceTable.getColumns().get(1);
        TableColumn<AttendanceTM, String> nameCol = (TableColumn<AttendanceTM, String>) attendanceTable.getColumns().get(2);
        TableColumn<AttendanceTM, String> statusCol = (TableColumn<AttendanceTM, String>) attendanceTable.getColumns().get(3);

        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        regCol.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadSessions() {
        try {
            sessionComboBox.setItems(FXCollections.observableArrayList(classSessionService.getAllSessions()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentsForSession() {
        ClassSession session = sessionComboBox.getValue();
        if (session == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a class session first.");
            return;
        }

        studentAttendanceList.clear();
        String sql = "SELECT student_id, registration_no, student_name FROM students WHERE course_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, session.getCourseId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentAttendanceList.add(new AttendanceTM(
                        rs.getInt("student_id"),
                        rs.getString("registration_no"),
                        rs.getString("student_name"),
                        "PRESENT" // Default status
                ));
            }
            attendanceTable.setItems(studentAttendanceList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void markSelectedStatus(String status) {
        AttendanceTM selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(status);
            attendanceTable.refresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select a student in table first.");
        }
    }

    @FXML
    void btnSaveAttendanceOnAction(ActionEvent event) {
        ClassSession session = sessionComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (session == null || date == null || studentAttendanceList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Ensure session and date are selected and students are loaded.");
            return;
        }

        try {
            for (AttendanceTM item : studentAttendanceList) {
                attendanceRepository.saveOrUpdateAttendance(session.getSessionId(), item.getStudentId(), item.getStatus(), date);
            }
            showAlert(Alert.AlertType.INFORMATION, "Success", "Attendance saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save attendance.");
        }
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