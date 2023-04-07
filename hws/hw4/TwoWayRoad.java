/**
 *  TwoWayRoad class is designed to hold place of a two-way road (forward and backward of a street) and
 *  its attributes like name, green time and
 *  methods like isLaneEmpty or proceed
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 */
public class TwoWayRoad {
    public final int FORWARD_WAY = 0;
    public final int BACKWARD_WAY = 1;
    public final int NUM_WAYS = 2;
    public final int LEFT_LANE = 0;
    public final int MIDDLE_LANE = 1;
    public final int RIGHT_LANE = 2;
    public final int NUM_LANES = 3;
    public final int MAX_ROADS = 4;
    private String name;
    private int greenTime;
    private int leftSignalGreenTime;
    private VehicleQueue[][] lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
    private LightValue lightValue;

    /**
     * @return green time
     */
    public int getGreenTime(){
        return this.greenTime;
    }

    /**
     * @return light value
     */
    public LightValue getLightValue(){
        return this.lightValue;
    }

    /**
     * constructs a two-way road that is based on porameters given
     * @param initName is the name
     * @param initGreenTime is the green time at the road
     */
    public TwoWayRoad(String initName, int initGreenTime){
        if(initGreenTime <= 0 || initName==null){
            throw new IllegalArgumentException("Invalid argument.");
        }else {
            this.leftSignalGreenTime = (int) (1.0/NUM_LANES * initGreenTime);
            this.name = initName;
            this.greenTime = initGreenTime;
            for (int eachWay=0; eachWay < NUM_WAYS; eachWay++){
                for (int eachLane=0; eachLane < NUM_LANES; eachLane++){
                    this.lanes[eachWay][eachLane] = new VehicleQueue();
                }
            }
        }
    }

    /**
     * peeks cars from the road
     * @param wayIndex is the way to retrieve from (FORWARD-BACKWARD)
     * @param laneIndex is the lane to retrieve from (L-M-R)
     * @return the car at given way-lane
     */
    public VehicleQueue peek(int wayIndex, int laneIndex) {
        return this.lanes[wayIndex][laneIndex];
    }
    public boolean isLaneEmpty(int wayIndex, int laneIndex) throws IllegalArgumentException {
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2){
            throw new IllegalArgumentException("Invalid argument.");
        }else{
            return this.lanes[wayIndex][laneIndex].isEmpty();
        }
    }

    /**
     * proceeds cars on the road (via dequeuing)
     * @param timerVal is the time given for the proceed action
     * @return array of cars that were able to proceed/pass thru the road
     * @throws IllegalArgumentException when invalid argument
     */
    public Vehicle[] proceed(int timerVal){
        if(timerVal <= 0){
            throw new IllegalArgumentException("Invalid argument.");
        }else {
            int carCount = 0;
            boolean isEmpty = true;
            Vehicle[] passArray = new Vehicle[MAX_ROADS];
            for (int way=0; way < NUM_WAYS; way++){
                for (int lane = MIDDLE_LANE; lane < NUM_LANES; lane++){
                    if (!isLaneEmpty(way, lane)){
                        isEmpty = false;
                        break;
                    }
                }
            }
            lightValue = ( (timerVal <= leftSignalGreenTime || isEmpty) ? LightValue.LEFT_SIGNAL : LightValue.GREEN );
            for (int way = 0; way < NUM_WAYS; way++){
                for (int lane = 0; lane < NUM_LANES; lane++) {
                    //cars that proceed straight
                    if (lightValue == LightValue.GREEN && !lanes[way][lane].isEmpty() && lane != LEFT_LANE){
                        passArray[carCount++] = this.lanes[way][lane].dequeue();
                    }
                    //cars that proceed w/ left turn
                    else if(lightValue == LightValue.LEFT_SIGNAL && !lanes[way][lane].isEmpty() && lane == LEFT_LANE){
                        passArray[carCount++] = this.lanes[way][lane].dequeue();
                    }
                }
            }
            //all cars that proceeded straight or w/ left turn
            return passArray;
        }
    }

    /**
     * enqueues cars to the road at rear
     * @param wayIndex is which way to enqueue on (FORWARD-BACKWARD)
     * @param laneIndex is which lane to enqueue on (L-M-R)
     * @param vehicle is the car that will be enqueued
     */
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle){
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2 || vehicle==null) {
            throw new IllegalArgumentException("Invalid argument.");
        }else{
            this.lanes[wayIndex][laneIndex].enqueue(vehicle);
        }
    }

    /**
     * @return name of String for visuality in simulator
     */
    public String toString() { return this.name; }
}