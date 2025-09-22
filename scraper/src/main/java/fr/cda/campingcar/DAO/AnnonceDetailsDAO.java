package fr.cda.campingcar.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.cda.campingcar.Controller.VehicleDetails;
import fr.cda.campingcar.util.VehicleInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static fr.cda.campingcar.DAO.Connect.connection;

public class AnnonceDetailsDAO {

    Connection conn = connection();



    // Method to save the announcement details into the database
    public void saveAnnonceDetails(String vehicleName, String reference, String length, String height,
                                   String year, String transmission, String fuelType, String consumption,
                                   String mileage, String description, String availabilityDate, List<String> imageUrls) {

        String imageUrlsJson = new Gson().toJson(imageUrls);

        String query = "INSERT INTO annonce_details (vehicle_name, reference, length, height, year, transmission, " +
                "fuel_type, consumption, mileage, description, availability_date, image_urls) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicleName);
            stmt.setString(2, reference);
            stmt.setString(3, length);
            stmt.setString(4, height);
            stmt.setString(5, year);
            stmt.setString(6, transmission);
            stmt.setString(7, fuelType);
            stmt.setString(8, consumption);
            stmt.setString(9, mileage);
            stmt.setString(10, description);
            stmt.setString(11, availabilityDate);
            stmt.setString(12, imageUrlsJson);  // Store image URLs as comma-separated values

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<VehicleDetails> selectAll() {
        List<VehicleDetails> detailsList = new ArrayList<>();

        Gson gson = new Gson();
        String query = "SELECT * FROM annonce_details";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                // Retrieve image URLs as a comma-separated string and split into a List
                String imageUrlsJson = rs.getString("image_urls");
                List<String> imageUrls = gson.fromJson(imageUrlsJson, new TypeToken<List<String>>() {}.getType());
                // Create VehicleDetails object and populate it with data from ResultSet
                VehicleDetails details = new VehicleDetails(
                        rs.getString("vehicle_name"),
                        rs.getString("reference"),
                        rs.getString("length"),
                        rs.getString("height"),
                        rs.getString("year"),
                        rs.getString("transmission"),
                        rs.getString("fuel_type"),
                        rs.getString("consumption"),
                        rs.getString("mileage"),
                        rs.getString("description"),
                        rs.getString("availability_date"),
                        imageUrls
                );
                detailsList.add(details);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detailsList;
    }
}
