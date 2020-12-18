import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Stores methods to handle events on controls
 * */

public class EventObserver {
    private GraphicPanel graphicPanel;
    private World map;

    //handle click on button "genom"
    public void showAnimalsWithDominateGenoms(){
        if(!this.graphicPanel.grassField.isDominateGenomViewSelected()){
            this.graphicPanel.grassField.setDominateGenomViewSelected(true);
            this.graphicPanel.getVisualizer().drawAnimals((Graphics2D) this.graphicPanel.getGraphics());
            this.map.dominateGenomAnimalsButton.setText("ukryj");
        }
        else{
            this.graphicPanel.grassField.setDominateGenomViewSelected(false);
            this.graphicPanel.getVisualizer().drawAnimals((Graphics2D) this.graphicPanel.getGraphics());
            this.map.dominateGenomAnimalsButton.setText("genom");
        }

    }

    //handle click on button "generuj do pliku"
    public void generateStatisticsToFile(){
        int fileNumber = 1;
        String path = "statisticsFile";
        File statisticsFile = new File(path+".txt");
        try{
            while(!statisticsFile.createNewFile()){
                statisticsFile = new File(path+fileNumber+".txt");
                fileNumber+=1;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        LinkedList<DailyStatistics> dailyStatisticsList = this.graphicPanel.grassField.getStatisticsCollector().getAllDailyStatistics();
        int averageAnimalNumber = 0;
        int averageGrassNumber = 0;
        int averageAnimalEnergy = 0;
        int averageAnimalLifeLength = 0;
        double averageChildNumber = 0;
        Genome dominatedGenome;
        HashMap<Genome, Integer> numberOfGenomes = new HashMap<>();
        for(DailyStatistics dailyStatistics: dailyStatisticsList){
            averageAnimalEnergy += dailyStatistics.getAverageEnergy();
            averageAnimalLifeLength += dailyStatistics.getAverageLifeLength();
            averageAnimalNumber += dailyStatistics.getAnimalNumber();
            averageChildNumber += dailyStatistics.getAverageChildNumber();
            averageGrassNumber += dailyStatistics.getGrassNumber();
            if(numberOfGenomes.get(dailyStatistics.getDominateGenome()) == null) numberOfGenomes.put(dailyStatistics.getDominateGenome(), 0);
            numberOfGenomes.put(dailyStatistics.getDominateGenome(), numberOfGenomes.get(dailyStatistics.getDominateGenome())+1);
        }
        dominatedGenome = Collections.max(numberOfGenomes.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        try{
            FileWriter fileWriter =  new FileWriter(statisticsFile);
            fileWriter.write("średnia ilość zwierząt: "+averageAnimalNumber/dailyStatisticsList.size()+"\n");
            fileWriter.write("średnia ilość trawy: "+averageGrassNumber/dailyStatisticsList.size()+"\n");
            fileWriter.write("średnia ilość energi zwierząt: "+averageAnimalEnergy/dailyStatisticsList.size()+"\n");
            fileWriter.write("średnia długość życia zwierząt w dniach: "+averageAnimalLifeLength/dailyStatisticsList.size()+"\n");
            fileWriter.write("średnia ilość posiadanych dzieci w każdym dniu: "+ averageChildNumber/dailyStatisticsList.size()+"\n");
            fileWriter.write("najczęściej dominujący genom: "+dominatedGenome+"\n");
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clickedStart(){
        this.map.dominateGenomAnimalsButton.setText("genom");
        this.graphicPanel.grassField.setDominateGenomViewSelected(false);
        this.map.buttonContainer.remove(this.map.dominateGenomAnimalsButton);
        this.map.buttonContainer.repaint();
        this.map.validate();
        graphicPanel.getTimer().start();
    }

    public void clickedPause(){
        graphicPanel.getTimer().stop();
        this.map.buttonContainer.add(this.map.dominateGenomAnimalsButton, 2);
        this.map.buttonContainer.repaint();
        this.map.validate();
    }

    public void changeFPS(int value){
        if(value == 0) graphicPanel.getTimer().setDelay(0);
        else graphicPanel.getTimer().setDelay((int)(Math.pow(2,value)));
    }

    public void animalClicked(Animal animal){
        graphicPanel.grassField.getStatisticsCollector().setClickedAnimal(animal);
        map.add(map.oneAnimalStatistics);
        map.repaint();
        map.revalidate();
    }

    public void animalUnclicked(){
        this.stopFollow();
        map.remove(map.oneAnimalStatistics);
        map.repaint();
        map.revalidate();
    }

    EventObserver(GraphicPanel graphicPanel, World map){
        this.map = map;
        this.graphicPanel = graphicPanel;
    }

    public void startFollow(){
        this.graphicPanel.grassField.isHistoryFollowed = true;
        this.graphicPanel.grassField.getStatisticsCollector().updateAllStatistics();
        this.map.oneAnimalStatistics.add(map.followedAnimalContainer);
        this.map.oneAnimalStatistics.add(map.stopFollowButton);
        map.oneAnimalStatistics.repaint();
        map.revalidate();
    }

    public void stopFollow(){
        LinkedList<Animal> animals = this.graphicPanel.grassField.animalPositions;
        for(Animal animal: animals){
            animal.setChild(false);
            animal.setDescendant(false);
        }
        this.map.followedAnimalContainer.remove(map.followedAnimalDeathDayNumberContainer);
        this.graphicPanel.grassField.isHistoryFollowed = false;
        this.map.oneAnimalStatistics.remove(map.followedAnimalContainer);
        this.map.oneAnimalStatistics.remove(map.stopFollowButton);
        map.oneAnimalStatistics.repaint();
        map.revalidate();
    }

    //evaluates when the animal was followed and died
    public void showDeathInformation(){
        this.map.followedAnimalContainer.add(map.followedAnimalDeathDayNumberContainer);
        this.map.followedAnimalContainer.repaint();
        this.map.revalidate();
    }
}
