package citywars.finalproject.Controller;

import citywars.finalproject.Model.RandomPasswordGenerator;
import citywars.finalproject.service.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class resetpass_controller {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPassField;
    @FXML
    private TextField fatherField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField petField;
    @FXML
    public Label statusLabel;
    @FXML
    public Label randomPassLabel;
    private DatabaseManager databaseManager;

    @FXML
    private void initialize() {
        databaseManager = new DatabaseManager();
    }
    @FXML
    public void SetPassAction (ActionEvent event) throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmPassField.getText();
        String father = fatherField.getText();
        String color = colorField.getText();
        String pet = petField.getText();

        //check pass
        String checkPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passPattern = Pattern.compile(checkPass);
        Matcher passMatcher = passPattern.matcher(password);


        if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || father.isEmpty() || color.isEmpty() || pet.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        if (!databaseManager.isUsernameTaken(username)) {
            statusLabel.setText("Username doesn’t exist!");
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

        if (!databaseManager.isValidCredentials(username, father, color, pet)) {
            statusLabel.setText("Security answers do not match our records. Please try again.");
            return;
        }

        statusLabel.setText("Your password changed successfully! Now login...");
        statusLabel.setStyle("-fx-text-fill: #00ec82;");

        databaseManager.updateUserField(username, "password", password);

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
}
