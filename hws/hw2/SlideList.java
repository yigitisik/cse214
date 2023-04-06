/**
 * SlideList class is designed to help with navigating through slides.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class SlideList {
    private SlideListNode head;
    private SlideListNode tail;
    private SlideListNode cursor;
    private int size = 0;
    public SlideList() {
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }
    /**
     * @return the size in O(1) complexity
     */
    public int size() {
        return this.size;
    }

    /**
     * @return duration of slide
     */
    public double duration(){
        double duration = 0;
        SlideListNode durationPointer = head;

        while (durationPointer != null) {
            duration += durationPointer.getData().getDuration();
            durationPointer = durationPointer.getNext();
        }
        return duration;
    }

    /**
     * @return the number of bullets
     */
    public int numBullets() {
        int bulletCount = 0;
        SlideListNode bulletPointer = head;

        while (bulletPointer != null) {
            bulletCount += bulletPointer.getData().getNumBullets();
            bulletPointer = bulletPointer.getNext();
        }
        return bulletCount;
    }

    /**
     * @return cursor slide
     */
    public Slide getCursorSlide() {
        return (this.cursor == null) ? null : cursor.getData();
    }

    /**
     * resets cursor to head of list
     */
    public void resetCursorToHead() {
        cursor = (head == null) ? null : head;
    }

    /**
     * advances cursor by one node
     * @throws EndOfListException when cursor is already at tail
     */
    public void cursorForward() throws EndOfListException {
        if (cursor != tail) {
            cursor = cursor.getNext();
        } else {
            throw new EndOfListException("cursor == null");
        }
    }

    /**
     * takes back cursor by one node
     * @throws EndOfListException when cursor is already at head
     */
    public void cursorBackward() throws EndOfListException {
        if (cursor != head) {
            cursor = cursor.getPrev();
        } else {
            throw new EndOfListException("cursor == null");
        }
    }

    /**
     * inserts a new slide before cursor slide
     * @param newSlide is new slide we insert
     * size is dynamic and gets incremented by one
     */
    public void insertBeforeCursor(Slide newSlide) {
        if (newSlide == null) {
            throw new IllegalArgumentException();
        }
        if (cursor == null) {
            SlideListNode insertSlideEmptyList = new SlideListNode(newSlide);
            head = insertSlideEmptyList;
            tail = insertSlideEmptyList;
            cursor = insertSlideEmptyList;
            return;
        } else {
            SlideListNode insertSlideGeneral = new SlideListNode(newSlide);
            insertSlideGeneral.setNext(cursor);
            insertSlideGeneral.setPrev(cursor.getPrev());
            cursor.getPrev().setNext(insertSlideGeneral);
            cursor.setPrev(insertSlideGeneral);

            cursor = insertSlideGeneral;
        }
        size++;
    }

    /**
     * appends a new slide to tail
     * @param newSlide is the new slide we append
     * size is dynamic and gets incremented by one
     */
    public void appendToTail(Slide newSlide) throws IllegalArgumentException{
        if (newSlide == null) {
            throw new IllegalArgumentException("slide can't be null");
        }
        SlideListNode appendedSlide = new SlideListNode(newSlide);
        if (tail == null) {
            head = appendedSlide;
            tail = appendedSlide;
            cursor = appendedSlide;
        } else {
            tail.setNext(appendedSlide);
            appendedSlide.setPrev(tail);
            tail = appendedSlide;
        }
        size++;
    }

    /**
     * removes the slide at the cursor
     * uses nodeRemove as instance to work on
     *
     * first "if" after exception acts in case cursor at head
     * "else" acts in other possible scenarios
     *
     * @return the removed slide that was referenced by the cursor
     * @throws EndOfListException when cursor at null
     */
    public Slide removeCursor() throws EndOfListException {
        if (cursor == null) {throw new EndOfListException("cursor == null");}

        SlideListNode nodeRemove = cursor;
        Slide removed = nodeRemove.getData();

        if (cursor == head && head.getNext() != null) {
            head = cursor.getNext();
            head.setPrev(null);
        }else{
            nodeRemove.getPrev().setNext(nodeRemove.getNext());
            if (nodeRemove.getNext() != null) {
                nodeRemove.getNext().setPrev(nodeRemove.getPrev());
            } else {
                tail = nodeRemove.getPrev();
            }
        }
        cursor = nodeRemove.getPrev();
        size--;
        return removed;
    }

    /**
     * @return whether head slide is null or not.
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * overridden toString method
     * @return info of slideshow in a formatted form
     */
    @Override
    public String toString() {
        SlideListNode nodePtr = head;
        String s = "Slideshow Summary:\n" +
                   "==============================================\n" +
                   "Slide    Title         Duration   Bullets\n" +
                   "----------------------------------------------\n";
        for (int i = 1; i < 6; i++) {
            if (nodePtr != null) {
                s += String.format("%-9d%-14s%2f%4d", i, nodePtr.getData().getTitle(),
                         nodePtr.getData().getDuration(), nodePtr.getData().getNumBullets());
                s += "\n";
            }
            nodePtr = (nodePtr != null) ? nodePtr.getNext() : null;
        }
        s+= "\n==============================================\n" +
            "Total: " + size() + " slides, " + duration() + " minute(s), " + numBullets() + " bullet(s)" +
            "\n==============================================\n";
        return s;
    }
}