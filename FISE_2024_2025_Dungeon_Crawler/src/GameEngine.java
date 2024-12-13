import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine implements KeyListener {
    private DynamicSprite hero;
    private boolean isRunning = false; // Pentru a urmări starea de alergare
    private static final double NORMAL_SPEED = 5; // Viteza normală
    private static final double RUNNING_SPEED = 80; // Viteza la alergare

    // Variabile de stare a tastelor
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    public void update(ArrayList<Sprite> environment) {
        // Determină viteza în funcție de starea de alergare
        double speed = isRunning ? RUNNING_SPEED : NORMAL_SPEED;

        // Actualizează direcția în funcție de starea tastelor
        if (leftPressed) {
            hero.setDirection(Direction.WEST);
        } else if (rightPressed) {
            hero.setDirection(Direction.EAST);
        }
        if (upPressed) {
            hero.setDirection(Direction.NORTH);
        } else if (downPressed) {
            hero.setDirection(Direction.SOUTH);
        }

        // Încercăm să mișcăm eroul dacă o tastă este apăsată
        if (leftPressed || rightPressed || upPressed || downPressed) {
            hero.moveIfPossible(environment); // Mută eroul pe baza mediului și vitezei
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                isRunning = true; // Începe alergarea
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true; // Tasta stângă apăsată
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true; // Tasta dreapta apăsată
                break;
            case KeyEvent.VK_UP:
                upPressed = true; // Tasta sus apăsată
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true; // Tasta jos apăsată
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                isRunning = false; // Oprește alergarea
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false; // Tasta stângă eliberată
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false; // Tasta dreapta eliberată
                break;
            case KeyEvent.VK_UP:
                upPressed = false; // Tasta sus eliberată
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false; // Tasta jos eliberată
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nu este folosit
    }
}
