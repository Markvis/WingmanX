package wingmanx;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author markfavis
 */
public class Island {

    Image img;
    int x, y, speed;
    Random gen;

    Island(Image img, int x, int y, int speed, Random gen) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gen = gen;
    }

    public void update() {
        y += speed;
        if (y >= WingmanX.h) {
            y = -100;
            x = Math.abs(gen.nextInt() % (WingmanX.w - 30));
        }
    }

    public void draw(ImageObserver obs) {
        WingmanX.g2.drawImage(img, x, y, obs);
    }
}
