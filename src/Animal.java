public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private GrassField map;
    private int energy;
    private int maxEnergy;
    private MoveDirection moveDirection;

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int[] genome;

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void setPosition(Vector2d position){
        this.position = position;
    }

    public MoveDirection chooseNewDirection(){
        return MoveDirection.mapIntToMove(genome[map.random.nextInt(32)]);

    }

    public void setMoveDirection(MoveDirection moveDirection){
        this.moveDirection = moveDirection;
    }

    public boolean move(){
        Vector2d tmp = this.position.add(this.moveDirection.moveToVector());
        if(this.map.canMoveTo(tmp)){
            this.position = tmp;
            return true;
        }
        else{
            return false;
        }
    }


    public Animal(GrassField map, Vector2d initialPosition, int maxEnergy, int[] genome){
        this.map = map;
        this.position = initialPosition;
        this.maxEnergy = maxEnergy;
        this.setEnergy(maxEnergy);
        this.moveDirection = MoveDirection.getRandomMove();
        this.genome = genome;
    }

    void prepareBeforeAddToMap(int x, int y){
        this.setPosition(new Vector2d(x,y));
        map.animalPositions.add(this);
        try{
            map.cellMap.get(this.getPosition()).animals.add(this);
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
            int oldX = grass.getPosition().getX();
            int oldY = grass.getPosition().getY();
            worldMap.revaluateEmptyCellsInformation(grass.getPosition(), null,map.getJungle().isCellCoordInJungle(oldX,oldY),false);
        }

    }
}