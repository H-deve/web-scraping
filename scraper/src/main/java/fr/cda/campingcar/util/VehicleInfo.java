package fr.cda.campingcar.util;

import java.time.LocalDate;

public class VehicleInfo {

    private String type;
    private String location;
    private String url;
    private String price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String departureLocation;
    private String arrivalLocation;

    // Constructor with all fields
    public VehicleInfo(String type, String location, String url, String price) {
        this.type = type;
        this.location = location;
        this.url = "https://www.wikicampers.fr" + url;
        this.price = price;
    }

    // Constructor with all fields including startDate and endDate
    public VehicleInfo(String type, String location, String url, String price, LocalDate startDate, LocalDate endDate) {
        this.type = type;
        this.location = location;
        this.url = url;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // No-argument constructor (default constructor)
    public VehicleInfo() {
        // You can initialize fields with default values or leave them uninitialized
        this.type = "";
        this.location = "";
        this.url = "";
        this.price = "";
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.departureLocation = "";
        this.arrivalLocation = "";
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate setStartDate(LocalDate startDate) {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate setEndDate(LocalDate endDate) {
       return  endDate;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    @Override
    public String toString() {
        return "Vehicle Type: " + type + ", Location: " + location + ", Price: " + price + ", URL: " + url;
    }
}



