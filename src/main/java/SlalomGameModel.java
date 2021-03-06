import java.awt.*;
import java.util.List;

public class SlalomGameModel {// ZAImplemoen Pattern Observer???? Byłoby najs

    private final int gameBoardWidth = 1200;
    private final int gameBoardHeight = 700;
    private final int gameBoardTextBarLine = 560;
    private int bound = 50;

    private int skierPositionX = 588;
    private int skierPositionY = gameBoardHeight / 5 * 3;
    private int skierSize = 34;

    private List<Gate> gates;
    private final int gatesDistance = 300;
    private final int flagSize = 40;

    private long score = 0;
    private Status status = Status.NULL;

    public SlalomGameModel() {
    }

    public void moveLeft() {
        int newPosition = skierPositionX + (-skierSize);
        if (newPosition <= bound) {
            status = Status.NO_PLACE_TO_MOVE;
        } else {
            skierPositionX = newPosition;
            status = Status.NULL;
        }
    }

    public void moveRight() {
        int newPosition = skierPositionX + (skierSize);
        if (newPosition >= gameBoardWidth - bound - skierSize) {
            status = Status.NO_PLACE_TO_MOVE;
        } else {
            skierPositionX = newPosition;
            status = Status.NULL;
        }
    }

    public int getSkierPositionX() {
        return skierPositionX;
    }

    public void setSkierPositionX(int skierPositionX) {
        this.skierPositionX = skierPositionX;
    }

    public int getSkierPositionY() {
        return skierPositionY;
    }

    public void setSkierPositionY(int skierPositionY) {
        this.skierPositionY = skierPositionY;
    }

    public Dimension getGameBoardDimension() {
        return new Dimension(gameBoardWidth, gameBoardHeight);
    }

    public int getGameBoardTextBarLine() {
        return gameBoardTextBarLine;
    }

    public int getSkierSize() {
        return skierSize;
    }

    public void setSkierSize(int skierSize) {
        this.skierSize = skierSize;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    public int getGatesDistance() {
        return gatesDistance;
    }

    public int getFlagSize() {
        return flagSize;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
