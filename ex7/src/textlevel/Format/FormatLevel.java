package textlevel.Format;

import interfaces.LevelInformation;

import interfaces.Sprite;
import geometry.GameObjects.Block;
import geometry.Bases.Velocity;

import java.util.List;
/**
 *
 * @author Ben Azoulay
 *
 */
public class FormatLevel implements LevelInformation {
    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite backGround;
    private int numberOfBlocksToRemove;
    private List<Block> blocks;
    private int boardWidth;
    private int boardHeight;
    /**
     *
     * @param numberOfBalls number of balls
     * @param ballsV the balls velocities
     * @param paddleS the paddle speed
     * @param paddleW the paddle width
     * @param levelName the level name
     * @param fh the format level helper
     */
    public FormatLevel(int numberOfBalls, List<Velocity> ballsV, int paddleS,
                       int paddleW, String levelName, FormatLevelHelper fh) {
        this.numberOfBalls = numberOfBalls;
        this.initialBallVelocities = ballsV;
        this.paddleSpeed = paddleS;
        this.paddleWidth = paddleW;
        this.levelName = levelName;
        this.backGround = fh.getBackGround();
        this.numberOfBlocksToRemove = fh.getNumBlocks();
        this.blocks = fh.getListBlocks();
        this.boardHeight = fh.getHeight();
        this.boardWidth = fh.getWidth();
    }

    /**
     * @return the board width
     */
    public int width() {
        return this.boardWidth;
    }
    /**
     * @return the board height
     */
    public int height() {
        return this.boardHeight;
    }
    /**
     * @return the number of balls
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }
    /**
     * @return list of all the balls velocities
     */
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }
    /**
     * @return the level background
     */
    public Sprite getBackground() {
        return this.backGround;
    }

    /**
     * @return list of the level blocks
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * @return the number of blocks to remove
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
    /**
     * @return the paddle speed
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return the paddle width
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return the level name
     */
    public String levelName() {
        return this.levelName;
    }
}
