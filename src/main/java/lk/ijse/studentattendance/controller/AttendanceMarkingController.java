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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import lk.ijse.studentattendance.util.NavigationUtil;

public class AttendanceMarkingController {

    @FXML
    private ComboBox<String> cmbSession;
    @FXML
    private DatePicker dpAttendanceDate;
    @FXML
    private TableView<?> tblAttendanceMarking;

    @FXML
    void btnSaveAttendanceOnAction(ActionEvent event) {
    }

    @FXML
    private void btnBackOnAction(javafx.event.ActionEvent event) {
        NavigationUtil.goBack(event);
    }
}
