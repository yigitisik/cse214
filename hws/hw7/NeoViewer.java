/**
 * NeoViewer class represents a managing point of the database and executes all desired choices
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw7
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Scanner;
public class NeoViewer {
    /**
     * DOES EXECUTIONS OF:
     * ADDING, SORTING, PRINTING, QUIT
     * @param args for all possible args
     */
    public static void main(String[] args) {
        boolean isQuit = false;
        Scanner scan = new Scanner(System.in);
        NeoDatabase neoDatabase = new NeoDatabase();
        System.out.println("Welcome to NEO Viewer!");
        while(!isQuit){
            String menu =   "    Option Menu:\n" +
                            "    A) Add a page to the database\n" +
                            "    S) Sort the database \n" +
                            "    P) Print the database as a table.\n" +
                            "    Q) Quit\n";
            System.out.println(menu);
            System.out.println("Select a menu option: ");
            String opt = scan.next().toLowerCase();
            scan.nextLine();
            switch (opt) {
                case "a":
                    int page;
                    try {
                        System.out.println("Enter a page to load:");
                        page = scan.nextInt();
                        scan.nextLine();
                        if (page < 0 || page > 715)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid argument, try again.");
                        break;
                    }
                    neoDatabase.addAll(neoDatabase.buildQueryURL(page));
                    System.out.println("Page loaded successfully!");
                    break;
                case "s":
                    System.out.println(
                                    "  R) Sort by referenceID\n" +
                                    "  D) Sort by diameter\n" +
                                    "  A) Sort by approach date\n" +
                                    "  M) Sort by miss distance\n\n");
                    System.out.println("Select a menu option: ");
                    String sortOpt = scan.next().toLowerCase();
                    switch (sortOpt) {
                        case "r":
                            neoDatabase.sorting(new ReferenceIDComparator());
                            System.out.println("  Table sorted on reference id.\n");
                            break;
                        case "d":
                            neoDatabase.sorting(new DiameterComparator());
                            System.out.println("  Table sorted on diameter.\n");
                            break;
                        case "a":
                            neoDatabase.sorting(new ApproachDateComparator());
                            System.out.println("  Table sorted on approach date.\n");
                            break;
                        case "m":
                            neoDatabase.sorting(new MissDistanceComparator());
                            System.out.println("  Table sorted on miss distance.\n");
                            break;
                        default:
                            System.out.println("Invalid argument, try again.");
                            break;
                    }
                    break;
                case "p":
                    neoDatabase.printTable();
                    break;
                case "q":
                    isQuit = true;
                    System.out.println("Program terminating normally...");
                    break;
                default:
                    System.out.println("Invalid argument, try again.");
                    break;
            }
        }
    }
}
