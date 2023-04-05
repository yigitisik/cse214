/**
 * TeamManager class is designed to hold place of Main, as in the point of user and executor
 * The executions are displayed to the user in a menu and acted upon depending on the user's input choice.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw1
 * Rec 02 Jamieson Barkume - Steven Secreti
 */

import java.util.Scanner;

public class TeamManager {
    public static final int MAX_TEAMS = 5;
    private static Team[] teams;
    private static int selectedTeam = 0;

        public Team[] getTeams() {
                return this.teams;
        }

        public static Team getSelectedTeam(int i) {
            return teams[i];
        }

        public static void setSelectedTeam(int newSelected) {
            selectedTeam = newSelected-1;
        }

        public TeamManager() {
        this.teams = new Team[MAX_TEAMS];
    }

        public static void main(String[] args) throws FullTeamException {
                printMenu();
                optionsList();
        }

    public static void printMenu(){
        String openingMenu = """
                Welcome to TeamManager!
                        A) Add Player
                        G) Get Player stats
                        L) Get leader in a stat
                        R) Remove a player
                        P) Print all players
                        S) Size of team
                        T) Select team 
                        C) Clone team 
                        E) Team equals
                        U) Update stat
                        Q) Quit.
                        """;
        System.out.println(openingMenu);

    }
    public static void optionsList() throws FullTeamException {
        System.out.println("Select a menu option: ");
        Scanner infoScanner = new Scanner(System.in);

        String temp = infoScanner.nextLine();
        char input = temp.charAt(0);
        String option = Character.toString(input).toLowerCase();

        while (!option.equals("q")) {
            if (option.equals("a")) {
                System.out.println("Enter the player's name: ");
                String name = infoScanner.nextLine();

                System.out.println("Enter the number of hits: ");
                int hits = infoScanner.nextInt();

                System.out.println("Enter the number of errors: ");
                int errors = infoScanner.nextInt();

                System.out.println("Enter the position: ");
                int position = infoScanner.nextInt();

                Player player = new Player(name, hits, errors);
                Team team = new Team();
                team.addPlayer(player, position);

                System.out.println("Player added: " + name
                        + " - " + hits + " hits, " + errors + " errors");
                printMenu();
                optionsList();
            }
            if (option.equals("g")) {

                System.out.print("Enter position: ");
                int position = infoScanner.nextInt();

                Team team = new Team();

                System.out.println(team.getPlayer(position).toString());
                printMenu();
                optionsList();
            }

            if (option.equals("l")) {
                System.out.println("Enter the stats: ");
                String stat = infoScanner.nextLine();
                Team team = new Team();

                System.out.println("Leader in" + stat + ": "
                        + team.getLeader(stat).getName() + " - "
                        + team.getLeader(stat).getNumHits() + " hits, "
                        + team.getLeader(stat).getNumErrors() + " errors");
                printMenu();
                optionsList();
            }
            if (option.equals("r")) {
                System.out.println("Enter the position: ");
                int position = infoScanner.nextInt();
                Team team = new Team();

                String name = team.getPlayer(position).getName();
                team.removePlayer(position);
                System.out.println("Player Removed at position " + position);

                System.out.println(name + " has been removed from the team.");
                printMenu();
                optionsList();
            }
            if (option.equals("p")) {
                System.out.println("Select team index: ");
                int teamNumber = infoScanner.nextInt();
                Team team = new Team();
                team.printAllPlayers();
                printMenu();
                optionsList();
            }
            if (option.equals("s")) {
                int teamNumber = infoScanner.nextInt();
                setSelectedTeam(teamNumber);
                Team team = new Team();
                System.out.println("There are " + team.size() + "player(s) in the current Team");
                printMenu();
                optionsList();
            }
            if (option.equals("t")) {
                System.out.println("Enter team index to select: ");
                int teamNumber = infoScanner.nextInt();

                System.out.println(teamNumber + " has been selected.");
                printMenu();
                optionsList();
            }
            if (option.equals("c")) {
                System.out.println("Select team to clone from: ");
                int cloneFrom = infoScanner.nextInt();

                System.out.println("Select team to clone to: ");
                int cloneTo = infoScanner.nextInt();

                Team team1 = teams[cloneFrom];
                Team team2 = (Team) team1.clone(team1);

                System.out.println("Team " + cloneFrom + " has been copied to team " + cloneTo);
                printMenu();
                optionsList();
            }
            if (option.equals("e")) {
                System.out.println("Enter first team#: ");
                int team1Number = infoScanner.nextInt();

                System.out.println("Enter second team#: ");
                int team2Number = infoScanner.nextInt();

                Team team1 = teams[team1Number];
                Team team2 = teams[team2Number];

                if (team1.equals(team2)) {
                    System.out.println("These teams are equal.");
                } else {
                    System.out.println("These teams are not equal.");
                }
                printMenu();
                optionsList();
            }
            if (option.equals("u")) {
                Team team = new Team();

                System.out.println("Enter the player to update: ");
                String name = infoScanner.nextLine();
                System.out.println("Enter stat to update: ");
                String stat = infoScanner.nextLine();
                System.out.println("Enter the new number of errors: ");
                int updateValue = infoScanner.nextInt();

                team.updateStat(name, stat, updateValue);
                printMenu();
                optionsList();
            }
        }
        System.out.println("Program terminating shortly...");
    }
}