/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author markfavis
 */
public class GameEnemy {

    Image img;
    int x, y, sizeX, sizeY, speed;
    Random gen;
    boolean show, spawned;
    int originalHealth;
    int currentHealth;

    /**
     *
     * @param img the image to be drawn for the enemy class
     * @param speed the speed of the enemy
     * @param gen random number generated of the spawn point
     */
    GameEnemy(Image img, int speed, Random gen, int passedHealth) {
        this.img = img;
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = -20;
        this.speed = speed;
        this.gen = gen;
        this.show = false;
        this.spawned = false;
        this.originalHealth = passedHealth;
        this.currentHealth = this.originalHealth;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }

    public void update() {
        if (show && spawned) {
            y += speed;
        }

        // check if theres collision on the visible bullets
        for (GameBullets playerBullet : WingmanX.playerBullets) {
            if (playerBullet.collision(x, y, sizeX, sizeY) && playerBullet.show == true) {
                this.currentHealth--;
                if (currentHealth <= 0) {
                    show = false;
                    WingmanX.explosionSound2();
                    playerBullet.reset();
                    this.reset();
                    show = true;
                }
            }
        }

        // check player one and two collisions
        if (WingmanX.playerOne.collision(x, y, sizeX, sizeY)) {
            show = false;
            // You need to remove this one and increase score etc
            WingmanX.gameEvents.setValue("Explosion player 1");
            WingmanX.gameEvents.setValue("");
            WingmanX.explosionSound1();
            this.reset();
            show = true;
            WingmanX.playerOneHealth.update();
        } else if (WingmanX.playerTwo.collision(x, y, sizeX, sizeY)) {
            show = false;
            // You need to remove this one and increase score etc
            WingmanX.gameEvents.setValue("Explosion player 2");
            WingmanX.gameEvents.setValue("");
            WingmanX.explosionSound1();
            this.reset();
            show = true;
            WingmanX.playerTwoHealth.update();
        } // if enemy goes past frame, reset it
        else if (y > WingmanX.h) {
            this.reset();
        } // reset gameevents value
        else {
            WingmanX.gameEvents.setValue("");
        }
    }

    public void reset() {
        this.show = false;
        this.spawned = false;
        this.x = Math.abs(WingmanX.generator.nextInt() % (600 - 30));
        this.y = -10;
        this.currentHealth = this.originalHealth;
    }

    public void draw(ImageObserver obs) {
        if (show && spawned) {
            WingmanX.g2.drawImage(img, x, y, obs);
        }
    }

    public void fire() {
        for (GameBullets enemyBullet : WingmanX.enemyBullets) {
            if (enemyBullet.show == false) {
                enemyBullet.show = true;
                enemyBullet.x = this.x + 2;
                enemyBullet.y = this.y + 8;
                break;
            }
        }
    }
}
