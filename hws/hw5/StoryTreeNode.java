/**
 * StoryTreeNode class is designed to hold place of a single node of a tree and its
 * attributes such as position, option, and message.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw5
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class StoryTreeNode {
    public static String WIN_MESSAGE = "YOU WIN";
    public static String LOSE_MESSAGE = "YOU LOSE";
    public String position;
    public String option;
    public String message;
    StoryTreeNode leftChild;
    StoryTreeNode middleChild;
    StoryTreeNode rightChild;

    /**
     * constructor for a node, doesn't take position param since it's calculated with a different approach
     * @param option for initializing node's option
     * @param message for initializing node's message
     */
    public StoryTreeNode(String option, String message) {
        this.option = option;
        this.message = message;
    }

    //GETTERS-SETTERS

    /**
     * @return node position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position to adjust position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    /**
     * @return node option
     */
    public String getOption() {
        return option;
    }
    /**
     * @param option to adjust option
     */
    public void setOption(String option) {
        this.option = option;
    }
    /**
     * @return node message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message to adjust message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return left child of node
     */
    public StoryTreeNode getLeftChild() {
        return leftChild;
    }

    /**
     * @param leftChild to adjust left child of node
     */
    public void setLeftChild(StoryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * @return middle child of node
     */
    public StoryTreeNode getMiddleChild() {
        return middleChild;
    }

    /**
     * @param middleChild to adjust middle child of node
     */
    public void setMiddleChild(StoryTreeNode middleChild) {
        this.middleChild = middleChild;
    }

    /**
     * @return right child of node
     */
    public StoryTreeNode getRightChild() {
        return rightChild;
    }

    /**
     * @param rightChild to adjust right child of node
     */
    public void setRightChild(StoryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * checks if node is a leaf (no children)
     * @return true or false based on condition
     */
    public boolean isLeaf(){
        return this.getLeftChild() == null && this.getMiddleChild() == null && this.getRightChild() == null;
    }

    /**
     * checks if node is a winning node
     * @return true or false based on condition
     */
    public boolean isWinningNode(){
        return this.isLeaf() && this.getMessage().contains(WIN_MESSAGE);
    }

    /**
     * checks if node is a losing node
     * @return true or false based on condition
     */
    public boolean isLosingNode(){
        return this.isLeaf() && this.getMessage().contains(LOSE_MESSAGE);
    }
}
