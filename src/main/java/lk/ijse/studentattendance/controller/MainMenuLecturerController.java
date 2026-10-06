/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.controller;

/**
 *
 * @author Admin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuLecturerController {

    private void navigateTo(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML void btnMarkAttendanceOnAction(ActionEvent event) throws IOException {
        navigateTo(event, "/lk/ijse/studentattendance/AttendanceMarking.fxml");
    }

    @FXML void btnViewReportsOnAction(ActionEvent event) throws IOException {
        navigateTo(event, "/lk/ijse/studentattendance/AttendanceReporting.fxml");
    }

    @FXML void btnLogoutOnAction(ActionEvent event) throws IOException {
        navigateTo(event, "/lk/ijse/studentattendance/Login.fxml");
    }
}