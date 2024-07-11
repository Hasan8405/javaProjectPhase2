package citywars.finalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class start_page_controller {
    @FXML
    public void loginButtonAction (ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void signupButtonAction (ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/signup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }
//    private UserService userService;
//
//    public start_page_controller() {
//        this.userService = new UserService();
//    }
//
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        User firstUser = userService.getFirstUser();
//        if (firstUser != null) {
//            welcomeText.setText("Name: " + firstUser.getName());
//        } else {
//            welcomeText.setText("No user found");
//        }
//    }
}
