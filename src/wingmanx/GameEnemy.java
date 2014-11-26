/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author markfavis
 */
public class GameEnemy {

    Image img;
    int x, y, sizeX, sizeY, speed;
    Random gen;
    boolean show;
    int health;

    GameEnemy(Image img, int speed, Random gen) {
        this.img = img;
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = -20;
        this.speed = speed;
        this.gen = gen;
        this.show = true;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }

    public void update() {
        y += speed;
        if (WingmanX.m.collision(x, y, sizeX, sizeY)) {
            show = false;
            // You need to remove this one and increase score etc
            WingmanX.gameEvents.setValue("Explosion");
            WingmanX.gameEvents.setValue("");
            this.reset();
            show = true;
        } else {
            WingmanX.gameEvents.setValue("");
        }
    }

    public void reset() {
        this.x = Math.abs(WingmanX.generator.nextInt() % (600 - 30));
        this.y = -10;
    }

    public void draw(ImageObserver obs) {
        if (show) {
            WingmanX.g2.drawImage(img, x, y, obs);
        }
    }
}
