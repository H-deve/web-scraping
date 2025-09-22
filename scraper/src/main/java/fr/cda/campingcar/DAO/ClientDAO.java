package fr.cda.campingcar.DAO;


import fr.cda.campingcar.modele.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static fr.cda.campingcar.DAO.Connect.connection;

public class ClientDAO {
    Connection conn = connection();

    public void ajouterClient(fr.cda.campingcar.modele.Client client) throws SQLException {
        String sql = "INSERT INTO Client (nom, prenom, email, mot_de_passe, telephone, adresse) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getMotDePasse());
            stmt.setString(5, client.getTelephone());
            stmt.setString(6, client.getAdresse());
            stmt.executeUpdate();
        }
    }

    public Client trouverClientParId(int id) throws SQLException {
        String sql = "SELECT * FROM Client WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("mot_de_passe"), rs.getString("telephone"), rs.getString("adresse"));
            }
        }
        return null;
    }

    public boolean clientExists(String email, String hashedPassword) {
        String query = "SELECT COUNT(*) FROM client WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getHashedPasswordByEmail(String email) {
        String query = "SELECT mot_de_passe FROM Client WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("mot_de_passe"); // retrieve stored hashed password
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // if email not found or error occurs

    }


    public Client getClientByEmail(String email) throws SQLException {

        String q = "SELECT * FROM Client WHERE email = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                return new Client(

                        rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("mot_de_passe"), rs.getString("telephone"), rs.getString("adresse"));

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null; // if email not found or error occurs
    }
}
