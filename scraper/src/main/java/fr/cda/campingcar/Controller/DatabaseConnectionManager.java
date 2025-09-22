package fr.cda.campingcar.Controller;


import java.util.ArrayList;
import java.util.List;
/**
 * Classe singleton pour gérer l'état de la connexion à la base de données.
 * Permet de suivre le statut de connexion et de notifier les auditeurs en cas de changement.
 */
public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;
    private boolean isDatabaseConnected = false;
    private final List<DatabaseStatusListener> listeners = new ArrayList<>();
    /**
     * Constructeur privé pour empêcher l'instanciation directe (Singleton).
     */
    private DatabaseConnectionManager() {}




    /**
     * Retourne l'instance unique de la classe.
     *
     * @return L'instance unique de {@link DatabaseConnectionManager}.
     */
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    public boolean isConnected() {
        return isDatabaseConnected;
    }

    /**
     * Définit le statut de connexion à la base de données et notifie les auditeurs.
     *
     * @param isConnected true si la base de données est connectée, false sinon.
     */
    public void setConnectionStatus(boolean isConnected) {
        this.isDatabaseConnected = isConnected;
        notifyListeners();
    }

    /**
     * Ajoute un auditeur qui sera notifié lorsque le statut de la base de données change.
     *
     * @param listener Un objet implémentant l'interface {@link DatabaseStatusListener}.
     */
    public void addListener(DatabaseStatusListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifie tous les auditeurs du changement de statut de la base de données.
     */
    private void notifyListeners() {
        for (DatabaseStatusListener listener : listeners) {
            listener.onDatabaseStatusChanged(isDatabaseConnected);
        }
    }
    /**
     * Interface pour les auditeurs de changement de statut de la base de données.
     */
    public interface DatabaseStatusListener {

        /**
         * Méthode appelée lorsque le statut de connexion à la base de données change.
         *
         * @param isConnected true si la base de données est connectée, false sinon.
         */
        void onDatabaseStatusChanged(boolean isConnected);
    }
}
