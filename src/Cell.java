import java.util.LinkedList;
import java.util.Objects;

public class Cell {
    public LinkedList<Animal> animals;

    int x;
    int y;
    Grass grass;
    boolean jungle;
    Cell(LinkedList<Animal> animals, Grass grass, boolean jungle, int x, int y){
        this.animals = animals;
        this.grass = grass;
        this.jungle = jungle;
        this.x = x;
        this.y = y;
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
}
