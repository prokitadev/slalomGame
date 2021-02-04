public class Gate {

    private int leftPosition;
    private int rightPosition;

    public Gate(int leftPosition, int rightPosition) {
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
    }

    public int getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(int leftPosition) {
        this.leftPosition = leftPosition;
    }

    public int getRightPosition() {
        return rightPosition;
    }

    public void setRightPosition(int rightPosition) {
        this.rightPosition = rightPosition;
    }

}
