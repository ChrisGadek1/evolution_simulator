import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JPanel mapContainer, mainContainer, oneAnimalStatistics,followedAnimalNumberOfChildrenContainer,
            followedAnimalNumberOfDescendantsContainer, followedAnimalContainer, followedAnimalDeathDayNumberContainer, buttonContainer
            ;

    JButton stopFollowButton, dominateGenomAnimalsButton;
    public Frame() {
        super("Evolution Simulator");
        int windowSize = 650;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setLocation(200,200);
        JPanel graphics1 = new GraphicPanel(windowSize-100,0.3, 40, 50, 21, 20, 1);
        graphics1.setPreferredSize(new Dimension(graphics1.getWidth(), graphics1.getHeight()));
        setSize(new Dimension(graphics1.getWidth()+150, graphics1.getHeight()+250));
        EventObserver eventObserver = new EventObserver((GraphicPanel) graphics1, this);
        ClickOnPanelObserver clickObserver = new ClickOnPanelObserver(eventObserver);
        ((GraphicPanel) graphics1).grassField.setClickObserver(clickObserver);

        this.dominateGenomAnimalsButton = new JButton("genom");

        this.mainContainer = new JPanel();
        mainContainer.setLayout(new FlowLayout());

        this.mapContainer = new JPanel();
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
        sliderLabel.setText("Change the delay between frames");
        sliderContainer.add(sliderLabel);

        this.oneAnimalStatistics = new JPanel();
        oneAnimalStatistics.setLayout(new BoxLayout(oneAnimalStatistics, BoxLayout.Y_AXIS));
        JPanel mainGenomeOfOneAnimalContainer = new JPanel();
        JLabel mainGenomeOfOneAnimalLabel = new JLabel("genom:");
        JLabel mainGenomeOfOneAnimalValue = new JLabel();
        mainGenomeOfOneAnimalContainer.setLayout(new BoxLayout(mainGenomeOfOneAnimalContainer, BoxLayout.Y_AXIS));
        JButton followButton = new JButton("śledź historię");
        oneAnimalStatistics.add(mainGenomeOfOneAnimalContainer);
        mainGenomeOfOneAnimalContainer.add(mainGenomeOfOneAnimalLabel);
        mainGenomeOfOneAnimalContainer.add(mainGenomeOfOneAnimalValue);
        mainGenomeOfOneAnimalContainer.add(followButton);

        this.followedAnimalContainer = new JPanel();
        this.followedAnimalContainer.setLayout(new BoxLayout(followedAnimalContainer, BoxLayout.Y_AXIS));

        this.followedAnimalNumberOfChildrenContainer = new JPanel();
        JLabel followedAnimalNumberOfChildrenDesc = new JLabel("ilość dzieci:");
        JLabel followedAnimalNumberOfChildrenValue = new JLabel();

        this.followedAnimalDeathDayNumberContainer = new JPanel();
        followedAnimalDeathDayNumberContainer.setLayout(new FlowLayout());
        JLabel followedAnimalDeathDayNumberDesc = new JLabel("zwierzę zmarło w epoce:");
        JLabel followedAnimalDeathDayNumberValue = new JLabel();

        followedAnimalDeathDayNumberContainer.add(followedAnimalDeathDayNumberDesc);
        followedAnimalDeathDayNumberContainer.add(followedAnimalDeathDayNumberValue);

        followedAnimalNumberOfChildrenContainer.setLayout(new FlowLayout());
        followedAnimalNumberOfChildrenContainer.add(followedAnimalNumberOfChildrenDesc);
        followedAnimalNumberOfChildrenContainer.add(followedAnimalNumberOfChildrenValue);

        this.followedAnimalNumberOfDescendantsContainer = new JPanel();
        JLabel followedAnimalNumberOfDescendantsDesc = new JLabel("ilość potomków");
        JLabel followedAnimalNumberOfDescendantsValue = new JLabel();
        this.stopFollowButton = new JButton("Zakończ śledzenie");

        followedAnimalNumberOfDescendantsContainer.setLayout(new FlowLayout());
        followedAnimalNumberOfDescendantsContainer.add(followedAnimalNumberOfDescendantsDesc);
        followedAnimalNumberOfDescendantsContainer.add(followedAnimalNumberOfDescendantsValue);

        this.followedAnimalContainer.add(followedAnimalNumberOfChildrenContainer);
        this.followedAnimalContainer.add(followedAnimalNumberOfDescendantsContainer);

        //add listeners to Buttons

        startButton.addActionListener(e -> eventObserver.clickedStart());

        pauseButton.addActionListener(e -> eventObserver.clickedPause());

        slider.addChangeListener(e -> eventObserver.changeFPS(slider.getValue()));

        followButton.addActionListener(e -> {
            ((GraphicPanel) graphics1).grassField.getStatisticsCollector().updateAllStatistics();
            eventObserver.startFollow();
        });

        this.stopFollowButton.addActionListener(e -> {
            eventObserver.stopFollow();
            if(((GraphicPanel) graphics1).grassField.getClickedAnimal() == null){
                ((GraphicPanel) graphics1).grassField.getClickObserver().getEventObserver().animalUnclicked();
            }
        });

        this.dominateGenomAnimalsButton.addActionListener(e -> {
            eventObserver.showAnimalsWithDominateGenoms();
        });


        this.buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        buttonContainer.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonContainer.setAlignmentX(-10);
        buttonContainer.add(startButton);
        buttonContainer.add(dominateGenomAnimalsButton);
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

        JPanel mainGenomeContainer = new JPanel();
        JLabel mainGenomeLabel = new JLabel("dominujący genotyp");
        JLabel mainGenomeHelpLabel = new JLabel("{numer genu = ilość genu}");
        JLabel mainGenomeValue = new JLabel();

        statisticsContainer.setLayout(new BoxLayout(statisticsContainer, BoxLayout.Y_AXIS));
        statisticsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        animalStatisticsContainer.setLayout(new FlowLayout());
        grassStatisticsContainer.setLayout(new FlowLayout());
        averageAnimalEnergyContainer.setLayout(new FlowLayout());
        averageLifeLengthContainer.setLayout(new FlowLayout());
        mainGenomeContainer.setLayout(new FlowLayout());

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

        mainGenomeContainer.add(mainGenomeLabel);
        mainGenomeContainer.add(mainGenomeValue);
        mainGenomeContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainGenomeHelpLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        statisticsContainer.add(animalStatisticsContainer);
        statisticsContainer.add(grassStatisticsContainer);
        statisticsContainer.add(averageAnimalEnergyContainer);
        statisticsContainer.add(averageLifeLengthContainer);
        statisticsContainer.add(mainGenomeContainer);
        statisticsContainer.add(mainGenomeHelpLabel);
        statisticsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAnimalNumberLabel(animalNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setGrassNumberLabel(grassNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageAnimalEnergyLabel(averageAnimalEnergyNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageLifeLength(averageLifeLengthNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setMainGenomeLabel(mainGenomeValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setOneAnimalGenomeLabel(mainGenomeOfOneAnimalValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setNumberOfChildrenLabel(followedAnimalNumberOfChildrenValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setNumberOfDescendantsLabel(followedAnimalNumberOfDescendantsValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setDeathDayLabel(followedAnimalDeathDayNumberValue);


        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().updateAllStatistics();


        mapContainer.add(buttonContainer);
        mapContainer.add(statisticsContainer);
        mainContainer.add(mapContainer);
        this.add(mainContainer);
    }
}
