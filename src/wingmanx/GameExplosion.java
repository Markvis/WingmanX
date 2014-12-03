/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 *
 * @author markfavis
 */
public class GameExplosion {
    
    
    int x, y, imageWidth, imageHeight, imageIndex;
    boolean visible;
    ArrayList <Image> imageArray;

    GameExplosion(ArrayList <Image> sprite) {
        this.x = 0;
        this.y = 0;
        this.visible = false;
        this.imageArray = sprite;
        this.imageIndex = 0;
        this.imageWidth = this.imageArray.get(0).getWidth(null);
        this.imageHeight = this.imageArray.get(0).getHeight(null);
    }
    
    public void update(){
        if(visible){
            this.imageIndex++;
            if(this.imageIndex >= imageArray.size()){
                visible = false;
                imageIndex = 0;
            }
        }
    }
    
    public void draw(ImageObserver obs){
        if(visible){
            WingmanX.g2.drawImage(imageArray.get(imageIndex), 
                    x-this.imageWidth/2, y-this.imageHeight/2, obs);
        }
    }
}
