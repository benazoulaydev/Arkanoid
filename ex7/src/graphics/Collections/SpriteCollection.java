/**
 * @author Ben Azoulay
 */
package graphics.Collections;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
import interfaces.Sprite;



/**
 * sprites.
 */
public class SpriteCollection {
    private List<Sprite> objects;

    /**
     * Constructor.
     * if empty param initialize
     */
    public SpriteCollection() {
        this.objects  = new ArrayList<Sprite>();
    }

    /**
     *
     * add a sprite object.
     * @param s a sprite obj
     */
    public void addSprite(Sprite s) {
        this.objects.add(s);

    }

    /**
     * remove a sprite object.
     * @param s a sprite obj
     */
    public void removeSprite(Sprite s) {
        this.objects.remove(s);

    }

    /**
     *
     *   call timePassed() on all sprites.
     * @param dt the dt
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).timePassed(dt);
        }

    }

    /**
     *
     *  call drawOn(d) on all sprites.
     *
     *  @param d the drawsurface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite obj : this.objects) {
            obj.drawOn(d);
        }
    }
}
