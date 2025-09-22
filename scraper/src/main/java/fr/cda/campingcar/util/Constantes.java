package fr.cda.campingcar.util;

public class Constantes {

    private static String user = "";
    private static String password = "";
    private static String host = "";

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Constantes.user = user;
    }


    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Constantes.password = password;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Constantes.host = host;
    }
}
