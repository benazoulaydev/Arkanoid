package interfaces;
import biuoop.DrawSurface;
/**
 * @author Ben azoulay
 */
public interface Animation {

    /**
     * .
     * the game-specific logic and stopping conditions are handled in the
     *
     * @param d the drawSurface
     * @param dt the dt
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    boolean shouldStop();
}
