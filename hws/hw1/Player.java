/**
 * Player class is designed to hold place of a player and their attributes such as name, hits, and errors.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw1
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

public class Player {
    private String name;
    private int numHits, numErrors;
    private int position;
    /**
     * This is a Constructor used to create a new Player object
     *
     * @param name      Name of the player
     * @param numHits   Hit stats for the player
     * @param numErrors Error stats for the player
     */

    public Player(String name, int numHits, int numErrors) {
        setName(name);
        setNumHits(numHits);
        setNumErrors(numErrors);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumHits() {
        return numHits;
    }

    public void setNumHits(int numHits) throws IllegalArgumentException {
        if (numHits < 0) {
            throw new IllegalArgumentException("numHits can't be less than zero");
        } else {
            this.numHits = numHits;
        }
    }

    public int getNumErrors() {
        return numErrors;
    }

    public void setNumErrors(int numErrors) throws IllegalArgumentException {
        if (numErrors < 0) {
            throw new IllegalArgumentException("numErrors can't be less than zero");
        } else {
            this.numErrors = numErrors;
        }
    }

    /**
     * @return a print of the player and his statistics (hits and errors) in a formatted form.
     */
    @Override
    public String toString() {
        return String.format("%-2d%-25s%-3d%-3d\n", position, name, numHits, numErrors);
    }
}