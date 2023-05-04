/**
 * ApproachDateComparator class is a helper class for sorting procedures.
 * It helps with comparing/sorting based on approach date
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw7
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class ApproachDateComparator implements java.util.Comparator<NearEarthObject>{
    @Override
    public int compare(NearEarthObject o1, NearEarthObject o2) {
        if(o1.getClosestApproachDate().after(o2.getClosestApproachDate())){
            return 1;
        } else if(o1.getClosestApproachDate().before(o2.getClosestApproachDate())){
            return -1;
        } else{
            return 0;
        }
    }
}
