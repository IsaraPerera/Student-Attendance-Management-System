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
import java.time.LocalTime;

public class ClassSession {
    private int sessionId;
    private int courseId;
    private int lecturerId;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String room;

    public ClassSession(int sessionId, int courseId, int lecturerId, LocalDate sessionDate, LocalTime sessionTime, String room) {
        this.sessionId = sessionId;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.room = room;
    }

    public int getSessionId() { return sessionId; }
    public int getCourseId() { return courseId; }
    public int getLecturerId() { return lecturerId; }
    public LocalDate getSessionDate() { return sessionDate; }
    public LocalTime getSessionTime() { return sessionTime; }
    public String getRoom() { return room; }

    @Override
    public String toString() { return sessionDate + " " + sessionTime + " (Room " + room + ")"; }
}