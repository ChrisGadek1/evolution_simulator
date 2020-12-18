import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents every biome on map
 * On the biome depends some factors, like the quantity of Grass grown every day
 *
 * */

abstract public class AbstractWorldBiome {

    /*
    * Map contains XAxises mapped by their indexes, where
    * still exist free positions
    * */

    Map<Integer, XAxis> usedAxis = new HashMap<>();

    /*
    * Array contains XAxises indexes, which contains free positions to draw
    * */

    ArrayList<Integer> activeAxisIndexes = new ArrayList<>();

    //all XAxis stored in this array to not be deleted by the garbage collector
    XAxis XAxis[];
    GrassField map;
    Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public AbstractWorldBiome(GrassField map){
        this.map = map;
        this.XAxis = new XAxis[map.getHeight()];
    }


    /*
    * After every change of position of every world element, we must check,
    * if their previous cell is empty now (if so, we need to and this to free positions in correct XAxis)
    * analogously if the new position was empty, we need to delete now from empty positions
    * */
    void revaluateEmptyCellsInformation(Vector2d oldPosition, Vector2d newPosition){
        if(oldPosition != null && this.map.cellMap.get(oldPosition).getBiome() == this){
            Cell currentCell = this.map.cellMap.get(new Vector2d(oldPosition.getX(),oldPosition.getY()));
            if(currentCell.animals.size() == 0 && currentCell.grass == null){
                this.XAxis[oldPosition.getY()].freePositions.add(oldPosition.getX());
                if(this.usedAxis.get(oldPosition.getY()) == null){
                    this.usedAxis.put(oldPosition.getY(), XAxis[oldPosition.getY()]);
                    this.activeAxisIndexes.add(oldPosition.getY());
                }
            }
        }
        if(newPosition != null && this.map.cellMap.get(newPosition).getBiome() == this){
            ArrayList<Integer> freePositions =  this.XAxis[newPosition.getY()].freePositions;
            Cell currentCell = this.map.cellMap.get(new Vector2d(newPosition.getX(),newPosition.getY()));
            if(currentCell.animals.size() != 0 || currentCell.grass != null){
                freePositions.remove((Object)newPosition.getX());
                if(this.usedAxis.get(newPosition.getY()) != null && this.usedAxis.get(newPosition.getY()).freePositions.size() == 0){
                    this.usedAxis.remove(newPosition.getY());
                    this.activeAxisIndexes.remove((Object)newPosition.getY());
                }
            }
        }
    }


    /*
    * Initial method, where we need to create empty XAxises depends on the biome area.
    * F.e. suppose that the biome is rectangular and begins in (3,4) and ends in (10,9)
    * It have XAxis with indexes: [4,5,6,7,8,9], and every XAxis has free positions at the
    * beginning: [3,4,5,6,7,8,9,10]
    * */
    void generateEmptyAxises(){
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = 0; j < map.getHeight(); j++){
                if(map.getCell().get(new Vector2d(i,j)).getBiome() == this){
                    if(usedAxis.get(j) == null){
                        XAxis Axis = new XAxis(j,map.getWidth());
                        usedAxis.put(j,Axis);
                        this.XAxis[j] = Axis;
                        this.activeAxisIndexes.add(j);
                    }
                    XAxis axis = usedAxis.get(j);
                    axis.freePositions.add(i);
                }
            }
        }
    }

    /*
    * Uses the information about free positions in every XAxis and draws one point
    * */
    int[] getRandomFreeCoordinates(){
        int randomIndex = this.map.random.nextInt(this.activeAxisIndexes.size());
        int randomY = this.activeAxisIndexes.get(randomIndex);
        ArrayList<Integer> freePositions = this.usedAxis.get(randomY).freePositions;
        int randomX = freePositions.get(this.map.random.nextInt(freePositions.size()));
        int result[] = new int[2];
        result[0] = randomX;
        result[1] = randomY;
        return result;
    }



    boolean placeWorldElementInBiomeInRandomPlace(AbstractWorldElement element){
        if(this.activeAxisIndexes.size() > 0){
            int randomCoords[] = getRandomFreeCoordinates();
            element.prepareBeforeAddToMap(randomCoords[0], randomCoords[1]);
            revaluateEmptyCellsInformation(null, element.getPosition());
            return true;
        }
        else{
            return false;
        }
    }

    boolean placeWorldElementInBiome(AbstractWorldElement element){
        int x = element.getPosition().getX();
        int y = element.getPosition().getY();
        if(this.activeAxisIndexes.contains(y) && XAxis[y].freePositions.contains(x)){
            element.prepareBeforeAddToMap(x,y);
            revaluateEmptyCellsInformation(null, element.getPosition());
            return true;
        }
        else {
            return false;
        }
    }

}
