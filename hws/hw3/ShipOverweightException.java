/**
 * An Exception class that is called when cargo would make the ship exceed maxWeight and sink.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class ShipOverweightException extends Exception {
    public ShipOverweightException(String s) {
        super(s);
    }
}
