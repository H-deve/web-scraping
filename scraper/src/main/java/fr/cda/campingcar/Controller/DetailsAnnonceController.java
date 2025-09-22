package fr.cda.campingcar.Controller;

import fr.cda.campingcar.DAO.AnnonceDetailsDAO;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.util.Fichier.PDFHandler;
import fr.cda.campingcar.util.Session.UserSession;
import fr.cda.campingcar.util.Urls;
import fr.cda.campingcar.util.VehicleInfo;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailsAnnonceController {

    @FXML
    private HBox iconsContainer;
    @FXML
    private HBox imagesContainer;

    @FXML
    private GridPane infoContainer;
    @FXML
    private ImageView photoImageView;
    @FXML
    private Label vehicleTypeLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label pricePerDayLabel;
    @FXML
    private Label descriptionLabel;

    @FXML
    private Label vehicleNameLabel;

    @FXML
    private Label referenceLabel;
    @FXML
    private Label lengthLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label transmissionLabel;
    @FXML
    private Label fuelTypeLabel;
    @FXML
    private Label consumptionLabel;
    //    @FXML
//    private Label seatbeltLabel;
    @FXML
    private Label mileageLabel;
//    @FXML
//    private Label adBlueLabel;


    @FXML
    private Button showDisponibiliteButton;

    @FXML
    private Label availabilityDateLabel;


    private String annonceUrl;

    private String imageUrl;

    private List<String> imageForPDF = new ArrayList<>(); // Initialize as an ArrayList;


    @FXML
    private Label userEmailLabel;

    @FXML
    public void initialize() {
        UserSession session = UserSession.getInstance();
        if (session != null && session.getLoggedInClient() != null) {
            Client loggedInClient = session.getLoggedInClient();
            userEmailLabel.setText("bienvenu, " + loggedInClient.getEmail());
        } else {
            userEmailLabel.setText("No user logged in");
        }
    }


    public void setAnnonceUrl(String url) {
        this.annonceUrl = url;
        loadAnnonceDetails(url);
    }

    /**
     * Charge les détails d'une annonce à partir de l'URL fournie et les affiche dans l'interface utilisateur.
     * Cette méthode utilise WebClient pour extraire les informations suivantes depuis la page de l'annonce :
     * - Icônes et libellés associés aux informations du véhicule.
     * - Galerie d'images du véhicule.
     * - Détails du véhicule tels que le nom, la référence, la longueur, la hauteur, l'année, la transmission,
     *   le type de carburant, la consommation, le kilométrage, etc.
     * - Description du véhicule, limitée à un nombre maximal de caractères.
     * - Date de disponibilité du véhicule.
     *
     * Les informations extraites sont affichées dans des éléments de l'interface graphique (labels, containers d'images, etc.).
     *
     * @param annonceUrl L'URL de la page de l'annonce à charger.
     * @throws Exception Si une erreur survient lors du chargement de la page ou de l'extraction des informations.
     */
    public void loadAnnonceDetails(String annonceUrl) {
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            HtmlPage page = webClient.getPage(annonceUrl);

            List<HtmlElement> iconElements = page.getByXPath("//div[@class='col-md-2 col-sm-4 col-6']//div[contains(@class,'text-center')]");
            String baseUrl = "https://www.wikicampers.fr";

            for (HtmlElement iconElement : iconElements) {
                HtmlElement imgElement = iconElement.getFirstByXPath("//img");
                HtmlElement labelElement = iconElement.getFirstByXPath(".//p");

                if (imgElement != null && labelElement != null) {
                    String imageIconUrl = imgElement.getAttribute("src");  // Directly get the src attribute
                    String labelText = labelElement.asNormalizedText();

                    // Add base URL if necessary
                    if (imageIconUrl != null && !imageIconUrl.startsWith("http")) {
                        imageIconUrl = baseUrl + imageIconUrl;
                    }

                    // Create ImageView and Label for each icon
                    Image iconImage = new Image(imageIconUrl, true);  // Load asynchronously
                    ImageView iconView = new ImageView(iconImage);
                    iconView.setFitWidth(50);  // Set icon size
                    iconView.setPreserveRatio(true);

                    Label iconLabel = new Label(labelText);

                    // Create a VBox to hold icon and label together
                    VBox iconBox = new VBox(5);  // Set spacing between icon and label
                    iconBox.getChildren().addAll(iconView, iconLabel);
                    iconBox.setStyle("-fx-alignment: center;");

                    // Add VBox to iconsContainer
                    iconsContainer.getChildren().add(iconBox);
                }
            }

            // Scrape all image URLs
            List<HtmlElement> imageElements = page.getByXPath("//div[contains(@class, 'image-slideshow-item') and @data-src]");
            for (HtmlElement imageElement : imageElements) {
                imageUrl = imageElement.getAttribute("data-src");


                // Check if the URL is relative and add the base URL if necessary
                if (imageUrl != null && !imageUrl.startsWith("http")) {
                    imageUrl = baseUrl + imageUrl;
                }

                imageForPDF.add(imageUrl);
                // Load and display the image
                Image image = new Image(imageUrl, true);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);  // Keep aspect ratio
                imageView.setSmooth(true);  // Enable smoothing for better quality
                imagesContainer.getChildren().add(imageView);


                // Scrape vehicle name and reference
                HtmlElement vehicleNameElement = page.getFirstByXPath("//h2[@class='grey3 police-big my-auto']/span");
                String vehicleName = (vehicleNameElement != null) ? vehicleNameElement.asNormalizedText() : "Name not found";

                HtmlElement referenceElement = page.getFirstByXPath("//span[@class='fst-italic']");
                String reference = (referenceElement != null) ? referenceElement.asNormalizedText() : "Reference not found";

                // Scrape additional details
                String length = getDetail(page, "Longueur");
                String height = getDetail(page, "Hauteur");
                String year = getDetail(page, "Année");
                String transmission = getDetail(page, "Boite de vitesse");
                String fuelType = getDetail(page, "Carburant");
                String consumption = getDetail(page, "Consommation");
//                String seatbeltCount = getDetail(page, "Ceinture de sécurité");
                String mileage = getDetail(page, "Kilométrage");
//                String adBlue = getDetail(page, "AdBlue");

                // Set values to the FXML components
                vehicleNameLabel.setText("Vehicle Name: " + vehicleName);
                referenceLabel.setText("Reference: " + reference);
                lengthLabel.setText("Length: " + length);
                heightLabel.setText("Height: " + height);
                yearLabel.setText("Year: " + year);
                transmissionLabel.setText("Transmission: " + transmission);
                fuelTypeLabel.setText("Fuel Type: " + fuelType);
                consumptionLabel.setText("Consumption: " + consumption);
//                seatbeltLabel.setText("Seatbelt Count: " + seatbeltCount);
                mileageLabel.setText("Mileage: " + mileage);
//                adBlueLabel.setText("AdBlue: " + adBlue);


                HtmlElement descriptionElement = page.getFirstByXPath("//div[@id='detail_vehicule_description']");
                String description = (descriptionElement != null) ? descriptionElement.asNormalizedText() : "Description not found";

                // Limit description to 4-5 lines
                int maxCharacters = 800;
                if (description.length() > maxCharacters) {
                    description = description.substring(0, maxCharacters) + "...";
                }

                // Set the truncated description to the descriptionLabel
                descriptionLabel.setText(description);


                // Scrape availability date
                String availabilityDate = getDetail(page, "Disponibilité");
                availabilityDateLabel.setText("Available From: " + availabilityDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveDetailsToDatabase() {
        // Get the details to save from the scraped information (labels and image URLs)
        String vehicleName = vehicleNameLabel.getText();
        String reference = referenceLabel.getText();
        String length = lengthLabel.getText();
        String height = heightLabel.getText();
        String year = yearLabel.getText();
        String transmission = transmissionLabel.getText();
        String fuelType = fuelTypeLabel.getText();
        String consumption = consumptionLabel.getText();
        String mileage = mileageLabel.getText();
        String description = descriptionLabel.getText();
        String availabilityDate = availabilityDateLabel.getText();

        // Assuming imageForPDF is a List<String> containing the image URLs
        List<String> imageUrls = new ArrayList<>(imageForPDF);

        // Create DAO and save details to the database
        AnnonceDetailsDAO annonceDetailsDAO = new AnnonceDetailsDAO();
        annonceDetailsDAO.saveAnnonceDetails(vehicleName, reference, length, height, year, transmission,
                fuelType, consumption, mileage, description, availabilityDate, imageUrls);

        showAlert("Succès", "Les détails de l'annonce ont été enregistrés avec succès dans la base de données.");
    }


    private String getDetail(HtmlPage page, String labelText) {
        HtmlElement element = page.getFirstByXPath("//p[contains(text(), '" + labelText + "')]/strong");
        return (element != null) ? element.asNormalizedText() : labelText + " not found";
    }

    private String getTextFromXPath(HtmlPage page, String xpath, String defaultValue) {
        HtmlElement element = page.getFirstByXPath(xpath);
        return (element != null) ? element.asNormalizedText() : defaultValue;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void saveDetailsToPDF() {
        PDFHandler pdfHandler = new PDFHandler("AnnonceDetails.pdf");
        pdfHandler.generateAnnonceDetailsPDF(
                vehicleNameLabel.getText(),
                referenceLabel.getText(),
                lengthLabel.getText(),
                heightLabel.getText(),
                yearLabel.getText(),
                transmissionLabel.getText(),
                fuelTypeLabel.getText(),
                consumptionLabel.getText(),
                mileageLabel.getText(),
                availabilityDateLabel.getText(),
                descriptionLabel.getText(),
                imageForPDF
        );

        showAlert("Succès", "Le PDF a été généré avec succès.");
    }


}
