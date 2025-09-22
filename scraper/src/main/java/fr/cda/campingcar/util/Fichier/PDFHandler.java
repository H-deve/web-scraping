package fr.cda.campingcar.util.Fichier;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.wp.usermodel.Paragraph;

public class PDFHandler extends FichierImp {
    /**
     * Classe PDFHandler utilisée pour générer des fichiers PDF, y compris les résultats
     * de recherche et les détails d'annonce. Cette classe s'appuie sur la bibliothèque iText.
     */
    public PDFHandler(String fileName1) {
        super(fileName1);
    }

    private boolean isExiste(){

        return false;
    }

    /**
     * Génère un fichier PDF contenant les résultats de recherche des annonces.
     *
     * @param results Liste des chaînes représentant les résultats de recherche.
     *
     * Cette méthode crée un fichier PDF nommé comme défini dans `fileName1`. Chaque
     * élément de la liste `results` est ajouté en tant que paragraphe dans le PDF.
     *
     * En cas d'erreur de création de document ou d'écriture de fichier, les exceptions
     * `DocumentException` et `FileNotFoundException` sont gérées.
     */
    public void generateRechercheAnnoncePDF(List<String> results) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(super.getFileName1()));

            document.open();

            document.add(new com.itextpdf.text.Paragraph("Recherche Annonce Results"));
            document.add(Chunk.NEWLINE);

            for (String result : results) {
                document.add(new com.itextpdf.text.Paragraph(result));
            }

            // Close the document
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Génère un fichier PDF contenant les détails d'une annonce spécifique.
     *
     * @param vehicleName Nom du véhicule.
     * @param reference Référence de l'annonce.
     * @param length Longueur du véhicule.
     * @param height Hauteur du véhicule.
     * @param year Année de fabrication.
     * @param transmission Type de transmission (manuelle ou automatique).
     * @param fuelType Type de carburant utilisé.
     * @param consumption Consommation du véhicule.
     * @param mileage Kilométrage actuel.
     * @param availabilityDate Date de disponibilité du véhicule.
     * @param description Description détaillée de l'annonce.
     * @param imageUrls Liste des URLs des images associées à l'annonce.
     *
     * Cette méthode crée un PDF contenant les informations détaillées d'une annonce.
     * Les images fournies via `imageUrls` sont chargées et insérées dans le document,
     * ajustées à une taille raisonnable. Si une image ne peut pas être chargée, une erreur
     * est imprimée dans la console.
     *
     * En cas d'erreur de création de document ou d'écriture de fichier, les exceptions
     * `DocumentException` et `FileNotFoundException` sont gérées.
     */
    public void generateAnnonceDetailsPDF(String vehicleName, String reference, String length, String height,
                                          String year, String transmission, String fuelType, String consumption,
                                          String mileage, String availabilityDate, String description,
                                          List<String> imageUrls) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(super.getFileName1()));
            document.open();

            document.add(new com.itextpdf.text.Paragraph("Annonce Details"));
            document.add(Chunk.NEWLINE);

            document.add(new com.itextpdf.text.Paragraph("Vehicle Name: " + vehicleName));
            document.add(new com.itextpdf.text.Paragraph("Reference: " + reference));
            document.add(new com.itextpdf.text.Paragraph("Length: " + length));
            document.add(new com.itextpdf.text.Paragraph("Height: " + height));
            document.add(new com.itextpdf.text.Paragraph("Year: " + year));
            document.add(new com.itextpdf.text.Paragraph("Transmission: " + transmission));
            document.add(new com.itextpdf.text.Paragraph("Fuel Type: " + fuelType));
            document.add(new com.itextpdf.text.Paragraph("Consumption: " + consumption));
            document.add(new com.itextpdf.text.Paragraph("Mileage: " + mileage));
            document.add(new com.itextpdf.text.Paragraph("Available From: " + availabilityDate));
            document.add(Chunk.NEWLINE);

            document.add(new com.itextpdf.text.Paragraph("Description:"));
            document.add(new com.itextpdf.text.Paragraph(description));
            document.add(Chunk.NEWLINE);

            document.add(new com.itextpdf.text.Paragraph("Images:"));
            for (String imageUrl : imageUrls) {
                try {
                    Image pdfImage = Image.getInstance(new URL(imageUrl));
                    pdfImage.scaleToFit(200, 200); // Adjust image size if necessary
                    document.add(pdfImage);
                    document.add(Chunk.NEWLINE);
                } catch (Exception e) {
                    System.out.println("Could not load image: " + imageUrl);
                }
            }

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}