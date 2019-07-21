package graphics.Handlers;

import interfaces.HitListener;


import geometry.GameObjects.Ball;
import geometry.GameObjects.Block;

/**
 *
 * @author Ben Azoulay
 *
 */
public class PrintingHitListener implements HitListener {


    /**.
     * hit event test print func
     * @param beingHit the block being hit
     * @param hitter the ball making the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }
}