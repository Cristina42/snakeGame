import javax.swing.*;
import java.awt.*;


public class SnakeGame extends JFrame {

    public SnakeGame() {
        startUI();
    }

    private void startUI() {
        add(new GameBoard());
        setResizable(false);
        pack();
        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new SnakeGame();
            frame.setVisible(true);
        });
    }
}



