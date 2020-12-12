import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        super("Evolution Simulator");
        int windowSize = 650;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);

        setLocation(200,200);
        JPanel graphics1 = new GraphicPanel(windowSize-100,0.3, 40, 50, 12, 12, 1);
        graphics1.setPreferredSize(new Dimension(graphics1.getWidth(), graphics1.getHeight()));
        setSize(new Dimension(graphics1.getWidth()+100, graphics1.getHeight()+250));
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
        sliderLabel.setText("Change the speed of the animation");
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

        JPanel statisticsContainer = new JPanel();

        JPanel animalStatisticsContainer = new JPanel();
        JLabel animalNumberLabel = new JLabel("Ilość zwierząt");
        JLabel animalNumber = new JLabel("");

        JPanel grassStatisticsContainer = new JPanel();
        JLabel grassNumberLabel = new JLabel("Ilość trawy");
        JLabel grassNumber = new JLabel("");

        JPanel averageAnimalEnergyContainer = new JPanel();
        JLabel averageAnimalEnergyLabel = new JLabel("średnia ilość energi");
        JLabel averageAnimalEnergyNumber = new JLabel();

        JPanel averageLifeLengthContainer = new JPanel();
        JLabel averageLifeLengthLabel = new JLabel("średnia długość życia");
        JLabel averageLifeLengthNumber = new JLabel("-");

        statisticsContainer.setLayout(new BoxLayout(statisticsContainer, BoxLayout.Y_AXIS));
        statisticsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        animalStatisticsContainer.setLayout(new FlowLayout());
        grassStatisticsContainer.setLayout(new FlowLayout());
        averageAnimalEnergyContainer.setLayout(new FlowLayout());
        averageLifeLengthContainer.setLayout(new FlowLayout());

        animalStatisticsContainer.add(animalNumberLabel);
        animalStatisticsContainer.add(animalNumber);
        animalStatisticsContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

        grassStatisticsContainer.add(grassNumberLabel);
        grassStatisticsContainer.add(grassNumber);
        grassStatisticsContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

        averageAnimalEnergyContainer.add(averageAnimalEnergyLabel);
        averageAnimalEnergyContainer.add(averageAnimalEnergyNumber);
        averageAnimalEnergyContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

        averageLifeLengthContainer.add(averageLifeLengthLabel);
        averageLifeLengthContainer.add(averageLifeLengthNumber);
        averageLifeLengthContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

        statisticsContainer.add(animalStatisticsContainer);
        statisticsContainer.add(grassStatisticsContainer);
        statisticsContainer.add(averageAnimalEnergyContainer);
        statisticsContainer.add(averageLifeLengthContainer);
        statisticsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAnimalNumberLabel(animalNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setGrassNumberLabel(grassNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageAnimalEnergyLabel(averageAnimalEnergyNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageLifeLength(averageLifeLengthNumber);


        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setCurrentNumberOfGrass();
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setCurrentNumberOfAnimals();
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setCurrentAverageAnimalEnergy();
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setCurrentAverageLifeLength();

        mapContainer.add(buttonContainer);
        mapContainer.add(statisticsContainer);
        mainContainer.add(mapContainer);
        this.add(mainContainer);
    }
}
