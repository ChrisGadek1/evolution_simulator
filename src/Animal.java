public class Animal extends AbstractWorldElement {
    private int energy;
    private int maxEnergy;
    private int dayOfBirth;
    private MoveDirection moveDirection;
    public Genome genome;
    private boolean isClicked;
    private boolean isChild;
    private boolean isDescendant;

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public boolean isDescendant() {
        return isDescendant;
    }

    public void setDescendant(boolean descendant) {
        isDescendant = descendant;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }


    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public Genome getGenome() {
        return genome;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public MoveDirection chooseNewDirection(){
        return MoveDirection.mapIntToMove(genome.getGens()[this.getMap().random.nextInt(32)]);
    }

    public void setMoveDirection(MoveDirection moveDirection){
        this.moveDirection = moveDirection;
    }

    public boolean move(){
        Vector2d tmp = this.getPosition().add(this.moveDirection.moveToVector());
        tmp = this.getMap().vectorToMap(tmp);
        this.setPosition(tmp);
        return true;
    }

    public Animal(GrassField map, Vector2d initialPosition, int maxEnergy, int[] genome){
        super(map);
        this.setPosition(initialPosition);
        this.maxEnergy = maxEnergy;
        this.setEnergy(maxEnergy);
        this.moveDirection = MoveDirection.getRandomMove();
        this.genome = new Genome(genome);
        this.dayOfBirth = map.getDay();
    }

    @Override
    void prepareBeforeAddToMap(int x, int y){
        this.setPosition(new Vector2d(x,y));
        this.getMap().animalPositions.add(this);
        Cell.manageCellsBreedMap(null, new Vector2d(x,y), this.getMap());
        try{
            this.getMap().cellMap.get(this.getPosition()).animals.add(this);
        }catch (Exception e){
            System.out.println("nie ma takiej komórki: "+this.getPosition());
        }
    }

    void eat(Grass grass, int grassEnergy, GrassField worldMap){
        this.setEnergy(this.getEnergy() + grassEnergy);
        if(this.getEnergy() > this.maxEnergy) this.setEnergy(this.maxEnergy);
        if(grass != null){
            worldMap.grassesMap.remove(grass.getPosition());
            worldMap.cellMap.get(grass.getPosition()).grass = null;
            worldMap.revaluateEmptyCellsInformation(grass.getPosition(), null);
        }
    }
}