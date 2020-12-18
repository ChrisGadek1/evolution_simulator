import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This object represents one independent simulation environment
 * */

public class World extends JPanel{

    JPanel mapContainer, oneAnimalStatistics,followedAnimalNumberOfChildrenContainer,
            followedAnimalNumberOfDescendantsContainer, followedAnimalContainer, followedAnimalDeathDayNumberContainer, buttonContainer,graphics1;
    JButton stopFollowButton, dominateGenomAnimalsButton;

    InitialParameters parameters;

    public World(){
        parameters = null;

        /*
        * Used the external library GSon from Google to parse JSON to Java object
        * */
        try{
            Gson gson = new Gson();
            String jsonContent = Files.readString(Paths.get("parameters.json"), StandardCharsets.UTF_8);
            parameters = gson.fromJson(jsonContent, InitialParameters.class);
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int windowSize = 600;
        this.graphics1 = new GraphicPanel(windowSize-100,parameters);
        graphics1.setPreferredSize(new Dimension(graphics1.getWidth(), graphics1.getHeight()));

        EventObserver eventObserver = new EventObserver((GraphicPanel) graphics1, this);
        ClickOnPanelObserver clickObserver = new ClickOnPanelObserver(eventObserver);
        ((GraphicPanel) graphics1).grassField.setClickObserver(clickObserver);
        JButton generateStatisticsToFileButton = new JButton("generuj do pliku");

        this.dominateGenomAnimalsButton = new JButton("genom");

        this.mapContainer = new JPanel();
        mapContainer.setLayout(new BoxLayout(mapContainer, BoxLayout.Y_AXIS));
        mapContainer.add(graphics1);

        //define all controls: panels, labels, buttons and slider

        JButton startButton = new JButton("start");
        JButton pauseButton = new JButton("pauza");

        JPanel sliderContainer = new JPanel();
        sliderContainer.setLayout(new BoxLayout(sliderContainer, BoxLayout.Y_AXIS));
        sliderContainer.setPreferredSize(new Dimension(100, 30));

        JSlider slider = new JSlider(JSlider.HORIZONTAL,0, 10,5);
        slider.setPreferredSize(new Dimension(100, 10));
        sliderContainer.add(slider);

        JLabel sliderLabel = new JLabel();
        sliderLabel.setText("opóźnienie");
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

        generateStatisticsToFileButton.addActionListener(e -> {
            eventObserver.generateStatisticsToFile();
        });

        this.buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
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

        JPanel averageChildNumberContainer = new JPanel();
        JLabel averageChildNumberLabel = new JLabel("średnia ilość dzieci");
        JLabel averageChildNumberValue = new JLabel("0");

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
        averageChildNumberContainer.setLayout(new FlowLayout());

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

        averageChildNumberContainer.add(averageChildNumberLabel);
        averageChildNumberContainer.add(averageChildNumberValue);
        averageChildNumberContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

        mainGenomeContainer.add(mainGenomeLabel);
        mainGenomeContainer.add(mainGenomeValue);
        mainGenomeContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        mainGenomeHelpLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        generateStatisticsToFileButton.setSize(generateStatisticsToFileButton.getPreferredSize());

        statisticsContainer.add(generateStatisticsToFileButton);
        statisticsContainer.add(animalStatisticsContainer);
        statisticsContainer.add(grassStatisticsContainer);
        statisticsContainer.add(averageAnimalEnergyContainer);
        statisticsContainer.add(averageLifeLengthContainer);
        statisticsContainer.add(averageChildNumberContainer);
        statisticsContainer.add(mainGenomeContainer);
        statisticsContainer.add(mainGenomeHelpLabel);
        statisticsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statisticsContainer.setMaximumSize(new Dimension(500, 9999));
        sliderContainer.setMaximumSize(new Dimension(200, 9999));

        //send references of every statistics label to the statistics collector
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAnimalNumberLabel(animalNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setGrassNumberLabel(grassNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageAnimalEnergyLabel(averageAnimalEnergyNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageLifeLength(averageLifeLengthNumber);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setMainGenomeLabel(mainGenomeValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setOneAnimalGenomeLabel(mainGenomeOfOneAnimalValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setNumberOfChildrenLabel(followedAnimalNumberOfChildrenValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setNumberOfDescendantsLabel(followedAnimalNumberOfDescendantsValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setDeathDayLabel(followedAnimalDeathDayNumberValue);
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().setAverageChildNumberLabel(averageChildNumberValue);

        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().updateAllStatistics();
        ((GraphicPanel) graphics1).grassField.getStatisticsCollector().saveStatistics();

        mapContainer.setPreferredSize(new Dimension(Math.max(windowSize-100,510),260+graphics1.getHeight()));

        mapContainer.add(buttonContainer);
        mapContainer.add(statisticsContainer);
        this.add(mapContainer);
    }

    public InitialParameters getParameters() {
        return parameters;
    }
}
