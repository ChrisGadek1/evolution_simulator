public class Grass extends AbstractWorldElement{
    public Grass(Vector2d vector2d, GrassField grassField){
        super(grassField);
        this.setPosition(vector2d);
    }

    @Override
    void prepareBeforeAddToMap(int x, int y) {
        this.setPosition(new Vector2d(x,y));
        this.getMap().grassesMap.put(new Vector2d(x,y), this);
        this.getMap().cellMap.get(this.getPosition()).grass = this;
    }
}
