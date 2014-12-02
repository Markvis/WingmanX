/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author markfavis
 */
public class HealthBar {

    Image fullHealth;
    Image health2;
    Image health1;
    Image health0;
    Image spareLifeImg;
    int x, y;
    int playerHealth;
    int spareLives;
    boolean gameOver;

    public HealthBar(int x, int y, int playerHealth) {
        this.x = x;
        this.y = y;
        this.playerHealth = playerHealth;
        spareLives = 3;
        gameOver = false;

        try {
            fullHealth = ImageIO.read(new File("Resources/health.png"));
            health2 = ImageIO.read(new File("Resources/health1.png"));
            health1 = ImageIO.read(new File("Resources/health2.png"));
            health0 = ImageIO.read(new File("Resources/health3.png"));
            spareLifeImg = ImageIO.read(new File("Resources/life.png"));
        } catch (Exception e) {
            System.out.println("file access error Healthbar class");
        }
    }

    public void update() {
        playerHealth -= 1;
        if (playerHealth == 0) {
            if (spareLives > 0) {
                playerHealth = 4;
                spareLives--;
            } else {
                gameOver = true;
                WingmanX.gameEvents.gameOver(1);
            }
        }
    }

    public void draw(ImageObserver obs) {
        
        // draw lives
        for(int i = 0; i < spareLives; i++){
            WingmanX.g2.drawImage(spareLifeImg, x + spareLifeImg.getWidth(null) * i ,y, obs);
        }
        
        // draw healthbar
        if (playerHealth == 4) {
            WingmanX.g2.drawImage(fullHealth, x + spareLifeImg.getWidth(null)*3, y, obs);
        } else if (playerHealth == 3) {
            WingmanX.g2.drawImage(health2, x + spareLifeImg.getWidth(null)*3, y, obs);
        } else if (playerHealth == 2) {
            WingmanX.g2.drawImage(health1, x + spareLifeImg.getWidth(null)*3, y, obs);
        } else if (playerHealth == 1) {
            WingmanX.g2.drawImage(health0, x + spareLifeImg.getWidth(null)*3, y, obs);
        }
    }
}
