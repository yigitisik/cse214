/**
 * Zork class is designed to hold place of Main, as in the point of user and executor
 * The executions are displayed to the user in a menu and acted upon depending on the user's input choice.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw5
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Scanner;
import java.util.zip.DataFormatException;
public class Zork {
    /**
     * main class that initiates editing, playing or quitting
     * @param args for syntax
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        StoryTree storyTree;
        System.out.println("\nHello and Welcome to Zork! "+
                "\n\nPlease enter the file name: ");

        String fileName = scan.nextLine();
        try {
            storyTree = StoryTree.readTree(fileName);
            System.out.println("Loading game from file..."+
                    "\n\nFile loaded!");
        } catch (DataFormatException dfe) {
            storyTree = new StoryTree("tempOption", "tempMessage");
            System.out.println("\nFile not read properly."+
                    "\n\nEmpty tree created.");
        }
        boolean resume = true;
        while(resume){
            System.out.println("\nWould you like to edit (E), play (P) or quit (Q)? ");
            String decision = scan.nextLine().toLowerCase();
            if(decision.equals("e")){
                editTree(storyTree);
            } else if (decision.equals("p")) {
                playTree(storyTree);
                storyTree.setGameState(GameState.GAME_NOT_OVER);
            } else if (decision.equals("q")) {
                StoryTree.saveTree(fileName, storyTree);
                System.out.printf("Game being saved to %s...%n%nSave successful!%n%nProgram terminating normally.%n", fileName);
                resume = false;
            } else{
                System.out.println("Try input again:");
            }
        }
    }

    /**
     * plays tree
     * @param tree for which tree to play
     */
    public static void playTree(StoryTree tree){
        Scanner scan = new Scanner(System.in);
        System.out.println(tree.getCursorOption());
        while(tree.getGameState().equals(GameState.GAME_NOT_OVER)){
            System.out.println(tree.getCursorMessage());
            int j = 0;
            while(tree.getOptions()[0][j] != null && tree.getOptions()[1][j] != null && j<=2){
                System.out.println(tree.getOptions()[0][j] + ". " + tree.getOptions()[1][j]);
                if (j == 2) {
                    break;
                }
                j++;
            }
            System.out.print("Please make a choice. ");
            String decision = scan.nextLine();
            if(decision.equals("c") || decision.equals("C")){
                System.out.println("Probability of a win at this point: " + tree.winProbability());
            }else {
                try {
                    tree.selectChild(decision);
                } catch (NodeNotPresentException ndne) {
                    System.out.println("No node is not present at: " + decision);
                }
            }
            System.out.println();
        }
        System.out.println(tree.getCursorMessage() + "\n" +
                           tree.getGameState()     + "\n" +
                           "Thanks for playing.\n");
    }

    /**
     * edits tree
     * @param tree for which tree to edit
     */
    public static void editTree(StoryTree tree){
        Scanner scan = new Scanner(System.in);
        boolean doesQuit = false;
        String childNum;
        while (doesQuit == false) {
            System.out.println(" Zork Editor: \n" +
                    "\tV: View the cursor's position, option and message.\n" +
                    "\tS: Select a child of this cursor (options are 1, 2, and 3).\n" +
                    "\tO: Set the option of the cursor.\n" +
                    "\tM: Set the message of the cursor.\n" +
                    "\tA: Add a child StoryNode to the cursor.\n" +
                    "\tD: Delete one of the cursor's children and all its descendants.\n" +
                    "\tR: Move the cursor to the root of the tree.\n" +
                    "\tP: Return to cursor's parent\n" +
                    "\tQ: Quit editing and return to main menu.\n" +
                    "\tPlease select an option: \n");
            String decision = scan.nextLine().toLowerCase();

            switch (decision) {
                case "v":
                    System.out.println("Position: " + tree.cursor.getPosition()+
                            "\nOption: " + tree.cursor.getOption()+
                            "\nMessage: " + tree.cursor.getMessage());
                    break;
                case "s":
                    System.out.println("Please select a child: ");
                    childNum = scan.nextLine();
                    try {
                        tree.selectChild(childNum);
                        System.out.println("Child " + childNum + " is selected.");
                    } catch (NodeNotPresentException nnpe) {
                        System.out.println("No child to select at: " + childNum);
                    }
                    break;
                case "o":
                    System.out.println("Please enter a new option: ");
                    tree.setCursorOption(scan.nextLine());
                    System.out.println("\nOption set.");
                    break;
                case "m":
                    System.out.println("Please enter a new Message: ");
                    tree.setCursorMessage(scan.nextLine());
                    System.out.println("\nMessage set.");
                    break;
                case "a":
                    System.out.println("Enter an option: ");
                    String option = scan.nextLine();
                    System.out.println("Enter a message: ");
                    String message = scan.nextLine();
                    try {
                        tree.addChild(option, message);
                        System.out.println("Added child.");
                    } catch (TreeFullException e) {
                        System.out.println("3 children exist, currently can't add.");
                    }
                    break;
                case "d":
                    System.out.println("Please select a child: ");
                    childNum = scan.nextLine();
                    try {
                        tree.removeChild(String.valueOf(childNum));
                        System.out.println("Removed child at: " + childNum);
                    } catch (NodeNotPresentException e) {
                        System.out.println("No node to remove at: " + childNum);
                    }
                    break;
                case "r":
                    tree.resetCursor();
                    System.out.println("Cursor moved to root.");
                    break;
                case "p":
                    tree.returnToParent();
                    System.out.println("Returned to cursor's parent");
                    break;
                case "q":
                    doesQuit = true;
                    break;
                default:
                    System.out.println("Invalid argument. Try input again.");
                    break;
            }
        }
    }
}
