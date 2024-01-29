import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameBoard extends JPanel implements ActionListener {

    private final int BOARD_SIZE = 15;
    private final int POINT_SIZE = 30;
    private final int POINTS_ALL = BOARD_SIZE * BOARD_SIZE;

    private final int[] horizontal = new int[POINTS_ALL];
    private final int[] vertical = new int[POINTS_ALL];

    private int score = 0;
    private boolean gameOn = true;
    private int foodX;
    private int foodY;
    private int snakeSize;
    


    private final GameAdapter gameAdapter = new GameAdapter(false, true, false, false);

    private Timer timer;

    public GameBoard() {
        createBoard();
    }

    private void createBoard() {
        addKeyListener(gameAdapter);
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_SIZE * POINT_SIZE, BOARD_SIZE * POINT_SIZE));
        startGame();
    }

    private void startGame() {
        snakeSize = 1; // Snake starts with only the head

        // Initialize the head within the grid boundaries
        horizontal[0] = (int) (Math.random() * (BOARD_SIZE - 2) + 1) * POINT_SIZE;
        vertical[0] = (int) (Math.random() * (BOARD_SIZE - 2) + 1) * POINT_SIZE;

        foodLocation();

        // Slower speed
        int DELAY = 220;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g);
    }

    private void drawAll(Graphics g) {
        drawGrid(g);

        if (gameOn) {
            drawApple(g);
            drawSnake(g);
            drawScore(g);
        } else {
            endGame(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);

        // Draw vertical lines
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g.drawLine(i * POINT_SIZE, 0, i * POINT_SIZE, BOARD_SIZE * POINT_SIZE);
        }

        // Draw horizontal lines
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g.drawLine(0, i * POINT_SIZE, BOARD_SIZE * POINT_SIZE, i * POINT_SIZE);
        }
    }

    private void drawSnake(Graphics g) {
        for (int z = 0; z < snakeSize; z++) {
            if (z == 0) {
                g.setColor(Color.GREEN); // Snake head color
                g.fillRoundRect(horizontal[z], vertical[z], POINT_SIZE, POINT_SIZE, 10, 10);
            } else {
                g.setColor(Color.BLUE); // Snake body color
                g.fillRoundRect(horizontal[z], vertical[z], POINT_SIZE, POINT_SIZE, 10, 10);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(foodX * POINT_SIZE + POINT_SIZE / 4, foodY * POINT_SIZE + POINT_SIZE / 4, POINT_SIZE / 2, POINT_SIZE / 2);
    }


    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 10, 20);
    }

    private void endGame(Graphics g) {
        String message = "Game Over - Score: " + score;
        Font font = new Font("Candara", Font.BOLD, 20);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(message, (BOARD_SIZE * POINT_SIZE - metrics.stringWidth(message)) / 2, BOARD_SIZE * POINT_SIZE / 2);
    }

    private void isFood() {
        // Divide the coordinates by DOT_SIZE to get the correct position on the grid
        if ((horizontal[0] / POINT_SIZE == foodX) && (vertical[0] / POINT_SIZE == foodY)) {
            score++;
            snakeSize++;
            foodLocation();
        }
    }

    private void typeOfMove() {
        for (int z = snakeSize; z > 0; z--) {
            horizontal[z] = horizontal[z - 1];
            vertical[z] = vertical[z - 1];
        }

        if (gameAdapter.isLeft()) {
            horizontal[0] -= POINT_SIZE;
            if (horizontal[0] < 0) {
                horizontal[0] = (BOARD_SIZE - 1) * POINT_SIZE;
            }
        }

        if (gameAdapter.isRight()) {
            horizontal[0] += POINT_SIZE;
            if (horizontal[0] >= BOARD_SIZE * POINT_SIZE) {
                horizontal[0] = 0;
            }
        }

        if (gameAdapter.isUp()) {
            vertical[0] -= POINT_SIZE;
            if (vertical[0] < 0) {
                vertical[0] = (BOARD_SIZE - 1) * POINT_SIZE;
            }
        }

        if (gameAdapter.isDown()) {
            vertical[0] += POINT_SIZE;
            if (vertical[0] >= BOARD_SIZE * POINT_SIZE) {
                vertical[0] = 0;
            }
        }
    }


    private void ifImpact() {
        for (int z = snakeSize; z > 0; z--) {
            if ((z > 4) && (horizontal[0] == horizontal[z]) && (vertical[0] == vertical[z])) {
                gameOn = false;
            }
        }

        if (vertical[0] >= BOARD_SIZE * POINT_SIZE || vertical[0] < 0 || horizontal[0] >= BOARD_SIZE * POINT_SIZE || horizontal[0] < 0) {
            gameOn = false;
        }

        if (!gameOn) {
            timer.stop();
        }
    }

    private void foodLocation() {
        boolean validPos = false;
        while (!validPos) {
            int RAND_POS = BOARD_SIZE - 1;
            foodX = (int) (Math.random() * RAND_POS);
            foodY = (int) (Math.random() * RAND_POS);
            validPos = true;

            // Check if the apple is on the snake's body
            for (int i = 0; i < snakeSize; i++) {
                if (foodX == horizontal[i] / POINT_SIZE && foodY == vertical[i] / POINT_SIZE) {
                    validPos = false;
                    break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOn) {
            isFood();
            ifImpact();
            typeOfMove();
        }
        repaint();
    }
}