import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST;
    private double speed = 5; // Viteza normală
    private double runningSpeed = 10; // Viteza de alergare
    private double timeBetweenFrame = 250;
    private boolean isWalking = true;
    private final int spriteSheetNumberOfColumn = 10;

    // Atribut pentru a urmări starea de alergare (true când Ctrl este apăsat)
    private boolean isRunning = false;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    // Metodă pentru a verifica dacă mișcarea este posibilă (cu coliziuni)
    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        double currentSpeed = isRunning ? runningSpeed : speed; // Folosim viteza de alergare dacă e activată

        switch (direction) {
            case EAST:
                moved.setRect(super.getHitBox().getX() + currentSpeed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                moved.setRect(super.getHitBox().getX() - currentSpeed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - currentSpeed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + currentSpeed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false; // Mișcarea nu este posibilă din cauza coliziunii
                }
            }
        }
        return true; // Mișcarea este posibilă
    }

    // Setează direcția eroului
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Mișcă eroul într-o direcție dată
    private void move() {
        double currentSpeed = isRunning ? runningSpeed : speed; // Folosim viteza de alergare dacă e activată

        switch (direction) {
            case NORTH:
                this.y -= currentSpeed;
                break;
            case SOUTH:
                this.y += currentSpeed;
                break;
            case EAST:
                this.x += currentSpeed;
                break;
            case WEST:
                this.x -= currentSpeed;
                break;
        }
    }

    // Mișcă eroul dacă mișcarea este posibilă
    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    // Metodă pentru a activa sau dezactiva alergarea
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning; // Setează starea de alergare
    }

    // Desenează eroul pe ecran
    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);

        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
    }
}
