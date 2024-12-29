package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.Slime;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // Screen and World Settings
    final int originalTileSize = 32; // 32x32 tiles
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 96x96 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1536 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 1152 pixels
    public final int maxWorldCol = 112;
    public final int maxWorldRow = 84;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public int gameTime = 0; // Track elapsed time in frames

    // Game Components
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    public Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player; // Initialized after hero selection
    public UI ui = new UI(this);
    int FPS = 60;

    // Entities
    public List<Entity> monsters = new ArrayList<>();

    // Game States
    public final int STATE_SELECTION = 0;
    public final int STATE_PLAYING = 1;
    public final int STATE_GAME_OVER = 2;
    public int gameState = STATE_SELECTION; // Start with hero selection

    // Hero Selection
    List<String> heroes = Arrays.asList("Sorceress", "Warrior");
    int selectedHeroIndex = 0;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow(); // Ensure the panel has focus

        // Initialize monsters
        setupMonsters();
    }


    // spawn monsters
    public void setupMonsters() {
        // Example: Spawn two Slimes at different locations
        Slime slime1 = new Slime(this, 10, 10);
        monsters.add(slime1);

        Slime slime2 = new Slime(this, 11, 11);
        monsters.add(slime2);

        // Add more Slimes as needed
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Time between frames in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // 1. Update: Update information such as character positions
            update();

            // 2. Draw: Draw the screen with the updated information
            repaint();

            // 3. Sleep: Sleep to maintain the desired FPS
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000; // Convert to milliseconds
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

                gameTime++; // Increment game time

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == STATE_PLAYING && player != null) {
            player.update();
    
            // Update all monsters
            Iterator<Entity> iterator = monsters.iterator();
            while (iterator.hasNext()) {
                Entity monster = iterator.next();
                monster.update();
    
                // Remove dead monsters
                if (monster.isDead) {
                    iterator.remove();
                    System.out.println("Monster removed from the list!"); // Debugging
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == STATE_SELECTION) {
            drawHeroSelection(g2);
        } else if (gameState == STATE_PLAYING) {
            tileM.draw(g2);
            player.draw(g2);

            // Draw all monsters
            for (Entity monster : monsters) {
                monster.draw(g2);
            }

            ui.draw(g2);
        } else if (gameState == STATE_GAME_OVER) {
            drawGameOver(g2);
        }

        g2.dispose();
    }

    private void drawHeroSelection(Graphics2D g2) {
        // Draw background
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "Choose Your Hero";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (screenWidth - titleWidth) / 2, 100);

        // Define hero display parameters
        int heroBoxWidth = 200;
        int heroBoxHeight = 200;
        int padding = 100;
        int startX = padding;
        int startY = 200;
        int gapX = 300; // Horizontal gap between hero boxes
        int gapY = 300; // Vertical gap between hero boxes (for multiple rows)

        for (int i = 0; i < heroes.size(); i++) {
            int row = i / 2; // Two heroes per row
            int col = i % 2;
            int x = startX + col * (heroBoxWidth + gapX);
            int y = startY + row * (heroBoxHeight + gapY);

            // Draw hero box
            if (i == selectedHeroIndex) {
                g2.setColor(Color.YELLOW);
                g2.fillRect(x - 5, y - 5, heroBoxWidth + 10, heroBoxHeight + 10); // Highlight border
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawRect(x, y, heroBoxWidth, heroBoxHeight);

            // Draw hero name
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String heroName = heroes.get(i);
            int heroNameWidth = g2.getFontMetrics().stringWidth(heroName);
            g2.drawString(heroName, x + (heroBoxWidth - heroNameWidth) / 2, y + heroBoxHeight + 30);
        }
    }

    private void drawGameOver(Graphics2D g2) {
        // Draw game over screen
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw "Game Over" text
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 100));
        String text = "GAME OVER";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (screenWidth - textWidth) / 2, screenHeight / 2);

        // Optionally, add instructions to restart or exit
        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        String restartText = "Press Enter to Restart";
        int restartWidth = g2.getFontMetrics().stringWidth(restartText);
        g2.drawString(restartText, (screenWidth - restartWidth) / 2, screenHeight / 2 + 100);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == STATE_SELECTION) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) { // Allow 'A' as alternative
                selectedHeroIndex = (selectedHeroIndex - 1 + heroes.size()) % heroes.size();
                repaint();
            }
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) { // Allow 'D' as alternative
                selectedHeroIndex = (selectedHeroIndex + 1) % heroes.size();
                repaint();
            }
            if (code == KeyEvent.VK_ENTER) {
                startGameWithHero(selectedHeroIndex);
            }
        } else if (gameState == STATE_PLAYING) {
            keyH.keyPressed(e); // Delegate to KeyHandler for game controls
        } else if (gameState == STATE_GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameState == STATE_PLAYING) {
            keyH.keyReleased(e); // Delegate to KeyHandler
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public void startGameWithHero(int heroIndex) {
        if (heroIndex >= 0 && heroIndex < heroes.size()) {
            String heroType = heroes.get(heroIndex);
            player = new Player(this, keyH, heroType); // Instantiate Player with the selected hero type
            gameState = STATE_PLAYING;
        } else {
            System.out.println("Invalid hero selection.");
        }
    }

    public void restartGame() {
        // Reset player
        player = null;

        // Reset monsters
        monsters.clear();
        setupMonsters();

        // Reset game state
        gameState = STATE_SELECTION;

        // Reset UI elements if necessary
    }
}
