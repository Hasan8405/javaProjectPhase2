package citywars.finalproject.Controller;

import citywars.finalproject.Model.CaptchaModel;
import citywars.finalproject.Model.RandomPasswordGenerator;
import citywars.finalproject.Model.UserSession;
import citywars.finalproject.service.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profile_page_controller {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField passwordField2;
    @FXML
    public PasswordField confirmPassField;
    @FXML
    public TextField nicknameField;
    @FXML
    public TextField emailField;
    @FXML
    public Label statusLabel;
    @FXML
    public Label randomPassLabel;
    @FXML
    private ImageView toggleImageView;
    private DatabaseManager databaseManager;
    private UserSession userSession;
    private boolean isPasswordVisible = false;
    @FXML
    private void initialize() {
        passwordField2.managedProperty().bind(passwordField2.visibleProperty());
        passwordField2.textProperty().bindBidirectional(passwordField.textProperty());

        databaseManager = new DatabaseManager();

        userSession = UserSession.getInstance();

        usernameField.setText(userSession.getUsername());
        passwordField.setText(userSession.getPassword());
        nicknameField.setText(userSession.getNickname());
        emailField.setText(userSession.getEmail());


    }
    @FXML
    public void saveButtonAction () {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmPassField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();

        //check username...
        String checkUsername = "^[a-zA-Z0-9_]+$";
        Pattern usernamePattern = Pattern.compile(checkUsername);
        Matcher usernameChecker = usernamePattern.matcher(username);

        //check pass
        String checkPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passPattern = Pattern.compile(checkPass);
        Matcher passMatcher = passPattern.matcher(password);

        //check email
        String checkEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(checkEmail);
        Matcher emailMatcher = emailPattern.matcher(email);


        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPass.isEmpty() || nickname.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        if (!usernameChecker.matches()) {
            statusLabel.setText("Username can only contain uppercase and lowercase letters, numbers and underscores.");
            return;
        }

        if (databaseManager.isUsernameTaken(username)) {
            statusLabel.setText("Username already taken.");
            return;
        }

        if (!passMatcher.matches()) {
            statusLabel.setText("Password must have at least 8 characters including a-z, A-Z, 0-9, and @$!%*?&.");
            return;
        }

        if (userSession.getPassword().equals(password)) {
            statusLabel.setText("Please enter a new password!");
            return;
        }

        if (!password.equals(confirmPass)) {
            statusLabel.setText("Password confirmation does not match");
            return;
        }

        if (!emailMatcher.matches()) {
            statusLabel.setText("The entered email format should be <email>@<domain>.com");
            return;
        }

        statusLabel.setText("Your changes applied successfully!");
        statusLabel.setStyle("-fx-text-fill: #00ec82;");

        userSession.setUsername_updateDB(username);
        userSession.setPassword_updateDB(password);
        userSession.setNickname_updateDB(nickname);
        userSession.setEmail_updateDB(email);

    }
    @FXML
    public void back_click_action (MouseEvent event) throws IOException {
        Parent gameRoot = FXMLLoader.load(getClass().getResource("/View/game-menu.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(gameRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void password_click_event () {
        randomPassLabel.setVisible(true);

        Random random = new Random();
        int randomNumer = random.nextInt(3)+8;
        String randomPass = RandomPasswordGenerator.generatePassword(randomNumer);

        randomPassLabel.setText(randomPass);
        passwordField.setText(randomPass);
    }

    @FXML
    private void visibilty_click (MouseEvent event) {
        if (isPasswordVisible) {
            passwordField.setVisible(true);
            passwordField2.setVisible(false);
            toggleImageView.setImage(new Image(getClass().getResourceAsStream("/images/show.png"))); // Set the eye image
        } else {
            passwordField.setVisible(false);
            passwordField2.setVisible(true);
            toggleImageView.setImage(new Image(getClass().getResourceAsStream("/images/hide.png"))); // Set the eye-slash image
        }
        isPasswordVisible = !isPasswordVisible;
    }
}
