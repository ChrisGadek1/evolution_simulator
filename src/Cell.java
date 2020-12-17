import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Cell {
    public LinkedList<Animal> animals;

    public AbstractWorldBiome getBiome() {
        return biome;
    }

    public void setBiome(AbstractWorldBiome biome) {
        this.biome = biome;
    }

    public AbstractWorldBiome biome;

    int x;
    int y;
    Grass grass;
    GrassField map;



    Cell(LinkedList<Animal> animals, Grass grass, int x, int y, GrassField map){
        this.animals = animals;
        this.grass = grass;
        this.x = x;
        this.y = y;
        this.map = map;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private LinkedList<MoveDirection> getMovesToRandomPotentialNeighbourCollection(){
        LinkedList<MoveDirection> potentialMoveToTheFreeNeighbour = new LinkedList<>();
        MoveDirection firstMove = MoveDirection.STRAIGHT_ON;
        for(int i = 0; i < 8; i++){
            potentialMoveToTheFreeNeighbour.push(firstMove);
            firstMove = firstMove.getNextMove();
        }
        Collections.shuffle(potentialMoveToTheFreeNeighbour);
        return potentialMoveToTheFreeNeighbour;
    }

    Cell getRandomFreeNeighbour(){
        LinkedList<MoveDirection> potentialMoveToTheFreeNeighbour = this.getMovesToRandomPotentialNeighbourCollection();
        for(MoveDirection move: potentialMoveToTheFreeNeighbour){
            Vector2d neighbourPosition = new Vector2d(new Vector2d(x,y).add(move.moveToVector()));
            Cell potentialNeighbour = map.getCell().get(neighbourPosition);
            if(potentialNeighbour != null && potentialNeighbour.animals.size() == 0 && potentialNeighbour.grass == null){
                return potentialNeighbour;
            }
        }
        return null;
    }

    Cell getRandomNeighbour(){
        LinkedList<MoveDirection> potentialMoveToTheFreeNeighbour = this.getMovesToRandomPotentialNeighbourCollection();
        for(MoveDirection move: potentialMoveToTheFreeNeighbour){
            Vector2d neighbourPosition = new Vector2d(new Vector2d(x,y).add(move.moveToVector()));
            Cell potentialNeighbour = map.getCell().get(neighbourPosition);
            if(potentialNeighbour != null){
                return potentialNeighbour;
            }
        }
        return null;
    }

    static void manageCellsBreedMap(Vector2d oldPosition, Vector2d newPosition, GrassField map){
        if(newPosition != null && map.getCell().get(newPosition).animals.size() > 1){
            map.cellsReadyToBreed.add(map.getCell().get(newPosition));
        }
        if(oldPosition != null && map.getCell().get(oldPosition).animals.size() <= 1){
            Cell cellToRemove = map.getCell().get(oldPosition);
            map.cellsReadyToBreed.remove(cellToRemove);
        }
    }

    Vector2d breed(){
        animals.sort(new AnimalsComparator());
        Animal animal1 = animals.get(0);
        Animal animal2 = animals.get(1);
        if(animal1.getEnergy()> animal1.getMaxEnergy()/2 && animal2.getEnergy() > animal2.getMaxEnergy()/2){
            Cell newAnimalCell = this.getRandomFreeNeighbour();
            if(newAnimalCell == null){
                newAnimalCell = this.getRandomNeighbour();
            }
            animal1.setEnergy(animal1.getEnergy()/2);
            animal2.setEnergy(animal2.getEnergy()/2);
            Vector2d newAnimalPosition = new Vector2d(newAnimalCell.x, newAnimalCell.y);
            int[] genome = map.getGenomeGenerator().generateChildGenome(animal1, animal2);
            Animal child = new Animal(map, newAnimalPosition, animal1.getMaxEnergy(), genome);
            child.setEnergy(child.getMaxEnergy()/2);
            int newX = newAnimalPosition.getX();
            int newY = newAnimalPosition.getY();
            child.prepareBeforeAddToMap(newX, newY);
            if(animal1.isClicked() || animal2.isClicked()){
                child.setChild(true);
                child.setDescendant(true);
            }
            if(animal1.isDescendant() || animal2.isDescendant()){
                child.setDescendant(true);
            }
            Animal[] parents = new Animal[2];
            parents[0] = animal1;
            parents[1] = animal2;
            child.setParents(parents);
            map.revaluateEmptyCellsInformation(null, child.getPosition());
            return new Vector2d(newAnimalCell.x, newAnimalCell.y);
        }
        else return null;
    }
}
