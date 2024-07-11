package citywars.finalproject.Controller;

import citywars.finalproject.Model.CaptchaModel;
import citywars.finalproject.Model.RandomPasswordGenerator;
import citywars.finalproject.service.DatabaseManager;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signup_page_controller {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPassField;
    @FXML
    public TextField nicknameField;
    @FXML
    public TextField captchaField;
    @FXML
    public TextField emailField;
    @FXML
    public Label statusLabel;
    @FXML
    public Label randomPassLabel;
    @FXML
    private HBox captchaBox;
    @FXML
    private TextField fatherField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField petField;

    private DatabaseManager databaseManager;
    private CaptchaModel captchaModel;



    @FXML
    private void signup_action(ActionEvent event) throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmPassField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String captcha = captchaField.getText();
        String father = fatherField.getText();
        String color = colorField.getText();
        String pet = petField.getText();

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


        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPass.isEmpty() || nickname.isEmpty() || captcha.isEmpty() || father.isEmpty() || color.isEmpty() || pet.isEmpty()) {
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

        if (!password.equals(confirmPass)) {
            statusLabel.setText("Password confirmation does not match");
            return;
        }

        if (!emailMatcher.matches()) {
            statusLabel.setText("The entered email format should be <email>@<domain>.com");
            return;
        }

        if (!captchaModel.getGeneratedCaptcha().equals(captchaField.getText())) {
            statusLabel.setText("Captcha is incorrect.");
            return;
        }

        if (!databaseManager.insertUser(username, password, nickname, email, father, color, pet)) {
            statusLabel.setText("Failed to register user.");
            return;
        }

        statusLabel.setText("User registered successfully! now login");
        statusLabel.setStyle("-fx-text-fill: #00ec82;");


        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            try {
                Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(loginRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delay.play();


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
    public void move_to_login_Action (ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        databaseManager = new DatabaseManager();

        captchaModel = new CaptchaModel();
        captchaBox.setAlignment(Pos.CENTER); // تنظیم چینش به مرکز
        refreshCaptcha();
    }

    @FXML
    public void refreshCaptcha() {
        captchaBox.getChildren().clear(); // پاک کردن تصاویر قبلی

        List<Image> images = captchaModel.generateCaptcha();
        for (Image image : images) {
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(60); // اندازه مورد نظر را تنظیم کنید
            imageView.setFitWidth(35); // اندازه مورد نظر را تنظیم کنید
            captchaBox.getChildren().add(imageView);
        }

//        captchaLabel.setText("Enter the following Captcha: " + captchaModel.getGeneratedCaptcha());
    }
    @FXML
    public void Regenerate_Button_Action () {
        refreshCaptcha();
    }

}
