package interfaces;
import geometry.GameObjects.Ball;
import geometry.GameObjects.Block;

/**
 * @author Ben azoulay
 */
public interface HitListener {


    /**
     * .
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit the block being hit
     * @param  hitter the ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
