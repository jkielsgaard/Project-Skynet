package Handlers;

public class Commands {

    SQL sql = new SQL();

    public String Request(String[] cmd) {
        String request;
        switch (cmd[1]) {

            //region LOGIN
            case "LOGIN":
                String CC = sql.CheckUser(cmd[2], cmd[3]); //cmd[2] = Username | cmd[3] = Password
                if (CC.equals("NULL")) {
                    request = "false|Wrong Username or Password";
                } else {
                    request = "true|" + CC;
                }
                break;
            //endregion
            //region NEWUSER
            case "NEWUSER":
                boolean UserCreated = sql.CreateUser(cmd[2], cmd[3], cmd[4]); //cmd[2] = Username | cmd[3] = Password | cmd[4] = Mail
                if (UserCreated == true) {
                    request = "true";
                } else {
                    request = "false|NULL";
                }
                break;
            //endregion
            //region SCAN
            case "SCAN":
                if (cmd[2].equals("VIONSY")) {
                    request = "true|" + sql.ScanVionsy();
                } else if (cmd[2].equals("NODES")) {
                    request = "true|NODES";
                } else {
                    request = "false|NULL";
                }
                break;
            //endregion
            //region CREATE
            case "CREATE":
                sql.CreateVionsyAccount(cmd[2], cmd[3]);
                request = "true|Account create please connect to " + sql.GetVionsyName(cmd[2]);
                break;
            //endregion
            //region CONNECT
            case "CONNECT":
                boolean Account = sql.CheckVionsyUser(cmd[2], cmd[3]); // cmd[2] = Vionsyadr | cmd[3] = Userid
                if (Account == true) {
                    request = "true|" + sql.GetVionsyName(cmd[2]);
                } else {
                    request = "false|No account on " + sql.GetVionsyName(cmd[2]) + "System - Type CREATE [Vionsyadr] to create and account";
                }
                break;
            //endregion
            //region DEPOSIT
            case "DEPOSIT":
                boolean DepositCheck = sql.Deposit(cmd[2], cmd[3], Double.parseDouble(cmd[4])); //cmd[2] = Userid | cmd[3] = Vionsyadr | cmd[4] = Value
                if (DepositCheck == true) {
                    request = "true";
                } else {
                    request = "false|not enough Value for deposit";
                }
                break;
            //endregion
            //region WITHDRAW
            case "WITHDRAW":
                boolean WithdrawCheck = sql.Withdraw(cmd[2], cmd[3], Double.parseDouble(cmd[4])); //cmd[2] = Userid | cmd[3] = Vionsyadr | cmd[4] = Value
                if (WithdrawCheck == true) {
                    request = "true";
                } else {
                    request = "false|not enough Value for withdraw";
                }
                break;
            //endregion
            //region GETUSERVALUE
            case "GETUSERVALUE":
                request = sql.GetUserValue(cmd[2]); //cmd[2] = Userid
                break;
            //endregion
            //region GETVIONSYUSERVALUE
            case "GETVIONSYUSERVALUE":
                request = sql.GetVionsyUserValue(cmd[2], cmd[3]); //cmd[2] = Userid | cmd[3] = Vionsyadr
                break;
            //endregion

            default:
                return "Command do not exist";
        }
        return request;
    }

}
