package TestUnitaire;

import static fr.cda.campingcar.DAO.Connect.connection;
import static org.junit.jupiter.api.Assertions.*;

import fr.cda.campingcar.Controller.VehicleDetails;
import fr.cda.campingcar.DAO.AnnonceDetailsDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

class AnnonceDetailsDAOTest {

    private AnnonceDetailsDAO dao;
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        // Set up an in-memory database
        conn = connection();


        // Create the DAO instance
        dao = new AnnonceDetailsDAO() {

        };
    }

    /**
     * Teste la méthode saveAnnonceDetails du DAO.
     * Vérifie que les détails d'une annonce sont correctement insérés dans la base de données.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */
    @Test
    void testSaveAnnonceDetails() throws SQLException {
        // Arrange
        String vehicleName = "Test Vehicle";
        String reference = "Ref123";
        String length = "4.5m";
        String height = "2.0m";
        String year = "2020";
        String transmission = "Automatic";
        String fuelType = "Diesel";
        String consumption = "10L/100km";
        String mileage = "50000 km";
        String description = "A test vehicle";
        String availabilityDate = "2024-12-01";
        List<String> imageUrls = Arrays.asList("https://static.wikicampers.fr/uploads/media/vehicule/0031/98/thumb_3097440_vehicule_gallery.jpeg");

        // Act
        dao.saveAnnonceDetails(vehicleName, reference, length, height, year, transmission, fuelType,
                consumption, mileage, description, availabilityDate, imageUrls);

        // Assert: Check if the data is actually inserted
        String query = "SELECT * FROM annonce_details WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reference);
            ResultSet rs = stmt.executeQuery();
            assertTrue(rs.next(), "The record should exist in the database");

            assertEquals(vehicleName, rs.getString("vehicle_name"));
            assertEquals(reference, rs.getString("reference"));
            assertEquals(length, rs.getString("length"));
            assertEquals(height, rs.getString("height"));
            assertEquals(year, rs.getString("year"));
            assertEquals(transmission, rs.getString("transmission"));
            assertEquals(fuelType, rs.getString("fuel_type"));
            assertEquals(consumption, rs.getString("consumption"));
            assertEquals(mileage, rs.getString("mileage"));
            assertEquals(description, rs.getString("description"));
            assertEquals(availabilityDate, rs.getString("availability_date"));
        }
    }
    /**
     * Teste la méthode selectAll du DAO.
     * Vérifie que tous les détails des annonces sont correctement récupérés depuis la base de données.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */
    @Test
    void testSelectAll() throws SQLException {
        // Arrange
        String vehicleName = "Test Vehicle";
        String reference = "Ref123";
        String length = "4.5m";
        String height = "2.0m";
        String year = "2020";
        String transmission = "Automatic";
        String fuelType = "Diesel";
        String consumption = "10L/100km";
        String mileage = "50000 km";
        String description = "A test vehicle";
        String availabilityDate = "2024-12-01";
        List<String> imageUrls = Arrays.asList("https://static.wikicampers.fr/uploads/media/vehicule/0031/98/thumb_3097440_vehicule_gallery.jpeg");

        dao.saveAnnonceDetails(vehicleName, reference, length, height, year, transmission, fuelType,
                consumption, mileage, description, availabilityDate, imageUrls);


        // Act: Retrieve all vehicle details
        List<VehicleDetails> detailsList = dao.selectAll();

        // Assert: Verify that the data is correctly retrieved
        assertNotNull(detailsList);
        assertEquals(1, detailsList.size());  // Ensure only one record is fetched

        VehicleDetails details = detailsList.get(0); // Get the first vehicle details

        // Assert each field matches the expected values
        assertEquals(vehicleName, details.getVehicleName());
        assertEquals(reference, details.getReference());
        assertEquals(length, details.getLength());
        assertEquals(height, details.getHeight());
        assertEquals(year, details.getYear());
        assertEquals(transmission, details.getTransmission());
        assertEquals(fuelType, details.getFuelType());
        assertEquals(consumption, details.getConsumption());
        assertEquals(mileage, details.getMileage());
        assertEquals(description, details.getDescription());
        assertEquals(availabilityDate, details.getAvailabilityDate());

        assertNotNull(details.getImageUrls());
        assertEquals(imageUrls.size(), details.getImageUrls().size());
        assertTrue(details.getImageUrls().containsAll(imageUrls));

    }

}

