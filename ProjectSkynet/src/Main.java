import Handlers.Config;
import Handlers.SQL;
import Handlers.Network;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.Run();
    }

    //Config con = new Config();
    //SQL sql = new SQL();
    Network net = new Network();

    private void Run() {
        try {
            net.StartServer();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
