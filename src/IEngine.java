/**
 * The interface responsible for managing the moves of the animals.
 * Assumes that lab6.Vector2d and lab6.MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IEngine {
    /**
     * Move the animal on the map according to the provided move directions. Every
     * n-th direction should be sent to the n-th animal on the map.
     *
     */
    void moveAnimals();

    void animalsEatGrass();

    void growGrass();

    void initSimulation(int animalInitNumber);

    void loseEnergy();

    void removeDeadAnimals();

    void breed();
}