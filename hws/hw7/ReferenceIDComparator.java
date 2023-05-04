/**
 * ReferenceIDComparator class is a helper class for sorting procedures.
 * It helps with comparing/sorting based on reference ID
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw7
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
public class ReferenceIDComparator implements java.util.Comparator<NearEarthObject> {
    @Override
    public int compare(NearEarthObject o1, NearEarthObject o2) {
        if (o1.getReferenceID() - o2.getReferenceID() > 0){
            return 1;
        }else if(o1.getReferenceID() - o2.getReferenceID() < 0){
            return -1;
        }else{
            return 0;
        }
    }
}
