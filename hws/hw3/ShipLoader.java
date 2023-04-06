/**
 * ShipLoader class is designed to hold place of Main, as in the point of user and executor
 * The executions are displayed to the user in a menu and acted upon depending on the user's input choice.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Scanner;
public class ShipLoader {
    static CargoShip cargoShip;
    static CargoStack docker;
    static Cargo cargo;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to ShipLoader!\n" +
                "Cargo Ship Parameters\n" +
                "--------------------------------------------------");
        System.out.print("Number of stacks: ");
        int stackNum = input.nextInt();
        System.out.print("Maximum height of stacks: ");
        int maxStackHeight = input.nextInt();
        System.out.print("Maximum total cargo weight: ");
        double maxTotalCargoWeight = input.nextDouble();

        cargoShip = new CargoShip(stackNum, maxStackHeight, maxTotalCargoWeight);
        docker = new CargoStack();
        cargo = null;

        System.out.println("Cargo ship created.\n" +
                "Pulling ship in to dock...\n" +
                "Cargo ship ready to be loaded.\n");
        optionsList();
    }

    public static void optionsList() {
        Scanner input = new Scanner(System.in);
        printMenu();
        String option = input.next().toUpperCase();
        while (!option.equals("Q")) {
            switch (option) {
                case "C": {
                    System.out.print("Enter the name of the cargo: ");
                    input.nextLine();
                    String cargoName = input.nextLine();
                    System.out.print("Enter the weight of the cargo: ");
                    double cargoWeight = input.nextDouble();
                    System.out.print("Enter the container strength (F/M/S): ");
                    String cargoStrength = input.next();

                    Cargo cargo = new Cargo(cargoName, cargoWeight,
                            cargoStrength.equals("F") ? CargoStrength.FRAGILE :
                            cargoStrength.equals("M") ? CargoStrength.MODERATE :
                                                        CargoStrength.STURDY);
                    try {
                        docker.push(cargo);
                    } catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    }
                    System.out.println("Cargo " + cargo.getName() + " pushed onto the dock.");
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "L": {
                    System.out.print("Select the load destination stack index: ");
                    int stack = input.nextInt();
                    try {
                        cargo = docker.peek();
                        cargoShip.pushCargo(cargo, stack);
                        docker.pop();
                        System.out.println("Cargo '" + cargo.getName() + "' moved from dock to stack " + stack);
                    } catch (IllegalArgumentException e) {
                        System.out.print("Operation failed! Invalid argument.");
                        break;
                    } catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    } catch (FullStackException e) {
                        System.out.print("Operation failed! Cargo stack is at maximum height.");
                        break;
                    } catch (ShipOverweightException e) {
                        System.out.print("Operation failed! Cargo would put ship overweight");
                        break;
                    }
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "U": {
                    System.out.print("Select the unload destination stack index: ");
                    int stack = input.nextInt();
                    try {
                        cargo = cargoShip.getStack(stack).peek();
                        docker.push(cargo);
                        cargoShip.popCargo(stack);
                        System.out.println("Cargo " + cargo.getName() + " unloaded off the ship from stack " + stack);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Operation failed! Invalid argument.");
                        break;
                    } catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    } catch (EmptyStackException e) {
                        System.out.println("Operation failed! Stack is empty.");
                        break;
                    }
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "M": {
                    System.out.print("Source stack index: ");
                    int fromStack = input.nextInt();
                    System.out.print("Destination stack index: ");
                    int toStack = input.nextInt();
                    try {
                        cargo = cargoShip.popCargo(fromStack);
                        cargoShip.pushCargo(cargo, toStack);
                        System.out.println("Cargo '" + cargo.getName() + "' pushed onto the dock.");
                    } catch (CargoStrengthException e) {
                        System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
                        break;
                    } catch (FullStackException e) {
                        System.out.print("Operation failed! Cargo stack is at maximum height.");
                        break;
                    } catch (ShipOverweightException e) {
                        System.out.print("Operation failed! Cargo would put ship overweight");
                        break;
                    } catch (EmptyStackException e) {
                        System.out.println("Operation failed! Stack is empty.");
                        break;
                    }
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "K": {
                    while (!docker.isEmpty()) {
                        docker.pop();
                    }

                    System.out.println("Dock cleared");
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "P": {
                    printAllShip(cargoShip, docker);
                    optionsList();
                    break;
                }
                case "S": {
                    System.out.print("Enter the name of the cargo: ");
                    input.nextLine();
                    String searchName = input.nextLine();
                    cargoShip.findAndPrint(searchName);
                    optionsList();
                    break;
                }
                default:
                    System.out.println("Please choose a valid option from the list below.");
                    optionsList();
            }
        }
        System.out.println("Program terminating normally...");
        System.exit(0);
    }

    public static void printMenu(){
        String menu =
                        "C) Create new cargo\n" +
                        "L) Load cargo from dock\n" +
                        "U) Unload cargo from ship\n" +
                        "M) Move cargo between stacks\n" +
                        "K) Clear dock\n" +
                        "P) Print ship stacks\n" +
                        "S) Search for cargo\n" +
                        "Q) Quit\n"+
                        "Select a menu option: ";
        System.out.println(menu);
    }
    public static void printAllShip(CargoShip ship, CargoStack docker) {
        CargoShip shipCloned = ship.clone();
        CargoStack dockerCloned = docker.clone();
        Cargo[][] Ship = new Cargo[ship.getMaxStackSize()][ship.getNumStacks()];

        for (int i = 1; i <= ship.getNumStacks(); i++) {
            for (int j = ship.getStack(i).size() - 1; j >= 0; j--) {
                try {
                    Ship[j][i-1] = shipCloned.popCargo(i);
                } catch (EmptyStackException e) {
                    break;
                }
            }
        }

        StringBuilder s = new StringBuilder();
        for (int i = 1; i <= ship.getNumStacks(); i++){
            s.append("Stack " + i + ": ");
            for (int j = 0; j <= ship.getStack(i).size() - 1; j++) {
                s.append(Ship[j][i-1].toString() + " ");
            }
            s.append("\n");
        }

        StringBuilder trace = new StringBuilder();
        s.append("Dock: ");
        while (dockerCloned.peek() != null) {
            trace.append(dockerCloned.peek().toString() + " ");
            dockerCloned.pop();
        }
        StringBuilder reverseSB = new StringBuilder(trace.toString());
        String reversedTrace = reverseSB.reverse().toString();
        s.append(reversedTrace);

        s.append("\nTotal Weight = " + ship.getTotalStacksWeight() + "/" + ship.getMaxWeight());
        String printingAll = String.valueOf(s);
        System.out.println(printingAll);
    }
}