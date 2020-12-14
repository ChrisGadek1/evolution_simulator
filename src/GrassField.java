import java.util.*;

public class GrassField{

    private int grassQuantity;
    private int width;
    private int height;
    private double jungleRatio;
    private Jungle jungle;
    private Savannah savannah;
    private StatisticsCollector statisticsCollector;
    private int day = 0;
    private GenomeGenerator genomeGenerator;
    private Animal clickedAnimal = null;
    boolean isHistoryFollowed = false;
    private ClickOnPanelObserver clickObserver;
    Random random;
    Map<Vector2d, Cell> cellMap = new LinkedHashMap<>();
    LinkedList<Animal> animalPositions = new LinkedList<Animal>();
    Map<Vector2d, Grass> grassesMap = new HashMap<>();
    HashSet<Cell> cellsReadyToBreed = new HashSet<>();

    public GrassField(int grassQuantity, int width, int height, double jungleRatio){
        this.width = width;
        this.height = height;
        Random random = new Random();
        this.random = random;
        this.grassQuantity = grassQuantity;
        this.jungleRatio = jungleRatio;
        this.genomeGenerator = new GenomeGenerator(new Random());
        this.statisticsCollector = new StatisticsCollector(this);
        this.jungle = new Jungle(this);
        this.savannah = new Savannah(this);

        int jungleWidth = (int)(width*Math.sqrt(this.jungleRatio));
        int jungleHeight = (int)(height*Math.sqrt(this.jungleRatio));
        int jungleBeginCellX = (width-jungleWidth)/2;
        int jungleBeginCellY = (height-jungleHeight)/2;

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Cell newCell = new Cell(new LinkedList<Animal>(),null,i,j, this);
                cellMap.put(new Vector2d(i,j), newCell);
                if(i >= jungleBeginCellX && i < jungleBeginCellX + jungleWidth && j >= jungleBeginCellY && j < jungleBeginCellY + jungleHeight){
                    cellMap.get(new Vector2d(i,j)).setBiome(jungle);
                }
                else{
                    cellMap.get(new Vector2d(i,j)).setBiome(savannah);
                }
            }
        }
        this.getSavannah().generateEmptyAxises();
        this.getJungle().generateEmptyAxises();
    }


    public boolean canMoveTo(Vector2d position) {
        if(position.getX() >= 0 && position.getX() < this.width && position.getY() >= 0 && position.getY() < this.height) return true;
        else return false;
    }

    void revaluateEmptyCellsInformation(Vector2d oldPosition, Vector2d newPosition){
        if(oldPosition != null) this.getCell().get(oldPosition).getBiome().revaluateEmptyCellsInformation(oldPosition, newPosition);
        if(newPosition != null) this.getCell().get(newPosition).getBiome().revaluateEmptyCellsInformation(oldPosition, newPosition);
    }

    public void setClickedAnimal(Animal clickedAnimal) {
        this.clickedAnimal = clickedAnimal;
    }

    public Animal getClickedAnimal() {
        return clickedAnimal;
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

    public Savannah getSavannah() {
        return savannah;
    }

    public void setSavannah(Savannah savannah) {
        this.savannah = savannah;
    }

    int getHeight(){
        return height;
    }

    int getWidth(){
        return width;
    }

    StatisticsCollector getStatisticsCollector(){
        return this.statisticsCollector;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public GenomeGenerator getGenomeGenerator() {
        return genomeGenerator;
    }

    public ClickOnPanelObserver getClickObserver() {
        return clickObserver;
    }

    public void setClickObserver(ClickOnPanelObserver clickObserver) {
        this.clickObserver = clickObserver;
    }

}
