package Handlers;

public class DataStore {

    private static String UniqueKey = "2CFE59D78A48B6CA98D21B581DFD9";

    public static String getUniqueKey() {
        return UniqueKey;
    }


    private static String StopKey = "2CA45FD54E";

    public static String getStopKey() {
        return StopKey;
    }


    private static String ServerIP = "127.0.0.1";

    public static String getServerIP() {
        return ServerIP;
    }


    private static int ServerPort = 9595;

    public static int getServerPort() {
        return ServerPort;
    }


    private static boolean Readresponse = true;

    public static boolean Get_Status_Readresponse() {
        return Readresponse;
    }

    public static void Stop_Readresponse() {
        Readresponse = false;
    }


    private static String UserID;

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String _UserID) {
        UserID = _UserID;
    }


    private static boolean UI_Updater = false;

    public static boolean getStatus_UI_Updater() {
        return UI_Updater;
    }

    public static void stop_UI_Updater() {
        UI_Updater = true;
    }


    private static String Vionsyadr = "";

    public static String getVionsyadr() {
        return Vionsyadr;
    }

    public static void setVionsyadr(String _Vionsyadr) {
        Vionsyadr = _Vionsyadr;
    }


    private static String Vionsyname = "";

    public static String getVionsyname() {
        return Vionsyname;
    }

    public static void setVionsyname(String _Vionsyname) {
        Vionsyname = _Vionsyname;
    }
}
