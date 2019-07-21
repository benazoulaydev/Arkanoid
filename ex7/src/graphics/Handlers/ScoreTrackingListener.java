package graphics.Handlers;


import interfaces.HitListener;


import geometry.GameObjects.Ball;
import geometry.GameObjects.Block;


/**
 * @author Ben Azoulay
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**.
     * constructor
     *
     * @param scoreCounter the score counter
     *
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**.
     *  add point if block was hit
     *
     * @param beingHit the block being hit
     * @param hitter the ball that it the block
     *
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // hitting a block but not destroying it 5 points
       if (beingHit.getHitPoints() > 1) {
           this.currentScore.increase(5);
       } else if (beingHit.getHitPoints() == 1) {
           // hitting a block and destroying it 10 + 5 points
           this.currentScore.increase(15);

       }
    }
}