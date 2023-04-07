/**
 *  IntersectionSimulator class is designed to hold place of Main as in the point of user and executor.
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Scanner;
public class IntersectionSimulator {
    /**
     *  does the intitaion for user to put fundamental inputs then refers to simulation.
     */
    public static void main(String[] args) {
        int simTime, numRoads; double prob;
        String[] names;int[] times;
        if (args.length > 1) { // LUMP OF INPUT FOR READING
            simTime    = Integer.parseInt(args[0]);
            prob       = Double.parseDouble(args[1]);
            numRoads   = Integer.parseInt(args[2]);
            names = new String[numRoads];
            times = new int[numRoads];

            for (int i = 0; i < numRoads; ++i) {
                names[i] = args[3 + i];
                times[i] = Integer.parseInt(args[3 + numRoads + i]);
            }

        }else{ //INTERACTIVE READING
            boolean duplicate = false;
            int maxGreen;
            String streetName;
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to IntersectionSimulator Spring 2023\n");
            System.out.print("Input the simulation time: ");
            simTime = input.nextInt();

            System.out.print("Input the arrival probability: ");
            prob = input.nextDouble();
            while (prob<=0.0 || prob>1.0) {
                System.out.println("Invalid argument.");
                System.out.print("Input the arrival probability: ");
                prob = input.nextDouble();
            }

            System.out.print("Input number of Streets: ");
            numRoads = input.nextInt();
            while (numRoads<0 || numRoads>4) {
                System.out.println("Invalid argument.");
                System.out.print("Input number of streets: ");
                numRoads = input.nextInt();
            }

            input.nextLine();
            names = new String[numRoads];
            times = new int[numRoads];
            for (int i=0; i<numRoads; i++) {
                System.out.println("Input Street "+ (i+1) +" name: ");
                streetName = input.nextLine();

                for (int j=0; j < i+1; j++){
                    if (streetName.equals(names[j])) {
                        duplicate = true;;
                        break;
                    }
                }

                while(duplicate){
                    System.out.println("Duplicate detected.");
                    streetName = input.nextLine();
                    duplicate=false;
                    for (int j=0; j<i+1; j++)
                        if (streetName.equals(names[j])) {
                            duplicate = true;
                            break;
                        }
                }

                names[i] = streetName;
            }

            for (int i=0; i<numRoads; i++) {
                System.out.println("Input max green time for "+ names[i] +": ");
                maxGreen = input.nextInt();
                while (maxGreen < 3) {
                    System.out.println("Invalid input.\n" +
                            "A max green time less than 3 will cause either an error or infinite loop.");
                    System.out.println("Input max green time for "+ names[i] +": ");
                    maxGreen = input.nextInt();
                }
                times[i] = maxGreen;
            }
        }
        System.out.println("\nStarting simulation...\n");
        if (numRoads==0)
            System.out.print("ERROR - NO SIMULATION - ERROR");
        else{
            simulate(simTime,prob,names,times);
        }
    }

    /**
     * does the actual simulation based on the parameters that were plugged by the user input in "main"
     * @param simulationTime time for continuing simulation
     * @param arrivalProbability for prob of car arrivals
     * @param roadNames is for each road's name
     * @param maxGreenTimes is for each road's green time
     */
    public static void simulate(int simulationTime,double arrivalProbability,String[] roadNames,int[] maxGreenTimes) {
        int passedCars=0, waitingCars=0, totalWait=0, maxWait=0;
        int time = 1;
        boolean isEmpty;
        TwoWayRoad[] roads = new TwoWayRoad[roadNames.length];

        for (int i = 0; i < roads.length; i++){ roads[i] = new TwoWayRoad(roadNames[i],maxGreenTimes[i]); }

        BooleanSourceHW4 bsArrival = new BooleanSourceHW4(arrivalProbability);
        Intersection simulating = new Intersection(roads);
        Vehicle[] passing;
        String[] arriving = new String[6];
        boolean continueSim = true;
        while (continueSim) {
            System.out.println("################################################################################\n"+
                               "Time Step: "+time+"\n\n");
            if (time < simulationTime) {
                arriving = new String[6];
                int numArrivals=0;
                for (TwoWayRoad road : roads) {
                    for (int way = 0; way < road.NUM_WAYS; way++) {

                        String direction = (way == road.FORWARD_WAY ? "FORWARD" : "BACKWARD");
                        for (int lane = 0; lane < road.NUM_LANES; lane++) {
                            String laneString =
                                    (lane == road.LEFT_LANE ? "LEFT" : lane == road.MIDDLE_LANE ? "MIDDLE" : "RIGHT");

                            if (bsArrival.occursHW4()&&numArrivals<6) {
                                Vehicle car = new Vehicle(time);
                                road.enqueueVehicle(way, lane, car);
                                arriving[numArrivals++] = String.format("Car"+car+" entered "+road+
                                        ", going "+direction+" in "+laneString+" lane.");

                            }
                        }

                    }
                }
            }
            passing = simulating.timeStep();
            System.out.println("    " + simulating.getCurrentLightValue() + " for " + roads[simulating.getLightIndex()]+
                               "    Timer = " + simulating.getCountdownTimer()+
                             "\n    ARRIVING CARS:");
            if (time < simulationTime) {
                isEmpty=true;
                for (String carArrivals : arriving)
                    if (carArrivals != null) {
                        System.out.printf("        %s\n", carArrivals);
                        isEmpty = false;
                        waitingCars++;
                    }
                if (isEmpty){
                    System.out.println("    No cars arrived.");
                }
            }else{
                System.out.println("    Cars no longer arriving.");
            }

            System.out.println("\n    PASSING CARS:");
            isEmpty=true;
            for (Vehicle car : passing) {
                if (car!=null) {
                    System.out.println("        Car"+car+" passes through. Wait time of " + (time - car.getTimeArrived()) +".\n");
                    isEmpty=false;
                    passedCars++; waitingCars--; //alter the numbers
                    totalWait += time - car.getTimeArrived();
                    maxWait = Math.max(maxWait, time-car.getTimeArrived());
                }
            }
            if (isEmpty){
                System.out.println("        No cars passed.");
            }


            simulating.display();
            //CURRENT IN-STEP STATS
            System.out.println("\n\n    STATISTICS:"+
            "        Cars currently waiting:  "+waitingCars+" cars\n"+
            "        Total cars passed:       "+passedCars+" cars\n"+
            "        Total wait time:         "+totalWait+" turns\n"+
            "        Average wait time:       "+(totalWait == 0 ? totalWait : (double)totalWait/passedCars) +" turns\n");
            boolean isRoadEmpty=true;
            for (TwoWayRoad road : roads){
                for (int way=0; way<road.NUM_WAYS; way++){
                    for (int lane=0; lane<road.NUM_LANES; lane++){
                        if (!road.isLaneEmpty(way,lane)){
                            isRoadEmpty=false;}
                    }
                }
            }
            if(time >= simulationTime && isRoadEmpty){
                continueSim = false;
            }
            time++;
        }
        //FINAL STATS
        System.out.println(
               "\n################################################################################\n"
                +"################################################################################\n"
                +"################################################################################\n" +
                "SIMULATION SUMMARY:\n" +
                "    Total time:           " + time + " steps\n" +
                "    Total vehicles:       " + passedCars + " cars\n" +
                "    Longest wait time:    " + maxWait + " turns\n" +
                "    Total wait time:      " + totalWait + " turns\n" +
                "    Average wait time:    " + (totalWait != 0 ? (double) totalWait / passedCars : totalWait) + " turns\n" +
                "\nEnd simulation.\n");
    }
}