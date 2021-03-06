import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class implements main simulation phases
 * */

public class SimulationEngine{

    GrassField map;
    int grassEnergy;
    int maxEnergy;
    int cellsWidth;
    int cellsHeight;
    int moveEnergy;
    Random rand;

    public SimulationEngine(GrassField map, int grassEnergy, int maxEnergy, int cellsWidth, int cellsHeight, int moveEnergy){
        this.map = map;
        this.grassEnergy = grassEnergy;
        this.maxEnergy = maxEnergy;
        this.cellsWidth = cellsWidth;
        this.cellsHeight = cellsHeight;
        this.moveEnergy = moveEnergy;
        rand = new Random();
    }

    public void moveAnimals(){
        Iterator<Animal> animalPositionItr = map.animalPositions.listIterator();
        while(animalPositionItr.hasNext()){
            Animal animal = animalPositionItr.next();
            MoveDirection move = animal.chooseNewDirection();
            Vector2d newPosition = animal.getPosition().add(move.moveToVector());
            Vector2d oldPosition = new Vector2d(animal.getPosition());
            newPosition = map.vectorToMap(newPosition);
            map.getCell().get(newPosition).animals.add(animal);
            map.getCell().get(animal.getPosition()).animals.remove(animal);
            map.revaluateEmptyCellsInformation(animal.getPosition(), newPosition);
            animal.setMoveDirection(move);
            animal.move();
            Cell.manageCellsBreedMap(oldPosition, newPosition,map);
        }
    }

    /*
    * The method checks, if there are more than one animal on the cell. If so,
    * it finds animal with the biggest quantity of energy. If more animals have the same
    * quantity, then divides the grass energy between them
    * */
    public void animalsEatGrass() {
        for(Animal animal: map.getAnimals()){
            if(map.grassesMap.get(animal.getPosition()) != null) {
                if (map.getCell().get(animal.getPosition()).animals.size() > 1) {
                    LinkedList<Animal> animalsOnTheCell = map.getCell().get(animal.getPosition()).animals;
                    int maxEnergyOnCell = 0;
                    LinkedList<Animal> animalsWithMaxEnergy = new LinkedList<>();
                    Iterator<Animal> animalsOnTheCellIterator = animalsOnTheCell.listIterator();
                    //first, find the biggest quantity of energy between animals on the cell
                    while (animalsOnTheCellIterator.hasNext()) {
                        Animal innerAnimal = animalsOnTheCellIterator.next();
                        if (innerAnimal.getEnergy() > maxEnergyOnCell) maxEnergyOnCell = innerAnimal.getEnergy();
                    }
                    //then find the animals with the biggest quantity of energy
                    animalsOnTheCellIterator = animalsOnTheCell.listIterator();
                    while (animalsOnTheCellIterator.hasNext()) {
                        Animal innerAnimal = animalsOnTheCellIterator.next();
                        if (innerAnimal.getEnergy() == maxEnergyOnCell) animalsWithMaxEnergy.push(innerAnimal);
                    }
                    Iterator<Animal> animalsWithMaxEnergyIterator = animalsWithMaxEnergy.listIterator();
                    while (animalsWithMaxEnergyIterator.hasNext()) {
                        Animal innerAnimal = animalsWithMaxEnergyIterator.next();
                        innerAnimal.eat(map.cellMap.get(innerAnimal.getPosition()).grass, grassEnergy / animalsWithMaxEnergy.size() + 1, this.map);
                    }
                } else animal.eat(map.cellMap.get(animal.getPosition()).grass, grassEnergy, this.map);
            }
        }
    }

    public void growGrass() {
        for(int i = 0; i < this.map.getParameters().getDailyJungleGrassGrow(); i++){
            this.map.getJungle().placeWorldElementInBiomeInRandomPlace(new Grass(new Vector2d(0,0), this.map));
        }
        for(int i = 0; i < this.map.getParameters().getDailySavannahGrassGrow(); i++){
            this.map.getSavannah().placeWorldElementInBiomeInRandomPlace(new Grass(new Vector2d(0,0), this.map));
        }

    }

    public void initSimulation(int animalInitNumber) {
        Random rand = new Random();
        for(int i = 0; i < animalInitNumber; i++){
            int[] genome = this.map.getGenomeGenerator().generateRandomGenome();
            this.map.getJungle().placeWorldElementInBiomeInRandomPlace(new Animal(this.map, new Vector2d(rand.nextInt(cellsWidth),
                    rand.nextInt(cellsHeight)), maxEnergy, genome));
        }
    }

    public void initSimulation(LinkedList<Animal> animals){
        for(Animal animal: animals){
            this.map.getJungle().placeWorldElementInBiome(animal);
        }
    }

    public void loseEnergy() {
        for(Animal animal: map.getAnimals()){
            animal.setEnergy(animal.getEnergy() - this.moveEnergy);
            if(animal.getEnergy() < 0) animal.setEnergy(0);
        }
    }

    public void removeDeadAnimals() {
        Iterator<Animal> animalIterator = map.getAnimals().listIterator();
        while(animalIterator.hasNext()){
            Animal animal = animalIterator.next();
            if(animal.getEnergy() == 0){
                if(map.getClickedAnimal() != null && map.getClickedAnimal().equals(animal)){
                    map.setClickedAnimal(null);
                    if(!map.isHistoryFollowed){
                        map.getClickObserver().getEventObserver().animalUnclicked();
                    }
                    else{
                        map.getClickObserver().getEventObserver().showDeathInformation();
                        map.getStatisticsCollector().setClickedAnimalDeathDay();
                    }
                }
                StatisticsCollector stats = map.getStatisticsCollector();
                stats.setTotalLifeLength(stats.getTotalLifeLength() + map.getDay() - animal.getDayOfBirth());
                stats.setDeadAnimals(stats.getDeadAnimals()+1);
                map.getCell().get(animal.getPosition()).animals.remove(animal);
                int oldX = animal.getPosition().getX();
                int oldY = animal.getPosition().getY();
                //new position is null, because the animal is beeing removed
                map.revaluateEmptyCellsInformation(animal.getPosition(), null);
                Cell.manageCellsBreedMap(new Vector2d(oldX, oldY),null, map);
                animalIterator.remove();
            }
        }
    }

    public void breeding() {
        LinkedList<Vector2d> newAnimalPositions = new LinkedList<>();
        for (Cell cell: map.cellsReadyToBreed){
            Vector2d newPosition = cell.breed();
            if(newPosition != null) newAnimalPositions.push(newPosition);
        }
        for(Vector2d newAnimalPosition: newAnimalPositions){
            Cell.manageCellsBreedMap(null, newAnimalPosition, map);
        }
    }
}
