module lk.ijse.studentattendance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens lk.ijse.studentattendance to javafx.fxml;
    opens lk.ijse.studentattendance.controller to javafx.fxml;
    opens lk.ijse.studentattendance.util to javafx.fxml;
    opens lk.ijse.studentattendance.model to javafx.base, javafx.fxml;

    exports lk.ijse.studentattendance;
    exports lk.ijse.studentattendance.controller;
    exports lk.ijse.studentattendance.util;
}