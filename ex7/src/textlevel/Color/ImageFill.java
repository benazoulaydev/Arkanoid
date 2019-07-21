package textlevel.Color;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import interfaces.Fill;

/**
 *
 * @author Ben Azoulay
 *
 */
public class ImageFill implements Fill {
    private Image img;
    /**.
     * the image fill
     */
    public ImageFill() {
        this.img = null;
    }
    /**.
     *
     * @param path the file path
     */
    public ImageFill(String path) {
        Image image = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().
                    getResourceAsStream(path);
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("failed! path:" + path);
        }
        this.img = image;
        //this.img = new ImageIcon(path).getImage();
    }

    /**.
     *
     * @param d surface value
     * @param x value
     * @param y value
     */
    public void fillBlock(DrawSurface d, int x, int y) {
        d.drawImage(x, y, img);
    }

    /**.
     *
     * @return if empty
     */
    public boolean isEmpty() {
        return false;
    }

    /**.
     *
     * @return if border to be updated
     */
    public boolean needToUpdateBorders() {
        return false;
    }

}
