public class InitialParameters {
    private double jungleRatio;
    private int grassEnergy;
    private int maxEnergy;
    private int width;
    private int height;
    private int moveEnergy;
    private int initialNumber;

    public InitialParameters(double jungleRatio, int grassEnergy, int maxEnergy, int width, int height, int moveEnergy, int initialNumber) {
        this.jungleRatio = jungleRatio;
        this.grassEnergy = grassEnergy;
        this.maxEnergy = maxEnergy;
        this.width = width;
        this.height = height;
        this.moveEnergy = moveEnergy;
        this.initialNumber = initialNumber;
    }

    public int getInitialNumber() {
        return initialNumber;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getGrassEnergy() {
        return grassEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }
}
