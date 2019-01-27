package Views.Controllers;


import Handlers.Commands;
import Handlers.DataStore;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Console {

    @FXML
    private TextArea ta_consoleview;
    @FXML
    private TextField tf_Command;
    @FXML
    private Label lb_uservalue;
    @FXML
    private Label lb_vionsyname;
    @FXML
    private Label lb_vionsyadr;
    @FXML
    private Label lb_vionsyuservalue;

    @FXML
    public void onEnter(ActionEvent ae) {
        Commands cmd = new Commands();

        if (tf_Command.getText().isEmpty()) {
            ta_consoleview.appendText("No cmd enters" + System.getProperty("line.separator"));
        } else if (tf_Command.getText().toUpperCase().equals("CLEAR")) {
            ta_consoleview.clear();
        } else if (tf_Command.getText().toUpperCase().equals("NEWCONSOLE")) {
            ta_consoleview.appendText(tf_Command.getText() + System.getProperty("line.separator"));
            try {
                Parent root_Console = FXMLLoader.load(getClass().getResource("/Views/Console.fxml"));
                Stage Console = new Stage();
                Console.setResizable(false);
                Console.setTitle("[Project Skynet]");
                Console.setScene(new Scene(root_Console, 800, 500));
                Console.show();
            } catch (IOException ioe) {
                System.out.println("OpenNewConsole error: " + ioe.getMessage());
            }
        } else if (tf_Command.getText().toUpperCase().equals("EXIT")) {
            String[] exit = {"EXIT"};
            cmd.SendRequest(exit);
            System.exit(0);
        } else {
            ta_consoleview.appendText(tf_Command.getText() + System.getProperty("line.separator"));
            String[] scmd = tf_Command.getText().split(" ");
            String[] RequestDataSplit = cmd.SendRequest(scmd).split("\\|");
            for(int i = 0; i < RequestDataSplit.length; i++) {
                ta_consoleview.appendText(RequestDataSplit[i] + System.getProperty("line.separator"));
            }
        }
        ta_consoleview.appendText(">> ");
        tf_Command.clear();
    }

    @FXML
    private void initialize() {
        ta_consoleview.appendText("Welcome to SkyNet console v 0.1" + System.getProperty("line.separator") + System.getProperty("line.separator"));
        ta_consoleview.appendText("Type Help or ? for more information" + System.getProperty("line.separator") + System.getProperty("line.separator"));
        ta_consoleview.appendText(">> ");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tf_Command.requestFocus();
            }
        });

        new Thread(() -> {
            UpdateUI();
        }).start();
    }

    private void UpdateUI() {
        while (DataStore.getStatus_UI_Updater() == false) {
            Commands cmd = new Commands();
            try {
                if (!DataStore.getVionsyname().isEmpty())
                {
                    Platform.runLater ( () ->  lb_vionsyname.setText (DataStore.getVionsyname()));
                    Platform.runLater ( () ->  lb_vionsyadr.setText (DataStore.getVionsyadr()));
                    Platform.runLater ( () ->  lb_vionsyuservalue.setText (cmd.GetVionsyUserValue()));
                }
                Platform.runLater ( () ->  lb_uservalue.setText (cmd.GetUserValue()));
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("UpdateUI error: " + ex.getMessage());
            }
        }
    }
}
