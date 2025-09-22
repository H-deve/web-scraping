package fr.cda.campingcar.Controller;

import fr.cda.campingcar.modele.Client;
import fr.cda.campingcar.util.*;
import fr.cda.campingcar.util.SendMail;
import fr.cda.campingcar.util.Session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MailController {
    @FXML
    private TextField tfRecipient;


    @FXML
    private TextField tfsujet;


    @FXML
    private TextArea tfbody;

    @FXML
    private Button btnFermer;

    @FXML
    private Label successLabel;

    @FXML
    private Label errorLabel;

    @FXML
    Button parcourir;


    @FXML
    private Label selectedFilesLabel;

    private List<File> selectedFiles;

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
     * Affiche un sélecteur de fichiers pour permettre à l'utilisateur de choisir plusieurs fichiers.
     * Les fichiers sélectionnés sont stockés dans une liste, et leurs noms sont affichés
     * dans une étiquette (`selectedFilesLabel`). Si aucun fichier n'est sélectionné, un message
     * approprié est affiché.
     */



    @FXML
    private void showChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose files");
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (files != null && !files.isEmpty()) {
            selectedFiles = files;
            String fileNames = files.stream().map(File::getName).collect(Collectors.joining(", "));
            selectedFilesLabel.setText("Selected files: " + fileNames);
        } else {
            selectedFilesLabel.setText("No files selected");
        }
    }


    /**
     * Envoie un e-mail au destinataire spécifié avec un sujet et un corps.
     * Valide l'adresse e-mail avant d'envoyer le message. Si des fichiers
     * ont été sélectionnés via le sélecteur de fichiers, ils sont ajoutés
     * en tant que pièces jointes. Utilise un thread distinct pour exécuter
     * l'envoi d'e-mails afin d'éviter de bloquer l'interface utilisateur.
     *
     * @throws InterruptedException Si le thread d'envoi est interrompu pendant l'exécution.
     */

    @FXML
    private void sendMail() throws InterruptedException {
        String recipient = tfRecipient.getText();
        if (Pattern.compile("^(?=.{1,64}@)[\\w]+(\\.[\\w]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(recipient).matches()) {
            String sujet = tfsujet.getText();
            String body = tfbody.getText();

            // Create SendMail instance
            SendMail sm = new SendMail(this, recipient, sujet, body);

            // Set multiple attachments if selected
            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                sm.setAttachments(selectedFiles);
            }

            Thread thread = new Thread(sm);
            thread.start();
            thread.join(5000);

            Stage stage = (Stage) btnFermer.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email address.");
            alert.showAndWait();
        }
    }

    @FXML
    private void fermerMail() {
        Stage stage = (Stage) btnFermer.getScene().getWindow();
        stage.close();
    }

    public Label getSuccessLabel() {

        return successLabel;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public TextField getTfRecipient() {
        return tfRecipient;
    }

    public void setTfRecipient(TextField tfRecipient) {
        this.tfRecipient = tfRecipient;
    }

    public TextField getTfsujet() {
        return tfsujet;
    }

    public void setTfsujet(TextField tfsujet) {
        this.tfsujet = tfsujet;
    }

    public TextArea getTfbody() {
        return tfbody;
    }

    public void setTfbody(TextArea tfbody) {
        this.tfbody = tfbody;
    }

    public Button getBtnFermer() {
        return btnFermer;
    }

    public void setBtnFermer(Button btnFermer) {
        this.btnFermer = btnFermer;
    }

    public void setSuccessLabel(Label successLabel) {
        this.successLabel = successLabel;
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    public Button getParcourir() {
        return parcourir;
    }

    public void setParcourir(Button parcourir) {
        this.parcourir = parcourir;
    }

    public Label getSelectedFilesLabel() {
        return selectedFilesLabel;
    }

    public void setSelectedFilesLabel(Label selectedFilesLabel) {
        this.selectedFilesLabel = selectedFilesLabel;
    }

    public List<File> getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(List<File> selectedFiles) {
        this.selectedFiles = selectedFiles;
    }

    public Label getUserEmailLabel() {
        return userEmailLabel;
    }

    public void setUserEmailLabel(Label userEmailLabel) {
        this.userEmailLabel = userEmailLabel;
    }
}
