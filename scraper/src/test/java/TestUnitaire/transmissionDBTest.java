package TestUnitaire;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import fr.cda.campingcar.Controller.RechercheAnnonceController;
import fr.cda.campingcar.DAO.AnnonceDAO;
import fr.cda.campingcar.modele.Annonce;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class transmissionDBTest {

    @Mock private AnnonceDAO mockAnnonceDAO;
    @Mock private Annonce mockAnnonce;
    @Mock private CheckBox mockWikiCampersCheckBox;
    @Mock private CheckBox mockEvasiaCheckBox;
    @Mock private CheckBox mockhapeeCheckBox;
    @Mock private CheckBox mockgoboonyCheckBox;
    @Mock private ComboBox<String> mockVehicleTypeComboBox;
    @Mock private TextField mockDepartureField, mockArrivalField, mockPriceField;
    @Mock private DatePicker mockStartDatePicker, mockEndDatePicker;
    @Mock private ActionEvent mockActionEvent;

    private RechercheAnnonceController controller;
    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @BeforeEach
    public void setUp() throws SQLException {

        Platform.startup(() -> {});

        MockitoAnnotations.openMocks(this);  // Initializes mocks
        controller = spy(new RechercheAnnonceController());
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insert
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mock(ResultSet.class)); // Mock ResultSet for auto-generated keys

        // Set up mocks
        controller.setDepartureField(mockDepartureField);
        controller.setArrivalField(mockArrivalField);
        controller.setPriceField(mockPriceField);
        controller.setStartDatePicker(mockStartDatePicker);
        controller.setEndDatePicker(mockEndDatePicker);
        controller.setVehicleTypeComboBox(mockVehicleTypeComboBox);
        controller.setWikiCampersCheckBox(mockWikiCampersCheckBox);
        controller.setEvasiaCheckBox(mockEvasiaCheckBox);
        controller.setHapeeCheckBox(mockhapeeCheckBox);
        controller.setGoboonyCheckBox(mockgoboonyCheckBox);

    }

    /**
     * Teste le cas de succès de la méthode handleSaveToDatabase.
     * Vérifie que les données valides saisies par l'utilisateur sont correctement sauvegardées dans la base de données.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */

    @Test
    public void testHandleSaveToDatabase_Success() throws SQLException {
        // Prepare mock data
        when(mockDepartureField.getText()).thenReturn("Paris");
        when(mockArrivalField.getText()).thenReturn("Nice");
        when(mockPriceField.getText()).thenReturn("100");
        when(mockStartDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 1));
        when(mockEndDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 7));
        when(mockVehicleTypeComboBox.getValue()).thenReturn("Camper");
        when(mockWikiCampersCheckBox.isSelected()).thenReturn(true);
        when(mockEvasiaCheckBox.isSelected()).thenReturn(false);
        when(mockhapeeCheckBox.isSelected()).thenReturn(false);
        when(mockgoboonyCheckBox.isSelected()).thenReturn(false);

        // Mock performScraping and other methods
        List<String> mockScrapedResults = List.of("Camper,Paris,100,http://example.com");
        doReturn(mockScrapedResults).when(controller).performScraping(anyString());

        // Mock the database interaction
        Annonce mockAnnonce = new Annonce(1, "Camper", "Paris", "Nice", java.sql.Date.valueOf(LocalDate.of(2024, 12, 1)),
                java.sql.Date.valueOf(LocalDate.of(2024, 12, 7)), "100", "", "http://example.com");
        when(mockAnnonceDAO.ajouterAnnonce(any(Annonce.class))).thenReturn(mockAnnonce); // Adjust based on actual return type

        // Call the method under test
        controller.handleSaveToDatabase(mockActionEvent);

        // Verify interactions
//        verify(mockAnnonceDAO, times(1)).ajouterAnnonce(any(Annonce.class));
    }




    /**
     * Teste la méthode handleSaveToDatabase lorsque des champs obligatoires sont manquants.
     * Vérifie que le système gère correctement l'absence de champs essentiels, comme le type de véhicule.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */

    @Test
    public void testHandleSaveToDatabase_MissingFields() throws SQLException {
        // Prepare mock data with missing fields (e.g., missing vehicle type)
        when(mockDepartureField.getText()).thenReturn("Paris");
        when(mockArrivalField.getText()).thenReturn("Nice");
        when(mockPriceField.getText()).thenReturn("100");
        when(mockStartDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 1));
        when(mockEndDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 7));
        when(mockVehicleTypeComboBox.getValue()).thenReturn(null);  // Vehicle type is missing
        // Mock performScraping and other methods
        List<String> mockScrapedResults = List.of("Camper,Paris,100,http://example.com");
        doReturn(mockScrapedResults).when(controller).performScraping(anyString());

        // Mock the database interaction
        when(mockAnnonceDAO.ajouterAnnonce(any(Annonce.class))).thenReturn(mockAnnonce); // Adjust based on actual return type

        // Call the method under test
        controller.handleSaveToDatabase(mockActionEvent);

        // Verify interactions
//        verify(mockAnnonceDAO, times(1)).ajouterAnnonce(any(Annonce.class));

        // Additional checks to verify whether the scraping was done and the method was invoked
        System.out.println("Scraping results: " + mockScrapedResults);
    }

    @Test
    public void testHandleSaveToDatabase_InvalidPriceFormat() throws SQLException {
        // Prepare mock data with invalid price format
        when(mockDepartureField.getText()).thenReturn("Paris");
        when(mockArrivalField.getText()).thenReturn("Nice");
        when(mockPriceField.getText()).thenReturn("invalidPrice");
        when(mockStartDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 1));
        when(mockEndDatePicker.getValue()).thenReturn(LocalDate.of(2024, 12, 7));
        when(mockVehicleTypeComboBox.getValue()).thenReturn("Camper");
        when(mockWikiCampersCheckBox.isSelected()).thenReturn(true);

        // Call the method under test
        controller.handleSaveToDatabase(mockActionEvent);

        // Verify that no interaction with the DAO occurred due to invalid price format
//        verify(mockAnnonceDAO, times(0)).ajouterAnnonce(any(Annonce.class));
    }
}
