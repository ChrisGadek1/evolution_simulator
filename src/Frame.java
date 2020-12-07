import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        super("Evolution Simulator");
        int windowSize = 600;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension((int)(windowSize*1.5), windowSize));
        setLocation(200,200);
        JPanel graphics1 = new GraphicPanel(windowSize-100,0.3, 40, 50, 20, 20, 1);
        JPanel container = new JPanel();
        container.add(graphics1);
        this.add(container);
    }
}
