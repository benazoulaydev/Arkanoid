package interfaces;


import biuoop.DrawSurface;
/**
 *
 * @author Ben Azoulay
 *
 */
public interface Fill {
    /**.
     *
     * @param d surface value
     * @param x value
     * @param y value
     */
    void fillBlock(DrawSurface d, int x, int y);
    /**.
     *
     * @return if empty
     */
    boolean isEmpty();
    /**.
     *
     * @return if border to be updated
     */
    boolean needToUpdateBorders();

}
