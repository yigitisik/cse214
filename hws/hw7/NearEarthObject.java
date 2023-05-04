/**
 * NearEarthObject class represent a near earth object and
 * has attributes like id, name, and average diameter
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw7
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Date;
public class NearEarthObject {
    private int referenceID;
    private String name;
    private double absoluteMagnitude;
    private double averageDiameter;
    private boolean isDangerous;
    private Date closestApproachDate;
    private double missDistance;
    private String orbitingBody;

    /**
     * constructor for creating a near earth object
     * @param referenceID for ref id param
     * @param name for name param
     * @param absoluteMagnitude for mag param
     * @param minDiameter for min diameter param
     * @param maxDiameter for max diameter param
     * @param isDangerous for danger param
     * @param closestDateTimestamp for time stamp param
     * @param missDistance for miss distance param
     * @param orbitingBody for orbiting body param
     */
    public NearEarthObject(int referenceID, String name, double absoluteMagnitude,
                           double minDiameter, double maxDiameter, boolean isDangerous, long closestDateTimestamp,
                           double missDistance, String orbitingBody) {
        this.referenceID = referenceID;
        this.name = name;
        this.absoluteMagnitude = absoluteMagnitude;
        this.averageDiameter = (minDiameter+maxDiameter)/2;
        this.isDangerous = isDangerous;
        this.closestApproachDate = new Date(closestDateTimestamp);
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
    }

    /**
     * gets ref id
     * @return ref id
     */
    public int getReferenceID() {
        return referenceID;
    }

    /**
     * sets ref id
     * @param referenceID for setting
     */
    public void setReferenceID(int referenceID) {
        this.referenceID = referenceID;
    }

    /**
     * gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name for setting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets absolute magnitude
     * @return
     */
    public double getAbsoluteMagnitude() {
        return absoluteMagnitude;
    }

    /**
     * sets absolute magnitude
     * @param absoluteMagnitude for setting
     */
    public void setAbsoluteMagnitude(double absoluteMagnitude) {
        this.absoluteMagnitude = absoluteMagnitude;
    }

    /**
     * gets avg diameter
     * @return avg diameter
     */
    public double getAverageDiameter() {
        return averageDiameter;
    }

    /**
     * sets avg diameter
     * @param averageDiameter for setting
     */
    public void setAverageDiameter(double averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    /**
     * gets danger boolean
     * @return boolean of danger
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * sets danger boolean
     * @param dangerous for setting
     */
    public void setDangerous(boolean dangerous) {
        this.isDangerous = dangerous;
    }

    /**
     * gets closest approach date
     * @return the date
     */
    public Date getClosestApproachDate() {
        return closestApproachDate;
    }

    /**
     * sets closest approach date
     * @param closestApproachDate for setting
     */
    public void setClosestApproachDate(Date closestApproachDate) {
        this.closestApproachDate = closestApproachDate;
    }

    /**
     * gets miss distance
     * @return double of distance
     */
    public double getMissDistance() {
        return missDistance;
    }

    /**
     * sets miss distance
     * @param missDistance for setting
     */
    public void setMissDistance(double missDistance) {
        this.missDistance = missDistance;
    }

    /**
     * gets orbiting body
     * @return orbit body
     */
    public String getOrbitingBody() {
        return orbitingBody;
    }

    /**
     * sets orbiting body
     * @param orbitingBody for setting
     */
    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }
}
