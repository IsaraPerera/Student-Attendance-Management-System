package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.studentattendance.model.Course;
import lk.ijse.studentattendance.service.ClassSessionService;
import lk.ijse.studentattendance.service.CourseService;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClassSchedulingController implements Initializable {

    @FXML private ComboBox<Course> cmbCourse;
    @FXML private DatePicker dpScheduleDate;
    @FXML private TextField txtStartTime;
    @FXML private TextField txtEndTime;
    @FXML private TextField txtHallNumber;
    @FXML private Button btnSaveSchedule;
    @FXML private Button btnBack;

    private final CourseService courseService = new CourseService();
    private final ClassSessionService classSessionService = new ClassSessionService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCourseData();
    }

    private void loadCourseData() {
        try {
            cmbCourse.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveScheduleOnAction(ActionEvent event) {
        Course selectedCourse = cmbCourse.getValue();

        if (selectedCourse == null || dpScheduleDate.getValue() == null || txtStartTime.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all required fields.");
            return;
        }

        try {
            // Standard parse format HH:mm (e.g. 08:30)
            LocalTime startTime = LocalTime.parse(txtStartTime.getText().trim());

            boolean success = classSessionService.addClassSession(
                    selectedCourse.getCourseId(),
                    1, // Default lecturer ID (or extract from session)
                    dpScheduleDate.getValue(),
                    startTime,
                    txtHallNumber.getText()
            );

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Class schedule saved successfully!");
                clearFields();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format (Use HH:mm) or Database error.");
        }
    }

    private void clearFields() {
        cmbCourse.getSelectionModel().clearSelection();
        dpScheduleDate.setValue(null);
        txtStartTime.clear();
        txtEndTime.clear();
        txtHallNumber.clear();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        NavigationUtil.goBack(event);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}