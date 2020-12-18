import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract public class AbstractWorldBiome {
    Map<Integer, XAxis> usedAxis = new HashMap<>();
    ArrayList<Integer> activeAxisIndexes = new ArrayList<>();
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
