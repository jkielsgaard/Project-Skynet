package Views.Controllers;

import Handlers.Commands;
import Handlers.DataStore;
import Handlers.Network;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sun.applet.Main;

import java.io.IOException;

public class Login {
   // Network net = new Network();
    Commands cmd = new Commands();

    @FXML
    private TextField tf_user;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Button btn_Login;

    @FXML
    public void onEnter(ActionEvent ae){
        Clicked_Login(ae);
    }

    @FXML
    public void Clicked_Login(ActionEvent event) {
        Boolean respone = cmd.LoginRequest(tf_user.getText(), pf_password.getText());
        tf_user.clear();
        pf_password.clear();

        if (respone) {
            try {
                Parent root_Console = FXMLLoader.load(getClass().getResource("/Views/Console.fxml"));
                Stage Console = new Stage();
                Console.setResizable(false);
                Console.setTitle("[Project Skynet]");
                Console.setScene(new Scene(root_Console, 800, 460));
                Console.show();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            //region old code
            /*
            try {
                Parent root_MainWin = FXMLLoader.load(getClass().getResource("/Views/MainWindow.fxml"));
                Stage MainWin = new Stage();
                MainWin.setResizable(false);
                MainWin.setTitle("[Project Skynet]");
                MainWin.setScene(new Scene(root_MainWin, 200, 500));
                MainWin.show();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            */
            //endregion

            Stage Login = (Stage) btn_Login.getScene().getWindow();
            Login.close();

        } else {
            Messagebox("Fail","Wrong Password or Username","");
        }
    }

    public void Clicked_Cancel(ActionEvent event) {
        System.exit(0);
    }

    public void Clicked_NewUser(ActionEvent actionEvent) {
        try {
            Parent root_MainWin = FXMLLoader.load(getClass().getResource("/Views/NewUser.fxml"));
            Stage MainWin = new Stage();
            MainWin.setResizable(false);
            MainWin.setTitle("[Project Skynet] NewUser");
            MainWin.setScene(new Scene(root_MainWin, 300, 300));
            MainWin.show();
        } catch (IOException ioe) {
            System.out.println("Clicked_NewUser error: " + ioe.getMessage());
        }
    }

    private void Messagebox(String Title, String HanderText, String Content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(HanderText);
        alert.setContentText(Content);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }
}
