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
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author markfavis
 */
public class GameEnemy {

    int x, y, sizeX, sizeY, ySpeed, xSpeed;
    Random gen;
    boolean show, spawned, boss;
    int originalHealth;
    int currentHealth;
    int imageIndex;
    ArrayList<Image> imageArray;
    ArrayList<Image> explosionArray = new ArrayList<Image>();
    boolean enteredFrame, whiteEnemy;

    /**
     *
     * @param img the image to be drawn for the enemy class
     * @param speed the speed of the enemy
     * @param gen random number generated of the spawn point
     */
    GameEnemy(ArrayList<Image> arrayOfImages, int speed, Random gen, int passedHealth) {
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = -20;
        this.ySpeed = speed;
        this.xSpeed = 1;
        this.gen = gen;
        this.show = false;
        this.spawned = false;
        this.originalHealth = passedHealth;
        this.currentHealth = this.originalHealth;
        this.imageIndex = 0;
        this.imageArray = arrayOfImages;
        this.boss = false;
        this.enteredFrame = false;
        this.whiteEnemy = false;
        sizeX = arrayOfImages.get(0).getWidth(null);
        sizeY = arrayOfImages.get(0).getHeight(null);
        System.out.println("w:" + sizeX + " y:" + sizeY);

        try {
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_1.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_2.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_3.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_4.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_5.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_6.png")));
            explosionArray.add(ImageIO.read(new File("Resources/explosion1_7.png")));
        } catch (Exception e) {
            System.out.println("Error explosion file gameenemy.java");
        }
    }

    public void update() {
        if (currentHealth <= 0) {
            show = false;
            this.reset();
            show = true;
        }

        if (show && spawned) {
            y += ySpeed;
        }
        
        if(boss && y > WingmanX.h/2 - sizeY*3/4 || (y == 0 && boss && enteredFrame)){
            enteredFrame = true;
            ySpeed *= -1;
        }
        if(boss && x > WingmanX.w-sizeX || (x == 0 && boss && enteredFrame)){
            xSpeed *= -1;
        }
        if(enteredFrame || whiteEnemy){
            x += xSpeed;
        }

        if (imageIndex == imageArray.size() - 1) {
            imageIndex = 0;
        } else {
            imageIndex++;
        }

        // check if theres collision on the visible bullets
        for (GameBullets playerBullet : WingmanX.playerBullets) {
            if (playerBullet.collision(x, y, sizeX, sizeY) && playerBullet.show == true) {
                this.currentHealth--;
                playerBullet.reset();
                WingmanX.explosionSound2();
                WingmanX.gameScore += 10;
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
        if(whiteEnemy){
            if(x < WingmanX.w/2){
                xSpeed = 1;
            }
            else 
                xSpeed = -1;
        }
        this.y = -10;
        this.currentHealth = this.originalHealth;
    }

    public void draw(ImageObserver obs) {
        if (show && spawned) {
            WingmanX.g2.drawImage(imageArray.get(imageIndex), x, y, obs);
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
