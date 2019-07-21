package interfaces;

import geometry.GameObjects.Block;

/**
 *
 * @author Ben Azoulay
 *
 */
public interface BlockCreator {

    // Create a block at the specified location.
    /**
     *
     * @param xpos value
     * @param ypos value
     * @return creates block
     */

    Block create(int xpos, int ypos);
}

