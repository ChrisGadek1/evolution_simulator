import java.util.Comparator;
/*
* Sorts animals descending by their energy
* */


public class AnimalsComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal o1, Animal o2) {
        return  o1.getEnergy() - o2.getEnergy();
    }
}
