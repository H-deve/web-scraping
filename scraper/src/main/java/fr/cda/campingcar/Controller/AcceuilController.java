

package fr.cda.campingcar.Controller;

import fr.cda.campingcar.Main;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.util.Session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class AcceuilController  {


    // FXML Components
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView campingCarImageView;
    @FXML
    private ImageView feature1ImageView;
    @FXML
    private ImageView feature2ImageView;
    @FXML
    private ImageView feature3ImageView;

    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Label userEmailLabel;

    @FXML
    private Button logoutButton;
    @FXML
    private Button Recherche;
    @FXML
    private Button favoritesButton;
    @FXML
    private Button historyButton;

    // Carousel-related attributes
    private int currentImageIndex = 0;
    private final String[] imageUrls = {
            "camping_car_2.jpg",
            "camping_car_3.jpg",
            "camping_car_4.jpg"
    };

    /**
     * apres login d'utilsateur garder la session et avoir l'informatios d'utilsateur
     */
    @FXML
    public void initialize() {
        UserSession session = UserSession.getInstance();
        if (session != null && session.getLoggedInClient() != null) {
            Client loggedInClient = session.getLoggedInClient();
            userEmailLabel.setText("bienvenu, " + loggedInClient.getEmail());
        } else {
            userEmailLabel.setText("Non user logged in");
        }
    }



    /**
     * Method pour l'affichage de la page Accueil
     *
     * @param w Window
     */
    public void HomeScene(Window w) {
        try {


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getResource("acceuil.fxml"));

            Scene scene = new Scene(loader.load(), 900, 500);
            Stage stage = (Stage) w;
            stage.setTitle("Accueil");
            stage.setScene(scene);

            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  affichage page recherch
     * @param w
     */
    public void showRechercheAnnoncePage(Window w) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getResource("rechercheAnnonce.fxml"));

            Scene scene = new Scene(loader.load(), 1200, 650);
            Stage stage = (Stage) w;
            stage.setTitle("Accueil");
            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleRechercheButton() {

        Window w = Recherche.getScene().getWindow();
        showRechercheAnnoncePage(w);
    }

    @FXML
    private void handleHistoryButton() {
        // Navigate to the history page
        System.out.println("Search History clicked.");
    }

    /**
     * Gère la navigation vers l'image suivante dans le carrousel.
     */
    @FXML
    private void handleNextImage() {
        currentImageIndex = (currentImageIndex + 1) % imageUrls.length;
        campingCarImageView.setImage(new Image(imageUrls[currentImageIndex]));
        System.out.println("Next image displayed.");
    }

    /**
     * Gère la navigation vers l'image précédent dans le carrousel.
     */
    @FXML
    private void handlePreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + imageUrls.length) % imageUrls.length;
        campingCarImageView.setImage(new Image(imageUrls[currentImageIndex]));
        System.out.println("Previous image displayed.");
    }

    /**
     * Gère l'action de l'élément de menu « Fermer ».
     */
    @FXML
    private void handleClose() {
        System.out.println("Application closing.");
        System.exit(0);
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton de déconnexion.
     * Elle efface la session utilisateur, met à jour l'interface utilisateur,
     * et redirige l'utilisateur vers l'écran de connexion.
     */
    @FXML
    public void handleLogout() {
        // Clear the session (log out the user)
        UserSession.clearSession();

        // Update the UI to reflect that the user is logged out
        userEmailLabel.setText("No user logged in");

        // Optionally, navigate to the login page
        try {
            // Load the login page (replace the path with your actual login.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Set the new scene (login screen)
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }



    }

