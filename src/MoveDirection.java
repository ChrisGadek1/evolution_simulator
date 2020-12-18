import java.util.Random;

public enum MoveDirection {
    STRAIGHT_ON,
    SLIGHT_RIGHT,
    RIGHT,
    BACK_RIGHT,
    BACK,
    BACK_LEFT,
    LEFT,
    SLIGHT_LEFT;

    public Vector2d moveToVector(){
        switch(this){
            case STRAIGHT_ON: return new Vector2d(0,1);
            case SLIGHT_RIGHT: return new Vector2d(0,-1);
            case RIGHT: return new Vector2d(-1,0);
            case BACK_RIGHT: return new Vector2d(1,0);
            case BACK: return new Vector2d(1,1);
            case SLIGHT_LEFT: return new Vector2d(-1,1);
            case LEFT: return new Vector2d(-1,-1);
            case BACK_LEFT: return new Vector2d(1,-1);
            default: return null;
        }
    }

    public static MoveDirection mapIntToMove(int value){
        MoveDirection move;
        switch (value){
            case 0: move = MoveDirection.STRAIGHT_ON; break;
            case 1: move = MoveDirection.BACK; break;
            case 2: move = MoveDirection.BACK_RIGHT; break;
            case 3: move = MoveDirection.BACK_LEFT; break;
            case 4: move = MoveDirection.SLIGHT_RIGHT; break;
            case 5: move = MoveDirection.LEFT; break;
            case 6: move = MoveDirection.RIGHT; break;
            case 7: move = MoveDirection.SLIGHT_LEFT; break;
            default: move = null; break;
        }
        return move;
    }

    public static MoveDirection getRandomMove(){
        Random rand = new Random();
        int random = rand.nextInt(8);
        return mapIntToMove(random);
    }

    public MoveDirection getNextMove(){
        MoveDirection move;
        switch (this){
            case SLIGHT_LEFT: move = MoveDirection.STRAIGHT_ON; break;
            case STRAIGHT_ON: move = MoveDirection.BACK; break;
            case BACK: move = MoveDirection.BACK_RIGHT; break;
            case BACK_RIGHT: move = MoveDirection.BACK_LEFT; break;
            case BACK_LEFT: move = MoveDirection.SLIGHT_RIGHT; break;
            case SLIGHT_RIGHT: move = MoveDirection.LEFT; break;
            case LEFT: move = MoveDirection.RIGHT; break;
            case RIGHT: move = MoveDirection.SLIGHT_LEFT; break;
            default: move = null; break;
        }
        return move;
    }
}