/**
 * StoryTree class is designed to hold place of a tree (consisting of nodes) and its
 * attributes such as root, cursor, and game state.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw5
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.zip.DataFormatException;
import java.io.*;
import java.util.Scanner;
public class StoryTree {
    StoryTreeNode root;
    StoryTreeNode cursor;
    GameState state;

    /**
     * helper method to establish the root in constructors
     */
    public void rootEstablish(){
        this.root = new StoryTreeNode("root", "Hello, Welcome to Zork!");
        this.root.setPosition("root");
    }

    /**
     * constructs a tree with the first node set to left child after the root
     * @param option for root's left child's option
     * @param message for root's left child's message
     */
    public StoryTree(String option, String message){
        rootEstablish();

        StoryTreeNode firstNode = new StoryTreeNode(option, message);
        this.root.setLeftChild(firstNode);
        firstNode.setPosition("1");
        resetCursor();
        this.state = GameState.GAME_NOT_OVER;
    }

    /**
     * constructs an empty tree with just root
     */
    public StoryTree() {
        rootEstablish();

        this.cursor = this.root;
        this.state = GameState.GAME_NOT_OVER;
    }

    /**
     * reads a file to conceptualize it as a tree
     * @param filename for the file to read from
     * @return the final tree after reading and structuring
     * @throws IllegalArgumentException when invalid argument
     * @throws DataFormatException when data format wrong
     */
    public static StoryTree readTree(String filename) throws IllegalArgumentException, DataFormatException {
        if (filename == null || filename.equals("")) {
            throw new IllegalArgumentException("Invalid argument.");
        }

        StoryTree storyTree = null;
        try {
            storyTree = new StoryTree();
            String[] position;
            String[] tempLineArray;

            /*
             * PARAMETER DEPENDING ON CUSTOM FILE OR PATH NAME, for me it was this:
             * /Users/mustafayigitisik/Desktop/stuff/comp work/cs/hw5/SampleStory.txt
             *
             * I used the following filepath /Users/mustafayigitisik/Desktop/stuff/comp work/cs/hw5/
             * which excludes SampleStory.txt because that's the part that I put in while running main
             * SO THEY CONCATENATE AND RUN BASED OFF OF NEW FULL PATHNAME
             */
            File stFile = new File("/Users/mustafayigitisik/Desktop/stuff/comp work/cs/hw5/", filename);
            Scanner stFileImport = new Scanner(stFile);

            while (stFileImport.hasNextLine()) {
                String tempLine = stFileImport.nextLine();
                tempLineArray = tempLine.split(" \\| ");
                position = tempLineArray[0].split("-");

                if (tempLineArray.length != 3) {
                    throw new DataFormatException();
                }

                else if (storyTree.root == storyTree.cursor) {
                    StoryTreeNode firstNode = new StoryTreeNode(tempLineArray[1], tempLineArray[2]);
                    storyTree.root.setLeftChild(firstNode);
                    firstNode.setPosition("1");
                    storyTree.resetCursor();
                }

                int i = 1;
                while (i <= position.length - 1) {
                    try{
                        storyTree.selectChild(position[i]);
                    }catch(NodeNotPresentException e) {
                        try{
                            storyTree.addChild(tempLineArray[1], tempLineArray[2]);
                        }catch(TreeFullException tfe) {
                            System.out.println("Tree is full for given location.");
                        }
                    }
                    i++;
                }
                storyTree.resetCursor();
            }
            stFileImport.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found.");
        }
        return storyTree;
    }

    /**
     * saves tree to a file
     * @param filename where it will be saved
     * @param tree for indicating the tree that will be saved
     */
    public static void saveTree(String filename, StoryTree tree){
        if (filename == null || filename.equals("") || tree == null) {
            throw new IllegalArgumentException("Invalid argument.");
        }
        try{
            tree.resetCursor();
            File storyTreeFile = new File(filename);

            PrintWriter storyTreeFileOut = new PrintWriter(storyTreeFile);
            tree.treePrintHelper(tree.cursor, storyTreeFileOut);
            storyTreeFileOut.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found.");
        }
    }

    /**
     * @return game state
     */
    public GameState getGameState(){
        return this.state;
    }

    /**
     * @return cursor option
     */
    public String getCursorOption() { return
            cursor.getOption();
    }

    /**
     * @return cursor position
     */
    public String getCursorPosition() {
        return cursor.getPosition();
    }

    /**
     * @return cursor message
     */
    public String getCursorMessage() {
        return cursor.getMessage();
    }

    /**
     * @return 2d array of position and option for the current cursor
     * structure visualized for clarity:
     * [LEFT POS] [MIDDLE POS] [RIGHT POS]
     * [LEFT OPT] [MIDDLE OPT] [RIGHT OPT]
     */
    public String[][] getOptions() {
        StoryTreeNode[] child = new StoryTreeNode[]
                {cursor.getLeftChild(), cursor.getMiddleChild(), cursor.getRightChild()};
        String[][] optionsPositionArray = new String[2][3];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j <= 2; j++) {
                if (child[j] == null) {
                    break;
                }
                optionsPositionArray[i][j] = child[j].getPosition();
                optionsPositionArray[i + 1][j] = child[j].getOption();
            }
        }
        return optionsPositionArray;
    }

    /**
     * @param message to adjust cursor's message
     */
    public void setCursorMessage(String message) {
        cursor.setMessage(message);
    }

    /**
     * @param option to adjust cursor's option
     */
    public void setCursorOption(String option) {
        cursor.setOption(option);
    }

    /**
     * resets cursor
     */
    public void resetCursor() {
        cursor = root.getLeftChild();
    }

    /**
     * selects a child at a given position
     * @param position to indicate where to select from
     * @throws NodeNotPresentException when node not present
     */
    public void selectChild(String position)throws NodeNotPresentException{
        if(position == null|| position.equals("")){
            throw new IllegalArgumentException("Invalid argument.");
        }
        if(position.equals("1") && cursor.getLeftChild() != null){
            cursor = cursor.getLeftChild();
        }
        else if(position.equals("2") && cursor.getMiddleChild() != null){
            cursor = cursor.getMiddleChild();
        }
        else if(position.equals("3") && cursor.getRightChild() != null){
            cursor = cursor.getRightChild();
        }
        else{
            throw new NodeNotPresentException("Node is not present at given position.");
        }
    }

    /**
     * adds a child below of cursor (if possible)
     * @param option for new child's option
     * @param message for new child's message
     * @throws TreeFullException when ternary subtree (full for adding)
     */
    public void addChild(String option, String message) throws TreeFullException {
        if(option == null || option.equals("") || message == null || message.equals("")){
            throw new IllegalArgumentException("Invalid argument.");
        }

        StoryTreeNode childAdd = new StoryTreeNode(option, message);
        if(cursor.getLeftChild() == null){
            cursor.setLeftChild(childAdd);
            childAdd.setPosition(getCursorPosition() + "-1");
        }
        else if(cursor.getMiddleChild() == null){
            cursor.setMiddleChild(childAdd);
            childAdd.setPosition(getCursorPosition() + "-2");
        }
        else if(cursor.getRightChild() == null){
            cursor.setRightChild(childAdd);
            childAdd.setPosition(getCursorPosition() + "-3");
        }
        else{
            throw new TreeFullException("Tree is full for given location.");
        }
    }

    /**
     * removes a child of cursor
     * @param position for position to remove
     * @return removed child
     * @throws NodeNotPresentException when node not present
     */
    public StoryTreeNode removeChild(String position) throws NodeNotPresentException{
        if(position == null || position.equals("")){
            throw new IllegalArgumentException("Invalid argument.");
        }

        StoryTreeNode childRemove = null;
        if(position.equals("1")){
                childRemove = cursor.getLeftChild();
                cursor.setLeftChild(cursor.getMiddleChild());
                cursor.setMiddleChild(cursor.getRightChild());
                cursor.setRightChild(null);
        }
        if(position.equals("2")){
                childRemove = cursor.getMiddleChild();
                cursor.setMiddleChild(cursor.getRightChild());
                cursor.setRightChild(null);
        }
        if(position.equals("3")){
                childRemove = cursor.getRightChild();
                cursor.setRightChild(null);
        }
        // CALLING EXTRA METHOD TO ARRANGE POSITIONS AFTER REMOVAL DONE
        childrenPositionArrange(cursor, getCursorPosition());
        return childRemove;
    }

    //ADDITIONAL METHODS

    /**
     * helper method for winProbability
     * @param node for which node
     * @return int of winning nodes
     */
    public int countWinNodes(StoryTreeNode node){
        int winNodes = 0;
        if(node == null){return 0;}
        else if(node.isWinningNode()){
            winNodes++;
            return winNodes;
        }else{
            winNodes += countWinNodes(node.getLeftChild()) + countWinNodes(node.getMiddleChild()) + countWinNodes(node.getRightChild());
            return winNodes;
        }
    }

    /**
     * helper method 2 for winProbability
     * @param node for which node
     * @return int of all leaves
     */
    public int countAllLeaf(StoryTreeNode node){
        int leafNodes = 0;
        if(node == null){return 0;}
        else if(node.isLeaf()){
            leafNodes++;
            return leafNodes;
        }else{
            leafNodes += countAllLeaf(node.getLeftChild()) + countAllLeaf(node.getMiddleChild()) + countAllLeaf(node.getRightChild());
            return leafNodes;
        }
    }

    /**
     * extra credit method where it:
     * @return the winning probability at a given condition node
     */
    public double winProbability(){
        return (double) ( countWinNodes(this.cursor) / countAllLeaf(this.cursor) ) * 100;
    }

    /**
     * recursively arrange positions of node's subtree
     * @param node for which node's subtree
     * @param position for new position of this node
     */
    public void childrenPositionArrange(StoryTreeNode node, String position){
        if(node == null || position == null || position.equals("")){
            return;
        }
        node.setPosition(position);
        childrenPositionArrange(node.getLeftChild(), node.getPosition() + "-1");
        childrenPositionArrange(node.getMiddleChild(), node.getPosition() + "-2");
        childrenPositionArrange(node.getRightChild(), node.getPosition() + "-3");
    }

    /**
     * @param state is what is set as current state
     */
    public void setGameState(GameState state) {
        this.state = state;
    }

    /**
     * helps to saveTree method for printing nodes line by line into the print writer
     * @param node for which node
     * @param pw for which print writer to add onto
     */
    public void treePrintHelper(StoryTreeNode node, PrintWriter pw){
        if(node == null) {return;}
        pw.println(node.getPosition() + " | " + node.getOption() + " | " + node.getMessage() + "\n");

        treePrintHelper(node.getLeftChild(), pw);
        treePrintHelper(node.getMiddleChild(), pw);
        treePrintHelper(node.getRightChild(), pw);
    }

    /**
     * extra credit method where the cursor goes back to its parent
     */
    public void returnToParent(){
        String cursorPosition = getCursorPosition();
        this.cursor = this.root;
        String[] cursorPositionArray = cursorPosition.split("-");
        int i = 0;
        while(i <= cursorPositionArray.length-2){
            try {
                selectChild(cursorPositionArray[i]);
            } catch (NodeNotPresentException e) {
                System.out.println("Node not present.");
            }
            i++;
        }
    }
}
