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
    boolean show;
    int health;
/**
 * 
 * @param img the image to be drawn for the enemy class
 * @param speed the speed of the enemy
 * @param gen random number generated of the spawn point
 */
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
            try {
                InputStream backgroundMusicPath = new FileInputStream(new File("Resources/snd_explosion2.wav"));
                AudioStream backgroundMusic = new AudioStream(backgroundMusicPath);
                AudioPlayer.player.start(backgroundMusic);
            } catch (Exception e) {
                System.out.println("Error accessing explosion sound file");
            }
            this.reset();
            show = true;
        }
        else if(y > WingmanX.h){
            this.reset();
        }
        else {
            WingmanX.gameEvents.setValue("");
        }
    }

    public void reset() {
        this.x = Math.abs(WingmanX.generator.nextInt() % (600 - 30));
        this.y = -10;
        //this.speed += 1;
    }

    public void draw(ImageObserver obs) {
        if (show) {
            WingmanX.g2.drawImage(img, x, y, obs);
        }
    }
}
