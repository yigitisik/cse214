/**
 * Cargo class is designed to hold place of a single piece or cargo and its
 * attributes such as name, weight, and strength.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class Cargo {
    private String name;
    private double weight;
    private CargoStrength strength;

    /**
     * constructor for creating new cargo
     * @param initName for cargo name
     * @param initWeight for cargo weight
     * @param initStrength for cargo strength
     */
    public Cargo(String initName, double initWeight, CargoStrength initStrength) {
        if(initName == null || initWeight <= 0){throw new IllegalArgumentException("Invalid argument.");}
        this.name = initName;
        this.weight = initWeight;
        this.strength = initStrength;
    }

    /**
     * @return cargo name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return cargo weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @return cargo strength
     */
    public CargoStrength getStrength() {
        return this.strength;
    }

    /**
     * @return cargo clone
     */
    public Cargo clone() {
        return new Cargo(this.name,this.weight,this.strength);
    }

    /**
     * @return cargo strength's initial letter for representation purposes
     * helps with the user command and implements the given requirement in the html file
     */
    public String toString() {
        String s = "";
        if(strength.equals(CargoStrength.FRAGILE)){s = "F";}
        if(strength.equals(CargoStrength.MODERATE)){s = "M";}
        if(strength.equals(CargoStrength.STURDY)){s = "S";}
        return s;
    }
}