/**
 * An Exception class that is called when the stack being popped from is empty.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class EmptyStackException extends Exception{
    public EmptyStackException(String s) {
        super(s);
    }
}