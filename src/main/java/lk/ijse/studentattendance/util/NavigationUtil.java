package lk.ijse.studentattendance.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationUtil {

    public static void goBack(ActionEvent event) {
        UserSession session = UserSession.getInstance();

        String fxmlPath = "/lk/ijse/studentattendance/Login.fxml";
        String title = "Login";

        if (session != null && session.getRole() != null) {
            switch (session.getRole().toUpperCase()) {
                case "ADMIN":
                    fxmlPath = "/lk/ijse/studentattendance/MainMenuAdmin.fxml";
                    title = "Admin Dashboard";
                    break;
                case "LECTURER":
                    fxmlPath = "/lk/ijse/studentattendance/MainMenuLecturer.fxml";
                    title = "Lecturer Main Menu";
                    break;
                case "STUDENT":
                    fxmlPath = "/lk/ijse/studentattendance/StudentDashboard.fxml";
                    title = "Student Dashboard";
                    break;
            }
        }

        try {
            URL resource = NavigationUtil.class.getResource(fxmlPath);
            if (resource == null) {
                System.err.println("Cannot find FXML resource at path: " + fxmlPath);
                return;
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}