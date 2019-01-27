package Handlers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

public class Network {

    private Socket ClientSocket = null;
    private ServerSocket server = null;
    private Boolean Stop = false;

    public void StartServer() {
        try {
            server = new ServerSocket(9595);

            while (Stop == false) {
                ClientSocket = server.accept();
                ClientHandler client = new ClientHandler();
                client.ClientSession(ClientSocket);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}

class ClientHandler {
    private Socket ClientSocket = null;
    private DataInputStream console = null;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

    Commands cmd = new Commands();

    public void ClientSession(Socket inClientSocket) {
        this.ClientSocket = inClientSocket;
        Thread ctThread = new Thread(() -> ClientSessionWork());
        ctThread.start();
    }

    private void ClientSessionWork() {
        try {
            ProcessingRequest();
            close();
        } catch (IOException ioe) {
            System.out.println("ClientSessionWork error: " + ioe.getMessage());
        }
    }

    private void ProcessingRequest() {
        try {
            while (true) {
                streamIn = new DataInputStream(new BufferedInputStream(ClientSocket.getInputStream()));
                streamOut = new DataOutputStream(ClientSocket.getOutputStream());
                console = new DataInputStream(System.in);
                String data;

                while (true) {
                    data = streamIn.readUTF();
                    if (data.substring(data.length() - 1).equals("$")) {
                        System.out.println("[From Client] " + ClientSocket);
                        break;
                    }
                }
                String[] sData = data.split("\\|");

                if (sData[1].equals(DataStore.getStopKey())) {
                    break;
                }

                if (sData[0].equals(DataStore.getUniqueKey())) {
                    streamOut.writeUTF(cmd.Request(sData) + "|$");
                    streamOut.flush();
                }
            }
        } catch (IOException ioe) {
            System.out.println("ProcessingRequest IOException error: " + ioe.getMessage());
        } catch (PatternSyntaxException ex) {
            System.out.println("ProcessingRequest PatternSyntaxException error: " + ex.getMessage());
        }
    }

    private void close() throws IOException {
        try {
            if (console != null) console.close();
            if (streamIn != null) streamIn.close();
            if (streamOut != null) streamOut.close();
            if (ClientSocket != null) ClientSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
    }
}