import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatisticsCollector {

    public StatisticsCollector(GrassField grassField) {
        this.grassField = grassField;
    }

    private Animal clickedAnimal;
    private GrassField grassField;
    private JLabel animalNumberLabel;
    private JLabel grassNumberLabel;
    private JLabel averageLifeLength;
    private JLabel averageAnimalEnergy;
    private JLabel mainGenomeLabel;
    private JLabel oneAnimalGenomeLabel;
    private JLabel numberOfChildrenLabel;
    private JLabel numberOfDescendantsLabel;
    private JLabel deathDayLabel;
    private JLabel averageChildNumberLabel;
    private int totalLifeLength = 0;
    private int deadAnimals = 0;

    public void setAverageChildNumberLabel(JLabel averageChildNumberLabel) {
        this.averageChildNumberLabel = averageChildNumberLabel;
    }

    public void setNumberOfChildrenLabel(JLabel numberOfChildrenLabel) {
        this.numberOfChildrenLabel = numberOfChildrenLabel;
    }

    public void setNumberOfDescendantsLabel(JLabel numberOfDescendantsLabel) {
        this.numberOfDescendantsLabel = numberOfDescendantsLabel;
    }

    public void setDeathDayLabel(JLabel deathDayLabel) {
        this.deathDayLabel = deathDayLabel;
    }

    public void setClickedAnimal(Animal animal){
        this.clickedAnimal = animal;
    }

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

    public void setOneAnimalGenomeLabel(JLabel oneAnimalGenomeLabel) {
        this.oneAnimalGenomeLabel = oneAnimalGenomeLabel;
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
        if(genome == null) {
            this.mainGenomeLabel.setText("-");
            this.grassField.setMainGenome(null);
        }
        else {
            this.mainGenomeLabel.setText(genome.toString());
            this.grassField.setMainGenome(genome);
        }
    }

    public void setCurrentAnimalGenome(){
        this.oneAnimalGenomeLabel.setText(this.clickedAnimal.getGenome().toString());
    }

    public void setClickedAnimalNumberOfChildren(){
        LinkedList<Animal> animals = this.grassField.animalPositions;
        int childrenNumber = 0;
        for(Animal animal: animals){
            if(animal.isChild()) childrenNumber++;
        }
        this.numberOfChildrenLabel.setText(String.valueOf(childrenNumber));
    }

    public void setClickedAnimalNumberOfDescendants(){
        LinkedList<Animal> animals = this.grassField.animalPositions;
        int descendantsNumber = 0;
        for(Animal animal: animals){
            if(animal.isDescendant()) descendantsNumber++;
        }
        this.numberOfDescendantsLabel.setText(String.valueOf(descendantsNumber));
    }

    public void setClickedAnimalDeathDay(){
        this.deathDayLabel.setText(String.valueOf(this.grassField.getDay()));
    }


    public void setAverageChildNumber(){
        Map<Animal, Integer> childNumber = new HashMap<>();
        LinkedList<Animal> animals = this.grassField.animalPositions;
        for(Animal animal: animals){
            if(animal.getParents() != null){
                Animal parent1 = animal.getParents()[0];
                Animal parent2 = animal.getParents()[1];
                if(childNumber.get(parent1) == null) childNumber.put(parent1, 0);
                if(childNumber.get(parent2) == null) childNumber.put(parent2, 0);
                childNumber.put(parent1, childNumber.get(parent1)+1);
                childNumber.put(parent2, childNumber.get(parent2)+1);
            }
            if(childNumber.get(animal) == null) childNumber.put(animal, 0);
        }
        int totalAnimalNumber = childNumber.size();
        int totalChildNumber = 0;
        for(Integer numberOfChildren: childNumber.values()){
            totalChildNumber += numberOfChildren;
        }
        if(totalAnimalNumber != 0) this.averageChildNumberLabel.setText(String.valueOf((double)(totalChildNumber/totalAnimalNumber)));
        else this.averageChildNumberLabel.setText("-");
    }


    public void updateAllStatistics(){
        this.setCurrentNumberOfGrass();
        this.setCurrentNumberOfAnimals();
        this.setCurrentAverageAnimalEnergy();
        this.setCurrentAverageLifeLength();
        this.setCurrentMainGenome();
        this.setAverageChildNumber();
        if(this.grassField.getClickedAnimal() != null || this.grassField.isHistoryFollowed){
            this.setClickedAnimalNumberOfChildren();
            this.setClickedAnimalNumberOfDescendants();
        }
    }
}
