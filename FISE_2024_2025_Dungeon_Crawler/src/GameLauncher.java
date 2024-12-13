import javax.swing.*;

public class GameLauncher {

    public static void main(String[] args) {
        JFrame launchFrame = new JFrame("Start Game");
        launchFrame.setSize(300, 150);
        launchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launchFrame.setLocationRelativeTo(null); // Center the window

        JLabel label = new JLabel("Press Enter to start the game", SwingConstants.CENTER);
        launchFrame.add(label);

        launchFrame.setVisible(true);

        launchFrame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
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