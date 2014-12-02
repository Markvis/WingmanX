package wingmanx;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author markfavis
 */
public class WingmanX extends JApplet implements Runnable {

    private Thread thread;
    Image sea;
    Image playerOneImg;
    Image playerTwoImg;
    Image playerBullet;
    Image enemyBullet;
    Image loadingScreen;
    private BufferedImage bimg;
    static Graphics2D g2;
    int speed = 1, move = 0;
    static Random generator = new Random();
    GameIsland I1, I2, I3;
    static GamePlayer playerOne;
    static GamePlayer playerTwo;
    static final int w = 640, h = 480; // fixed size window game 
    static GameEvents gameEvents;
    static ArrayList<GameEnemy> smallEnemies;
    static HealthBar playerOneHealth;
    static HealthBar playerTwoHealth;
    static ArrayList<GameBullets> playerBullets;
    static ArrayList<GameBullets> enemyBullets;
    static GameEnemy boss;
    Timer gameTimer;
    static int gameTimeCounter;
    static int gameScore;
    static boolean bossSpawned;
    static boolean gameStart = false;

    // sprites
    ArrayList<Image> enemySprite = new ArrayList<Image>();
    ArrayList<Image> bossSprite = new ArrayList<Image>();
    ArrayList<Image> playerSprite = new ArrayList<Image>();

    public void init() {

        setBackground(Color.white);
        Image island1, island2, island3;

        try {
            bossSpawned = false;
            gameTimer = new Timer(1000, new GameTimerClass());
            gameTimeCounter = 0;
            sea = ImageIO.read(new File("Resources/water.png"));
            island1 = ImageIO.read(new File("Resources/island1.png"));
            island2 = ImageIO.read(new File("Resources/island2.png"));
            island3 = ImageIO.read(new File("Resources/island3.png"));
            playerBullet = ImageIO.read(new File("Resources/bullet.png"));
            enemyBullet = ImageIO.read(new File("Resources/enemybullet1.png"));
            loadingScreen = ImageIO.read(new File("Resources/loading.gif"));

            enemySprite.add(ImageIO.read(new File("Resources/enemy1_1.png")));
            enemySprite.add(ImageIO.read(new File("Resources/enemy1_2.png")));
            enemySprite.add(ImageIO.read(new File("Resources/enemy1_3.png")));

            playerSprite.add(ImageIO.read(new File("Resources/myplane_1.png")));
            playerSprite.add(ImageIO.read(new File("Resources/myplane_2.png")));
            playerSprite.add(ImageIO.read(new File("Resources/myplane_3.png")));

            bossSprite.add(ImageIO.read(new File("Resources/boss.png")));

            I1 = new GameIsland(island1, 100, 100, speed, generator);
            I2 = new GameIsland(island2, 200, 400, speed, generator);
            I3 = new GameIsland(island3, 300, 200, speed, generator);

            // create array of player bullets
            playerBullets = new ArrayList<GameBullets>();
            for (int i = 0; i < 20; i++) {
                playerBullets.add(new GameBullets(playerBullet, -5));
            }
            // create array of enemy bullets
            enemyBullets = new ArrayList<GameBullets>();
            for (int i = 0; i < 20; i++) {
                enemyBullets.add(new GameBullets(enemyBullet, 4));
            }

            // create 5 enemies
            smallEnemies = new ArrayList<GameEnemy>();
            for (int i = 0; i < 3; i++) {
                smallEnemies.add(new GameEnemy(enemySprite, 2, generator, 1));
            }

            // initialize boss
            boss = new GameEnemy(bossSprite, 1, generator, 25);
            boss.x = w / 2 - 240 / 2;
            boss.y = 0 - bossSprite.get(0).getHeight(null);
            boss.boss = true;

            playerOne = new GamePlayer(playerSprite, 200, 360, 5, 1);
            playerTwo = new GamePlayer(playerSprite, 400, 360, 5, 2);

            playerOneHealth = new HealthBar(5, 420, 4);
            playerTwoHealth = new HealthBar(419, 420, 4);

            // generate background audio
            InputStream backgroundMusicPath = new FileInputStream(new File("Resources/background.mid"));
            AudioStream backgroundMusic = new AudioStream(backgroundMusicPath);
            AudioPlayer.player.start(backgroundMusic);
            gameEvents = new GameEvents();
            gameEvents.addObserver(playerOne);
            gameEvents.addObserver(playerTwo);
            KeyControl key = new KeyControl();
            addKeyListener(key);

            gameTimer.start();
        } catch (Exception e) {
            System.out.print("No resources are found");
        }
    }

    public static void explosionSound1() {
        try {
            InputStream backgroundMusicPath = new FileInputStream(new File("Resources/snd_explosion1.wav"));
            AudioStream explosionSound = new AudioStream(backgroundMusicPath);
            AudioPlayer.player.start(explosionSound);
        } catch (Exception e) {
            System.out.println("Error accessing explosionSound1() file");
        }
    }

    public static void explosionSound2() {
        try {
            InputStream backgroundMusicPath = new FileInputStream(new File("Resources/snd_explosion2.wav"));
            AudioStream explosionSound = new AudioStream(backgroundMusicPath);
            AudioPlayer.player.start(explosionSound);
        } catch (Exception e) {
            System.out.println("Error accessing explosionSound2() file");
        }
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawGame() {
        drawBackGroundWithTileImage();

        I1.update();
        I2.update();
        I3.update();

        // draw islands
        I1.draw(this);
        I2.draw(this);
        I3.draw(this);
        
        if(gameEvents.type == 1){
            KeyEvent e = (KeyEvent) gameEvents.event;
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                gameStart = true;
            }
        }
        
        if(WingmanX.gameStart == false){
            g2.drawImage(loadingScreen, w/3-40, h/3, null);
        }

        if (WingmanX.gameStart == true) {
            // spawns boss
            if (gameTimeCounter == 10) {
                bossSpawned = true;
                System.out.println("boss spawned");
            }

            // update enemies
            for (int i = 0; i < smallEnemies.size(); i++) {
                smallEnemies.get(i).update();
            }

            // update player bullets
            for (int i = 0; i < playerBullets.size(); i++) {
                playerBullets.get(i).update();
            }

            // update enemy bullets
            for (int i = 0; i < enemyBullets.size(); i++) {
                enemyBullets.get(i).update();
            }

            if (bossSpawned) {
                boss.update();
                if (boss.currentHealth == 0) {
                    gameEvents.gameOver(2);
                    gameStart = false;
                }
                boss.draw(this);
            }

            // draw fired player bullets
            for (int i = 0; i < playerBullets.size(); i++) {
                playerBullets.get(i).draw(this);
            }

            // draw fired enemy bullets
            for (int i = 0; i < enemyBullets.size(); i++) {
                enemyBullets.get(i).draw(this);
            }

            // draw enemies
            for (int i = 0; i < smallEnemies.size(); i++) {
                smallEnemies.get(i).draw(this);
            }

            // draw players
            playerOne.draw(this);
            playerTwo.draw(this);

            //draw health bars
            playerOneHealth.draw(this);
            playerTwoHealth.draw(this);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawGame();
        g.drawImage(bimg, 0, 0, this);
    }

    @Override
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        setFocusable(true);
        thread.start();
    }

    @Override
    public void run() {

        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

    public static void main(String argv[]) {
        final WingmanX game = new WingmanX();
        game.init();
        JFrame f = new JFrame("WingmanX");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", game);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.start();
    }

}
