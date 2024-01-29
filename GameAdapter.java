import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameAdapter extends KeyAdapter {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;



    public GameAdapter(boolean left, boolean right, boolean up, boolean down) {
        this.left = left;
        this.down = down;
        this.up = up;
        this.right = right;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!right)) {
            left = true;
            up = false;
            down = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!left)) {
            right = true;
            up = false;
            down = false;
        }

        if ((key == KeyEvent.VK_UP) && (!down)) {
            up = true;
            right = false;
            left = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!up)) {
            down = true;
            right = false;
            left = false;
        }
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }
}
