package interfaces;
import geometry.Bases.Velocity;
import geometry.GameObjects.Block;
import java.util.List;

/**
 * @author Ben azoulay
 */
public interface LevelInformation {
    /**
     * .
     * know the number of balls
     *
     * @return the number of balls
     */
    int numberOfBalls();
    /**
     * .
     * The initial velocity of each ball
     *  Note that initialBallVelocities().size() == numberOfBalls()
     * @return The initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * .
     * @return the speed of the paddle
     */
    int paddleSpeed();

    /**
     * .
     * @return the width of the paddle
     */
    int paddleWidth();

    /**
     * .
     * @return the level name that will be displayed at the top of the screen.
     */
    String levelName();

    /**
     * .
     * @return a sprite with the backgrounds of the level
     */
    Sprite getBackground();

    /**
     * .
     * The Blocks that make up this level, each block contains
     *  its size, color and location.
     * @return the blocks
     */
    List<Block> blocks();


    /**
     * .
     * Number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return the number of blocks to remove
     */
    int numberOfBlocksToRemove();
}
