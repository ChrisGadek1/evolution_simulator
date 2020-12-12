import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatisticsCollector {

    public StatisticsCollector(GrassField grassField) {
        this.grassField = grassField;
    }

    private GrassField grassField;
    private JLabel animalNumberLabel;
    private JLabel grassNumberLabel;
    private JLabel averageLifeLength;
    private JLabel averageAnimalEnergy;
    private JLabel mainGenomeLabel;
    private int totalLifeLength = 0;
    private int deadAnimals = 0;

    public int getTotalLifeLength() {
        return totalLifeLength;
    }

    public void setTotalLifeLength(int totalLifeLength) {
        this.totalLifeLength = totalLifeLength;
    }

    public int getDeadAnimals() {
        return deadAnimals;
    }

    public void setDeadAnimals(int deadAnimals) {
        this.deadAnimals = deadAnimals;
    }

    public void setAverageAnimalEnergyLabel(JLabel averageAnimalEnergy) {
        this.averageAnimalEnergy = averageAnimalEnergy;
    }

    public void setAverageLifeLength(JLabel averageLifeLength) {
        this.averageLifeLength = averageLifeLength;
    }

    public void setMainGenomeLabel(JLabel mainGenome) {
        this.mainGenomeLabel = mainGenome;
    }

    public void setAnimalNumberLabel(JLabel animalNumberLabel) {
        this.animalNumberLabel = animalNumberLabel;
    }

    public void setGrassNumberLabel(JLabel grassNumberLabel) {
        this.grassNumberLabel = grassNumberLabel;
    }


    public void setCurrentNumberOfAnimals(){
        this.animalNumberLabel.setText(String.valueOf(this.grassField.animalPositions.size()));
    }

    public void setCurrentNumberOfGrass(){
        this.grassNumberLabel.setText(String.valueOf(this.grassField.grassesMap.size()));
    }

    public void setCurrentAverageAnimalEnergy(){
        int result = 0;
        LinkedList<Animal> mapAnimalList = this.grassField.animalPositions;
        for(Animal animal: mapAnimalList){
            result += animal.getEnergy();
        }
        if(mapAnimalList.size() != 0) this.averageAnimalEnergy.setText(String.valueOf(result/mapAnimalList.size()));
        else this.averageAnimalEnergy.setText("0");
    }

    public void setCurrentAverageLifeLength(){
        if(this.deadAnimals != 0){
            this.averageLifeLength.setText(String.valueOf(this.totalLifeLength/this.deadAnimals));
        }
    }

    public void setCurrentMainGenome(){
        Map<Genome, Integer> numberOfGenoms = new HashMap<>();
        for(Animal animal: this.grassField.animalPositions){
            if(numberOfGenoms.get(animal.getGenome()) == null) numberOfGenoms.put(animal.getGenome(), 0);
            numberOfGenoms.put(animal.getGenome(), numberOfGenoms.get(animal.getGenome())+1);
        }
        Genome genome = null;
        int number = 0;
        for(Genome genomeIterator: numberOfGenoms.keySet()){
            if(genomeIterator == null || numberOfGenoms.get(genomeIterator) > number){
                genome = genomeIterator;
                number = numberOfGenoms.get(genomeIterator);
            }
        }
        if(genome == null) this.mainGenomeLabel.setText("-");
        else this.mainGenomeLabel.setText(genome.toString());
    }
}
