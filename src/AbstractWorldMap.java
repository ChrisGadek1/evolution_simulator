import java.util.*;

public abstract class AbstractWorldMap {
    Map<Vector2d, Cell> cellMap = new LinkedHashMap<>();
    LinkedList<Animal> animalPositions = new LinkedList<Animal>();

    Map<Vector2d, Grass> grassesMap = new HashMap<>();

    Map<Integer, XAxis> usedAxisForJungle = new HashMap<>();

    Map<Integer, XAxis> usedAxisForSavannah = new HashMap<>();

    ArrayList<Integer> activeAxisForJungleIndexes = new ArrayList<>();

    ArrayList<Integer> activeAxisForSavannahIndexes = new ArrayList<>();

    HashSet<Cell> cellsReadyToBreed = new HashSet<>();

    XAxis jungleXAxis[];

    XAxis savannahXAxis[];

    public boolean isOccupied(Vector2d position) {
        Object tmp = this.objectAt(position);
        if(tmp == null) return false;
        else return true;
    }



    void revaluateEmptyCellsInformation(Vector2d oldPosition, Vector2d newPosition, boolean oldJungle, boolean newJungle){
        if(oldPosition != null){
            if(oldJungle){
                Cell currentCell = cellMap.get(new Vector2d(oldPosition.getX(),oldPosition.getY()));
                if(currentCell.animals.size() == 0 && currentCell.grass == null){
                    this.jungleXAxis[oldPosition.getY()].freePositions.add(oldPosition.getX());
                    if(this.usedAxisForJungle.get(oldPosition.getY()) == null){
                        this.usedAxisForJungle.put(oldPosition.getY(), jungleXAxis[oldPosition.getY()]);
                        this.activeAxisForJungleIndexes.add(oldPosition.getY());
                    }
                }
            }
            else{
                Cell currentCell = cellMap.get(new Vector2d(oldPosition.getX(),oldPosition.getY()));
                if(currentCell.animals.size() == 0 && currentCell.grass == null){
                    this.savannahXAxis[oldPosition.getY()].freePositions.add(oldPosition.getX());
                    if(this.usedAxisForSavannah.get(oldPosition.getY()) == null){
                        this.usedAxisForSavannah.put(oldPosition.getY(), savannahXAxis[oldPosition.getY()]);
                        this.activeAxisForSavannahIndexes.add(oldPosition.getY());
                    }
                }
            }

        }
        if(newPosition != null){
            if(newJungle){
                ArrayList<Integer> freePositions =  this.jungleXAxis[newPosition.getY()].freePositions;
                Cell currentCell = cellMap.get(new Vector2d(newPosition.getX(),newPosition.getY()));
                if(currentCell.animals.size() != 0 || currentCell.grass != null){
                    freePositions.remove((Object)newPosition.getX());
                    if(this.usedAxisForJungle.get(newPosition.getY()) != null && this.usedAxisForJungle.get(newPosition.getY()).freePositions.size() == 0){
                        this.usedAxisForJungle.remove(newPosition.getY());
                        this.activeAxisForJungleIndexes.remove((Object)newPosition.getY());
                    }
                }
            }
            else{
                ArrayList<Integer> freePositions =  this.savannahXAxis[newPosition.getY()].freePositions;
                Cell currentCell = cellMap.get(new Vector2d(newPosition.getX(),newPosition.getY()));
                if(currentCell.animals.size() != 0 || currentCell.grass != null){
                    freePositions.remove((Object)newPosition.getX());
                    if(this.usedAxisForSavannah.get(newPosition.getY()) != null && this.usedAxisForSavannah.get(newPosition.getY()).freePositions.size() == 0){
                        this.usedAxisForSavannah.remove(newPosition.getY());
                        this.activeAxisForSavannahIndexes.remove((Object)newPosition.getY());
                    }
                }
            }
        }
    }



    abstract public int[] getBounds();

    abstract protected boolean canMoveTo(Vector2d position);

    abstract Object objectAt(Vector2d position);


}
