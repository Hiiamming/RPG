import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Title extends JPanel {

    private Image backgroundImage; // Variable to hold the background image
    private Rectangle startButton, optionsButton, exitButton; // Button bounds
    private JFrame frame; // Frame variable

    // Constructor
    public Title() {
        setPreferredSize(new Dimension(1000, 800)); // Fixed size
        setBackground(Color.WHITE); // Background color

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("game.png")); // Replace with your image file name
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load background image.");
        }
        // Initialize button bounds
        startButton = new Rectangle();
        optionsButton = new Rectangle();
        // Initialize button bounds
        startButton = new Rectangle();
        optionsButton = new Rectangle();
        exitButton = new Rectangle();
        
        // Add mouse listener for button clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (startButton.contains(p)) {
                    JOptionPane.showMessageDialog(frame, "Starting the game...");
                } else if (exitButton.contains(p)) {
                    JOptionPane.showMessageDialog(frame, "Are you sure you want to exit the game?");
                    System.exit(0);
                }
            }
        });
        startButton = new Rectangle();
        optionsButton = new Rectangle();
        exitButton = new Rectangle();

        // Add mouse listener for button clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (startButton.contains(p)) {
                    System.out.println("Start Game button clicked");
                } else if (optionsButton.contains(p)) {
                    System.out.println("Options button clicked");
                } else if (exitButton.contains(p)) {
                    System.exit(0);
                }
            }
        });
    }

    // Method to draw the title screen
    private void drawTitleScreen(Graphics2D g2) {
        // Title text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F)); // Font
        String text = ""; // Title
        int x = getCenteredTextX(g2, text); // Center text horizontally
        int y = 100; // Center for y
        g2.setColor(Color.BLACK); // Text color
        g2.drawString(text, x, y); // Draw title

        // Buttons
        g2.setFont(new Font("Arial", Font.PLAIN, 40)); // Font for buttons
        drawButton(g2, "Start Game", startButton.x, startButton.y); // Button 1
        drawButton(g2, "Exit", exitButton.x, exitButton.y);      // Button 3 
    }

    // Utility method to draw buttons
    private void drawButton(Graphics2D g2, String text, int x, int y) {
        // Button text
        int textX = x + (200 - g2.getFontMetrics().stringWidth(text)) / 2; // Center text
        int textY = y + 35; // Vertical alignment
        g2.drawString(text, textX, textY);
    }

    // Utility method for centering text
    private int getCenteredTextX(Graphics2D g2, String text) {
        int textWidth = g2.getFontMetrics().stringWidth(text); // Measure width
        return (getWidth() - textWidth) / 2; // Calculate position
    }

    // Utility method for centering buttons horizontally
    private int getCenteredButtonX(int buttonWidth) {
        return (getWidth() - buttonWidth) / 2; // Calculate position
    }

    // Utility method for centering buttons vertically
    private int getCenteredButtonY(int buttonHeight, int yOffset) {
        return (getHeight() - buttonHeight) / 2 + yOffset; // Calculate position
    }

    @Override
    protected void paintComponent(Graphics g) {
        Title titlePanel = new Title(); // Create panel
        titlePanel.frame = frame; // Set frame reference
        Graphics2D g2 = (Graphics2D) g; // Cast Graphics to Graphics2D
        
        // Draw the background image
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Update button bounds
        startButton.setBounds(getCenteredButtonX(200), getCenteredButtonY(50, 100), 200, 50);
        exitButton.setBounds(getCenteredButtonX(200), getCenteredButtonY(50, 250), 200, 50);

        drawTitleScreen(g2); // Draw the title screen components
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Title Screen");
        Title titlePanel = new Title(); // Create panel
        frame.add(titlePanel); // Add panel
        frame.pack(); // Adjust size
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close action
        frame.setVisible(true); // Show frame
    }
}
