package lk.ijse.studentattendance.model;

import java.time.LocalDate;

public class AttendanceReportTM {
    private LocalDate date;
    private String studentName;
    private String courseName;
    private String subject;
    private String lecturerName;
    private String status;

    public AttendanceReportTM(LocalDate date, String studentName, String courseName, String subject, String lecturerName, String status) {
        this.date = date;
        this.studentName = studentName;
        this.courseName = courseName;
        this.subject = subject;
        this.lecturerName = lecturerName;
        this.status = status;
    }

    public LocalDate getDate() { return date; }
    public String getStudentName() { return studentName; }
    public String getCourseName() { return courseName; }
    public String getSubject() { return subject; }
    public String getLecturerName() { return lecturerName; }
    public String getStatus() { return status; }
}