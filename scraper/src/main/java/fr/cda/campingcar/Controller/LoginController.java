package fr.cda.campingcar.Controller;

import fr.cda.campingcar.DAO.ClientDAO;
import fr.cda.campingcar.Main;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.util.CryptPWD;
import fr.cda.campingcar.util.Session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button Connexion;

    @FXML
    private Button Inscription;



    private ClientDAO clientDAO = new ClientDAO();
    private Client client ;

    @FXML
    public void handleLoginAction() throws SQLException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Champs manquants", "Veuillez entrer votre email et mot de passe.");
            return;
        }

        // Step 1: Hash the entered password
        String hashedPassword = CryptPWD.getSHA512SecurePassword(password);

        // Step 2: Retrieve stored hashed password from the database for the entered email
        String storedHashedPassword = clientDAO.getHashedPasswordByEmail(email);

        // Step 3: Compare the hashed values
        if (storedHashedPassword != null && storedHashedPassword.equals(hashedPassword)) {
            client = clientDAO.getClientByEmail(email);
            UserSession.createSession(client);
            showAlert("Connexion réussie", "Bienvenue !");
            AcceuilController acceuilController = new AcceuilController();
            acceuilController.HomeScene(Connexion.getScene().getWindow());
        } else {
            showAlert("Échec de connexion", "Email ou mot de passe incorrect.");
        }
    }
        // Vérification dans la base de données
//        if (clientDAO.clientExists(email, hashedPassword)) {
//            if (storedHashedPassword.equals(hashedPassword)) {
//                showAlert("Connexion réussie", "Bienvenue !");
//                AcceuilController acceuilController = new AcceuilController();
//                acceuilController.HomeScene(Connexion.getScene().getWindow());
//
//            }



//        } else {
//            showAlert("Échec de connexion", "Email ou mot de passe incorrect.");
//        }



    /**
     * Method epour l'affichage de la page login
     *
     * @param w Window
     */
    public void loginPage(Window w) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getResource("login.fxml"));

            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) w;
            stage.setTitle("Login");
            stage.setScene(scene);

            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void openRegisterPage(){

        RegisterController registerController = new RegisterController();
        registerController.registerPage(Inscription.getScene().getWindow());

    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
