import java.util.*;

public class GrassField{

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
            animal.prepareBeforeAddToMap(randomCoords[0], randomCoords[1]);
            Cell.manageCellsBreedMap(null, new Vector2d(randomCoords[0],randomCoords[1]), this);
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
                cellMap.put(new Vector2d(i,j), new Cell(new LinkedList<Animal>(),null, jungle.isCellCoordInJungle(i,j),i,j, this));
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


    public boolean canMoveTo(Vector2d position) {
        if(position.getX() >= 0 && position.getX() < this.width && position.getY() >= 0 && position.getY() < this.height) return true;
        else return false;
    }


    public Object objectAt(Vector2d position) {
        if(cellMap.get(position).animals.size() != 0) return cellMap.get(position).animals;
        if(cellMap.get(position).grass != null) return cellMap.get(position).grass;
        return null;
    }


    public Map<Vector2d, Cell> getCell() {
        return this.cellMap;
    }


    public LinkedList<Animal> getAnimals() {
        return this.animalPositions;
    }


    public Map<Vector2d, Grass> getGrassesMap() {
        return this.grassesMap;
    }

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
