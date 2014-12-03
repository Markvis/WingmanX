/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author markfavis
 */
public class GamePowerUps {

    int x, y, imageWidth, imageHeight, imageIndex, ySpeed;
    boolean visible;
    ArrayList<Image> imageArray;

    GamePowerUps(ArrayList<Image> sprite, Random gen) {

        this.visible = false;
        this.imageArray = sprite;
        this.imageIndex = 0;
        this.imageWidth = this.imageArray.get(0).getWidth(null);
        this.imageHeight = this.imageArray.get(0).getHeight(null);
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = 0 - imageHeight;
        this.ySpeed = 3;
    }

    public void update() {
        if (visible) {
            y += ySpeed;
            this.imageIndex++;
            if (this.imageIndex >= imageArray.size()) {
                imageIndex = 0;
            }
        }
        
        if (WingmanX.playerOne.collision(x, y, imageWidth, imageHeight)) {
            WingmanX.playerOne.power = 1;
            WingmanX.powerUpSpawned = false;
            this.reset();
            
        } else if (WingmanX.playerTwo.collision(x, y, imageWidth, imageHeight)) {
            WingmanX.playerTwo.power = 1;
            WingmanX.powerUpSpawned = false;
            this.reset();
        }
    }
    
    public void reset(){
        this.visible = false;
        this.x = Math.abs(WingmanX.generator.nextInt() % (600 - 30));
        this.y = -20;
    }

    public void draw(ImageObserver obs) {
        if (visible) {
            WingmanX.g2.drawImage(imageArray.get(imageIndex), x, y, obs);
        }
    }
}
