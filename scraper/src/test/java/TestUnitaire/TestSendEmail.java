package TestUnitaire;

import fr.cda.campingcar.Controller.MailController;
import fr.cda.campingcar.util.SendMail;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import sibModel.CreateSmtpEmail;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Create a custom SendMail subclass for testing
    class TestSendMail  {


    @BeforeAll
    static void initializeJavaFX() {
        Platform.startup(() -> {});
    }


    /**
     * Teste la méthode d'envoi de courriel via la classe MailController.
     * Vérifie que les champs de destinataire, sujet et corps de l'e-mail sont correctement définis
     * et que l'e-mail est envoyé avec succès en utilisant l'objet SendMail.
     *
     * @throws Exception si une erreur survient lors de l'exécution.
     */

    @Test
    void testSendMailFunction() throws Exception {

        MailController controller = new MailController();

        controller.setTfRecipient(new TextField());
        controller.setTfsujet(new TextField());
        controller.setTfbody(new TextArea());
        controller.setSuccessLabel(new Label());
        controller.setErrorLabel(new Label());

        controller.getTfRecipient().setText("");
        controller.getTfsujet().setText("Test Subject");
        controller.getTfbody().setText("This is a test email.");

        SendMail sendMail = new SendMail(controller,
                controller.getTfRecipient().getText(),
                controller.getTfsujet().getText(),
                controller.getTfbody().getText());
        Platform.runLater(sendMail::run);

        assertNotNull(sendMail,"L'objet SendMail devrait être créé avec succès.");
        assertEquals("Test Subject", controller.getTfsujet().getText(), "Le sujet devrait être correctement défini.");
        assertEquals("This is a test email.", controller.getTfbody().getText(), "Le corps de l'e-mail doit être correctement défini.");


        Thread.sleep(3000);
        assertEquals("", controller.getTfRecipient().getText(),"L'adresse électronique du destinataire doit être correctement définie. ");
        assertEquals("Mail sent successfully!", controller.getSuccessLabel().getText(),
                "The success label should indicate that the email was sent.");
    }


    }

