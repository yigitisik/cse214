/**
 * An Exception class that is called when the cargo would be stacked on top of a weaker cargo
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class CargoStrengthException extends Exception {
    public CargoStrengthException(String s) {
    super(s);
}
}