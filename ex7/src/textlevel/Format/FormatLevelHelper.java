package textlevel.Format;

import interfaces.Sprite;
import geometry.GameObjects.Block;

import java.util.List;
/**
 *
 * @author Ben Azoulay
 *
 */
public class FormatLevelHelper {
    private Sprite backGround;
    private int numberOfBlocksToRemove;
    private List<Block> blocks;
    private int boardWidth;
    private int boardHeight;
    /**
     *
     * @param backG back ground
     * @param blockRemove the number of blocks to remove
     * @param blocks the list of blocks
     * @param boardW the board width
     * @param boardH the board height
     */
    public FormatLevelHelper(Sprite backG, int blockRemove,
                             List<Block> blocks, int boardW, int boardH) {
        this.backGround = backG;
        this.numberOfBlocksToRemove = blockRemove;
        this.blocks = blocks;
        this.boardWidth = boardW;
        this.boardHeight = boardH;
    }
    /**
     *
     * @return the background
     */
    public Sprite getBackGround() {
        return this.backGround;
    }
    /**
     *
     * @return the number of balls to remove
     */
    public int getNumBlocks() {
        return this.numberOfBlocksToRemove;
    }
    /**
     *
     * @return the list of blocks
     */
    public List<Block> getListBlocks() {
        return this.blocks;
    }
    /**
     *
     * @return the board width
     */
    public int getWidth() {
        return this.boardWidth;
    }
    /**
     *
     * @return the board height
     */
    public int getHeight() {
        return this.boardHeight;
    }
}

