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
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author markfavis
 */
public class GamePlayer implements Observer {

    Image img;
    int x, y, speed, width, height;
    Rectangle bbox;
    boolean boom;
    int health;

    GamePlayer(Image img, int x, int y, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        width = img.getWidth(null);
        height = img.getHeight(null);
        boom = false;
        health = 3;
    }

    public void draw(ImageObserver obs) {
        WingmanX.g2.drawImage(img, x, y, obs);
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
        WingmanX.GameEvents ge = (WingmanX.GameEvents) arg;
        if (ge.type == 1) {
            KeyEvent e = (KeyEvent) ge.event;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    x -= speed;
                    break;
                case KeyEvent.VK_RIGHT:
                    x += speed;
                    break;
                case KeyEvent.VK_UP:
                    y -= speed;
                    break;
                case KeyEvent.VK_DOWN:
                    y += speed;
                    break;
                default:
                    if (e.getKeyChar() == ' ') {
                        System.out.println("Fire");
                    }
            }
        } else if (ge.type == 2) {
            String msg = (String) ge.event;
            if (msg.equals("Explosion")) {
                health--;
                System.out.println("Got hit, player hp: " + health);
            }
        }
    }
}
