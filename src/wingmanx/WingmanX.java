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
    Image myPlane;
    private BufferedImage bimg;
    static Graphics2D g2;
    int speed = 1, move = 0;
    static Random generator = new Random();
    GameIsland I1, I2, I3;
    static GamePlayer m;
    static final int w = 640, h = 480; // fixed size window game 
    static GameEvents gameEvents;
    ArrayList<GameEnemy> enemies;
    long time;

    public void init() {

        time = System.nanoTime();
        setBackground(Color.white);
        Image island1, island2, island3, enemyImg;

        try {
            //sea = getSprite("Resources/water.png");
            sea = ImageIO.read(new File("Resources/water.png"));
            island1 = ImageIO.read(new File("Resources/island1.png"));
            island2 = ImageIO.read(new File("Resources/island2.png"));
            island3 = ImageIO.read(new File("Resources/island3.png"));
            myPlane = ImageIO.read(new File("Resources/myplane_1.png"));
            enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));

            I1 = new GameIsland(island1, 100, 100, speed, generator);
            I2 = new GameIsland(island2, 200, 400, speed, generator);
            I3 = new GameIsland(island3, 300, 200, speed, generator);
            m = new GamePlayer(myPlane, 300, 360, 5);

            // create 3 enemies
            enemies = new ArrayList<GameEnemy>();
            for (int i = 0; i < 3; i++) {
                enemies.add(new GameEnemy(enemyImg, 1, generator));
            }

            // generate background audio
            InputStream backgroundMusicPath = new FileInputStream(new File("Resources/background.mid"));
            AudioStream backgroundMusic = new AudioStream(backgroundMusicPath);
            AudioPlayer.player.start(backgroundMusic);

            gameEvents = new GameEvents();
            gameEvents.addObserver(m);
            KeyControl key = new KeyControl();
            addKeyListener(key);
        } catch (Exception e) {
            System.out.print("No resources are found");
        }
    }

    public class GameEvents extends Observable {

        int type;
        Object event;

        public void setValue(KeyEvent e) {
            type = 1; // let's assume this means key input. 
            //Should use CONSTANT value for this when you program
            event = e;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }

        public void setValue(String msg) {
            type = 2;
            event = msg;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }
    }

    public class KeyControl extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            gameEvents.setValue(e);
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

    public void drawDemo() {
        drawBackGroundWithTileImage();
        I1.update();
        I2.update();
        I3.update();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        I1.draw(this);
        I2.draw(this);
        I3.draw(this);
        m.draw(this);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(this);
        }
    }

    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawDemo();
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
