import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    public Frame() {
        super("Evolution Simulator");
        int windowSize = 750;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        setLocation(200,200);
        JPanel graphics1 = new GraphicPanel(windowSize-100,0.3, 40, 50, 20, 20, 1);
        graphics1.setPreferredSize(new Dimension(graphics1.getWidth(), graphics1.getHeight()));
        setSize(new Dimension(graphics1.getWidth()+100, graphics1.getHeight()+200));
        EventObserver eventObserver = new EventObserver((GraphicPanel) graphics1);

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new FlowLayout());

        JPanel mapContainer = new JPanel();
        mapContainer.setLayout(new BoxLayout(mapContainer, BoxLayout.Y_AXIS));
        mapContainer.add(graphics1);
        JButton startButton = new JButton("start");
        JButton pauseButton = new JButton("pause");

        JPanel sliderContainer = new JPanel();
        sliderContainer.setLayout(new BoxLayout(sliderContainer, BoxLayout.Y_AXIS));
        sliderContainer.setPreferredSize(new Dimension(100, 30));

        JSlider slider = new JSlider(JSlider.HORIZONTAL,0, 10,5);
        slider.setPreferredSize(new Dimension(100, 10));
        sliderContainer.add(slider);

        JLabel sliderLabel = new JLabel();
        sliderLabel.setText("Change the FPS of the animation");
        sliderContainer.add(sliderLabel);

        //add listeners to Buttons

        startButton.addActionListener(e -> eventObserver.clickedStart());

        pauseButton.addActionListener(e -> eventObserver.clickedPause());

        slider.addChangeListener(e -> eventObserver.changeFPS(slider.getValue()));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        buttonContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonContainer.setAlignmentX(-10);
        buttonContainer.add(startButton);
        buttonContainer.add(pauseButton);
        buttonContainer.add(sliderContainer);
        mapContainer.add(buttonContainer);
        mainContainer.add(mapContainer);
        mainContainer.add(mapContainer);
        this.add(mainContainer);
    }
}
