package fr.cda.campingcar.DAO;

import fr.cda.campingcar.modele.Annonce;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.cda.campingcar.DAO.Connect.connection;

public class AnnonceDAO {

    Connection conn = connection();


    public Annonce ajouterAnnonce(Annonce annonce) throws SQLException {
        String sql = "INSERT INTO Annonce (type_vehicule, lieu_depart, lieu_arrivee, date_debut, date_fin, prix_par_jour, description, url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, annonce.getTypeVehicule());
            stmt.setString(2, annonce.getLieuDepart());
            stmt.setString(3, annonce.getLieuArrivee());
            stmt.setDate(4, annonce.getDateDebut());
            stmt.setDate(5, annonce.getDateFin());
            stmt.setString(6, annonce.getPrixParJour());
            stmt.setString(7, annonce.getDescription());
            stmt.setString(8, annonce.getUrl());

            int affectedRows = stmt.executeUpdate();

            // If the INSERT statement affected rows, retrieve the generated keys (e.g., the auto-generated ID)
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1); // Retrieve the auto-generated ID
                        return new Annonce(id, annonce.getTypeVehicule(), annonce.getLieuDepart(), annonce.getLieuArrivee(),
                                annonce.getDateDebut(), annonce.getDateFin(), annonce.getPrixParJour(),
                                annonce.getDescription(), annonce.getUrl());
                    }
                }
            }
        }
        return annonce;
    }

    public Annonce trouverAnnonceParId(int id) throws SQLException {
        String sql = "SELECT * FROM Annonce WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Annonce(rs.getInt("id"), rs.getString("type_vehicule"),
                        rs.getString("lieu_depart"), rs.getString("lieu_arrivee"),
                        rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getString("prix_par_jour"),
                        rs.getString("description"), rs.getString("url"));
            }
        }
        return null;
    }


    public List<Annonce> getAllAnnonces() throws SQLException {
        String sql = "SELECT * FROM Annonce";
        List<Annonce> annonces = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create an Annonce object and populate it with data from the ResultSet
                Annonce annonce = new Annonce(
                        rs.getInt("id"),
                        rs.getString("type_vehicule"),
                        rs.getString("lieu_depart"),
                        rs.getString("lieu_arrivee"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("prix_par_jour"),
                        rs.getString("description"),
                        rs.getString("url")
                );
                // Add the Annonce object to the list

                annonces.add(annonce);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return annonces;
    }
}
