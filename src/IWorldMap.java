import java.util.LinkedList;
import java.util.Map;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that lab6.Vector2d and lab6.MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */

    /**
     *changes the animal position to a random free position on the map and places it there.
     */

    boolean placeAnimalInRandomPlaceInJungle(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    int[] getBounds();

    Map<Vector2d, Cell> getCell();
    LinkedList<Animal> getAnimals();
    Map<Vector2d, Grass> getGrassesMap();

    Jungle getJungle();
}