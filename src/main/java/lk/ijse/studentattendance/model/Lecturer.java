/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.model;

/**
 *
 * @author Admin
 */

public class Lecturer {
    private int lecturerId;
    private Integer userId;
    private String lecturerName;
    private String email;
    private String subject;

    public Lecturer(int lecturerId, Integer userId, String lecturerName, String email, String subject) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.lecturerName = lecturerName;
        this.email = email;
        this.subject = subject;
    }

    public int getLecturerId() { return lecturerId; }
    public Integer getUserId() { return userId; }
    public String getLecturerName() { return lecturerName; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }

    @Override
    public String toString() { return lecturerName; }
}