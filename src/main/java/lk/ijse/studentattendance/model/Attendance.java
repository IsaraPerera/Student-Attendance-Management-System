/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.model;

/**
 *
 * @author Admin
 */

import java.time.LocalDate;

public class Attendance {
    private int attendanceId;
    private int sessionId;
    private int studentId;
    private LocalDate attendanceDate;
    private String status;

    public Attendance() {}

    public Attendance(int attendanceId, int sessionId, int studentId, LocalDate attendanceDate, String status) {
        this.attendanceId = attendanceId;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}