package textlevel.Blocks;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import geometry.GameObjects.Block;
import interfaces.BlockCreator;
import interfaces.Fill;

/**
 *
 * @author Ben Azoulay
 *
 */
public class DefaultBlockCreator implements BlockCreator {
    private int height;
    private int width;
    private int hitPoints;
    private List<Fill> fills;
    private Color stroke;
    /**.
     *
     * @param height val
     * @param width val
     * @param hitPoints val
     * @param fills val
     * @param stroke val
     */
    public DefaultBlockCreator(int height, int width,
                               int hitPoints, List<Fill> fills, Color stroke) {
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
        this.stroke = stroke;
        this.fills = new ArrayList<Fill>(fills);
    }
    @Override
    public Block create(int xpos, int ypos) {
        Block b = new Block(xpos, ypos, this.height, this.width,
                this.hitPoints, this.fills, this.stroke);

        return b;
    }

}
