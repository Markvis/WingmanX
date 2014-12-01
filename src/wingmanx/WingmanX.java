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

/**
 *
 * @author markfavis
 */
public class WingmanX extends JApplet implements Runnable {

    private Thread thread;
    Image sea;
    Image playerOneImg;
    Image playerTwoImg;
    private BufferedImage bimg;
    static Graphics2D g2;
    int speed = 1, move = 0;
    static Random generator = new Random();
    GameIsland I1, I2, I3;
    static GamePlayer playerOne;
    static GamePlayer playerTwo;
    static final int w = 640, h = 480; // fixed size window game 
    static GameEvents gameEvents;
    ArrayList<GameEnemy> smallEnemies;
    long time;
    static HealthBar playerOneHealth;
    static HealthBar playerTwoHealth;

    public void init() {

        time = System.nanoTime();
        setBackground(Color.white);
        Image island1, island2, island3, smallEnemyImg;

        try {
            //sea = getSprite("Resources/water.png");
            sea = ImageIO.read(new File("Resources/water.png"));
            island1 = ImageIO.read(new File("Resources/island1.png"));
            island2 = ImageIO.read(new File("Resources/island2.png"));
            island3 = ImageIO.read(new File("Resources/island3.png"));
            playerOneImg = ImageIO.read(new File("Resources/myplane_1.png"));
            playerTwoImg = ImageIO.read(new File("Resources/myplane_2.png"));
            smallEnemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));

            I1 = new GameIsland(island1, 100, 100, speed, generator);
            I2 = new GameIsland(island2, 200, 400, speed, generator);
            I3 = new GameIsland(island3, 300, 200, speed, generator);
            playerOne = new GamePlayer(playerOneImg, 200, 360, 5, 1);
            playerTwo = new GamePlayer(playerTwoImg, 400, 360, 5, 2);
            
            playerOneHealth = new HealthBar(5, 420, 3);
            playerTwoHealth = new HealthBar(515, 420, 3);

            // create 3 enemies
            smallEnemies = new ArrayList<GameEnemy>();
            for (int i = 0; i < 5; i++) {
                smallEnemies.add(new GameEnemy(smallEnemyImg, 1, generator));
            }

            // generate background audio
//            InputStream backgroundMusicPath = new FileInputStream(new File("Resources/background.mid"));
//            AudioStream backgroundMusic = new AudioStream(backgroundMusicPath);
//            AudioPlayer.player.start(backgroundMusic);

            gameEvents = new GameEvents();
            gameEvents.addObserver(playerOne);
            gameEvents.addObserver(playerTwo);
            KeyControl key = new KeyControl();
            addKeyListener(key);
        } catch (Exception e) {
            System.out.print("No resources are found");
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
        // update enemies
        for (int i = 0; i < smallEnemies.size(); i++) {
            smallEnemies.get(i).update();
        }

        I1.draw(this);
        I2.draw(this);
        I3.draw(this);
        // draw players
        playerOne.draw(this);
        playerTwo.draw(this);
        // draw enemies
        for (int i = 0; i < smallEnemies.size(); i++) {
            smallEnemies.get(i).draw(this);
        }
        
        //draw health bars
        playerOneHealth.draw(this);
        playerTwoHealth.draw(this);
    }

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

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        setFocusable(true);
        thread.start();
    }

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
