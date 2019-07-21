package textlevel.Blocks;

import java.util.Map;
import java.util.TreeMap;

import geometry.GameObjects.Block;
import interfaces.BlockCreator;

/**
 *
 * @author Ben Azoulay
 *
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;
    /**.
     * constructor
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<String, Integer>();
        this.blockCreators = new TreeMap<String, BlockCreator>();
    }
    /**.
     *
     * @param s name value
     * @param bc block create
     */
    public void addBlockCreator(String s, BlockCreator bc) {
        this.blockCreators.put(s, bc);
    }
    /**.
     *
     * @param s string value
     * @param i integer
     */
    public void addSpacer(String s, Integer i) {
        this.spacerWidths.put(s, i);
    }
    /**.
     *
     * @param s string param
     * @return true if 's' is a valid space symbol
     */
    // returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        try {
            return this.spacerWidths.containsKey(s);
        } catch (Exception e) {
            System.err.println("can't search for null key");
            return false;
        }
    }
    /**.
     *
     * @param s string value
     * @return true if 's' is a valid block symbol.
     */
    // returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        try {
            return this.blockCreators.containsKey(s);
        } catch (Exception e) {
            System.err.println("can't search for null key");
            return false;
        }
    }
    /**.
     *
     * @param s string val
     * @param xpos val
     * @param ypos val
     * @return a block according to the definitions associated
     */
    // Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }
    /**
     *
     * @param s string val
     * @return the width in pixels associated with the given spacer-symbol.
     */
    // Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s).intValue();
    }
}
