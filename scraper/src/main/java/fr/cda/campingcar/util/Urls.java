package fr.cda.campingcar.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static fr.cda.campingcar.util.GeoHelper.getGeoNameId;

public class Urls {

    /**
     * Construit une URL pour un site spécifique avec les paramètres de recherche donnés.
     *
     * @param siteName Le nom du site pour lequel construire l'URL
     * @param departure La ville de départ
     * @param arrival La ville d'arrivée
     * @param startDate La date de début de la location
     * @param endDate La date de fin de la location
     * @return L'URL construite pour le site donné, ou null si le site n'est pas pris en charge.
     */
    public static String constructUrl(String siteName, String departure, String arrival, LocalDate startDate, LocalDate endDate, String vehicleType) {
        // Fetch geonameid for departure city
        int geonameId = getGeoNameId(departure);

        // Format vehicleType as per each site’s expected value
        String formattedVehicleType = formatVehicleTypeForSite(siteName, vehicleType);
        String formattedDeparture = departure.replace(" ", "+");
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        switch (siteName.toLowerCase()) {
            case "wikicampers":
                return "https://www.wikicampers.fr/location/s/?country=FR&geoname=" + formattedDeparture +
                        "&geonameid=" + geonameId + "&numberPlace=3&startDate=" + formattedStartDate +
                        "&endDate=" + formattedEndDate + "&vehicleType=" + formattedVehicleType;

            case "hapee":
                return "https://www.hapee.fr/location?depart=" + formattedDeparture +
                        "&arrivee=" + arrival.replace(" ", "+") +
                        "&start=" + formattedStartDate + "&end=" + formattedEndDate +
                        "&type=" + formattedVehicleType;

            case "evasia":
                return "https://www.evasia.fr/recherche?ville_depart=" + formattedDeparture +
                        "&ville_arrivee=" + arrival.replace(" ", "+") +
                        "&date_debut=" + formattedStartDate + "&date_fin=" + formattedEndDate +
                        "&vehicleType=" + formattedVehicleType;

            case "goboony":
                return "https://www.goboony.fr/recherche?location_depart=" + formattedDeparture +
                        "&location_arrivee=" + arrival.replace(" ", "+") +
                        "&date_start=" + formattedStartDate + "&date_end=" + formattedEndDate +
                        "&typeVehicule=" + formattedVehicleType;

            default:
                return null;
        }
    }
    private static String formatVehicleTypeForSite(String siteName, String vehicleType) {
        switch (siteName.toLowerCase()) {
            case "wikicampers":
                // Example mappings for WikiCampers
                return switch (vehicleType.toLowerCase()) {
                    case "camping car" -> "camping-car";
                    case "van" -> "van";
                    case "fourgon aménagé" -> "fourgon";
                    default -> "";
                };
            case "hapee":
                // Example mappings for Hapee
                return switch (vehicleType.toLowerCase()) {
                    case "camping car" -> "camping-car";
                    case "van" -> "van";
                    case "fourgon aménagé" -> "fourgon";
                    default -> "";
                };
            case "evasia":
                // Example mappings for Evasia
                return switch (vehicleType.toLowerCase()) {
                    case "camping car" -> "campingcar";
                    case "van" -> "van";
                    case "fourgon aménagé" -> "fourgon";
                    default -> "";
                };
            case "goboony":
                // Example mappings for Goboony
                return switch (vehicleType.toLowerCase()) {
                    case "camping car" -> "motorhome";
                    case "van" -> "van";
                    case "fourgon aménagé" -> "converted-van";
                    default -> "";
                };
            default:
                return "";
        }
    }


}
