package Handlers;


import java.sql.*;
import java.util.Random;


public class SQL {


    private String SQL_UserDB = "Users";
    private String SQL_UserDataDB = "UserData";
    private String SQL_VionsyDB = "Vionsy";
    private String SQL_VionsyUserdataDB = "Vionsyuserdata";
    SQL_Query SQ = new SQL_Query();

    public String CheckUser(String Username, String Password) {

        String DB_Username = SQ.GetData("Username", SQL_UserDB, "Username = '" +Username + "'");

        if (Username.toUpperCase().equals(DB_Username)) {
            String DB_Userid = SQ.GetData("Userid", SQL_UserDB, "Username = '" + Username + "'");
            String DB_Password = SQ.GetData("Password", SQL_UserDB, "Userid = '" + DB_Userid + "'");
            if (Password.equals(DB_Password)) {
                return DB_Userid;
            } else {
                return "NULL";
            }
        } else {
            return "NULL";
        }
    }

    public boolean CreateUser(String Username, String Password, String Mail) {
        String Userid = "";
        while (true) {
            Userid = SQ.UserID_Generator().toUpperCase();
            if (!SQ.GetData("Userid", SQL_UserDB, "Userid = '" + Userid + "'").equals(Userid)) {
                break;
            }
        }
        if (SQ.GetData("Username", SQL_UserDB, "Username = '" + Username + "'").equals(Username)) {
            return false;
        } else {
            try {
                SQ.Setdata(SQL_UserDB, "Userid, Username, Password, Mail", "'" + Userid + "', '" + Username + "', '" + Password + "', '" + Mail + "'");
                SQ.Setdata(SQL_UserDataDB, "Userid, Value", "'" + Userid + "', 1000");
                return true;
            }
            catch (Exception ex){
                System.out.println("CreateUser error: " + ex.getMessage());
                return false;
            }
        }
    }

    public String ScanVionsy() {
        String returndata = "";
        String Vionsy = SQ.GetAllData("Vionsyname", SQL_VionsyDB);
        String[] VionsySplit = Vionsy.split("\\|");

        for (int i = 0; i < VionsySplit.length; i++) {
            returndata += VionsySplit[i] + " -   Adr: " + SQ.GetData("Vionsyadr", SQL_VionsyDB, "Vionsyname = '" + VionsySplit[i] + "'") + "|";
        }
        return returndata;
    }

    public void CreateVionsyAccount(String Vionsyadr, String Userid) {
        String VionsydataID = SQ.UniqueID_Generator(6);
        SQ.Setdata(SQL_VionsyUserdataDB, "Vionsydataid, Vionsyadr, Userid, Useradr, Uservalue", "'" + VionsydataID + "', '" + Vionsyadr + "', '" + Userid + "', 'NULL', '0'");
    }

    public boolean CheckVionsyUser(String Vionsyadr, String Userid) {
        boolean Accountexist = SQ.ExistsData(SQL_VionsyUserdataDB, "Vionsyadr = '" + Vionsyadr + "'", "Userid = '" + Userid + "'");
        //String data = SQ.GetData("*", SQL_VionsyUserdataDB, "Vionsyadr", Vionsyadr);
        if (Accountexist) {
            return true;
        } else {
            return false;
        }
    }

    public String GetVionsyName(String Vionsyadr) {
        return SQ.GetData("Vionsyname", SQL_VionsyDB, "Vionsyadr = '" + Vionsyadr + "'");
    }

    public boolean Deposit(String Userid, String Vionsyadr, double Value) {
        double UserValue = Double.parseDouble(GetUserValue(Userid));
        double VionsyUserValue = Double.parseDouble(GetVionsyUserValue(Userid, Vionsyadr));

        if ((UserValue - Value) < 0) {
            return false;
        } else {
            double NewVionsyUserValue = VionsyUserValue + Value;
            double NewUserValue = UserValue - Value;
            SQ.Updatedata(SQL_UserDataDB, "Value", String.valueOf(NewUserValue), "Userid = '" + Userid + "'");
            SQ.Updatedata(SQL_VionsyUserdataDB, "Uservalue", String.valueOf(NewVionsyUserValue), "Userid = '" + Userid + "' AND Vionsyadr = '" + Vionsyadr + "'");
            return true;
        }
    }

