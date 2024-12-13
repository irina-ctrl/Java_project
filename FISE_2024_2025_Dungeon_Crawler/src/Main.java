import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(600, 800);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Playground level = new Playground("./data/level1.txt");
        ArrayList<Sprite> environment = level.getSolidSpriteList(); // Get the environment for collision detection

        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(environment);

        displayZoneFrame.addKeyListener(gameEngine);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update(environment)); // Pass the environment here
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame launchFrame = new JFrame("Start Game") {
            @Override
            public void paint(Graphics g) {
                // Set background color to purple
                g.setColor(new Color(128, 0, 128));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Draw the game title in the purple area, centered
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 60));
                String title = "Dungeon";
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int titleWidth = metrics.stringWidth(title);
                g.drawString(title, (getWidth() - titleWidth) / 2, 80); // Centered title, moved down

                // Draw the "Press Enter to Start" text
                String startText = "Press Enter to Start";
                g.setColor(Color.WHITE);
                Font startFont = new Font("Arial", Font.BOLD, 30);
                g.setFont(startFont);
                FontMetrics startMetrics = g.getFontMetrics(startFont);
                int startWidth = startMetrics.stringWidth(startText);
                g.fillRoundRect((getWidth() - startWidth - 20) / 2, 200, startWidth + 20, 50, 15, 15); // Rounded rectangle

                // Draw the "Press Enter to Start" text
                g.setColor(Color.BLACK);
                g.drawString(startText, (getWidth() - startWidth) / 2, 230); // Center the text in the box
            }
        };

        launchFrame.setSize(300, 450);
        launchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launchFrame.setLocationRelativeTo(null); // Center the window
        launchFrame.setVisible(true);

        launchFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    launchFrame.dispose(); // Close the launch window
                    try {
                        new Main(); // Start the game
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}