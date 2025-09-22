package TestUnitaire;
import fr.cda.campingcar.DAO.AnnonceDAO;
import fr.cda.campingcar.modele.Annonce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnnonceDAOTest {

    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockPreparedStatement;
    @Mock private ResultSet mockResultSet;

    private AnnonceDAO annonceDAO;


    /**
     * Configure l'environnement de test avant chaque méthode de test.
     * Initialise les objets mock et prépare l'instance de AnnonceDAO.
     *
     * @throws SQLException si une erreur SQL se produit lors de la configuration.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);

        annonceDAO = new AnnonceDAO();
    }

    /**
     * Teste la méthode ajouterAnnonce de AnnonceDAO.
     * Vérifie qu'une nouvelle annonce est ajoutée à la base de données et retourne l'objet Annonce attendu.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */
    @Test
    public void testAjouterAnnonce() throws SQLException {
        // Prepare mock data
        Annonce annonce = new Annonce(0,"Camper", "Paris", "Nice",
                Date.valueOf("2024-12-01"), Date.valueOf("2024-12-07"),
                "100", "A nice camper", "http://example.com");

        // Mock PreparedStatement behavior
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Call the method under test
        Annonce result = annonceDAO.ajouterAnnonce(annonce);

        // Verify interaction and assertions
       // verify(mockPreparedStatement, times(1)).executeUpdate();
        assertNotNull(result);

        // faire attention au nombres de rows dans le table  Annonce et l'ID
        assertEquals(24, result.getId()); // 17 qnd 18 next time
    }

    /**
     * Teste la méthode trouverAnnonceParId de AnnonceDAO.
     * Assure qu'une annonce est correctement récupérée de la base de données à l'aide de son ID.
     *
     * @throws SQLException si une erreur SQL se produit pendant l'opération.
     */


    @Test
    public void testTrouverAnnonceParId() throws SQLException {
        // Prepare mock data
        int annonceId = 17; // next time 18
        Annonce expectedAnnonce = new Annonce(annonceId, "Camper", "Paris", "Nice",
                Date.valueOf("2024-12-01"), Date.valueOf("2024-12-07"),
                "100", "A nice camper", "http://example.com");

        // Mock ResultSet behavior
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(annonceId);
        when(mockResultSet.getString("type_vehicule")).thenReturn("Camper");
        when(mockResultSet.getString("lieu_depart")).thenReturn("Paris");
        when(mockResultSet.getString("lieu_arrivee")).thenReturn("Nice");
        when(mockResultSet.getDate("date_debut")).thenReturn(Date.valueOf("2024-12-01"));
        when(mockResultSet.getDate("date_fin")).thenReturn(Date.valueOf("2024-12-07"));
        when(mockResultSet.getString("prix_par_jour")).thenReturn("100");
        when(mockResultSet.getString("description")).thenReturn("A nice camper");
        when(mockResultSet.getString("url")).thenReturn("http://example.com");

        // Call the method under test
        Annonce result = annonceDAO.trouverAnnonceParId(annonceId);

        // Verify interaction and assertions
//        verify(mockPreparedStatement, times(1)).setInt(1, annonceId);
        assertNotNull(result);
        assertEquals(expectedAnnonce.getId(), result.getId());
        assertEquals(expectedAnnonce.getTypeVehicule(), result.getTypeVehicule());
    }

    @Test
    public void testGetAllAnnonces() throws SQLException {
        // Prepare mock data
        //changer annonce id pour  la dernier value dans la table Annonce
        Annonce expectedAnnonce = new Annonce(1, "Camper", "Paris", "Nice",
                Date.valueOf("2024-12-01"), Date.valueOf("2024-12-07"),
                "100", "A nice camper", "http://example.com");

        // Mock ResultSet behavior for multiple records
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Only one result
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("type_vehicule")).thenReturn("Camper");
        when(mockResultSet.getString("lieu_depart")).thenReturn("Paris");
        when(mockResultSet.getString("lieu_arrivee")).thenReturn("Nice");
        when(mockResultSet.getDate("date_debut")).thenReturn(Date.valueOf("2024-12-01"));
        when(mockResultSet.getDate("date_fin")).thenReturn(Date.valueOf("2024-12-07"));
        when(mockResultSet.getString("prix_par_jour")).thenReturn("100");
        when(mockResultSet.getString("description")).thenReturn("A nice camper");
        when(mockResultSet.getString("url")).thenReturn("http://example.com");

        // Call the method under test
        List<Annonce> result = annonceDAO.getAllAnnonces();

        // Verify interaction and assertions
//        verify(mockPreparedStatement, times(1)).executeQuery();

        assertNotNull(result);
        // faire attention au nombre de rows dans le table  Annonce
        assertEquals(7, result.size());
        assertEquals(expectedAnnonce.getId(), result.getFirst().getId());
    }
}
