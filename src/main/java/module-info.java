module lk.ijse.studentattendance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j; // <-- Add this line

    opens lk.ijse.studentattendance.controller to javafx.fxml;
    exports lk.ijse.studentattendance;
    exports lk.ijse.studentattendance.controller;
}