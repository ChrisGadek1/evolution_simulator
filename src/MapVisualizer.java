import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class MapVisualizer {

    GrassField map;
    int width;
    int height;
    int cellSize;

    MapVisualizer(GrassField world, int width, int height){
        this.map = world;
        this.width = width;
        this.height = height;
        this.cellSize = width/map.getBounds()[2];
    }

   public void drawAnimal(Graphics2D g, Animal animal){
        Vector2d position = animal.getPosition();
        Ellipse2D.Double circle = new Ellipse2D.Double(position.getX()*cellSize,position.getY()*cellSize,cellSize,cellSize);
        int colorLow = (int)(255 * ((double)(animal.getEnergy())/(double)(animal.getMaxEnergy())));
        int colorValue = 255 - colorLow;
        g.setColor(new Color(Math.max(0,255-colorLow/10), Math.max(colorValue,0), Math.max(colorValue,0)));
        g.fill(circle);
   }

   public void drawGrass(Graphics2D graphics2D, Grass grass){
        Vector2d position = grass.getPosition();
        Rectangle2D.Double rectangle = new Rectangle2D.Double(position.getX()*cellSize,position.getY()*cellSize,cellSize,cellSize);
        graphics2D.setColor(new Color(11, 212, 0));
        graphics2D.fill(rectangle);
   }

   public void drawAnimals(Graphics2D graphics2D){
       for (Animal animal: map.getAnimals()) {
           drawAnimal(graphics2D, animal);
       }
   }

   public void drawGrass(Graphics2D graphics2D){
        for(Grass grass: map.getGrassesMap().values()){
            drawGrass(graphics2D, grass);
        }
   }

   void drawBiomes(Graphics2D g){
        for(int i = 0; i < map.getWidth(); i++){
            for (int j = 0; i < map.getHeight(); i++){
                Vector2d position = new Vector2d(i, j);
                g.setColor(map.cellMap.get(position).getBiome().getColor());
                Rectangle2D.Double rectangle = new Rectangle2D.Double(i*cellSize, j*cellSize, cellSize, cellSize);
                g.fill(rectangle);
            }
        }
    }

}