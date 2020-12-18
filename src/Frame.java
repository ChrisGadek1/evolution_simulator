

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


public class Frame extends JFrame {
    JPanel mainContainer;
    World mainWorld;
    LinkedList<Animal> copiedAnimals;

    public World addNewMap(){
        JPanel world = new World();
        GraphicPanel graphicPanel = (GraphicPanel) ((World) world).graphics1;
        for(Animal animal: copiedAnimals){
            animal.setMap(graphicPanel.grassField);
            animal.setDayOfBirth(0);
        }
        graphicPanel.getEngine().initSimulation(copiedAnimals);
        copiedAnimals = graphicPanel.grassField.copyAnimals(null);
        world.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.mainContainer.add(world);
        this.mainContainer.repaint();
        this.mainContainer.revalidate();
        return (World) world;
    }

    public Frame() {
        super("Evolution Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setLocation(200,200);
        setSize(new Dimension(600, 600));
        this.mainContainer = new JPanel();
        mainContainer.setLayout(new FlowLayout());
        JButton addMapButton = new JButton("dodaj kolejną mapę");
        addMapButton.addActionListener(e -> {
            this.addNewMap();
            mainContainer.remove(addMapButton);
        });
        mainContainer.add(addMapButton);
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        getContentPane().add(scrollPane);
        World world = new World();
        GraphicPanel graphicPanel = (GraphicPanel) world.graphics1;
        graphicPanel.getEngine().initSimulation(world.getParameters().getInitialNumber());
        copiedAnimals = graphicPanel.grassField.copyAnimals(null);
        world.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.mainWorld = world;
        mainContainer.add(world);
    }
}
