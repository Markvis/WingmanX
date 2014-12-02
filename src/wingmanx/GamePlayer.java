/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
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
public class GamePlayer implements Observer {

    int x, y, speed, width, height;
    Rectangle bbox;
    boolean boom;
    static int sensitivity = 15;
    int playerNumber;
    int imageIndex;
    ArrayList <Image> imageArray;

    GamePlayer(ArrayList <Image> arrayOfImages, int x, int y, int speed, int pNum) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.imageArray = arrayOfImages;
        this.imageIndex = 0;
        width = imageArray.get(0).getWidth(null);
        height = imageArray.get(0).getHeight(null);
        boom = false;
        this.playerNumber = pNum;
    }

    public void draw(ImageObserver obs) {
        WingmanX.g2.drawImage(imageArray.get(imageIndex), x, y, obs);
    }

    public boolean collision(int x, int y, int w, int h) {
        bbox = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherBBox = new Rectangle(x, y, w, h);
        if (this.bbox.intersects(otherBBox)) {
            return true;
        }
        return false;
    }

    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;
        
        // update arraylist index
        if(imageIndex == imageArray.size() - 1){
            imageIndex = 0;
        }
        else {
            imageIndex++;
        }
        
        for (int i = 0; i < WingmanX.enemyBullets.size(); i++) {
            if (WingmanX.enemyBullets.get(i).collision(x, y, width, height)
                    && WingmanX.enemyBullets.get(i).show == true) {
                if (this.playerNumber == 1) {
                    WingmanX.playerOneHealth.update();
                }
                if (this.playerNumber == 2) {
                    WingmanX.playerTwoHealth.update();
                }
                WingmanX.explosionSound1();
                WingmanX.enemyBullets.get(i).reset();
            }
        }

        if (playerNumber == 1 && ge.type == 1) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (x > 0) {
                        x -= speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (x < WingmanX.w - width) {
                        x += speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (y > 0) {
                        y -= speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (y < WingmanX.h - height - 20) {
                        y += speed + sensitivity;
                    }
                    break;
                default:
                    if (e.getKeyChar() == '/') {
                        fire();
                        System.out.println("player 1 Fire");
                    }
            }
        } else if (playerNumber == 2 && ge.type == 1) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (x > 0) {
                        x -= speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (x < WingmanX.w - width) {
                        x += speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_W:
                    if (y > 0) {
                        y -= speed + sensitivity;
                    }
                    break;
                case KeyEvent.VK_S:
                    if (y < WingmanX.h - height - 20) {
                        y += speed + sensitivity;
                    }
                    break;
                default:
                    if (e.getKeyChar() == ' ') {
                        fire();
                        System.out.println("player 2 Fire");
                    }
            }
        } 
        else if (ge.type == 2) {
            String msg = (String) ge.event;
            switch (msg) {
                case "Explosion player 1":
                    System.out.println("Player 1 hit!");
                    break;
                case "Explosion player 2":
                    System.out.println("Player 2 hit!");
                    break;
            }
        }
    }

    public void fire() {
        for (int i = 0; i < WingmanX.playerBullets.size(); i++) {
            if (WingmanX.playerBullets.get(i).show == false) {
                WingmanX.playerBullets.get(i).show = true;
                WingmanX.playerBullets.get(i).x = this.x + 16;
                WingmanX.playerBullets.get(i).y = this.y + 16;
                break;
            }
        }
    }
}
