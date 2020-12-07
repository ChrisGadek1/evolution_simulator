import java.util.Random;

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

    public static MoveDirection getRandomMove(){
        MoveDirection move;
        Random rand = new Random();
        int random = rand.nextInt(8);
        switch (random){
            case 0: move = MoveDirection.TOP; break;
            case 1: move = MoveDirection.TOPRIGHT; break;
            case 2: move = MoveDirection.RIGHT; break;
            case 3: move = MoveDirection.DOWNRIGHT; break;
            case 4: move = MoveDirection.DOWN; break;
            case 5: move = MoveDirection.DOWNLEFT; break;
            case 6: move = MoveDirection.LEFT; break;
            case 7: move = MoveDirection.TOPLEFT; break;
            default: move = null; break;
        }
        return move;
    }

    public MoveDirection getNextMove(){
        MoveDirection move;
        switch (this){
            case TOPLEFT: move = MoveDirection.TOP; break;
            case TOP: move = MoveDirection.TOPRIGHT; break;
            case TOPRIGHT: move = MoveDirection.RIGHT; break;
            case RIGHT: move = MoveDirection.DOWNRIGHT; break;
            case DOWNRIGHT: move = MoveDirection.DOWN; break;
            case DOWN: move = MoveDirection.DOWNLEFT; break;
            case DOWNLEFT: move = MoveDirection.LEFT; break;
            case LEFT: move = MoveDirection.TOPLEFT; break;
            default: move = null; break;
        }
        return move;
    }
}