import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;


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

    private int height;
    private int width;
    private int grassEnergy;
    private int maxEnergy;
    private SimulationEngine engine;
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


    public GraphicPanel(int windowSize, double jungleRatio, int grassEnergy, int maxEnergy, int cellsWidth, int cellsHeight, int moveEnergy){
        if(cellsWidth > cellsHeight){
            int rawHeight = (int)((double)(cellsHeight)/(double)(cellsWidth)*windowSize);
            this.height = rawHeight - (rawHeight % cellsHeight);
            this.width = windowSize - (windowSize % cellsWidth);
        }
        else{
            int rawHeight = (int)((double)(cellsWidth)/(double)(cellsHeight)*windowSize);
            this.height = windowSize - (windowSize % cellsHeight);
            this.width = rawHeight - (rawHeight % cellsWidth);
        }
        this.grassEnergy = grassEnergy;
        this.maxEnergy = maxEnergy;
        this.moveEnergy = moveEnergy;
        this.cellSize = width/cellsWidth;
        this.jungleRatio = jungleRatio/(jungleRatio+1);
        this.grassField = new GrassField(10, cellsWidth, cellsHeight, this.jungleRatio);
        this.engine = new SimulationEngine(grassField, grassEnergy, maxEnergy, cellsWidth, cellsHeight, moveEnergy);
        setPreferredSize(new Dimension(Math.max(this.width, 400),this.height));
        this.engine.initSimulation(30);
        this.visualizer = new MapVisualizer(this.grassField, this.width, this.height);
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
                        if(!animal.isClicked() && grassField.getClickedAnimal() == null){
                            animal.setClicked(true);
                            grassField.getClickObserver().getEventObserver().animalClicked(animal);
                            grassField.getStatisticsCollector().setCurrentAnimalGenome();
                            grassField.setClickedAnimal(animal);
                        }
                        else if(animal.isClicked()){
                            animal.setClicked(false);
                            grassField.getClickObserver().getEventObserver().animalUnclicked();
                            grassField.setClickedAnimal(null);
                        }

                        visualizer.drawAnimal((Graphics2D) getGraphics(), animal);
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
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
        }
        canRepaint = false;
    }

}
