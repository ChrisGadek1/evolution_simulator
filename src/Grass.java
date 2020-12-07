import java.util.Iterator;
import java.util.LinkedList;

public class Grass {
    private Vector2d position;
    private LinkedList<IPositionChangeObserver> observers = new LinkedList<>();
    void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        Iterator<IPositionChangeObserver> itr = this.observers.listIterator(0);
        observers.remove(observer);
    }

    public Grass(Vector2d vector2d){
        this.position = vector2d;
    }

    Vector2d getPosition(){
        return position;
    }

    void setPosition(Vector2d vector2d){
        this.position = vector2d;
    }

    public String toString(){
        return "*";
    }

}
