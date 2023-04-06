/**
 * PresentationManager class is designed to hold place of Main, as in the point of user and executor
 * The executions are displayed to the user in a menu and acted upon depending on the user's input choice.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Scanner;
public class PresentationManager {
    public static SlideList list = new SlideList();
    public static void main(String[] args) throws EndOfListException {
        optionsList();
    }
    public static void printMenu() {
        String menu = "F) Move cursor forward\n" +
                      "B) Move cursor backward\n" +
                      "D) Display cursor slide\n" +
                      "E) Edit cursor slide\n" +
                      "P) Print presentation summary\n" +
                      "A) Append new slide to tail\n" +
                      "I) Insert new slide before cursor\n" +
                      "R) Remove slide at cursor\n" +
                      "H) Reset cursor to head\n" +
                      "Q) Quit\n";
        System.out.println(menu);
    }
    public static void optionsList() throws EndOfListException {
        printMenu();
        System.out.println("Select a menu option: ");
        Scanner input = new Scanner(System.in);
        String option = input.next().toUpperCase();

        switch (option) {
            case "A": {
                list.appendToTail(addSlide());
            }
            case "F": {
                list.cursorForward();
                System.out.println("The cursor moved forward to slide \"" + list.getCursorSlide().getTitle() + "\"." );
                optionsList();
            }
            case "B": {
                list.cursorBackward();
                System.out.println("The cursor moved backward to slide \"" + list.getCursorSlide().getTitle() + "\"." );
                optionsList();
            }
            case "D": {
                if(list.isEmpty()){
                    System.out.println("Empty slideshow");
                }else{
                Slide slide = list.getCursorSlide();
                System.out.println(slide.toString());
                optionsList();
                }
            }
            case "E": {
                System.out.println("Edit title, duration, or bullets? (t/d/b)");
                String pick = input.nextLine();
                String pick1 = pick.toUpperCase();
                switch (pick1) {
                    case "T": {
                        System.out.println("Enter the slide title: ");
                        input.nextLine();
                        list.getCursorSlide().setTitle(input.nextLine());
                    }
                    case "D": {
                        System.out.println("Enter the new Duration: ");
                        list.getCursorSlide().setDuration(input.nextDouble());
                    }
                    case "B": {
                        System.out.println("Bullet index: ");
                        int bulletPick = input.nextInt();

                        if (bulletPick > 0 && bulletPick < 6) {
                            System.out.println("Delete or edit? (d/e)");
                            pick = input.nextLine();
                            String pick2 = pick.toUpperCase();
                            if (pick2.equals("e")) {
                                String newBullet = input.nextLine();
                                list.getCursorSlide().setBullet(bulletPick, newBullet);
                            } else if (pick2.equals("d")) {
                                list.getCursorSlide().setBullet(bulletPick, null);
                            }else{
                                System.out.println("Invalid Option");
                            }
                        } else {
                            System.out.println("Invalid Index");
                        }
                    }
                }
            }
            case "P": {
                System.out.println(list.toString());
                optionsList();
            }
            case "I": {
                Slide slide = new Slide();
                System.out.println("Slide title: ");
                input.nextLine();
                slide.setTitle(input.nextLine());
                System.out.println("Enter the duration");
                slide.setDuration(input.nextDouble());
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Bullet " + i);
                    input.nextLine();
                    String bul = input.nextLine();
                    slide.setBullet(i, bul);
                    if (i == 5) {
                        System.out.println("Slide added before cursor");
                        System.out.println(slide);
                        list.insertBeforeCursor(slide);
                        optionsList();
                        break;
                    }
                    System.out.println("Add another bullet? (y/n)");
                    String yesNoPick = input.next().toUpperCase();
                    switch (yesNoPick) {
                        case "Y": {
                            continue;
                        }
                        case "N": {
                            System.out.println(slide);
                            list.insertBeforeCursor(slide);
                            optionsList();
                        }
                        default: {
                            System.out.println("Choose between y/n");
                            yesNoPick = input.next().toUpperCase();
                        }
                    }
                }
                optionsList();
            }
            case "R": {
                System.out.println("You've selected to remove the slide at the cursor.\n" +
                        "Are you sure you want to remove the following slide?(y/n)\n"
                        + list.getCursorSlide().toString());
                String yesNoPick = input.next().toUpperCase();
                switch (yesNoPick) {
                    case "Y":
                        list.removeCursor();
                        System.out.println("The slide" + list.getCursorSlide().getTitle() + "has been removed. The cursor now references the following slide:\n" +
                                list.getCursorSlide().toString());
                        optionsList();
                    case "N":
                        System.out.println("The cursor has not been removed.");
                        optionsList();
                }
            }
            case "H": {
                if(list.isEmpty()){
                    System.out.println("Empty slideshow");
                }else{
                list.resetCursorToHead();
                System.out.println("The cursor has been reset to head");
                System.out.println("Cursor slide: \n" + list.getCursorSlide().toString());
                optionsList();
                }
            }
            case "Q": {
                System.out.println("Program terminating normally...");
                System.exit(0);
            }
        }
    }

    public static Slide addSlide() throws EndOfListException {
        Scanner input = new Scanner(System.in);
        Slide slide = new Slide();

        System.out.println("Enter the slide title: ");
        slide.setTitle(input.nextLine());
        System.out.println("Enter the slide duration: ");
        slide.setDuration(input.nextDouble());
        for (int i = 1; i <= 5; i++) {
            System.out.println("Bullet " + i);
            input.nextLine();
            String bul = input.nextLine();
            slide.setBullet(i, bul);
            if (i == 5) {
                System.out.println("No more bullets allowed. Slide is full.");
                System.out.println(slide);
                list.appendToTail(slide);
                break;
            }
            System.out.println("Add another bullet point? (y/n)");
            String yesNoPick = input.next().toUpperCase();
            switch (yesNoPick) {
                case "Y": {
                    continue;
                }
                case "N": {
                    System.out.println(slide);
                    list.appendToTail(slide);
                    optionsList();
                }
                default: {
                    System.out.println("Invalid option");
                    yesNoPick = input.next().toUpperCase();
                }
            }
        }
        return slide;
    }
}