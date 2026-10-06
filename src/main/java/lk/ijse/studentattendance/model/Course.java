/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.model;

/**
 *
 * @author Admin
 */

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private String subject;
    private String description;

    public Course(int courseId, String courseCode, String courseName, String subject, String description) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subject = subject;
        this.description = description;
    }

    public int getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }

    @Override
    public String toString() { return courseCode + " - " + courseName; }
}
