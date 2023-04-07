/**
 *  VehicleQueue class is designed to hold place of a vehicle queue implemented via linked list and
 *  methods like enqueue, dequeue, or peek
 *  Mustafa Yigit Isik
 *  113080465
 *  CSE214_hw4
 *  Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.LinkedList;
public class VehicleQueue extends LinkedList<Vehicle>{
    /**
     * enqueues a vehicle into the queue at rear
     * @param vehicle is the car that we plug in
     */
    public void enqueue(Vehicle vehicle) {
        add(vehicle);
    }

    /**
     * dequeues a vehicle from queue at front
     * @return car that got plugged out
     */
    public Vehicle dequeue() {
        return remove();
    }

    /**
     * peeks on a vehicle from queue
     * @return car that is at front of the light
     */
    public Vehicle peek() {
        return getFirst();
    }

    /**
     * finds whether queue is empty or not
     * @return boolean based on 0-size or not
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * finds size of queue
     * @return int for the size
     */
    public int size() {
        return super.size();
    }

    /**
     * clears the queue by dequeueing all cars
     */
    public void clear() {
        super.clear();
    }
}