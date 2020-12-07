import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel{
    public GraphPanel(){
        setPreferredSize(new Dimension(360,360));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

    }
}
