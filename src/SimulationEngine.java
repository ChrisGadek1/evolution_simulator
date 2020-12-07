
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class SimulationEngine implements IEngine{

    GrassField map;
    int grassEnergy;
    int maxEnergy;
    int cellsWidth;
    int cellsHeight;
    int moveEnergy;
    Random rand;

    public SimulationEngine(GrassField map,
                            int grassEnergy,
                            int maxEnergy,
                            int cellsWidth,
                            int cellsHeight,
                            int moveEnergy){
        this.map = map;
        this.grassEnergy = grassEnergy;
        this.maxEnergy = maxEnergy;
        this.cellsWidth = cellsWidth;
        this.cellsHeight = cellsHeight;
        this.moveEnergy = moveEnergy;
        rand = new Random();
    }

    @Override
    public void moveAnimals(){
        Iterator<Animal> animalPositionItr = map.animalPositions.listIterator();
        while(animalPositionItr.hasNext()){
            Animal animal = animalPositionItr.next();
            MoveDirection move = MoveDirection.getRandomMove();
            Vector2d newPosition = animal.getPosition().add(move.moveToVector());
            Vector2d oldPosition = new Vector2d(animal.getPosition());
            if(map.canMoveTo(newPosition)){
                map.getCell().get(newPosition).animals.add(animal);
                map.getCell().get(animal.getPosition()).animals.remove(animal);
                int oldX = animal.getPosition().getX();
                int oldY = animal.getPosition().getY();
                int newX = newPosition.getX();
                int newY = newPosition.getY();
                map.revaluateEmptyCellsInformation(animal.getPosition(), newPosition,map.getJungle().isCellCoordInJungle(oldX,oldY)
                        ,map.getJungle().isCellCoordInJungle(newX,newY));
                animal.move(move);
                Cell.manageCellsBreedMap(oldPosition, newPosition,map);
            }
        }
    }

    @Override
    public void animalsEatGrass() {
        for(Animal animal: map.getAnimals()){
            if(map.grassesMap.get(animal.getPosition()) != null) {
                if (map.getCell().get(animal.getPosition()).animals.size() > 1) {
                    LinkedList<Animal> animalsOnTheCell = map.getCell().get(animal.getPosition()).animals;
                    int maxEnergyOnCell = 0;
                    LinkedList<Animal> animalsWithMaxEnergy = new LinkedList<>();
                    Iterator<Animal> animalsOnTheCellIterator = animalsOnTheCell.listIterator();
                    while (animalsOnTheCellIterator.hasNext()) {
                        Animal innerAnimal = animalsOnTheCellIterator.next();
                        if (innerAnimal.getEnergy() > maxEnergyOnCell) maxEnergyOnCell = innerAnimal.getEnergy();
                    }
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

    @Override
    public void growGrass() {
        this.map.addGrassToRandomPlaceInSavannah(new Grass(new Vector2d(0,0)));
        this.map.addGrassToRandomPlaceInJungle(new Grass(new Vector2d(0,0)));
    }

    @Override
    public void initSimulation(int animalInitNumber) {
        Random rand = new Random();
        for(int i = 0; i < animalInitNumber; i++){
            this.map.placeAnimalInRandomPlaceInJungle(new Animal(this.map, new Vector2d(rand.nextInt(cellsWidth),
                    rand.nextInt(cellsHeight)), maxEnergy));
        }
    }

    @Override
    public void loseEnergy() {
        for(Animal animal: map.getAnimals()){
            animal.setEnergy(animal.getEnergy() - this.moveEnergy);
            if(animal.getEnergy() < 0) animal.setEnergy(0);
        }
    }

    @Override
    public void removeDeadAnimals() {
        Iterator<Animal> animalIterator = map.getAnimals().listIterator();
        while(animalIterator.hasNext()){
            Animal animal = animalIterator.next();
            if(animal.getEnergy() == 0){
                map.getCell().get(animal.getPosition()).animals.remove(animal);
                int oldX = animal.getPosition().getX();
                int oldY = animal.getPosition().getY();
                map.revaluateEmptyCellsInformation(animal.getPosition(), null, map.getJungle().isCellCoordInJungle(oldX,oldY),false);
                Cell.manageCellsBreedMap(new Vector2d(oldX, oldY),null, map);
                animalIterator.remove();
            }
        }

    }

    @Override
    public void breeding() {
        for (Cell cell: map.cellsReadyToBreed){
            cell.breed();
        }
    }
}
