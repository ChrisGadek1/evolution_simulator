import java.util.*;

/**
 * Class that represent the map
 * */

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
    private boolean isDominateGenomViewSelected;
    private Genome mainGenome;
    private InitialParameters parameters;
    Random random;


    Map<Vector2d, Cell> cellMap = new LinkedHashMap<>();
    LinkedList<Animal> animalPositions = new LinkedList<Animal>();
    Map<Vector2d, Grass> grassesMap = new HashMap<>();

    //The cell is ready to breed when it contains two animals or more
    HashSet<Cell> cellsReadyToBreed = new HashSet<>();

    public GrassField(int grassQuantity, int width, int height, double jungleRatio, InitialParameters parameters){
        this.parameters = parameters;
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

        /* computes the jungle parameters to place the jungle as a rectangle
        * in the middle of the map
        * */
        int jungleWidth = (int)(width*Math.sqrt(this.jungleRatio));
        int jungleHeight = (int)(height*Math.sqrt(this.jungleRatio));
        int jungleBeginCellX = (width-jungleWidth)/2;
        int jungleBeginCellY = (height-jungleHeight)/2;

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //fills the cellMap with information
                Cell newCell = new Cell(new LinkedList<Animal>(),null,i,j, this);
                cellMap.put(new Vector2d(i,j), newCell);
                //set the cells biome information depending on the jungle width and height
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


    /* if some world element (animal) would like to go out of the map
    * this method prevents from NullPointerException and returns it to the map
    * */
    public Vector2d vectorToMap(Vector2d tmp) {
        if(tmp.getY() < 0) tmp.setY(this.getHeight()-1);
        else if(tmp.getY() >= this.getHeight()) tmp.setY(0);
        if(tmp.getX() < 0) tmp.setX(this.getWidth()-1);
        else if(tmp.getX() >= this.getWidth()) tmp.setX(0);
        return tmp;
    }

    void revaluateEmptyCellsInformation(Vector2d oldPosition, Vector2d newPosition){
        if(oldPosition != null) this.getCell().get(oldPosition).getBiome().revaluateEmptyCellsInformation(oldPosition, newPosition);
        if(newPosition != null) this.getCell().get(newPosition).getBiome().revaluateEmptyCellsInformation(oldPosition, newPosition);
    }



    public LinkedList<Animal> copyAnimals(GrassField grassField){
        LinkedList<Animal> copiedAnimals = new LinkedList<>();
        for(Animal animal: this.animalPositions){
            Animal copiedAnimal = new Animal(grassField,new Vector2d(animal.getPosition()), animal.getMaxEnergy(), animal.getGenome().copyGenome().getGens());
            copiedAnimals.push(copiedAnimal);
        }
        return copiedAnimals;
    }

    //getters and setters:

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

    public boolean isDominateGenomViewSelected() {
        return isDominateGenomViewSelected;
    }

    public void setDominateGenomViewSelected(boolean dominateGenomViewSelected) {
        isDominateGenomViewSelected = dominateGenomViewSelected;
    }

    public Genome getMainGenome() {
        return mainGenome;
    }

    public void setMainGenome(Genome mainGenome) {
        this.mainGenome = mainGenome;
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

    public InitialParameters getParameters() {
        return parameters;
    }
}
