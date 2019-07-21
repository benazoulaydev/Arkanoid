package geometry.GameObjectsRemover;

import interfaces.HitListener;
import game.GameLevel;

import graphics.Handlers.Counter;


import geometry.GameObjects.Block;
import geometry.GameObjects.Ball;

/**
 * @author Ben Azoulay
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**.
     * block remover constructor
     * @param game the current game
     * @param removedBalls the number of block to remove
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }


    /**.
     *
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit the block being hit
     * @param hitter the ball that make the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {



            hitter.removeHitListener(this);
            hitter.removeFromGame(this.game);
            this.remainingBalls.decrease(1);



    }

    /**.
     *
     * get remaining balls
     *
     * @return return remaining balls
     */
    public int getRemainingBalls() {
        return this.remainingBalls.getValue();
    }

    /**.
     *
     * get remaining balls counter
     *
     * @return return remaining  counter
     */
    public Counter getBallsCounter() {
        return this.remainingBalls;
    }
}