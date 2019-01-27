import Handlers.DataStore;
import Handlers.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Network net = new Network();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root_login = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        primaryStage.setTitle("[Project Skynet] Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root_login, 450, 150));
        primaryStage.show();
    }

    @Override
    public void stop() {
        DataStore.stop_UI_Updater();
        if (DataStore.Get_Status_Readresponse() == true) {
            net.SendRequest("EXIT");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
