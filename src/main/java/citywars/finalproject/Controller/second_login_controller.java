package citywars.finalproject.Controller;

import citywars.finalproject.Model.CaptchaModel;
import citywars.finalproject.Model.UserSession2;
import citywars.finalproject.service.DatabaseManager;
import citywars.finalproject.Model.UserSession;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class second_login_controller {

    @FXML
    private TextField usernameField;
    @FXML
    private Label statusLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label time_count;
    @FXML
    private Button loginButton;
    private int loginAttempts = 0;
    private int lockoutTime = 0; // Time in seconds
    private Timeline timeline;
    private DatabaseManager databaseManager;

    @FXML
    private void initialize() {
        databaseManager = new DatabaseManager();
    }
    @FXML
    public void loginButtonAction (ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        if (!databaseManager.isUsernameTaken(username)) {
            statusLabel.setText("Username doesn’t exist!");
            return;
        }



        if (databaseManager.isValidCredentials(username, password)) {
            statusLabel.setText("Login successful!");
            loginAttempts = 0;
            statusLabel.setStyle("-fx-text-fill: #00ec82;");

            // Store user details in UserSession
            UserSession2 userSession2 = UserSession2.getInstance();
            userSession2.setUsername(username);
            userSession2.setPassword(password);
            userSession2.setNickname(databaseManager.getUserNickname(username));
            userSession2.setEmail(databaseManager.getUserEmail(username));

            String selectedMode = select_mode_controller.getSelectedMode();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                // Redirect to game mode
                try {
                    Parent gameRoot;
                    if ("betting".equals(selectedMode)) {
                        gameRoot = FXMLLoader.load(getClass().getResource("/View/betting-mode.fxml"));
                    } else {
                        gameRoot = FXMLLoader.load(getClass().getResource("/View/two-player-mode.fxml"));
                    }

                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(gameRoot);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            delay.play();

        } else {
            statusLabel.setText("Invalid username or password.");

            loginAttempts++;
            lockoutTime = 5 * loginAttempts;
            startLockoutTimer();
        }
    }
    @FXML
    public void move_to_signup_action (ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/signup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }

    private void startLockoutTimer() {
        loginButton.setDisable(true);
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (lockoutTime > 0) {
                time_count.setText("Please wait: " + lockoutTime + " seconds");
                lockoutTime--;
            } else {
                time_count.setText("");
                loginButton.setDisable(false);
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    public void resetpass_page_action (ActionEvent event) throws IOException{
        Parent resetPassRoot = FXMLLoader.load(getClass().getResource("/View/reset-pass.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(resetPassRoot);
        stage.setScene(scene);
        stage.show();
    }

}
