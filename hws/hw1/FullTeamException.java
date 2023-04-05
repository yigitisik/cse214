/**
 * FullTeamException is thrown when the user tries adding a player while the team is at capacity.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw1
 * Rec 02 Jamieson Barkume - Steven Secreti
*/

public class FullTeamException extends Exception{
    public FullTeamException(){
        super("The team is full, can't add any more players.");
    }
}