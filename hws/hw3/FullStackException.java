/**
 * An Exception class that is called when the stack being pushed to is at the max height.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class FullStackException extends Exception {
    public FullStackException(String s) {
        super(s);
    }
}