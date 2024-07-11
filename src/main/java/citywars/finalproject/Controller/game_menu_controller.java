package citywars.finalproject.Controller;

import citywars.finalproject.Model.UserSession;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class game_menu_controller {
        @FXML
        public void profile_click_action(MouseEvent event) {
            try {
                Parent profileRoot = FXMLLoader.load(getClass().getResource("/View/profile.fxml"));
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(profileRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void PlayButtonAction(ActionEvent event) throws IOException{
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/select-mode.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loginRoot);
        stage.setScene(scene);
        stage.show();
    }

    public void LogButtonAction(ActionEvent event) {
    }

    public void StoreButtonAction(ActionEvent event) {
    }
    @FXML
    public void logout_click(MouseEvent event) {
        UserSession.getInstance().logout();

        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
