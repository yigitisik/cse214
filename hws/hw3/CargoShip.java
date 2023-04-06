/**
 * CargoShip class is designed to hold place of a ship container that includes bunch of cargo stacks
 * It allows the methods from CargoStack to be implemented while also
 * plugging in the exception cases where we need to think about sitaution like:
 * cargo strength rules, ship weight limit, or a single stack reaching its limit
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw3
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class CargoShip{
    private CargoStack[] stacks;
    private int maxHeight;
    private double maxWeight;

    /**
     * constructor for cargo ship
     * @param numStacks for number of stacks
     * @param initMaxHeight for max height limit
     * @param initMaxWeight for max weight limit
     */
    public CargoShip(int numStacks, int initMaxHeight, double initMaxWeight){
        if(numStacks <= 0 || initMaxHeight <= 0 || initMaxWeight <= 0){
            throw new IllegalArgumentException("Invalid argument.");
        }else{
            this.stacks = new CargoStack[numStacks];
            this.maxHeight = initMaxHeight;
            this.maxWeight = initMaxWeight;
            for (int i = 0; i <= numStacks-1; i++) {
            stacks[i] = new CargoStack();
            }
        }
    }

    /**
     * pushes cargo to a given stack
     * @param cargo is the cargo being pushed
     * @param stack is the stack it's being pushed to
     * @throws IllegalArgumentException when invalid argument
     * @throws FullStackException when stack is full
     * @throws ShipOverweightException when weight limit is exceeded
     * @throws CargoStrengthException when cargo strength rules violated
     */
    public void pushCargo(Cargo cargo, int stack) throws
            IllegalArgumentException, FullStackException, ShipOverweightException, CargoStrengthException{
        if(cargo == null || stack<1 || stack > this.stacks.length){
            throw new IllegalArgumentException("Invalid argument.");
        }
        else if(this.stacks[stack-1].size() == this.maxHeight) {
            throw new FullStackException("Stack is full.");
        }
        else if(this.getTotalStacksWeight() + cargo.getWeight() > maxWeight){
            throw new ShipOverweightException("Can't pass weight limit");
        }
        else if (this.stacks[stack-1].peek()!=null &&
                 this.stacks[stack-1].peek().getStrength().compareTo(cargo.getStrength()) < 0){
            throw new CargoStrengthException("Cargo strength rules violated.");
        }
        else{
            this.stacks[stack-1].push(cargo);
        }
    }

    /**
     * poppes a cargo from a given stack
     * @param stack for where to pop from
     * @return cargo that's popped
     * @throws EmptyStackException when stack is empty
     */
    public Cargo popCargo(int stack) throws EmptyStackException{
        if(stack<1 || stack > this.stacks.length){
            throw new IllegalArgumentException("Invalid argument.");
        }
        else if(stacks[stack-1].isEmpty()){
            throw new EmptyStackException("Stack is empty.");
        }
        else{
            return this.stacks[stack-1].pop();
        }
    }

    /**
     * searches and finds a cargo to print
     * @param name is for what name to search
     */
    public void findAndPrint(String name){
        StringBuilder s = new StringBuilder();
        s.append(" Stack   Depth   Weight   Strength\n" +
                "=======+=======+========+==========\n");
        boolean isFound=false;
        CargoShip shipCloned = this.clone();
        Cargo[][] Ship = new Cargo[getMaxStackSize()][getNumStacks()];
        for(int i = 1; i <= getNumStacks(); i++) {
            for(int j = getStack(i).size() - 1; j >= 0; j--) {
                try{
                    Ship[j][i-1] = shipCloned.popCargo(i);
                }catch (EmptyStackException e) {
                    break; //from the inner loop
                }
            }
        }
        for(int i = 1; i < Ship.length - 1; i++) {
            for(int j = 0; j < Ship[i-1].length - 1; j++) {
                if(Ship[j][i-1] != null && Ship[j][i-1].getName().equals(name)) {
                s.append("|   "+(j+1)+"   |   "+i+"   |  "+Ship[j][i].getWeight()+"  |  "+Ship[j][i].getStrength()+"  |\n");
                isFound = true;
                }
            }
        }
        s.append("\n===================================\n");
        if(isFound){ System.out.println(s); }
        else{
            System.out.println("Cargo '" + name + "' could not be found on the ship.");
        }
    }

    //ADDITIONAL METHODS USED FOR FIND/PRINT AND SHIPLOADER

    /**
     * @param stack for which stack to return
     * @return the stack
     * @throws IllegalArgumentException when invalid argument
     */
    public CargoStack getStack(int stack) throws IllegalArgumentException {
        if(stack < 1 || stack > this.stacks.length){
            throw new IllegalArgumentException("Invalid argument.");
        }else{
            return this.stacks[stack-1];
        }
    }

    /**
     * @return number of stacks
     */
    public int getNumStacks() {
        return this.stacks.length;
    }

    /**
     * @return total stack weight of all, aka the ship's weight
     * excludes the dock
     */
    public double getTotalStacksWeight() {
        double weight = 0;
        for(CargoStack stack : this.stacks){
            weight += stack.getWeight();
        }
        return weight;
    }

    /**
     * @return max weight limit
     */
    public double getMaxWeight() {
        return this.maxWeight;
    }

    /**
     * @return max size among the stacks
     */
    public int getMaxStackSize() {
        int maxSize=0;
        for(int i=0; i <= this.stacks.length-1; i++) {
            if(getStack(i+1).size() > maxSize){
                maxSize = getStack(i+1).size();
            }
        }
        return maxSize;
    }

    /**
     * @return clone of cargo ship
     */
    public CargoShip clone() {
        CargoShip cloneShip = new CargoShip(this.stacks.length,this.maxHeight,this.maxWeight);
        for(int i=0; i <= this.stacks.length-1; i++){
            cloneShip.stacks[i] = this.stacks[i].clone();
        }
        return cloneShip;
    }
}