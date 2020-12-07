import java.util.ArrayList;

public class XAxis {
    public int axisNumber;
    public int axisSize;
    ArrayList<Integer> freePositions = new ArrayList<>();

    XAxis(int axisNumber, int axisSize){
        this.axisNumber = axisNumber;
        this.axisSize = axisSize;
    }
    public String toString(){
        return freePositions.toString();
    }
}
