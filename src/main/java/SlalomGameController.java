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

    private final SlalomGameModel model;
    private final GUI view;

    private final Timer timer = new Timer(15, this);

    private int distance;
    private int controller;

    public SlalomGameController(SlalomGameModel model, GUI view) {
        this.model = model;
        this.view = view;
    }

    public void play() {
        view.initializeGUI(this, model.getGameBoardDimension());
        model.setGates(initializeGates(150, 90, 280));
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        int h = getHeight();
        int w = getWidth();
        int s = model.getSkierSize();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // SKIER
        g.setColor(Color.DARK_GRAY);
        g.fillRect(model.getSkierPosition(), h / 5 * 3, s, s);
        final int skisHeight = (h / 5 * 3) - (s * 3 / 4);
        g.fillRect(model.getSkierPosition() + (s / 4), skisHeight, s / 6, s * 2);
        g.fillRect(model.getSkierPosition() + (s - s / 4 - s / 6), skisHeight, s / 6, s * 2);

        // GATES
        paintGates(g);
        g.drawString(String.valueOf(distance), 500, 500);

        // TEXT BAR
        g.setColor(Color.DARK_GRAY);
        g.drawLine(0, model.getGameBoardTextBarLine(), w, model.getGameBoardTextBarLine());

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (model.getStatus() != null) {
            g.drawString(model.getStatus().getMessage(), w / 20,  h * 90 /100);
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
            case LEFT -> model.moveLeft();
            case RIGHT -> model.moveRight();
        }
        repaint();
    }

    private List<Gate> initializeGates(int noOfGates, int minGateSize, int maxGateSize) {
        List<Gate> gates = new ArrayList<>();
        Random random = new Random();
        int n = maxGateSize - minGateSize + 1;
        int size;
        int position;
        int minPositionLeft = model.getBound();
        int maxPositionLeft = (int)model.getGameBoardDimension().getWidth() - maxGateSize;
        while(noOfGates > 0) {
            size = random.nextInt(n) + minGateSize;
            position = random.nextInt(maxPositionLeft - minPositionLeft) + minGateSize;
            gates.add(new Gate(position, position + size));
            noOfGates--;
        }
        return gates;
    }
    int gateIndex = 0;
    private void paintGates(Graphics g) {
        List<Gate> gates = model.getGates();
        int gatesDis = model.getGatesDistance();
        int flagSize = model.getFlagSize();



        int y1 = controller;
        int y2 = y1 - gatesDis;
        
        Gate gate1 = gates.get(gateIndex);
        Gate gate2 = gates.get(gateIndex + 1);

        if (controller == model.getGameBoardTextBarLine()) {
            gate1 = gate2;
            gateIndex++;
            gate2 = gates.get(gateIndex);
            controller = y2;
        }





        // MAX 2 GATES ON BOARD
        // GATE 1
        g.setColor(Color.BLUE);
        g.fillRect(gate1.getLeftPosition(), y1, flagSize, flagSize);
        g.setColor(Color.RED);
        g.fillRect(gate1.getRightPosition(), y1, flagSize, flagSize);

        // GATE 2
        g.setColor(Color.BLUE);
        g.fillRect(gate2.getLeftPosition(), y2, flagSize, flagSize);
        g.setColor(Color.RED);

        g.fillRect(gate2.getRightPosition(), y2, flagSize, flagSize);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            repaint();
            distance++;
            controller++;
        }
    }
}
