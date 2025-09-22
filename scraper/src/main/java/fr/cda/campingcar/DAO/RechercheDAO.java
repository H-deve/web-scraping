package fr.cda.campingcar.DAO;

import fr.cda.campingcar.modele.Recherche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static fr.cda.campingcar.DAO.Connect.connection;

public class RechercheDAO {

    Connection conn = connection();

    public void ajouterRecherche(Recherche recherche) throws SQLException {
        String sql = "INSERT INTO Recherche (client_id, type_vehicule, lieu_depart, lieu_arrivee, date_debut, date_fin, prix_max)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recherche.getClientId());
            stmt.setString(2, recherche.getTypeVehicule());
            stmt.setString(3, recherche.getLieuDepart());
            stmt.setString(4, recherche.getLieuArrivee());
            stmt.setDate(5, recherche.getDateDebut());
            stmt.setDate(6, recherche.getDateFin());
            stmt.setDouble(7, recherche.getPrixMax());
            stmt.executeUpdate();
        }
    }

    public Recherche trouverRechercheParId(int id) throws SQLException {
        String sql = "SELECT * FROM Recherche WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Recherche(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getString("type_vehicule"),
                        rs.getString("lieu_depart"),
                        rs.getString("lieu_arrivee"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getDouble("prix_max")
                );
            }
        }
        return null;
    }

}
