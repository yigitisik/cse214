/**
 * CargoStack class is designed to hold place of a single stack consisting of cargos
 * Using ArrayList for implementation helps out with methods that are being used such as:
 * pushing cargo, popping cargo, or peeking
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.ArrayList;
public class CargoStack {
    private ArrayList<Cargo> singlestack = new ArrayList<>();
    private double weight = 0;
    /**
     * pushes a single cargo to on top of the stack
     * increments the weight accordingly
     * @param cargo is taken in for pushing
     * @throws CargoStrengthException when Cargo strength rules violated.
     */
    public void push(Cargo cargo) throws CargoStrengthException {
        if (peek() != null &&
            peek().getStrength().compareTo(cargo.getStrength()) < 0) {
            throw new CargoStrengthException("Cargo strength rules violated.");
        }
        weight += cargo.getWeight();
        singlestack.add(cargo);
    }

    /**
     * poppes a single cargo from on top of the stack
     * decrements the weight accordingly
     * @return the popped cargo
     */
    public Cargo pop() {
        Cargo cargo = singlestack.remove(size() - 1);
        weight -= cargo.getWeight();
        return cargo;
    }

    /**
     * peeks a single cargo from the top of stack
     * @return the peeked cargo
     */
    public Cargo peek() {
        return (singlestack.isEmpty()) ?
                null : //if empty true
                singlestack.get(size() - 1); // if empty false
    }

    /**
     * @return whether the stack is empty or not
     */
    public boolean isEmpty() {
        return this.singlestack.isEmpty();
    }

    /**
     * @return size of stack
     */
    public int size() {
        return this.singlestack.size();
    }

    /**
     * @return weight of stack
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @return clone of stack
     */
    public CargoStack clone() {
        Cargo temp;
        CargoStack clonedCargoStack = new CargoStack();
        Cargo[] cloneCargo = new Cargo[this.size()];
        for (int i = 0; i <= cloneCargo.length-1; i++) {
            cloneCargo[i] = pop();
        }
        for (int j = cloneCargo.length-1; j >= 0; j--) {
            temp = cloneCargo[j];
            if (temp == null) {
                break;
            } else {
                try {
                    push(temp);
                    clonedCargoStack.push(temp.clone());
                } catch (CargoStrengthException e) {
                    System.out.print("Cargo strength rules violated.");
                    break;
                }
            }
        }
        return clonedCargoStack;
    }
}