public class EventObserver {
    private GraphicPanel graphicPanel;

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


    EventObserver(GraphicPanel graphicPanel){
        this.graphicPanel = graphicPanel;
    }
}
