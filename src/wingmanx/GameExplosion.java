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

    GameExplosion(ArrayList <Image> imageArray, int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = false;
        this.imageArray = imageArray;
        this.imageIndex = 0;
        this.imageWidth = this.imageArray.get(0).getWidth(null);
        this.imageHeight = this.imageArray.get(0).getHeight(null);
    }
    
    public void update(){
        
    }
    
    public void draw(ImageObserver obs){
        if(visible){
            WingmanX.g2.drawImage(imageArray.get(imageIndex), 
                    x-this.imageWidth/2, y-this.imageHeight/2, obs);
        }
    }
}
