/**
 * NeoDatabase class represent a database of near earth objects
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw7
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import org.json.*;
import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.ArrayList;
public class NeoDatabase extends ArrayList<NearEarthObject>{
    public static final String API_KEY = "RpsReXjKLuauekec7E2HiknasgFimjylPmzlDdbV";
    public static final String API_ROOT = "https://api.nasa.gov/neo/rest/v1/neo/browse?";

    /**
     * default constructor to build upon
     */
    public NeoDatabase(){}

    /**
     * builds query with the given parameters for the database
     * @param pageNumber for page number
     * @return string of built query
     * @throws IllegalArgumentException when invalid argument
     */
    public String buildQueryURL(int pageNumber) throws IllegalArgumentException{
        if(pageNumber < 0 || pageNumber > 715){
            throw new IllegalArgumentException("Invalid argument.");
        }
        return API_ROOT + "page=" + pageNumber + "&api_key=" + API_KEY;
    }

    /**
     * adds the object into database view
     * @param queryURL so that we add via the url
     * @throws IllegalArgumentException when invalid argument
     */
    public void addAll(String queryURL) throws IllegalArgumentException{
        if(queryURL == null){throw new IllegalArgumentException("Invalid argument.");}
        try {
            URL getRequest = new URL(queryURL);
            JSONTokener tokenizer = new JSONTokener(getRequest.openStream());
            JSONObject root = new JSONObject(tokenizer);
            JSONArray array = root.getJSONArray("near_earth_objects");
            for(int i = 0; i <= array.length()-1; i++) {
                JSONArray approachArray = array.getJSONObject(i).getJSONArray("close_approach_data");
                JSONObject obj = (JSONObject) array.get(i);
                int referenceID = Integer.parseInt((String) obj.get("neo_reference_id"));
                String name = (String) obj.get("name");
                double absoluteMagnitude = (Double) (obj.get("absolute_magnitude_h"));
                double minDiameter = obj.getJSONObject("estimated_diameter").getJSONObject("kilometers").getDouble("estimated_diameter_min");
                double maxDiameter = obj.getJSONObject("estimated_diameter").getJSONObject("kilometers").getDouble("estimated_diameter_max");
                boolean isDangerous = (boolean) obj.get("is_potentially_hazardous_asteroid");

                //bring into scope
                long closestDateTimestamp = -1;
                double missDistance = -1;
                String orbitingBody = null;

                for (int j = 0; j <= array.length() - 1; j++) {
                    JSONObject approachObj = (JSONObject) approachArray.get(j);
                    closestDateTimestamp = approachArray.getJSONObject(i).getLong("epoch_date_close_approach");
                    missDistance = approachObj.getJSONObject("miss_distance").getDouble("kilometers");
                    orbitingBody = approachObj.getString("orbiting_body");
                }
                add(new NearEarthObject
                        (referenceID, name, absoluteMagnitude, minDiameter, maxDiameter,
                        isDangerous, closestDateTimestamp, missDistance, orbitingBody));
            }
        } catch (JSONException jsone) {
            System.out.println();
        } catch (IOException ioe) {
            System.out.println("IOException");
        }
    }

    /**
     * sorts the set of near earth objects
     * @param comparator built in comparator
     * @throws IllegalArgumentException when invalid argument
     */
    public void sorting(Comparator<NearEarthObject> comparator) throws IllegalArgumentException{
        if (comparator == null) {throw new IllegalArgumentException("Invalid argument.");}
        sort(comparator);
    }

    /**
     * prints the table consisting of near earth objects
     */
    public void printTable(){
        StringBuilder table = new StringBuilder(
                "  ID   |           Name            | Mag. | Diameter | Danger | Close Date | Miss Dist | Orbits\n" +
                "================================================================================================");

        for (NearEarthObject obj: this) {
            table.append("\n").append(String.format("%8d%26s%6.01f%9.03f%10b%8tm/%<td/%<ty%14.02f%8s",
                    obj.getReferenceID(), obj.getName(), obj.getAbsoluteMagnitude(), obj.getAverageDiameter(),
                    obj.isDangerous(), obj.getClosestApproachDate(), obj.getMissDistance(), obj.getOrbitingBody()));
        }
        System.out.println(table);
    }
}
