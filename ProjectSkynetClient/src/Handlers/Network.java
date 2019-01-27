package Handlers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {

    private static Socket ServerSocket = null;
    private static DataInputStream console = null;
    private static DataOutputStream streamOut = null;
    private static DataInputStream streamIn = null;

    public String LoginRequest(String cmd) {
        try {
            ServerSocket = new Socket(DataStore.getServerIP(), DataStore.getServerPort());
            //System.out.println("Connected: " + ServerSocket);
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }

        String data = "NULL";

        try {
            streamIn = new DataInputStream(new BufferedInputStream(ServerSocket.getInputStream()));
            streamOut = new DataOutputStream(ServerSocket.getOutputStream());
            console = new DataInputStream(System.in);

            streamOut.writeUTF(DataStore.getUniqueKey() + "|" + cmd + "|$");
            streamOut.flush();

            while (true) {
                data = streamIn.readUTF();
                if (data.substring(data.length() - 1).equals("$")) {
                    break;
                }
            }

        } catch (IOException ioe) {
            System.out.println("LoginRequest error: " + ioe.getMessage());
        }
        return data;
    }

    public String NewUserRequest(String cmd) {
        try {
            ServerSocket = new Socket(DataStore.getServerIP(), DataStore.getServerPort());
            //System.out.println("Connected: " + ServerSocket);
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }

        String data = "NULL";

        try {
            streamIn = new DataInputStream(new BufferedInputStream(ServerSocket.getInputStream()));
            streamOut = new DataOutputStream(ServerSocket.getOutputStream());
            console = new DataInputStream(System.in);

            streamOut.writeUTF(DataStore.getUniqueKey() + "|" + cmd + "|$");
            streamOut.flush();

            while (true) {
                data = streamIn.readUTF();
                if (data.substring(data.length() - 1).equals("$")) {
                    break;
                }
            }

            close();

        } catch (IOException ioe) {
            System.out.println("NewUserRequest error: " + ioe.getMessage());
        }
        return data;
    }

    public String SendRequest(String cmd) {

        String data = "NULL";
        try {
            streamIn = new DataInputStream(new BufferedInputStream(ServerSocket.getInputStream()));
            streamOut = new DataOutputStream(ServerSocket.getOutputStream());
            console = new DataInputStream(System.in);

            if (cmd.equals("EXIT")) {
                streamOut.writeUTF(DataStore.getUniqueKey() + "|" + DataStore.getStopKey() + "|$");
                streamOut.flush();
                DataStore.Stop_Readresponse();
                close();
            } else {
                streamOut.writeUTF(DataStore.getUniqueKey() + "|" + cmd + "|$");
                streamOut.flush();
            }


            while (DataStore.Get_Status_Readresponse()) {
                data = streamIn.readUTF();
                if (data.substring(data.length() - 1).equals("$")) {
                    break;
                }
            }

        } catch (IOException ioe) {
            System.out.println("SendRequest error: " + ioe.getMessage());
        }
        return data;
    }

    private void close() {
        try {
            if (console != null) console.close();
            if (streamIn != null) streamIn.close();
            if (streamOut != null) streamOut.close();
            if (ServerSocket != null) ServerSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
    }
}
