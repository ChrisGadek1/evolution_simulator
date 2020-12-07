import java.util.*;

public class GrassField extends AbstractWorldMap implements IWorldMap {

    private int grassQuantity;
    private int width;
    private int height;
    private Jungle jungle;

    Random random;

    int getHeight(){
        return height;
    }

    int getWidth(){
        return width;
    }


    int[] getRandomFreeCoordinatesInJungle(){
        int randomIndex = this.random.nextInt(this.activeAxisForJungleIndexes.size());
        int randomY = this.activeAxisForJungleIndexes.get(randomIndex);
        ArrayList<Integer> freePositions = this.usedAxisForJungle.get(randomY).freePositions;
        int randomX = freePositions.get(this.random.nextInt(freePositions.size()));
        int result[] = new int[2];
        result[0] = randomX;
        result[1] = randomY;
        return result;
    }

    int[] getRandomFreeCoordinatesInSavannah(){
        int randomIndex = this.random.nextInt(this.activeAxisForSavannahIndexes.size());
        int randomY = this.activeAxisForSavannahIndexes.get(randomIndex);
        ArrayList<Integer> freePositions = this.usedAxisForSavannah.get(randomY).freePositions;
        int randomX = freePositions.get(this.random.nextInt(freePositions.size()));
        int result[] = new int[2];
        result[0] = randomX;
        result[1] = randomY;
        return result;
    }


    public boolean placeAnimalInRandomPlaceInJungle(Animal animal){
        if(this.activeAxisForJungleIndexes.size() > 0){
            int randomCoords[] = getRandomFreeCoordinatesInJungle();
            animal.setPosition(new Vector2d(randomCoords[0],randomCoords[1]));
            animalPositions.add(animal);
            cellMap.get(animal.getPosition()).animals.add(animal);
            revaluateEmptyCellsInformation(null, animal.getPosition(),false,true);
            return true;
        }
        else{
            return false;
        }

    }

    public void addGrass(Grass grass, Vector2d oldPosition){
        this.cellMap.get(grass.getPosition()).grass = grass;
        int newX = grass.getPosition().getX();
        int newY = grass.getPosition().getY();
        this.revaluateEmptyCellsInformation(null, grass.getPosition(),false,jungle.isCellCoordInJungle(newX, newY));
    }

    public boolean addGrassToRandomPlaceInJungle(Grass grass){
        if(this.activeAxisForJungleIndexes.size() > 0){
            int randomCoords[] = getRandomFreeCoordinatesInJungle();
            grass.setPosition(new Vector2d(randomCoords[0], randomCoords[1]));
            addGrass(grass, null);

            this.grassesMap.put(grass.getPosition(), grass);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addGrassToRandomPlaceInSavannah(Grass grass){
        if(this.activeAxisForSavannahIndexes.size() > 0){
            int randomCoords[] = getRandomFreeCoordinatesInSavannah();
            grass.setPosition(new Vector2d(randomCoords[0], randomCoords[1]));
            addGrass(grass, null);
            this.grassesMap.put(grass.getPosition(), grass);
            return true;
        }
        else {
            return false;
        }
    }

    public GrassField(int grassQuantity, int width, int height, Jungle jungle){
        Random random = new Random();
        this.random = random;
        this.grassQuantity = grassQuantity;
        this.jungleXAxis = new XAxis[height];
        this.savannahXAxis = new XAxis[height];
        this.jungle = jungle;

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                cellMap.put(new Vector2d(i,j), new Cell(new LinkedList<Animal>(),null, jungle.isCellCoordInJungle(i,j),i,j));
                if(jungle.isCellCoordInJungle(i,j) && usedAxisForJungle.get(j) == null){
                    XAxis jungleAxis = new XAxis(j,width);
                    for(int k = 0; k < jungle.getCellsWidth(); k++){
                        jungleAxis.freePositions.add(i+k);
                    }
                    usedAxisForJungle.put(j,jungleAxis);
                    this.jungleXAxis[j] = jungleAxis;
                    this.activeAxisForJungleIndexes.add(j);
                }
                else if(!jungle.isCellCoordInJungle(i,j) && usedAxisForSavannah.get(j) == null){
                    XAxis savannahAxis = new XAxis(j,width);
                    for(int k = 0; k < width; k++){
                        if(!jungle.isCellCoordInJungle(k,j)) savannahAxis.freePositions.add(k);
                    }
                    usedAxisForSavannah.put(j,savannahAxis);
                    this.savannahXAxis[j] = savannahAxis;
                    this.activeAxisForSavannahIndexes.add(j);
                }
            }
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(position.getX() >= 0 && position.getX() < this.width && position.getY() >= 0 && position.getY() < this.height) return true;
        else return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(cellMap.get(position).animals.size() != 0) return cellMap.get(position).animals;
        if(cellMap.get(position).grass != null) return cellMap.get(position).grass;
        return null;
    }

    @Override
    public Map<Vector2d, Cell> getCell() {
        return this.cellMap;
    }

    @Override
    public LinkedList<Animal> getAnimals() {
        return this.animalPositions;
    }

    @Override
    public Map<Vector2d, Grass> getGrassesMap() {
        return this.grassesMap;
    }

    @Override
    public Jungle getJungle() {
        return this.jungle;
    }

    public int getRandom(int min, int max) {
        int result = this.random.nextInt(max - min) + min;
        return result;
    }

    public int[] getBounds(){
       int result[] =  {0,0,width,height};
       return result;
    }



}
