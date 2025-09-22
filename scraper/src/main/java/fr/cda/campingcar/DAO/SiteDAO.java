package fr.cda.campingcar.DAO;

import fr.cda.campingcar.modele.Annonce;
import fr.cda.campingcar.modele.Site;

import java.sql.*;

import static fr.cda.campingcar.DAO.Connect.connection;

public class SiteDAO {

    Connection conn = connection();


    public void ajouterSite(Site site) throws SQLException {
        String sql = "INSERT INTO Site (nom_site, url_site) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, site.getNom_site());
            stmt.setString(2, site.getUrl_site());
            stmt.executeUpdate();
        }
    }

    public Site trouverSiteParId(int id) throws SQLException {
        String sql = "SELECT * FROM Site WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Site(
                        rs.getInt("id"),
                        rs.getString("nom_site"),
                        rs.getString("url_site")
                );
            }
        }
        return null;
    }

//    public int getSiteByName(String wikicampers) throws SQLException {
//
//
//        String query = "SELECT * FROM Site WHERE nom_site = ?";
//
//        try {
//            PreparedStatement ps = conn.prepareStatement(query);
//
//            ps.setString(1, wikicampers);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new Site(
//                        rs.getInt("id"),
//                        rs.getString("nom_site"),
//                        rs.getString("url_site")
//                );
//            }
//            return null;
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

        public int getOrCreateSiteId(String siteName, String siteUrl) throws SQLException {
            // Check if site exists
            String selectQuery = "SELECT id FROM Site WHERE nom_site = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, siteName);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id"); // Return existing ID
                }
            }

            // If site doesn't exist, insert it and get the generated ID
            String insertQuery = "INSERT INTO Site (nom_site, url_site) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, siteName);
                insertStmt.setString(2, siteUrl);
                insertStmt.executeUpdate();
                ResultSet rs = insertStmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return new site ID
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new SQLException("Failed to insert or retrieve site ID.");
        }

    public Site findByName(String url) {
        String query = "SELECT * FROM Site WHERE nom_site = ?"; // SQL query to find site by URL
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, url); // Set the URL parameter in the query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the data from the result set and create a Site object
                    int id = rs.getInt("id"); // Assuming the column name is 'id'
                    String name = rs.getString("nom_site");
                    String siteUrl = rs.getString("url_site");

                    // Create and return a Site object with id
                    return new Site(id, name, siteUrl); // Return Site object with the 'id' field
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no site found
    }

}
