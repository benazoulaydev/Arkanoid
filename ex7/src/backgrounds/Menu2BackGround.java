package backgrounds;


import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import interfaces.Sprite;
/**
 *
 * @author Ben azoulay
 *
 */
public class Menu2BackGround implements Sprite {

    /**
     * draw object.
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {
        Image image = null;
        try {
            InputStream iS = ClassLoader.getSystemClassLoader().
                    getResourceAsStream("background_images/menu2.jpg");
            image = ImageIO.read(iS);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        d.drawImage(0, 0, image);
    }

    /**
     * alart that time passed.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }

}
