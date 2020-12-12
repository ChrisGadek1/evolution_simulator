import java.awt.*;

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

    private Shape shape;



    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    abstract void prepareBeforeAddToMap(int x, int y);


}
