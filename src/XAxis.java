import java.util.ArrayList;

/**
* This object represents XAxis in world biome object.
* */

public class XAxis {
    public int axisNumber;
    public int axisSize;
    //this field contains every free position (first coordinate in point) in the XAxis
    ArrayList<Integer> freePositions = new ArrayList<>();

    XAxis(int axisNumber, int axisSize){
        this.axisNumber = axisNumber;
        this.axisSize = axisSize;
    }
    public String toString(){
        return freePositions.toString();
    }
}
