public class DailyStatistics {
    private int animalNumber;
    private int grassNumber;
    private int averageEnergy;
    private int averageLifeLength;
    private double averageChildNumber;
    private Genome dominateGenome;

    public DailyStatistics(int animalNumber, int grassNumber, int averageEnergy, int averageLifeLength, double averageChildNumber, Genome dominateGenome) {
        this.animalNumber = animalNumber;
        this.grassNumber = grassNumber;
        this.averageEnergy = averageEnergy;
        this.averageLifeLength = averageLifeLength;
        this.averageChildNumber = averageChildNumber;
        this.dominateGenome = dominateGenome;
    }

    public int getAnimalNumber() {
        return animalNumber;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    public int getAverageEnergy() {
        return averageEnergy;
    }

    public int getAverageLifeLength() {
        return averageLifeLength;
    }

    public double getAverageChildNumber() {
        return averageChildNumber;
    }

    public Genome getDominateGenome() {
        return dominateGenome;
    }
}