    public boolean Withdraw(String Userid, String Vionsyadr, double Value) {

        double UserValue = Double.parseDouble(GetUserValue(Userid));
        double VionsyUserValue = Double.parseDouble(GetVionsyUserValue(Userid, Vionsyadr));

        if ((VionsyUserValue - Value) < 0) {
            return false;
        } else {
            double NewVionsyUserValue = VionsyUserValue - Value;
            double NewUserValue = UserValue + Value;
            SQ.Updatedata(SQL_UserDataDB, "Value", String.valueOf(NewUserValue), "Userid = '" + Userid + "'");
            SQ.Updatedata(SQL_VionsyUserdataDB, "Uservalue", String.valueOf(NewVionsyUserValue), "Userid = '" + Userid + "' AND Vionsyadr = '" + Vionsyadr + "'");
            return true;
        }
    }

    public String GetUserValue(String Userid) {
        return SQ.GetData("Value", SQL_UserDataDB, "Userid = '" + Userid + "'");
    }

    public String GetVionsyUserValue(String Userid, String Vionsyadr) {
        return SQ.GetData("Uservalue", SQL_VionsyUserdataDB, "Userid = '" + Userid + "' AND Vionsyadr = '" + Vionsyadr + "'");
    }

}

class SQL_Query {

    private String SQL_URL = "jdbc:mysql://35.195.175.23:3306/Project_Skynet_DB?useSSL=false";
    private String SQL_USER = "PS_User";
    private String SQL_PASS = "Jeger1lilleadmin!";

    public String GetAllData(String DATA, String FROM) {
        String data = "";
        try {
            Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            Statement stmt = conn.createStatement();
            String SQLquery = "SELECT * FROM " + FROM;
            ResultSet rset = stmt.executeQuery(SQLquery);

            while (rset.next()) {
                data += rset.getString(DATA) + "|";
            }
            return data;
        } catch (SQLException ex) {
            System.out.println("GetAllData error: " + ex.getMessage());
            data = "NULL";
        }
        return data;
    }

    public String GetData(String DATA, String FROM, String WHERE) {
        try {
            Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            Statement stmt = conn.createStatement();
            String SQLquery = "SELECT * FROM " + FROM + " WHERE " + WHERE;
            ResultSet rset = stmt.executeQuery(SQLquery);
            String data = "";
            while (rset.next()) {
                data = rset.getString(DATA);
            }
            return data;
        } catch (SQLException ex) {
            System.out.println("GetData error: " + ex.getMessage());
        }
        return "NULL";
    }

    public void Setdata(String INTO, String COLUMN, String VALUE) {

        try {
            Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            Statement stmt = conn.createStatement();
            String SQLquery = "INSERT INTO " + INTO + " (" + COLUMN + ") VALUES (" + VALUE + ")";
            stmt.executeUpdate(SQLquery);
        } catch (SQLException ex) {
            System.out.println("Setdata error: " + ex.getMessage());
        }
    }

    public void Updatedata(String UPDATE, String COLUMN, String VALUE, String WHERE) {
        try {
            Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            Statement stmt = conn.createStatement();
            String SQLquery = "UPDATE " + UPDATE + " SET " + COLUMN + " = '" + VALUE + "' WHERE " + WHERE;
            stmt.executeUpdate(SQLquery);
        } catch (SQLException ex) {
            System.out.println("Updatedata error: " + ex.getMessage());
        }
    }

    public boolean ExistsData(String FROM, String WHERE_1, String WHERE_2){
        try {
            Connection conn = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            Statement stmt = conn.createStatement();
            String SQLquery = "SELECT * FROM " + FROM + " WHERE " + WHERE_1 + " AND " + WHERE_2;
            ResultSet rset = null;
            rset = stmt.executeQuery(SQLquery);
            if (rset.isBeforeFirst()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("ExistsData error: " + ex.getMessage());
        }
        return false;
    }

    public String UserID_Generator() {
        Random r = new Random();
        final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] s = new char[7];
        int n = r.nextInt(0x1000000);

        for (int i = 0; i < 7; i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        return new String(s);
    }

    public String UniqueID_Generator(int length) {
        Random r = new Random();
        final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] s = new char[length - 1];
        int n = r.nextInt(0x1000000);

        for (int i = 0; i < length-1; i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        return new String(s);
    }

}
