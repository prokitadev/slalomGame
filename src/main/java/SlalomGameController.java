import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlalomGameController extends JPanel implements KeyListener, ActionListener {

    private final SlalomGameModel MODEL;
    private final GUI VIEW;

    private final Timer TIMER = new Timer(1, this);

    private int distance;
    private int controller;
    private int gateIndex = 0;

    // This both values allow calculates other positions (next Gate, right flag etc.)
    private Gate currentGateOne;
    private int currentGatePositionY;
    int leftBorder = 0;
    int rightBorder = 0;

    public SlalomGameController(SlalomGameModel model, GUI view) {
        this.MODEL = model;
        this.VIEW = view;
    }

    public void play() {
        VIEW.initializeGUI(this, MODEL.getGameBoardDimension());
        MODEL.setGates(initializeGates(150, 90, 280));
        TIMER.start();
    }

    @Override
    public void paint(Graphics g) {
        int h = getHeight();
        int w = getWidth();
        int s = MODEL.getSkierSize();
        g.setColor(new Color(227, 238, 242));
        g.fillRect(0, 0, getWidth(), getHeight());


        // SKIER
        g.setColor(Color.DARK_GRAY);
        g.fillRect(MODEL.getSkierPositionX(), MODEL.getSkierPositionY(), s, s);
        final int skisHeight = (h / 5 * 3) - (s * 3 / 4);
        g.fillRect(MODEL.getSkierPositionX() + (s / 4), skisHeight, s / 6, s * 2);
        g.fillRect(MODEL.getSkierPositionX() + (s - s / 4 - s / 6), skisHeight, s / 6, s * 2);



        // GATES
        paintGates(g);
        g.drawString(String.valueOf(distance), 500, 500);

        // DASHBOARD
        paintDashboard(g, h, w);


    }

    private void paintDashboard(Graphics g, int h, int w) {
        int x = 0;
        int y = MODEL.getGameBoardTextBarLine();
        g.setColor(new Color(229, 25, 25));
        g.drawLine(x, MODEL.getGameBoardTextBarLine(), w, MODEL.getGameBoardTextBarLine());
        g.fillRect(x, y, w, h - y);

        g.setColor(new Color(227, 238, 242));
        g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 22));
        g.drawString("SCORE: " + MODEL.getScore(), w / 20, h * 90 / 100);
        g.drawString("" + currentGatePositionY, w / 20, h * 95 / 100);
        g.drawString("" + MODEL.getSkierPositionX(), w / 10, h * 95 / 100);
        if (MODEL.getStatus() != null) {
            g.drawString(MODEL.getStatus().getMessage(), w / 2,  h * 90 / 100);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> moveSkier(Move.LEFT);
            case KeyEvent.VK_RIGHT -> moveSkier(Move.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void moveSkier(Move move) {
        switch (move) {
            case LEFT -> MODEL.moveLeft();
            case RIGHT -> MODEL.moveRight();
        }
        repaint();
    }

    private List<Gate> initializeGates(int noOfGates, int minGateSize, int maxGateSize) {
        List<Gate> gates = new ArrayList<>();
        Random random = new Random();
        int n = maxGateSize - minGateSize + 1;
        int size;
        int position;
        int minPositionLeft = MODEL.getBound();
        int maxPositionLeft = (int) MODEL.getGameBoardDimension().getWidth() - (maxGateSize + 2 * MODEL.getSkierSize());
        while(noOfGates > 0) {
            size = random.nextInt(n) + minGateSize;
            position = random.nextInt(maxPositionLeft - minPositionLeft) + minGateSize;
            gates.add(new Gate(position, position + size));
            noOfGates--;
        }
        return gates;
    }



    private void paintGates(Graphics g) {
        List<Gate> gates = MODEL.getGates();
        int gatesDis = MODEL.getGatesDistance();
        int flagSize = MODEL.getFlagSize();

        currentGatePositionY = controller;
        int y2 = currentGatePositionY - gatesDis;

        currentGateOne = gates.get(gateIndex);
        Gate gate2 = gates.get(gateIndex + 1);

        if (controller == MODEL.getGameBoardTextBarLine()) {
            currentGateOne = gate2;
            gateIndex++;
            gate2 = gates.get(gateIndex);
            controller = y2;
        }
        // MAX 2 GATES ON BOARD
        // GATE 1
        g.setColor(Color.BLUE);
        g.fillRect(currentGateOne.getLeftPosition(), currentGatePositionY, flagSize, flagSize);
        g.setColor(Color.RED);
        g.fillRect(currentGateOne.getRightPosition(), currentGatePositionY, flagSize, flagSize);

        // GATE 2
        g.setColor(Color.BLUE);
        g.fillRect(gate2.getLeftPosition(), y2, flagSize, flagSize);
        g.setColor(Color.RED);

        g.fillRect(gate2.getRightPosition(), y2, flagSize, flagSize);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == TIMER){
            repaint();
            distance++;
            controller++;
            scoreController();
        }
    }

    private void scoreController() {
        boolean passing = false;
        if (currentGatePositionY == MODEL.getSkierPositionY()) {
            leftBorder = currentGateOne.getLeftPosition() + MODEL.getSkierSize();
            rightBorder = currentGateOne.getRightPosition();
            passing = true;
        }
        if (currentGatePositionY == MODEL.getSkierPositionY() + MODEL.getSkierSize()) {
            leftBorder = 0;
            rightBorder = 0;
        }
        if (passing) {
            if (MODEL.getSkierPositionX() >= leftBorder && MODEL.getSkierPositionX() <= rightBorder) {
                MODEL.setScore(MODEL.getScore() + 100);
            } else {
                MODEL.setStatus(Status.MISSED_GATE);
                MODEL.setScore(0);
            }
        }
    }
}
