package textlevel.Color;


import java.awt.Color;

import biuoop.DrawSurface;
import interfaces.Fill;

/**
 *
 * @author Ben Azoulay
 *
 */
public class ColorFill implements Fill {
    private int width;
    private int height;
    private Color color;
    /**.
     * puts null in color
     */
    public ColorFill() {
        this.color = null;
    }
    /**.
     *
     * @param width value
     * @param height value
     * @param c color value
     */
    public ColorFill(int width, int height, Color c) {
        this.width = width;
        this.height = height;
        this.color = c;
    }
    /**.
     *
     * @param c color value
     */
    public ColorFill(Color c) {
        this.width = 0;
        this.height = 0;
        this.color = c;
    }
    /**.
     *
     * @param width1 value
     */
    public void setWidth(int width1) {
        this.width = width1;
    }
    /**.
     *
     * @param height1 the value
     */
    public void setHeight(int height1) {
        this.height = height1;
    }
    /**.
     *
     * @param d surface value
     * @param x value
     * @param y value
     */
    public void fillBlock(DrawSurface d, int x, int y) {
        d.setColor(this.color);
        d.fillRectangle(x, y, this.width, this.height);
    }

    /**.
     *
     * @return if empty
     */
    public boolean isEmpty() {
        if (this.color == null) {
            return true;
        }
        return false;
    }

    /**.
     *
     * @return if border to be updated
     */
    public boolean needToUpdateBorders() {
        if (this.width == 0) {
            return true;
        }
        if (this.height == 0) {
            return true;
        }
        return false;
    }
}

