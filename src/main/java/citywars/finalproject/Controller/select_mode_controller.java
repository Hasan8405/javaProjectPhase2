package citywars.finalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class select_mode_controller {
    private static String selectedMode;

    public void TwoPlayerButton(ActionEvent event) throws IOException {
        selectedMode = "normal";
        navigateToLogin(event);
    }

    public void BettingButton(ActionEvent event) {
        selectedMode = "betting";
        navigateToLogin(event);
    }
    private void navigateToLogin(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/login2.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSelectedMode() {
        return selectedMode;
    }

    public void back_click_action(MouseEvent event) throws IOException{
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/game_menu_controller.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }
}
