public class Grass {
    private Vector2d position;

    public Grass(Vector2d vector2d){
        this.position = vector2d;
    }

    Vector2d getPosition(){
        return position;
    }

    void setPosition(Vector2d vector2d){
        this.position = vector2d;
    }

    public String toString(){
        return "*";
    }

}
