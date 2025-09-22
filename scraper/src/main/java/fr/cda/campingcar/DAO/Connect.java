package fr.cda.campingcar.DAO;

import fr.cda.campingcar.util.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect extends Constantes {
        public static Connection connection() {
            try {
                Connection conn = DriverManager.getConnection(Constantes.getHost(), Constantes.getUser(), Constantes.getPassword());
                System.out.println("Connexion réussie");
                return conn;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Échec de la connexion. Veuillez vérifier les paramètres.");
                return null;
            }
        }
    }
