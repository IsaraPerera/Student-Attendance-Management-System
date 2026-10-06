package lk.ijse.studentattendance.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.studentattendance.util.DBConnection;
import lk.ijse.studentattendance.util.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button loginButton;
    @FXML private Button signUpButton;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().setAll("ADMIN", "LECTURER", "STUDENT");
        
        loginButton.setOnAction(e -> handleLogin());
        signUpButton.setOnAction(e -> navigateTo("/lk/ijse/studentattendance/Register.fxml", "Create Account"));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty() || 
            role == null) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Save user details globally
                UserSession.createSession(username, role);

                if ("ADMIN".equals(role)) {
                    navigateTo("/lk/ijse/studentattendance/MainMenuAdmin.fxml", "Admin Dashboard");
                } else if ("LECTURER".equals(role)) {
                    navigateTo("/lk/ijse/studentattendance/MainMenuLecturer.fxml", "Lecturer Dashboard");
                } else {
                    errorLabel.setText("Student portal access is not configured yet.");
                }
            } else {
                errorLabel.setText("Invalid credentials or role.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Database error: " + e.getMessage());
        }
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("Error: FXML file not found at path -> " + fxmlPath);
                errorLabel.setText("Navigation error: Page not found.");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(resource);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load page.");
        }
    }
}