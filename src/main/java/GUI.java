import javax.swing.*;
import java.awt.*;

//VIEW
public class GUI  {


    public void initializeGUI(SlalomGameController controller, Dimension dimension) {
        JFrame frame = new JFrame("Slalom Game");
        JPanel panel = controller;
        panel.setPreferredSize(dimension);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(controller);
    }
}
