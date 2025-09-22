package com.example.demo.model;

import com.example.demo.util.ListStringConverter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annonce_details")
public class annonceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String vehicle_name;

    @Column
    private String reference;

    @Column
    private String length;

    @Column
    private String height;

    @Column
    private String year;

    @Column
    private String transmission;

    @Column
    private String fuel_type;

    @Column
    private String consumption;

    @Column
    private String mileage;

    @Column
    private String description;

    @Column
    private String availability_date;

    @Column(name = "image_urls") // Match the database column name
    @Convert(converter = ListStringConverter.class)
    private List<String> imageUrls = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "annonce_id", referencedColumnName = "id")
    private Annonce annonce;


    @OneToMany(mappedBy = "annonceDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    // Default constructor
    public annonceDetails() {
    }

    // Constructor
    public annonceDetails(int id, String vehicle_name, String reference, String length, String height, String year,
                          String transmission, String fuel_type, String consumption, String mileage,
                          String description, String availability_date, List<String> imageUrls) {
        this.id = id;
        this.vehicle_name = vehicle_name;
        this.reference = reference;
        this.length = length;
        this.height = height;
        this.year = year;
        this.transmission = transmission;
        this.fuel_type = fuel_type;
        this.consumption = consumption;
        this.mileage = mileage;
        this.description = description;
        this.availability_date = availability_date;
        this.imageUrls = imageUrls;

    }



    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicle_name;
    }

    public void setVehicleName(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuel_type;
    }

    public void setFuelType(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailabilityDate() {
        return availability_date;
    }

    public void setAvailabilityDate(String availability_date) {
        this.availability_date = availability_date;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }


    public Integer getAnnonceId() {
        return (annonce != null) ? annonce.getId() : null;
    }


    //Encapsulation of rating methode

    public List<Rating> getRatings() {
        return ratings ;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) { // Use '&&' for both null and empty checks
            return 0.0;
        }
        double total = ratings.stream().mapToDouble(Rating::getRatingValue).sum();
        return total / ratings.size();
    }



    public int getRatingCount() {

        return ratings != null ? ratings.size() : 0;
    }

    // Setter for the associated Annonce
    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
    @Override
    public String toString() {
        return "annonceDetails{" +
                "id=" + id +
                ", vehicle_name='" + vehicle_name + '\'' +
                ", reference='" + reference + '\'' +
                ", length='" + length + '\'' +
                ", height='" + height + '\'' +
                ", year='" + year + '\'' +
                ", transmission='" + transmission + '\'' +
                ", fuel_type='" + fuel_type + '\'' +
                ", consumption='" + consumption + '\'' +
                ", mileage='" + mileage + '\'' +
                ", description='" + description + '\'' +
                ", availability_date='" + availability_date + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
