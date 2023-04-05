/**
 * Team class is designed to hold place of a team
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw1
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

import java.util.Arrays;
public class Team {
    /**
     * @param MAX_PLAYERS
     * static value for maximum number of players allowed in the team
     * @param currentlyFilled
     * used to find current filled amount in team, used as a helper variable for methods like clone()
     **/
    final int MAX_PLAYERS = 40;
    private int currentlyFilled;
    private Player[] players;

    public Team() {
        currentlyFilled = 0;
        players = new Player[MAX_PLAYERS];
    }
    /**
     * @return clone of team
     **/
    public Object clone(Object obj) {
        Team team = new Team();
        if (obj instanceof Team) {
            for (int i = 0; i < team.size(); i++) {
                team.players[i] = players[i];
            }
        }
        return team;
    }
    /**
     * Compare team to another object for equality
     * @param obj takes in object you want to check for equals
     * @return true if obj refers to a Team object
     * with the same Players in the same order as this Team. Otherwise, return false.
     * If obj is null or it is not a Team object, then the return value is false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Team team)) return false;
        return currentlyFilled == team.currentlyFilled && Arrays.equals(players, team.players);
    }

    /**
     * @return the size of the array, which in this case is the size of our current players' list
     */
    public int size() {
        return currentlyFilled;
    }


    /**
     * @param p        is the player parameter we accept for adding
     * @param position is the position parameter for where to add
     * @throws IllegalArgumentException when position is put in less than zero
     * @throws FullTeamException        when the team is at full capacity
     */
    public void addPlayer(Player p, int position) throws IllegalArgumentException, FullTeamException {
        if (currentlyFilled == MAX_PLAYERS) {
            throw new FullTeamException();
        }
        if (position < 0) {
            throw new IllegalArgumentException("Position can't be less than zero.");
        }
        if (position > currentlyFilled + 1) { // some specific scenarios need to be accounted for
            throw new IllegalArgumentException("Invalid position for adding the new player.");
        }

        for (int i = currentlyFilled; i >= position; i--) {
            players[i + 1] = players[i];
        }
        players[position] = p;
        currentlyFilled++;
    }

    /**
     * @param position is the position parameter
     * @throws IllegalArgumentException
     */
    public void removePlayer(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("Position can't be less than zero.");
        }
        if (players[position] == null) {
            throw new IllegalArgumentException("No player at position" + position + " to remove.");
        }

        for (int i = position; i < currentlyFilled; i++) {
            players[i] = players[i + 1];
        }
        players[--currentlyFilled] = null;
    }

    /**
     * @param position is what we accept to find a player at such position
     * @return player from the array based on @param
     * @throws IllegalArgumentException when position entered gives out null or beyond limits
     */
    public Player getPlayer(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("Position can't be less than zero.");
        }
        if (position + 1 > this.size() + 1) {
            throw new IllegalArgumentException("No player at position" + position + " to get.");
        }
        return players[position];
    }

    /**
     * @param stat is what we accept to find the leader of such stat
     * @return player who is the leader based on @param
     * @throws IllegalArgumentException when stat is neither "hits" nor "errors"
     */
    public Player getLeader(String stat) throws IllegalArgumentException {
        String loweredStat = stat.toLowerCase();

        if (loweredStat.equals("hits")) {
            int temp = 0;
            for (int i = 0; i < this.players.length; i++) {
                if (players[i].getNumHits() < players[i + 1].getNumHits()) {
                    ;
                    temp = i + 1;
                }
            }
            return players[temp];
        } else if (loweredStat.equals("errors")) {
            int temp = 0;
            for (int i = 0; i < players.length; i++) {
                if (players[i].getNumErrors() > players[i + 1].getNumErrors()) {
                    ;
                    temp = i + 1;
                }
            }
            return players[temp];
        } else {
            throw new IllegalArgumentException("No such statistic.");
        }
    }

    public void updateStat(String name, String stat, int updateValue) throws IllegalArgumentException {
        String loweredStat = stat.toLowerCase();

        for (int i = 0; i < this.size(); i++) {
            if (players[i].getName().equals(name)) {

                if (loweredStat.equals("hits")) {
                    players[i].setNumHits(updateValue);
                }
                if (loweredStat.equals("errors")) {
                    players[i].setNumErrors(updateValue);
                }
                System.out.println("Updated " + name + " " + stat);
            }
            throw new IllegalArgumentException("Player not found.");
        }
    }

    /**
     * @return printValue of all players in team
     */
    public void printAllPlayers() {
        System.out.println(toString());
    }

    /**
     * @return string version of row information
     */
    @Override
    public String toString() {
        String rows = "";
        String initialRowBlock = """
                Player#   Name               Hits       Errors
                ------------------------------------------------""";
        int i = 0;
        do {
            rows += String.format("%-8d%-25s%-12d%-12d\n",
                    i + 1, getPlayer(i).getName(), getPlayer(i).getNumHits(), getPlayer(i).getNumErrors());
            i++;
        } while (i < this.size());
        return initialRowBlock + "\n" + rows;
    }
}