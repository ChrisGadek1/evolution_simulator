import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class GraphicPanel extends JPanel implements ActionListener {
    private double jungleRatio;
    private int height;
    private int width;
    private int grassEnergy;
    private int maxEnergy;
    private SimulationEngine engine;
    private Jungle jungle;
    private int cellSize;
    private int moveEnergy;

    GrassField grassField;
    MapVisualizer visualizer;

    Timer tm = new Timer(5, this);


    public GraphicPanel(int windowSize,
                        double jungleRatio,
                        int grassEnergy,
                        int maxEnergy,
                        int cellsWidth,
                        int cellsHeight,
                        int moveEnergy){

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
        this.jungle = new Jungle((int)(cellsHeight*Math.sqrt(this.jungleRatio)),
                (int)(cellsWidth*Math.sqrt(this.jungleRatio)),
                cellsWidth, cellsHeight, this.cellSize);
        this.grassField = new GrassField(10, cellsWidth, cellsHeight, jungle);
        this.engine = new SimulationEngine(grassField,
                                           grassEnergy,
                                            maxEnergy,
                                            cellsWidth,
                                            cellsHeight,
                                            moveEnergy);

        setPreferredSize(new Dimension(this.width,this.height));
        setBackground(new Color(207, 250, 100));
        this.engine.initSimulation(15);
        this.visualizer = new MapVisualizer(this.grassField, this.width, this.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        this.visualizer.drawJungle(g2d, this.jungle);
        this.engine.removeDeadAnimals();
        this.engine.animalsEatGrass();
        this.engine.loseEnergy();
        this.engine.growGrass();
        this.visualizer.drawGrass(g2d);
        this.visualizer.drawAnimals(g2d);
        this.engine.moveAnimals();
        this.engine.breeding();
        tm.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
