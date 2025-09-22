package fr.cda.campingcar.Controller;

import java.util.List;

public class VehicleDetails {

    String vehicleName;
    String reference;
    String length;
    String height;
    String year;
    String transmission;
    String fuelType;
    String consumption;
    String mileage;
    String description;
    String availabilityDate;
    List<String> imageUrls;


    public VehicleDetails(String vehicleName, String reference, String length, String height,
                          String year, String transmission, String fuelType, String consumption,
                          String mileage, String description, String availabilityDate, List<String> imageUrls) {
        this.vehicleName = vehicleName;
        this.reference = reference;
        this.length = length;
        this.height = height;
        this.year = year;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.consumption = consumption;
        this.mileage = mileage;
        this.description = description;
        this.availabilityDate = availabilityDate;
        this.imageUrls = imageUrls;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
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
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VehicleDetails{");
        sb.append("vehicleName='").append(vehicleName).append('\'');
        sb.append(", reference='").append(reference).append('\'');
        sb.append(", length='").append(length).append('\'');
        sb.append(", height='").append(height).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append(", transmission='").append(transmission).append('\'');
        sb.append(", fuelType='").append(fuelType).append('\'');
        sb.append(", consumption='").append(consumption).append('\'');
        sb.append(", mileage='").append(mileage).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", availabilityDate='").append(availabilityDate).append('\'');
        sb.append(", imageUrls=").append(imageUrls);
        sb.append('}');
        return sb.toString();
    }
}
