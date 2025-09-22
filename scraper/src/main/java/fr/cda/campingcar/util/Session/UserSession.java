package fr.cda.campingcar.util.Session;

import fr.cda.campingcar.modele.Client;

public class UserSession {
    private static UserSession instance;
    private Client loggedInClient;

    private UserSession(Client client) {
        this.loggedInClient = client;
    }

    public static void createSession(Client client) {
        if (instance == null) {
            instance = new UserSession(client);
        }
    }

    public static UserSession getInstance() {
        return instance;
    }

    public static void clearSession() {
        instance = null;
    }

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public static void ShowSessionINFO() {
        Client loggedInClient = UserSession.getInstance().getLoggedInClient();

        if (loggedInClient != null) {
            System.out.println("Current user email: " + loggedInClient.getEmail());
            // Use loggedInClient information as needed
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}
