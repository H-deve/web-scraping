package fr.cda.campingcar.Controller;

import fr.cda.campingcar.DAO.ClientDAO;
import fr.cda.campingcar.Main;
import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.util.CryptPWD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button pageLogin;

    private ClientDAO clientDAO = new ClientDAO();


    @FXML
    private void handleRegisterAction() throws SQLException {


          String nom = nomField.getText();
          String prenom = prenomField.getText();
          String email = emailField.getText();
         String password = passwordField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Champs manquants", "Tous les champs doivent être remplis");
        }

        String hashedPassword = CryptPWD.getSHA512SecurePassword(password);

        if (IfClientExist(email,hashedPassword)){

            showAlert("Client exist " , "Cet email ou mot de passe est déja utilisé");

        }else {

            Client client = new Client(0, nom, prenom, email, hashedPassword, "", "");
            try {
                clientDAO.ajouterClient(client);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            showAlert("Succès", "Inscription réussie!");
        }


    }

    public boolean IfClientExist(String email , String password) {

        return clientDAO.clientExists(email, password);


    }


    /**
     * Method epour l'affichage de la page register
     *
     * @param w Window
     */
    public void registerPage(Window w) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getResource("register.fxml"));

            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) w;
            stage.setTitle("s'inscrir");
            stage.setScene(scene);

            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleLoginAction(){

        LoginController loginController = new LoginController();
        loginController.loginPage(pageLogin.getScene().getWindow());


    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }


}


