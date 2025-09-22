package fr.cda.campingcar.Controller;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;

public class PDFViewerController {
    @FXML
    private VBox pdfContainer;


    /**
     * Affiche un fichier PDF dans une nouvelle fenêtre (Stage).
     *
     * @param pdfPath Le chemin du fichier PDF à afficher.
     *
     * Cette méthode charge un fichier FXML pour l'interface utilisateur du visionneur de PDF,
     * configure le contrôleur correspondant, et affiche le contenu du PDF dans une nouvelle
     * scène. En cas d'erreur de chargement du fichier ou d'affichage, une exception est imprimée
     * dans la console.
     */
    public void showPDF(String pdfPath) {
        try {
            // Load the FXML file for the PDF Viewer
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/cda/campingcar/pdfViewer.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller and pass the PDF path
            PDFViewerController controller = fxmlLoader.getController();

            // Render the PDF into the VBox defined in the FXML
            controller.loadPDF(pdfPath, controller.pdfContainer);

            // Create a new Stage for the PDF viewer
            Stage pdfStage = new Stage();
            pdfStage.setTitle("Mode d'emploi");
            pdfStage.setScene(new Scene(root, 800, 600));
            pdfStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Charge et affiche le contenu d'un fichier PDF dans un conteneur VBox.
     *
     * @param pdfPath Le chemin du fichier PDF à charger.
     * @param pdfContainer Le conteneur VBox dans lequel les pages PDF seront affichées sous forme d'images.
     *
     * Cette méthode utilise `Apache PDFBox` pour lire le fichier PDF et convertir chaque page en
     * une image (BufferedImage). Les images sont ensuite transformées en objets `JavaFX Image`
     * et ajoutées au conteneur VBox sous forme de `ImageView`. En cas d'erreur (par exemple, fichier introuvable),
     * une exception est imprimée dans la console.
     */

    private void loadPDF(String pdfPath, VBox pdfContainer) {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                // Render the page as an image (BufferedImage)
                java.awt.image.BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 150); // 150 DPI

                // Convert BufferedImage to JavaFX Image
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                // Add Image to ImageView and then to VBox container
                ImageView imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(1200);
                imageView.setFitWidth(800); // Adjust the width
                pdfContainer.getChildren().add(imageView);
            }
            pdfContainer.setMinHeight(Region.USE_PREF_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
