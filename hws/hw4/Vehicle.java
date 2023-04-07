/**
 *  Vehicle class is designed to hold place of a vehicle and
 *  its attributes like id or time arrived and
 *  methods like toString
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 */
public class Vehicle {
    private static int serialCounter = 0;
    private int serialID;
    private int timeArrived;

    /**
     * constructs a vehicle based on parameters
     * @param initTimeArrived is the arrival time of car
     * @throws IllegalArgumentException when invalid argument.
     */
    public Vehicle(int initTimeArrived){
        if(initTimeArrived <= 0){
            throw new IllegalArgumentException("Invalid argument.");
        }else {
            this.serialID = ++serialCounter;
            this.timeArrived = initTimeArrived;
        }
    }
    public int getSerialID() {
        return this.serialID;
    } // not needed as shown in line 40
    public int getTimeArrived() {
        return this.timeArrived;
    }

    /**
     * helps with the visuality of vehicles in simulator,
     * cars look from birds eye like this: [005]
     * @return string look of a car
     */
    public String toString() {
        return String.format("[%03d]", this.serialID);
    }
}