/**
 * SlideListNode class is designed to help with navigating/background of SlideList class.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class SlideListNode {
    private Slide data;
    private SlideListNode next;
    private SlideListNode prev;

    /**
     * base constructor
     * @param initData is a slide variable being used for assignment
     */
    public SlideListNode(Slide initData) {
        if (initData == null) {
            throw new IllegalArgumentException();
        }

        this.data = initData;
        next = null;
        prev = null;
    }

    /**
     * gets the data of slide
     * @return data
     */
    public Slide getData() {
        return data;
    }

    /**
     * sets the data
     * @param newData is used for setting
     */
    public void setData(Slide newData) {
        if (newData == null) {
            throw new IllegalArgumentException();
        }
        this.data = newData;
    }

    /**
     * gets the next
     * @return next
     */
    public SlideListNode getNext() {
        return next;
    }

    /**
     * sets the next
     * @param newNext is used for setting
     */
    public void setNext(SlideListNode newNext) {
        this.next = newNext;
    }

    /**
     * gets the previous
     * @return previous
     */
    public SlideListNode getPrev() {
        return prev;
    }

    /**
     * sets the previous
     * @param newPrev is used for setting
     */
    public void setPrev(SlideListNode newPrev) {
        this.prev = newPrev;
    }

    /**
     * overridden equals method
     * @param o is for what you compare
     * @return True/False depending on equality check
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlideListNode)) return false;
        SlideListNode test = (SlideListNode) o;
        return this.data.equals(test.data) && this.next == test.next && this.prev == test.prev;
    }
}