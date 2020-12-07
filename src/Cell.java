import java.util.LinkedList;

public class Cell {
    public LinkedList<Animal> animals = new LinkedList<>();

    Grass grass;
    boolean jungle;
    Cell(LinkedList<Animal> animals, Grass grass, boolean jungle){
        this.animals = animals;
        this.grass = grass;
        this.jungle = jungle;
    }
}
