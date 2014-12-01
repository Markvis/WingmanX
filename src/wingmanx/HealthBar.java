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
    int x, y;
    int playerHealth;

    public HealthBar(int x, int y,int playerHealth) {
        this.x = x;
        this.y = y;
        this.playerHealth = playerHealth;
        
        try {
            fullHealth = ImageIO.read(new File("Resources/health.png"));
            health2 = ImageIO.read(new File("Resources/health1.png"));
            health1 = ImageIO.read(new File("Resources/health2.png"));
            health0 = ImageIO.read(new File("Resources/health3.png"));
        }
        catch(Exception e){
            System.out.println("file access error Healthbar class");
        }
    }
    
    public void update(){
        this.playerHealth -=1;
    }
    
    public void draw(ImageObserver obs){
        if(this.playerHealth == 3)
            WingmanX.g2.drawImage(fullHealth, x, y, obs);
        else if(this.playerHealth == 2){
            WingmanX.g2.drawImage(health2, x, y, obs);
        }
        else if(this.playerHealth == 1){
            WingmanX.g2.drawImage(health1, x, y, obs);
        }
        else
            WingmanX.g2.drawImage(health0, x, y, obs);
    }
}