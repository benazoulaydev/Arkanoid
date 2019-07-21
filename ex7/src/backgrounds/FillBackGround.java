package backgrounds;


import biuoop.DrawSurface;
import interfaces.Sprite;
import interfaces.Fill;
/**
 *
 * @author Ben azoulay
 *
 */
public class FillBackGround implements Sprite {

    private Fill fill;
    private int x;
    private int y;
    /**.
     * the informations for the fill
     * @param f what to fill with
     * @param x parameter
     * @param y parameter
     */
    public FillBackGround(Fill f, int x, int y) {
        this.fill = f;
        this.x = x;
        this.y = y;
    }

    /**
     * draw object.
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {
        this.fill.fillBlock(d, this.x, this.y);
    }

    /**
     * alart that time passed.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }

}
