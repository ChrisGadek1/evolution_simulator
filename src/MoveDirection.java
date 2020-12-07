public enum MoveDirection {
    TOP,
    DOWN,
    LEFT,
    RIGHT,
    TOPRIGHT,
    DOWNRIGHT,
    DOWNLEFT,
    TOPLEFT;

    public Vector2d moveToVector(){
        switch(this){
            case TOP: return new Vector2d(0,1);
            case DOWN: return new Vector2d(0,-1);
            case LEFT: return new Vector2d(-1,0);
            case RIGHT: return new Vector2d(1,0);
            case TOPRIGHT: return new Vector2d(1,1);
            case TOPLEFT: return new Vector2d(-1,1);
            case DOWNLEFT: return new Vector2d(-1,-1);
            case DOWNRIGHT: return new Vector2d(1,-1);
            default: return null;
        }
    }
}