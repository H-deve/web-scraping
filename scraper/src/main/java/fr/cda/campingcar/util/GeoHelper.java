package fr.cda.campingcar.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
public class GeoHelper {

    /**
     * Récupère l'ID GeoNames pour une ville donnée en utilisant l'API GeoNames.
     *
     * @param cityName Le nom de la ville pour laquelle récupérer l'ID GeoNames.
     *                 Les espaces dans le nom de la ville sont remplacés par "+" pour la requête API.
     * @return L'ID GeoNames sous forme de chaîne de caractères si trouvé, sinon une chaîne vide.
     *
     * La méthode construit une URL API à l'aide du nom de la ville donné et de
     * `GEO_NAMES_API_USERNAME`, envoie une requête HTTP GET et analyse la réponse JSON
     * pour extraire le premier ID GeoNames des résultats.
     *
     * <p>Exemple d'URL API :
     * {@code http://api.geonames.org/searchJSON?name_equals=NomDeVille&maxRows=1&username=VotreNomUtilisateur}</p>
     *
     * <p>Gestion des erreurs :</p>
     * Si une erreur se produit lors de la requête API, de l'analyse JSON ou de la connexion réseau,
     * l'exception est capturée et sa trace est affichée dans la console.
     *
     * <p>Exemple d'utilisation :</p>
     * <pre>
     * {@code
     * String geonameId = getGeonameIdFromApi("Paris");
     * if (!geonameId.isEmpty()) {
     *     System.out.println("ID GeoNames : " + geonameId);
     * } else {
     *     System.out.println("Aucun ID GeoNames trouvé.");
     * }
     * }
     * </pre>
     *
     * @see java.net.URL
     * @see java.net.HttpURLConnection
     * @see org.json.JSONObject
     * @see org.json.JSONArray
     */
    private static final String GEO_NAMES_API_USERNAME = ""; // Replace with your GeoNames username
    public static int getGeoNameId(String cityName) {
        int geonameId = 0;
        try {
            // Refine the API URL to specify the country and feature class
            String apiUrl = "http://api.geonames.org/searchJSON?q=" + cityName.replace(" ", "%20") +
                    "&maxRows=1&country=FR&featureClass=P&username=" + GEO_NAMES_API_USERNAME;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse.toString());
            JSONArray geonamesArray = responseJson.getJSONArray("geonames");
            if (geonamesArray.length() > 0) {
                geonameId = geonamesArray.getJSONObject(0).getInt("geonameId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return geonameId;
    }


    public static String getGeonameIdFromApi(String cityName) {
        String geonameId = "";
        try {
            String apiUrl = "http://api.geonames.org/searchJSON?name_equals=" + cityName.replace(" ", "+") +
                    "&maxRows=1&username=" + GEO_NAMES_API_USERNAME;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject json = new JSONObject(content.toString());
            JSONArray geonames = json.getJSONArray("geonames");

            // Check if any result was found
            if (geonames.length() > 0) {
                geonameId = geonames.getJSONObject(0).getString("geonameId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geonameId;
    }
}