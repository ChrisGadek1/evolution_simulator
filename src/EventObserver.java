import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EventObserver {
    private GraphicPanel graphicPanel;
    private Frame frame;

    public void showAnimalsWithDominateGenoms(){
        if(!this.graphicPanel.grassField.isDominateGenomViewSelected()){
            this.graphicPanel.grassField.setDominateGenomViewSelected(true);
            this.graphicPanel.getVisualizer().drawAnimals((Graphics2D) this.graphicPanel.getGraphics());
            this.frame.dominateGenomAnimalsButton.setText("ukryj");
        }
        else{
            this.graphicPanel.grassField.setDominateGenomViewSelected(false);
            this.graphicPanel.getVisualizer().drawAnimals((Graphics2D) this.graphicPanel.getGraphics());
            this.frame.dominateGenomAnimalsButton.setText("genom");
        }

    }

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
        this.frame.dominateGenomAnimalsButton.setText("genom");
        this.graphicPanel.grassField.setDominateGenomViewSelected(false);
        this.frame.buttonContainer.remove(this.frame.dominateGenomAnimalsButton);
        this.frame.buttonContainer.repaint();
        this.frame.validate();
        graphicPanel.getTimer().start();
    }

    public void clickedPause(){
        graphicPanel.getTimer().stop();
        this.frame.buttonContainer.add(this.frame.dominateGenomAnimalsButton, 2);
        this.frame.buttonContainer.repaint();
        this.frame.validate();
    }

    public void changeFPS(int value){
        if(value == 0) graphicPanel.getTimer().setDelay(0);
        else graphicPanel.getTimer().setDelay((int)(Math.pow(2,value)));
    }

    public void animalClicked(Animal animal){
        graphicPanel.grassField.getStatisticsCollector().setClickedAnimal(animal);
        frame.mainContainer.add(frame.oneAnimalStatistics);
        frame.mainContainer.repaint();
        frame.revalidate();
    }

    public void animalUnclicked(){
        this.stopFollow();
        frame.mainContainer.remove(frame.oneAnimalStatistics);
        frame.mainContainer.repaint();
        frame.revalidate();
    }

    EventObserver(GraphicPanel graphicPanel, Frame frame){
        this.frame = frame;
        this.graphicPanel = graphicPanel;
    }

    public void startFollow(){
        this.graphicPanel.grassField.isHistoryFollowed = true;
        this.graphicPanel.grassField.getStatisticsCollector().updateAllStatistics();
        this.frame.oneAnimalStatistics.add(frame.followedAnimalContainer);
        this.frame.oneAnimalStatistics.add(frame.stopFollowButton);
        frame.oneAnimalStatistics.repaint();
        frame.revalidate();
    }

    public void stopFollow(){
        LinkedList<Animal> animals = this.graphicPanel.grassField.animalPositions;
        for(Animal animal: animals){
            animal.setChild(false);
            animal.setDescendant(false);
        }
        this.frame.followedAnimalContainer.remove(frame.followedAnimalDeathDayNumberContainer);
        this.graphicPanel.grassField.isHistoryFollowed = false;
        this.frame.oneAnimalStatistics.remove(frame.followedAnimalContainer);
        this.frame.oneAnimalStatistics.remove(frame.stopFollowButton);
        frame.oneAnimalStatistics.repaint();
        frame.revalidate();
    }

    public void showDeathInformation(){
        this.frame.followedAnimalContainer.add(frame.followedAnimalDeathDayNumberContainer);
        this.frame.followedAnimalContainer.repaint();
        this.frame.revalidate();
    }
}
