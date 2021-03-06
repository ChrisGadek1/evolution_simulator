import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

/**
 * This object represents Swing panel, where world elements will be drawn
 * */

public class GraphicPanel extends JPanel {
    private double jungleRatio;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    //height and width are initiated as number of pixels
    private int height;
    private int width;
    private int cellsWidth;
    private int cellsHeight;
    private int grassEnergy;
    private int maxEnergy;
    private SimulationEngine engine;
    private int windowSize;
    //cellSize is a size of one cell in pixels
    private int cellSize;
    private int moveEnergy;
    private boolean canRepaint = false;

    GrassField grassField;
    MapVisualizer visualizer;

    public Timer getTimer() {
        return tm;
    }

    private Timer tm = new Timer(30, e -> {
        canRepaint = true;
        repaint();
    });

    public void setMapSizeInPixels(){
        if(this.cellsWidth > this.cellsHeight){
            int rawHeight = (int)((double)(this.cellsHeight)/(double)(this.cellsWidth)*this.windowSize);
            this.height = rawHeight - (rawHeight % this.cellsHeight);
            this.width = this.windowSize - (this.windowSize % this.cellsWidth);
        }
        else{
            int rawHeight = (int)((double)(this.cellsWidth)/(double)(this.cellsHeight)*this.windowSize);
            this.height = this.windowSize - (this.windowSize % this.cellsHeight);
            this.width = rawHeight - (rawHeight % this.cellsWidth);
        }
        this.cellSize = this.width/this.cellsWidth;
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    public GraphicPanel(int windowSize, InitialParameters parameters){
        int cellsWidth = parameters.getWidth();
        int cellsHeight = parameters.getHeight();
        this.cellsWidth = cellsWidth;
        this.cellsHeight = cellsHeight;
        this.windowSize = windowSize;
        double jungleRatio = parameters.getJungleRatio();
        //computes the optimal length and height depending on initial parameters
        setMapSizeInPixels();
        this.grassEnergy = parameters.getGrassEnergy();
        this.maxEnergy = parameters.getMaxEnergy();
        this.moveEnergy = parameters.getMoveEnergy();
        this.jungleRatio = jungleRatio/(jungleRatio+1);
        this.grassField = new GrassField(10, cellsWidth, cellsHeight, this.jungleRatio, parameters);
        this.engine = new SimulationEngine(grassField, grassEnergy, maxEnergy, cellsWidth, cellsHeight, moveEnergy);
        this.visualizer = new MapVisualizer(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!getTimer().isRunning()){
                    int x = e.getX()/cellSize;
                    int y = e.getY()/cellSize;
                    Cell clickedCell = grassField.cellMap.get(new Vector2d(x,y));
                    if(clickedCell != null && clickedCell.animals.size() > 0){
                        Animal animal = clickedCell.animals.get(clickedCell.animals.size()-1);

                        //if the user clicked on the animal and there wasn't chosen animal
                        if(!animal.isClicked() && grassField.getClickedAnimal() == null){
                            animal.setClicked(true);
                            grassField.getClickObserver().getEventObserver().animalClicked(animal);
                            grassField.getStatisticsCollector().setCurrentAnimalGenome();
                            grassField.setClickedAnimal(animal);
                        }
                        //if the user clicked on the already clicked animal to unclick it
                        else if(animal.isClicked()){
                            animal.setClicked(false);
                            grassField.getClickObserver().getEventObserver().animalUnclicked();
                            grassField.setClickedAnimal(null);
                        }
                        //otherwise click doesn't provide any actions
                        //next line recolors the clicked animal
                        visualizer.drawAnimal((Graphics2D) getGraphics(), animal);
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        //object allows to use engine only when timer is running
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        if(this.getTimer().isRunning() && this.canRepaint) {
            this.engine.moveAnimals();
            this.grassField.setDay(this.grassField.getDay() + 1);
            this.engine.removeDeadAnimals();
            this.engine.animalsEatGrass();
            this.engine.loseEnergy();
            this.engine.growGrass();
        }
        this.visualizer.drawBiomes(g2d);
        this.visualizer.drawGrass(g2d);
        this.visualizer.drawAnimals(g2d);
        if(this.getTimer().isRunning() && this.canRepaint){
            this.engine.breeding();
            this.grassField.getStatisticsCollector().updateAllStatistics();
            this.grassField.getStatisticsCollector().saveStatistics();
        }
        canRepaint = false;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getCellsHeight() {
        return cellsHeight;
    }

    public int getCellsWidth() {
        return cellsWidth;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    public MapVisualizer getVisualizer() {
        return visualizer;
    }
}
