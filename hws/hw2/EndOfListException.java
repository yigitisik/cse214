/**
 * An Exception class which indicates that the user attempted to access a SlideListNode
 * that does not exist (either before the head node or after the tail node). This exception can also be thrown
 * when an operation is performed on an empty list (i.e. head, tail, and cursor are all equal to null).
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class EndOfListException extends Exception {
    public EndOfListException(String s) {
        super(s);
    }
}