/**
 *  Intersection class is designed to hold place of an intersection (coming together of some two-way roads) and
 *  its attributes like count down timer or light index and
 *  methods like timeStep or display
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 */
public class Intersection {
    private TwoWayRoad[] roads;
    private int lightIndex;
    private int countdownTimer;
    private TwoWayRoad road;
    public final int MAX_ROADS = 4;

    /**
     * constructs an intersection consisting of roads
     * @param initRoads is the array of roads that will make up the intersection
     * @throws IllegalArgumentException when invalid argument
     */
    public Intersection(TwoWayRoad[] initRoads){
        if(initRoads == null || initRoads.length > MAX_ROADS){
            throw new IllegalArgumentException("Invalid argument.");
        }
        for(TwoWayRoad road: initRoads){
            if(road == null){
                throw new IllegalArgumentException("Invalid argument.");
            }
        }
        roads = new TwoWayRoad[initRoads.length];
        System.arraycopy(initRoads, 0, roads, 0, roads.length);
        lightIndex=0;
        road = roads[lightIndex];
        countdownTimer=road.getGreenTime();
    }

    /**
     * @return light index
     */
    public int getLightIndex() {
        return this.lightIndex;
    }

    /**
     * @return countdown timer of intersection
     */
    public int getCountdownTimer() {
        return this.countdownTimer;
    }

    /**
     * @return num of roads of intersection
     */
    public int getNumRoads() {
        return this.roads.length;
    }

    /**
     * @return light value at intersection as of now
     */
    public LightValue getCurrentLightValue() {
        return this.road.getLightValue();
    }

    /**
     * represents a single timestep action of cars moving
     * @return proceeding cars that moved off the intersection
     * @throws IllegalArgumentException when invalid argument
     */
    public Vehicle[] timeStep(){
        boolean isEmpty = true;
        for (int way = 0; way < road.NUM_WAYS; way++){
            for (int lane = road.MIDDLE_LANE; lane < road.NUM_LANES; lane++){
                if ( !road.isLaneEmpty(way,lane) ){
                    isEmpty = false;
                    break;
                }
            }
        }
        if(isEmpty){
            for (int way = 0; way < road.NUM_WAYS; way++){
                if ( !road.isLaneEmpty(way,road.LEFT_LANE) ) {
                    isEmpty=false;
                    break;
                }
            }
        }
        if(countdownTimer == 0 || isEmpty){
            lightIndex = ++lightIndex % getNumRoads();
            road = roads[lightIndex];
            countdownTimer = road.getGreenTime();
        }
        return roads[lightIndex].proceed(countdownTimer);
    }
    /**
     * displays the roads with attention to visuality to make it same as the html file
     */
    public void display(){
        String[] laneInitial = {"L","M","R"};
        StringBuilder carQueue;

        for (TwoWayRoad road : roads) {
            System.out.println("\n    " + road + ":\n"+
                               "                           FORWARD               BACKWARD\n"+
                               "    ==============================               ===============================");

            //lanes of each road being printed out (multiple queues)
            for (int lane = 0; lane < road.NUM_LANES*2; lane++) {
                if (lane%2 == 0) {
                    for (int way = 0; way < road.NUM_WAYS; way++) {
                        if (way == 0) {
                            carQueue = new StringBuilder();
                            for (int i = road.peek(way,lane/2).size() - 1;i >- 1;i--){
                                carQueue.append(road.peek(way, lane / 2).get(i));
                            }

                            System.out.printf("    %30s", carQueue);
                            System.out.printf(" [%s] %s    %s [%s] ",
                            laneInitial[lane/2],
                            this.road == road &&
                                (road.getLightValue() == LightValue.GREEN && lane/2 > 0) ||
                                (road.getLightValue() == LightValue.LEFT_SIGNAL && lane/2 == 0) ? " " : "X",
                            this.road == road &&
                                (road.getLightValue() == LightValue.GREEN && 2-lane/2>0) ||
                                (road.getLightValue() == LightValue.LEFT_SIGNAL && 2-lane/2 == 0) ? " " : "X",
                            laneInitial[laneInitial.length-1-lane/2]);

                        }
                        else{
                            for (Vehicle car : road.peek(way, road.NUM_LANES-1-lane/2)){
                                System.out.print(car);
                            }
                        }
                    }
                }
                // lane separators for visuality
                else if (lane<5){
                    System.out.println("\n    ------------------------------                ------------------------------");
                }
            }

            System.out.println("\n    ==============================               ===============================");
        }
        countdownTimer--;
    }
}
