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
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**.
     * block remover constructor
     * @param game the current game
     * @param removedBlocks the number of block to remove
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
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
        if (beingHit.getHitPoints() == 1) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            this.remainingBlocks.decrease(1);
        }

    }

    /**.
     *
     * get remaining blocks
     *
     * @return return remaining blocks
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }
}