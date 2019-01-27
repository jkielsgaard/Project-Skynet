package Handlers;

public class Commands {

    Network net = new Network();

    public boolean LoginRequest(String Username, String Password) {
        String cmd = "LOGIN|" + Username.toUpperCase() + "|" + Password;
        String response = LoginRequest(cmd);
        String[] response_split = response.split("\\|");

        if (response_split[0].equals("true")) {
            DataStore.setUserID(response_split[1]);
            return true;
        } else {
            return false;
        }
    }

    public boolean NewUserRequest(String Username, String Password, String Mail) {
        String cmd = "NEWUSER|" + Username.toUpperCase() + "|" + Password + "|" + Mail.toUpperCase();
        String response = NewUserRequest(cmd);
        String[] response_split = response.split("\\|");

        if (response_split[0].equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public String GetUserValue() {
        String cmd = "GETUSERVALUE|" + DataStore.getUserID();
        String response = Request(cmd);
        String[] response_split = response.split("\\|");
        return response_split[0];
    }

    public String GetVionsyUserValue() {
        String cmd = "GETVIONSYUSERVALUE|" + DataStore.getUserID() + "|" + DataStore.getVionsyadr();
        String response = Request(cmd);
        String[] response_split = response.split("\\|");
        return response_split[0];
    }

    public String SendRequest(String[] cmd) {
        String[] response_split;
        String _cmd;
        String response;
        String request;

        switch (cmd[0].toUpperCase()) {

            // region SCAN
            case "SCAN":
                _cmd = "SCAN|" + cmd[1].toUpperCase(); // cmd[1] = VIONSY or NODES
                response = Request(_cmd);
                response_split = response.split("\\|");
                if (response_split[0].equals("true")) {
                    response = response.substring(5);
                    request = response.substring(0, response.length() - 2);
                } else {
                    request = "Cannot Scan for " + cmd[1];
                }
                break;
            // endregion
            // region CREATE
            case "CREATE":
                _cmd = "CREATE|" + cmd[1].toUpperCase() + "|" + DataStore.getUserID(); // cmd[1] = VIONSYADR | cmd[2] = Userid
                response = Request(_cmd);
                response_split = response.split("\\|");
                if (response_split[0].equals("true")) {
                    request = response_split[1];
                } else {
                    request = response_split[1];
                }
                break;
            // endregion
            // region CONNECT
            case "CONNECT":
                _cmd = "CONNECT|" + cmd[1].toUpperCase() + "|" + DataStore.getUserID(); // cmd[1] = VIONSYADR
                response = Request(_cmd);
                response_split = response.split("\\|");
                if (response_split[0].equals("true")) {
                    DataStore.setVionsyadr(cmd[1]);
                    DataStore.setVionsyname(response_split[1]);
                    request = "Now connect to " + response_split[1];
                } else {
                    request = response_split[1];
                }
                break;
            // endregion
            // region DEPOSIT
            case "DEPOSIT":
                _cmd = "DEPOSIT|" + DataStore.getUserID() + "|" + DataStore.getVionsyadr() + "|" + cmd[1]; // cmd[1] = value
                response = Request(_cmd);
                response_split = response.split("\\|");

                if (response_split[0].equals("true")) {
                    request = "Deposit completed";
                } else {
                    request = response_split[1];
                }
                break;
            // endregion
            // region WITHDRAW
            case "WITHDRAW":
                _cmd = "WITHDRAW|" + DataStore.getUserID() + "|" + DataStore.getVionsyadr() + "|" + cmd[1]; // cmd[1] = value
                response = Request(_cmd);
                response_split = response.split("\\|");

                if (response_split[0].equals("true")) {
                    request = "Withdraw completed";
                } else {
                    request = response_split[1];
                }
                break;
            // endregion
            // region WITHDRAW
            case "EXIT":
                _cmd = "EXIT";
                Request(_cmd);
                request = "";
                break;
            // endregion
            default:
                return " Command do not exist";
        }
        return request;
    }

    private String Request(String cmd) {
        return net.SendRequest(cmd);
    }

    private String LoginRequest(String cmd) {
        return net.LoginRequest(cmd);
    }

    private String NewUserRequest(String cmd) {
        return net.NewUserRequest(cmd);
    }

}
