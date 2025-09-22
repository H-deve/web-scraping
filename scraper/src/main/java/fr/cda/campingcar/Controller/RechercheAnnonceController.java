package fr.cda.campingcar.Controller;

import fr.cda.campingcar.DAO.AnnonceDAO;
import fr.cda.campingcar.DAO.SiteDAO;
import fr.cda.campingcar.modele.Annonce;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.modele.Site;
import fr.cda.campingcar.util.Fichier.PDFHandler;
import fr.cda.campingcar.util.Session.UserSession;
import fr.cda.campingcar.util.Urls;
import fr.cda.campingcar.util.VehicleInfo;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RechercheAnnonceController implements DatabaseConnectionManager.DatabaseStatusListener {

    @FXML
    private MenuItem saveToDatabaseMenuItem;

    @FXML
    private MenuItem quitter;


    @FXML
    private ComboBox<String> vehicleTypeComboBox;
    @FXML
    private TextField departureField;
    @FXML
    private TextField arrivalField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField priceField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private CheckBox wikiCampersCheckBox;
    @FXML
    private CheckBox hapeeCheckBox;
    @FXML
    private CheckBox evasiaCheckBox;
    @FXML
    private CheckBox goboonyCheckBox;
    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button backButton;

    @FXML
    private MenuBar menuBar;

    private List<String> scrapedResults = new ArrayList<>();

    @FXML
    private Label userEmailLabel;

    private AnnonceDAO annonceDAO;

    public AnnonceDAO getAnnonceDAO() {
        return annonceDAO;
    }

    public void setAnnonceDAO(AnnonceDAO annonceDAO) {
        this.annonceDAO = annonceDAO;
    }

    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }

    public void setStartDatePicker(DatePicker startDatePicker) {
        this.startDatePicker = startDatePicker;
    }

    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }

    public void setEndDatePicker(DatePicker endDatePicker) {
        this.endDatePicker = endDatePicker;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public void setPriceField(TextField priceField) {
        this.priceField = priceField;
    }

    public TextField getArrivalField() {
        return arrivalField;
    }

    public void setArrivalField(TextField arrivalField) {
        this.arrivalField = arrivalField;
    }

    public TextField getDepartureField() {
        return departureField;
    }

    public void setDepartureField(TextField departureField) {
        this.departureField = departureField;
    }

    public ComboBox<String> getVehicleTypeComboBox() {
        return vehicleTypeComboBox;
    }

    public void setVehicleTypeComboBox(ComboBox<String> vehicleTypeComboBox) {
        this.vehicleTypeComboBox = vehicleTypeComboBox;
    }

    public CheckBox getWikiCampersCheckBox() {
        return wikiCampersCheckBox;
    }

    public void setWikiCampersCheckBox(CheckBox wikiCampersCheckBox) {
        this.wikiCampersCheckBox = wikiCampersCheckBox;
    }

    public CheckBox getHapeeCheckBox() {
        return hapeeCheckBox;
    }

    public void setHapeeCheckBox(CheckBox hapeeCheckBox) {
        this.hapeeCheckBox = hapeeCheckBox;
    }

    public CheckBox getEvasiaCheckBox() {
        return evasiaCheckBox;
    }

    public void setEvasiaCheckBox(CheckBox evasiaCheckBox) {
        this.evasiaCheckBox = evasiaCheckBox;
    }

    public CheckBox getGoboonyCheckBox() {
        return goboonyCheckBox;
    }

    public void setGoboonyCheckBox(CheckBox goboonyCheckBox) {
        this.goboonyCheckBox = goboonyCheckBox;
    }


    /**
     * Méthode appelée lors de l'initialisation de la scène.
     * Vérifie l'état de la session utilisateur et met à jour l'interface en conséquence.
     * Active également les écouteurs de changement d'état de la connexion à la base de données et met à jour les boutons de recherche.
     */
    @FXML
    public void initialize() {


        UserSession session = UserSession.getInstance();
        if (session != null && session.getLoggedInClient() != null) {
            Client loggedInClient = session.getLoggedInClient();
            userEmailLabel.setText("bienvenu, " + loggedInClient.getEmail());
        } else {
            userEmailLabel.setText("No user logged in");
        }

        saveToDatabaseMenuItem.setDisable(false);
        DatabaseConnectionManager.getInstance().addListener(this);
        updateSaveToDatabaseMenuItem(DatabaseConnectionManager.getInstance().isConnected());

        updateSearchButtonState();
        vehicleTypeComboBox.setOnAction(event -> updateSearchButtonState());
        departureField.textProperty().addListener((observable, oldValue, newValue) -> updateSearchButtonState());
        arrivalField.textProperty().addListener((observable, oldValue, newValue) -> updateSearchButtonState());
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSearchButtonState());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateSearchButtonState());
        priceField.textProperty().addListener((observable, oldValue, newValue) -> updateSearchButtonState());
        wikiCampersCheckBox.setOnAction(event -> updateSearchButtonState());
        hapeeCheckBox.setOnAction(event -> updateSearchButtonState());
        evasiaCheckBox.setOnAction(event -> updateSearchButtonState());
        goboonyCheckBox.setOnAction(event -> updateSearchButtonState());
    }

    /**
     * Met à jour l'état du bouton de recherche en fonction de la validité du formulaire.
     * Le bouton de recherche est désactivé si un champ obligatoire est vide ou si aucun checkbox n'est sélectionné.
     */
    private void updateSearchButtonState() {
        boolean isFormValid = vehicleTypeComboBox.getValue() != null && !departureField.getText().trim().isEmpty() && !arrivalField.getText().trim().isEmpty() && startDatePicker.getValue() != null && endDatePicker.getValue() != null && isAnyCheckboxSelected();
        searchButton.setDisable(!isFormValid);
    }

    /**
     * Vérifie si au moins une case à cocher pour les sites de recherche est sélectionnée.
     *
     * @return true si au moins une case à cocher est sélectionnée, false sinon.
     */
    private boolean isAnyCheckboxSelected() {
        return wikiCampersCheckBox.isSelected() || hapeeCheckBox.isSelected() || evasiaCheckBox.isSelected() || goboonyCheckBox.isSelected();
    }

    /**
     * Méthode appelée lors de l'action de recherche. Elle effectue la validation des entrées, construit les URL de recherche,
     * lance le scraping et affiche les résultats.
     */

    @FXML
    private void handleSearchAction() {
        String vehicleType = vehicleTypeComboBox.getValue();
        String departure = departureField.getText();
        String arrival = arrivalField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String priceText = priceField.getText();

        if (!validateInputs(departure, arrival, startDate, endDate, priceText)) return;

        double pricePerDay;
        try {
            pricePerDay = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un prix valide.");
            return;
        }

        List<String> urls = constructUrls(departure, arrival, startDate, endDate, vehicleType);

        if (urls.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner au moins un site pour la recherche.");
            return;
        }

        // Configuration progress par
        progressBar.setVisible(true);

        //Create Task
        Task<List<String>> searchTask = createSearchTask(urls);
        progressBar.progressProperty().bind(searchTask.progressProperty());
        //si reussi
        searchTask.setOnSucceeded(e -> {
            progressBar.setVisible(false);
            List<String> results = searchTask.getValue();
            showResultsPage(results);
        });
        // si Non
        searchTask.setOnFailed(e -> {
            progressBar.setVisible(false);
            showAlert("Erreur de Scraping", "Une erreur s'est produite lors de la récupération des données.");
        });

        new Thread(searchTask).start();
    }

    /**
     * Valide les entrées de l'utilisateur pour la recherche.
     * Vérifie que tous les champs requis sont remplis.
     *
     * @param departure Lieu de départ
     * @param arrival   Lieu d'arrivée
     * @param startDate Date de début
     * @param endDate   Date de fin
     * @param priceText Prix
     * @return true si toutes les entrées sont valides, false sinon.
     */
    private boolean validateInputs(String departure, String arrival, LocalDate startDate, LocalDate endDate, String priceText) {
        if (departure.isEmpty() || arrival.isEmpty() || startDate == null || endDate == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs de recherche.");
            return false;
        }
        return true;
    }


    /**
     * Construit une liste d'URLs de recherche en fonction des paramètres fournis.
     *
     * @param departure   Lieu de départ
     * @param arrival     Lieu d'arrivée
     * @param startDate   Date de début
     * @param endDate     Date de fin
     * @param vehicleType Type de véhicule
     * @return Liste des URLs à utiliser pour le scraping.
     */
    public List<String> constructUrls(String departure, String arrival, LocalDate startDate, LocalDate endDate, String vehicleType) {
        List<String> urls = new ArrayList<>();
        if (wikiCampersCheckBox.isSelected()) {
            urls.add(Urls.constructUrl("wikicampers", departure, arrival, startDate, endDate, vehicleType));
        }
        if (hapeeCheckBox.isSelected()) {
            urls.add(Urls.constructUrl("hapee", departure, arrival, startDate, endDate, vehicleType));
        }
        if (evasiaCheckBox.isSelected()) {
            urls.add(Urls.constructUrl("evasia", departure, arrival, startDate, endDate, vehicleType));
        }
        if (goboonyCheckBox.isSelected()) {
            urls.add(Urls.constructUrl("goboony", departure, arrival, startDate, endDate, vehicleType));
        }
        return urls;
    }


    /**
     * Crée une tâche qui effectue le scraping sur toutes les URLs fournies et retourne les résultats sous forme de liste.
     *
     * @param urls Liste des URLs à scraper
     * @return Une tâche qui effectue le scraping et retourne les résultats.
     */

    private Task<List<String>> createSearchTask(List<String> urls) {
        return new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                List<String> allResults = new ArrayList<>();
                int totalUrls = urls.size(); // Get the total number of URLs to process

                for (int i = 0; i < totalUrls; i++) {
                    String url = urls.get(i);
                    updateMessage("Scraping URL: " + url);

                    List<String> results = performScraping(url);
                    allResults.addAll(results);

                    updateProgress(i + 1, totalUrls);

                }

                return allResults;
            }
        };
    }

    /**
     * Extrait les informations d'un véhicule à partir d'un élément HTML de type carte d'annonce.
     *
     * @param card L'élément HTML représentant une carte d'annonce.
     * @return Un objet {@link VehicleInfo} contenant les informations extraites.
     */

    private VehicleInfo extractVehicleInfo(HtmlElement card) {
        try {
            HtmlAnchor linkElement = card.getFirstByXPath(".//a[@class='stretched-link']");
            String adUrl = linkElement != null ? linkElement.getHrefAttribute() : "URL not found";

            HtmlElement titleElement = card.getFirstByXPath(".//h5[@class='card-title']");
            String vehicleType = titleElement != null ? titleElement.asNormalizedText() : "Type not found";

            HtmlElement locationElement = card.getFirstByXPath(".//div[contains(@class, 'card-city-label')]/span[@class='fs-6 ms-2']");
            String location = locationElement != null ? locationElement.asNormalizedText() : "Location not found";

            HtmlElement priceElement = card.getFirstByXPath(".//div[contains(@class, 'price-block')]/span");
            String pricePerDay = priceElement != null ? priceElement.asNormalizedText() : "Price not found";


//            // Get start and end dates
//            HtmlInput startDateElement = card.getFirstByXPath(".//input[@name='startDate']");
//            HtmlElement endDateElement = card.getFirstByXPath(".//input[@name='endDate']");
//

//            String startDateStr = startDateElement != null ? startDateElement.getAttribute("value") : null;
//            String endDateStr = endDateElement != null ? endDateElement.getAttribute("value") : null;

//            // Parse dates from string format "dd/MM/yyyy"
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            LocalDate startDate = startDateStr != null ? LocalDate.parse(startDateStr, formatter) : null;
//            LocalDate endDate = endDateStr != null ? LocalDate.parse(endDateStr, formatter) : null;
//
//
//            // Assume departure and arrival elements are within the HTML structure
////            HtmlElement departureElement = card.getFirstByXPath(".//span[@class='departure-location']");
////            HtmlElement arrivalElement = card.getFirstByXPath(".//span[@class='arrival-location']");
////
////            String departureLocation = departureElement != null ? departureElement.asNormalizedText() : "Departure not found";
////            String arrivalLocation = arrivalElement != null ? arrivalElement.asNormalizedText() : "Arrival not found";
//
////                return new VehicleInfo(vehicleType, location, adUrl, pricePerDay);
            VehicleInfo vehicleInfo = new VehicleInfo(vehicleType, location, adUrl, pricePerDay);
            System.out.println("Vehicle Info: " + vehicleInfo);
//            System.out.println("Start Date: " + startDateStr);
//            System.out.println("End Date: " + endDateStr);
            return vehicleInfo;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> performScraping(String url) {

        System.out.println("Scraping URL: " + url); // Confirm URL
        List<String> results = new ArrayList<>();
        AnnonceDAO annonceDAO = new AnnonceDAO();

        // Get the selected start date and end date from the DatePicker
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String Arrival = arrivalField.getText();
        String desirPrice = priceField.getText();


        if (startDate == null || endDate == null) {
            System.out.println("Start or End date is missing");
            return results;
        }

        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            HtmlPage page = webClient.getPage(url);
            List<HtmlElement> cards = page.getByXPath("//div[@id='cards-list-vehicle']//div[contains(@class, 'mt-4 col-12 col-md-6 col-xl-4')]");

            for (HtmlElement card : cards) {
                VehicleInfo vehicle = extractVehicleInfo(card);
                if (vehicle != null) {
                    results.add(vehicle.toString());
                }
                if (results.size() >= 2) break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Récupère les informations sur les véhicules à partir de l'URL fournie en extrayant les détails pertinents
     * sur chaque fiche de véhicule. La méthode traite la liste des fiches de véhicules, extrait des informations telles que le type de véhicule, l'emplacement, le prix et d'autres détails.
     * des informations telles que le type de véhicule, la localisation, le prix et d'autres détails, et renvoie
     * une liste de résultats sous forme de chaînes. En outre, elle stocke les données sur les véhicules dans la base de données.
     *
     * @param 'URL du site web à partir duquel les données sur les véhicules doivent être extraites.
     * @return Une liste de chaînes de caractères représentant les informations sur le véhicule récupéré.
     */
    private void showResultsPage(List<String> results) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/resultats.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 600, 400));
            stage.setTitle("Résultats de la Recherche");

            ResultatsController controller = loader.getController();
            controller.setResults(results);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openAnnonceDetails(String urlAnnonce) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/detailsAnnonce.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 600, 400));
            stage.setTitle("Annonce details");
            stage.show();

            DetailsAnnonceController controller = loader.getController();
            controller.loadAnnonceDetails(urlAnnonce);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void openCouriel() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/mail.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 700, 500));

        stage.setTitle("sent Mail ");
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleClearAction() {
        vehicleTypeComboBox.setValue(null);
        departureField.clear();
        arrivalField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        priceField.clear();
        wikiCampersCheckBox.setSelected(false);
        hapeeCheckBox.setSelected(false);
        evasiaCheckBox.setSelected(false);
        goboonyCheckBox.setSelected(false);
        updateSearchButtonState();
    }

    /**
     * Gère l'action de génération du fichier PDF à partir des résultats de recherche.
     * Si des résultats ont été extraits (scrapés), cette méthode crée un fichier PDF
     * contenant les informations de ces résultats. Si aucun résultat n'a été trouvé,
     * une alerte sera affichée pour indiquer l'erreur.
     *
     * @see PDFHandler#generateRechercheAnnoncePDF(List)
     */
    @FXML
    private void handleGeneratePdfAction() {
        if (scrapedResults == null || scrapedResults.isEmpty()) {
            showAlert("Erreur", "Aucun résultat à exporter dans le PDF.");
            return;
        }

        // Create PDFHandler instance and generate the PDF
        PDFHandler pdfHandler = new PDFHandler("recherche_annonce_results.pdf");
        pdfHandler.generateRechercheAnnoncePDF(scrapedResults);

        showAlert("Succès", "Le PDF a été généré avec succès.");
    }

    /**
     * Gère l'ouverture du guide d'utilisation sous forme de fichier PDF.
     * Cette méthode crée une instance de `PDFViewerController` et lui fournit le chemin
     * du fichier PDF à afficher. En cas d'erreur lors de l'ouverture du fichier PDF,
     * une alerte d'erreur sera affichée.
     *
     * @see PDFViewerController#showPDF(String)
     */
    @FXML
    void handleModeEmploi() {
        try {
            // Create an instance of PDFViewerController
            PDFViewerController pdfViewerController = new PDFViewerController();

            // Set the path to your PDF file (replace with actual path)
            String pdfPath = "Guide.pdf"; // Replace with the correct path

            // Show the PDF in a new window
            pdfViewerController.showPDF(pdfPath);
        } catch (Exception e) {
            // Handle any errors that might occur when opening the PDF
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to open PDF");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void goToConnexionPage() {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/ParamétreBDpage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.setTitle("Résultats de la Recherche");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Gère l'action d'enregistrement des résultats de scraping dans la base de données.
     * Cette méthode récupère les données des champs de saisie, génère les URLs en fonction
     * des critères sélectionnés (lieu de départ, lieu d'arrivée, type de véhicule, etc.),
     * effectue le scraping de chaque URL, et sauvegarde les annonces correspondantes dans la base de données.
     * Si un champ nécessaire est vide ou invalide, l'action sera annulée.
     *
     * @param event L'événement qui déclenche cette méthode, en l'occurrence l'action utilisateur.
     * @throws SQLException Si une erreur survient lors de l'insertion dans la base de données.
     */

    @FXML
    public void handleSaveToDatabase(ActionEvent event) throws SQLException {
        // Get input data from fields
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String departure = departureField.getText();
        String arrival = arrivalField.getText();
        String vehicleType = vehicleTypeComboBox.getValue();
        String desiredPrice = priceField.getText();

        if (startDate == null || endDate == null || departure.isEmpty() || arrival.isEmpty() || vehicleType == null) {
            System.out.println("Please fill in all fields.");
            return;
        }

        // Build URLs based on selected websites
        List<String> urls = constructUrls(departure, arrival, startDate, endDate, vehicleType);

        // Initialize DAO for database insertion
        AnnonceDAO annonceDAO = new AnnonceDAO();

        // Scrape each URL and save results
        for (String url : urls) {
            System.out.println("Scraping URL: " + url);


            List<String> results = performScraping(url);

            // Convert the List<String> to List<VehicleInfo>
            List<VehicleInfo> vehicles = new ArrayList<>();
            for (String result : results) {

                VehicleInfo vehicle = parseVehicleInfo(result); // Implement this method based on your string format
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }

            for (VehicleInfo vehicle : vehicles) {
                // Convert desiredPrice to a double for comparison
                double vehiclePrice;
                try {
                    vehiclePrice = Double.parseDouble(vehicle.getPrice().replaceAll("[^0-9.]", ""));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format for vehicle: " + vehicle.getPrice());
                    continue;
                }

                // Filter based on desired price
                double targetPrice;
                try {
                    targetPrice = Double.parseDouble(desiredPrice);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid desired price format.");
                    return;
                }


                // Create Annonce object
                Annonce annonce = new Annonce(
                        0,  // ID will be auto-generated by the database
                        vehicle.getType(),
                        vehicle.getLocation(),  // Depart location
                        arrival,  // Arrival location
                        Date.valueOf(startDate),  // Start date from DatePicker
                        Date.valueOf(endDate),  // End date from DatePicker
                        vehicle.getPrice(),
                        "",  // Price per day or additional details
                        vehicle.getUrl()  // URL
                );

                // Insert annonce into the database
                annonceDAO.ajouterAnnonce(annonce);
                System.out.println("Annonce saved: " + annonce);
            }

        }
        System.out.println("Scraping and saving to database completed.");
    }
    /**
     * Convertit une chaîne de caractères représentant un véhicule en un objet `VehicleInfo`.
     * La chaîne est supposée être formatée de manière à séparer les différents attributs
     * (type, emplacement, prix, URL) par des virgules.
     * Si la chaîne n'a pas le format attendu, cette méthode retournera null.
     *
     * @param result La chaîne de caractères représentant un véhicule à convertir.
     * @return Un objet `VehicleInfo` avec les informations extraites de la chaîne, ou `null` si le format est invalide.
     */

    private VehicleInfo parseVehicleInfo(String result) {
        // Assuming the result is a comma-separated string: "type,location,price,url"
        String[] parts = result.split(",");
        if (parts.length >= 4) {
            VehicleInfo vehicle = new VehicleInfo();
            vehicle.setType(parts[0]);
            vehicle.setLocation(parts[1]);
            vehicle.setPrice(parts[2]);
            vehicle.setUrl(parts[3]);
            return vehicle;
        } else {
            System.out.println("Invalid VehicleInfo string: " + result);
            return null;
        }
    }

    @Override
    public void onDatabaseStatusChanged(boolean isConnected) {
        updateSaveToDatabaseMenuItem(isConnected);
    }

    private void updateSaveToDatabaseMenuItem(boolean isConnected) {
        saveToDatabaseMenuItem.setDisable(!isConnected);
    }


    public void handleQuitter() {
        System.exit(0); // Closes the application
    }

    public void handleBackAction() {
        try {
            // Load the previous scene (for example, the home page or previous page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/acceuil.fxml"));
            BorderPane root = loader.load();

            // Set the scene to the new page
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}






