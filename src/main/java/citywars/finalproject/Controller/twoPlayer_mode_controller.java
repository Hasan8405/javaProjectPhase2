package citywars.finalproject.Controller;

import citywars.finalproject.Model.UserSession;
import citywars.finalproject.Model.UserSession2;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class twoPlayer_mode_controller {

    @FXML
    public Label  username1Label;
    @FXML
    public Label  username2Label;
    @FXML
    public Label  password1Label;
    @FXML
    public Label  password2Label;
    public UserSession userSession1;
    public UserSession2 userSession2;

    public void initialize () {
        userSession1 = UserSession.getInstance();
        userSession2 = UserSession2.getInstance();

        username1Label.setText(userSession1.getUsername());
        username2Label.setText(userSession2.getUsername());

        password1Label.setText(userSession1.getPassword());
        password2Label.setText(userSession2.getPassword());
    }

}
