import java.awt.*;

/**
 * Represent every element in a GrassField, like Grass or Animal
 * */

abstract public class AbstractWorldElement {
    public AbstractWorldElement(GrassField grassField){
        this.map = grassField;
    }

    public GrassField getMap() {
        return map;
    }

    public void setMap(GrassField map) {
        this.map = map;
    }

    private GrassField map;
    private Vector2d position;

    //Shape to be drawn in the GrassField
    private Shape shape;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    /*
    * This function updates information about world elements in arrays and maps
    * */
    abstract void prepareBeforeAddToMap(int x, int y);


}
