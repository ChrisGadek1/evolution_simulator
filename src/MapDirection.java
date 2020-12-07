public enum MapDirection {
    NORTH, SOUTH, WEST, EAST;

    public String toString(){
        switch(this){
            case SOUTH: return "S";
            case NORTH: return "N";
            case WEST: return "W";
            case EAST: return "E";
            default: return "";
        }
    }

    public MapDirection next(){
        switch(this){
            case SOUTH: return WEST;
            case NORTH: return EAST;
            case WEST: return NORTH;
            case EAST: return SOUTH;
            default: return null;
        }
    }

    public MapDirection previous(){
        switch(this){
            case SOUTH: return EAST;
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case EAST: return NORTH;
            default: return null;
        }
    }

    Vector2d toUnitVector(){
        switch (this){
            case SOUTH: return new Vector2d(0,-1);
            case NORTH: return new Vector2d(0,1);
            case WEST: return new Vector2d(-1,0);
            case EAST: return new Vector2d(1,0);
            default: return null;
        }
    }
}
