/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.studentattendance.controller;

/**
 *
 * @author Admin
 */


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.studentattendance.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Button registerButton;
    @FXML private Button resetButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("ADMIN", "LECTURER", "STUDENT");

        registerButton.setOnAction(e -> handleRegister());
        resetButton.setOnAction(e -> clearFields());
        backButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/Login.fxml", "Login"));
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setText("Username, password, and role are required.");
            return;
        }

        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            stmt.executeUpdate();
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("User registered successfully!");
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Registration failed. Username may already exist.");
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        phoneField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}