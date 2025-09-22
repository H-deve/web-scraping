package fr.cda.campingcar.Controller;

import fr.cda.campingcar.DAO.AnnonceDAO;
import fr.cda.campingcar.DAO.SiteDAO;
import fr.cda.campingcar.modele.Annonce;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.modele.Site;
import fr.cda.campingcar.util.Session.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ResultatsController {


    @FXML
    private ListView<String> resultsListView;

    private List<String> results;

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


    /**
     * Définit les résultats de recherche dans la liste des résultats (`resultsListView`)
     * et ajoute un écouteur pour ouvrir une vue de détails lorsqu'un élément est sélectionné.
     *
     * @param results Une liste de chaînes représentant les résultats de recherche.
     *                <p>
     *                Cette méthode met à jour l'affichage des résultats dans une `ListView` et configure
     *                un écouteur pour répondre aux sélections des utilisateurs. Lorsqu'un élément est sélectionné,
     *                la méthode ouvre une vue détaillée via `openDetailsView`.
     */

    public void setResults(List<String> results) {
        this.results = results;
        resultsListView.getItems().addAll(results);

        // Add a listener to open details on item selection
        resultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                openDetailsView(newValue);
            }
        });
    }

    /**
     * Ouvre une nouvelle fenêtre pour afficher les détails d'une annonce sélectionnée.
     *
     * @param selectedResult La chaîne de texte représentant l'annonce sélectionnée dans la liste.
     *                       <p>
     *                       Cette méthode charge un fichier FXML pour l'interface utilisateur des détails d'une annonce
     *                       et transmet l'URL extraite de l'annonce sélectionnée au contrôleur associé. En cas de problème
     *                       lors du chargement du fichier FXML, une exception est imprimée dans la console.
     */
    @FXML

    private void openDetailsView(String selectedResult) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/detailsAnnonce.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 1200, 800));
            stage.setTitle("Détails de l'Annonce");
            String annonceUrl = extractUrlFromResult(selectedResult);

            // Pass the URL to the DetailsController
            DetailsAnnonceController detailsController = loader.getController();
            detailsController.setAnnonceUrl(annonceUrl);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Extrait l'URL d'une chaîne de texte représentant un résultat d'annonce.
     *
     * @param result La chaîne contenant les informations d'une annonce, y compris l'URL.
     * @return Une chaîne représentant l'URL extraite, ou null si elle ne peut pas être trouvée.
     *
     * Cette méthode suppose que l'URL est à la fin de la chaîne et est séparée par un délimiteur
     * spécifique (par exemple, " | "). Elle divise la chaîne et retourne la dernière partie comme URL.
     */
    private String extractUrlFromResult(String result) {
        String[] parts = result.split(" | ");
        return parts[parts.length - 1];
    }

    @FXML
    private void handleBackAction() {
        Stage stage = (Stage) resultsListView.getScene().getWindow();
        stage.close();


    }


}
