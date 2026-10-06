/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.model;

/**
 *
 * @author Admin
 */

public class Student {
    private int studentId;
    private Integer userId;
    private String registrationNo;
    private String studentName;
    private int courseId;

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

    @Override
    public String toString() { return registrationNo + " - " + studentName; }
}