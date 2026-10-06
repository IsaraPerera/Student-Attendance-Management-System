package lk.ijse.studentattendance.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttendanceTM {
    private final SimpleIntegerProperty studentId;
    private final SimpleStringProperty registrationNo;
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty status;

    public AttendanceTM(int studentId, String registrationNo, String studentName, String status) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.registrationNo = new SimpleStringProperty(registrationNo);
        this.studentName = new SimpleStringProperty(studentName);
        this.status = new SimpleStringProperty(status);
    }

    public int getStudentId() { return studentId.get(); }
    public SimpleIntegerProperty studentIdProperty() { return studentId; }

    public String getRegistrationNo() { return registrationNo.get(); }
    public SimpleStringProperty registrationNoProperty() { return registrationNo; }

    public String getStudentName() { return studentName.get(); }
    public SimpleStringProperty studentNameProperty() { return studentName; }

    public String getStatus() { return status.get(); }
    public SimpleStringProperty statusProperty() { return status; }
    public void setStatus(String status) { this.status.set(status); }
}