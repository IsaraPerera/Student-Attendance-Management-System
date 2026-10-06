package lk.ijse.studentattendance.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.studentattendance.model.AttendanceReportTM;
import lk.ijse.studentattendance.model.Course;
import lk.ijse.studentattendance.model.Student;
import lk.ijse.studentattendance.service.CourseService;
import lk.ijse.studentattendance.service.StudentService;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.NavigationUtil;

import java.sql.*;
import java.time.LocalDate;

public class AttendanceReportingController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private DatePicker fromDatePicker;
    @FXML private DatePicker toDatePicker;

    @FXML private Button filterButton;
    @FXML private Button backButton;

    @FXML private Label presentCountLabel;
    @FXML private Label absentCountLabel;
    @FXML private Label lateCountLabel;

    @FXML private TableView<AttendanceReportTM> reportTable;

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final ObservableList<AttendanceReportTM> reportList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadDropdowns();

        if (backButton != null) backButton.setOnAction(NavigationUtil::goBack);
        if (filterButton != null) filterButton.setOnAction(e -> filterReports());
    }

    private void setupTableColumns() {
        TableColumn<AttendanceReportTM, LocalDate> dateCol = (TableColumn<AttendanceReportTM, LocalDate>) reportTable.getColumns().get(0);
        TableColumn<AttendanceReportTM, String> studentCol = (TableColumn<AttendanceReportTM, String>) reportTable.getColumns().get(1);
        TableColumn<AttendanceReportTM, String> courseCol = (TableColumn<AttendanceReportTM, String>) reportTable.getColumns().get(2);
        TableColumn<AttendanceReportTM, String> subCol = (TableColumn<AttendanceReportTM, String>) reportTable.getColumns().get(3);
        TableColumn<AttendanceReportTM, String> lecCol = (TableColumn<AttendanceReportTM, String>) reportTable.getColumns().get(4);
        TableColumn<AttendanceReportTM, String> statusCol = (TableColumn<AttendanceReportTM, String>) reportTable.getColumns().get(5);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        lecCol.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDropdowns() {
        try {
            studentComboBox.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
            courseComboBox.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterReports() {
        reportList.clear();

        StringBuilder sql = new StringBuilder(
                "SELECT a.marked_date, st.student_name, c.course_name, c.subject, l.lecturer_name, a.status " +
                        "FROM attendance a " +
                        "JOIN students st ON a.student_id = st.student_id " +
                        "JOIN class_sessions cs ON a.session_id = cs.session_id " +
                        "JOIN courses c ON cs.course_id = c.course_id " +
                        "LEFT JOIN lecturers l ON cs.lecturer_id = l.lecturer_id WHERE 1=1 "
        );

        Student student = studentComboBox.getValue();
        Course course = courseComboBox.getValue();
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        if (student != null) sql.append(" AND a.student_id = ").append(student.getStudentId());
        if (course != null) sql.append(" AND cs.course_id = ").append(course.getCourseId());
        if (from != null) sql.append(" AND a.marked_date >= '").append(Date.valueOf(from)).append("'");
        if (to != null) sql.append(" AND a.marked_date <= '").append(Date.valueOf(to)).append("'");

        int presentCount = 0, absentCount = 0, lateCount = 0;

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                String status = rs.getString("status");
                if ("PRESENT".equalsIgnoreCase(status)) presentCount++;
                else if ("ABSENT".equalsIgnoreCase(status)) absentCount++;
                else if ("LATE".equalsIgnoreCase(status)) lateCount++;

                reportList.add(new AttendanceReportTM(
                        rs.getDate("marked_date").toLocalDate(),
                        rs.getString("student_name"),
                        rs.getString("course_name"),
                        rs.getString("subject"),
                        rs.getString("lecturer_name") != null ? rs.getString("lecturer_name") : "N/A",
                        status
                ));
            }

            presentCountLabel.setText(String.valueOf(presentCount));
            absentCountLabel.setText(String.valueOf(absentCount));
            lateCountLabel.setText(String.valueOf(lateCount));

            reportTable.setItems(reportList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnBackOnAction(ActionEvent event) {
        NavigationUtil.goBack(event);
    }
}