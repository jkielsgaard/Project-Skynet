package Views.Controllers;

import Handlers.Commands;
import Handlers.DataStore;
import Handlers.Network;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {
    Network net = new Network();

    @FXML
    private Label lb_MainSkyAddr;
    @FXML
    private Label lb_UserValue;

    @FXML
    private void initialize() {

        ActionEvent actionEvent = new ActionEvent();
        //Clicked_Console(actionEvent);

        new Thread(() -> {
            UpdateUI();
        }).start();
    }

    private void UpdateUI() {
        Commands cmd = new Commands();
        while (DataStore.getStatus_UI_Updater() == false) {
            try {
                Platform.runLater ( () -> lb_UserValue.setText(cmd.GetUserValue()));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
/*
    public void Clicked_Console(ActionEvent actionEvent) {
        try {
            Parent root_Console = FXMLLoader.load(getClass().getResource("/Views/Console.fxml"));
            Stage Console = new Stage();
            Console.setResizable(false);
            Console.setTitle("[Project Skynet]");
            Console.setScene(new Scene(root_Console, 800, 500));
            Console.show();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
    */
}
