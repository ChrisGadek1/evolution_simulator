import java.util.LinkedList;

public class EventObserver {
    private GraphicPanel graphicPanel;
    private Frame frame;

    public void clickedStart(){
        graphicPanel.getTimer().start();
    }

    public void clickedPause(){
        graphicPanel.getTimer().stop();
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
