import java.util.Iterator;
import java.util.LinkedList;

public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private IWorldMap map;

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    private int energy;

    public int getMaxEnergy() {
        return maxEnergy;
    }

    private int maxEnergy;
    private LinkedList<IPositionChangeObserver>  observers = new LinkedList<>();

    public Vector2d getPosition(){
        return this.position;
    }

    public void setPosition(Vector2d position){
        this.position = position;
    }


    public boolean move(MoveDirection direction){
        Vector2d tmp = this.position.add(direction.moveToVector());
        if(this.map.canMoveTo(tmp)){
            this.position = tmp;
            return true;
        }
        else return false;
    }


    public Animal(IWorldMap map, Vector2d initialPosition, int maxEnergy){
        this.map = map;
        this.position = initialPosition;
        this.maxEnergy = maxEnergy;
        this.setEnergy(maxEnergy);
    }

    void eat(Grass grass, int grassEnergy, AbstractWorldMap worldMap){
        this.setEnergy(this.getEnergy() + grassEnergy);
        if(this.getEnergy() > this.maxEnergy) this.setEnergy(this.maxEnergy);
        if(grass != null){
            worldMap.grassesMap.remove(grass.getPosition());
            worldMap.cellMap.get(grass.getPosition()).grass = null;
            int oldX = grass.getPosition().getX();
            int oldY = grass.getPosition().getY();
            worldMap.revaluateEmptyCellsInformation(grass.getPosition(), null,map.getJungle().isCellCoordInJungle(oldX,oldY),false);
        }

    }
}