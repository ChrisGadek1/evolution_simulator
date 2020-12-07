public class Jungle {

    public int getCellsWidth() {
        return cellsWidth;
    }

    public int getCellsHeight() {
        return cellsHeight;
    }

    // describes size of jungle in cells
    private int cellsWidth;
    private int cellsHeight;

    //describes size of whole world in cells
    private int wholeMapCellsHeight;
    private int wholeMapCellsWidth;

    // describes size of cell in pixels
    private int cellSize;

    //describes the first X coordinate, where jungle begins
    private int beginCellX;

    //describes the first Y coordinate, where jungle begins
    private int beginCellY;

    public Jungle(int cellsWidth, int cellsHeight, int wholeMapCellsHeight, int wholeMapCellsWidth, int cellSize) {
        this.cellsWidth = cellsWidth;
        this.cellsHeight = cellsHeight;
        this.wholeMapCellsHeight = wholeMapCellsHeight;
        this.wholeMapCellsWidth = wholeMapCellsWidth;
        this.cellSize = cellSize;
        this.beginCellX = (wholeMapCellsWidth-cellsWidth)/2;
        this.beginCellY = (wholeMapCellsHeight-cellsHeight)/2;
    }

    public int computeStartXPos(){
        return (wholeMapCellsWidth-cellsWidth)/2*cellSize;
    }

    public int computeStartYPos(){
        return  (wholeMapCellsHeight-cellsHeight)/2*cellSize;
    }

    public int getWidthInPixels(){
        return cellsWidth*cellSize;
    }

    public int getHeightInPixels(){
        return cellsHeight*cellSize;
    }

    public int getFirstXCoord(){
        return beginCellX;
    }

    public int getFirstYCoord(){
        return beginCellY;
    }

    public boolean isCellCoordInJungle(int x, int y){
        return x >= this.beginCellX && x < this.beginCellX + cellsWidth && y >= this.beginCellY && y < this.beginCellY + cellsHeight;
    }
}
