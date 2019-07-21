package interfaces;

/**
 * @author Ben azoulay
 */
public interface HitNotifier {

    /**
     * .
     * Add hl as a listener to hit events.
     *
     * @param hl the hitlistener
     */
    void addHitListener(HitListener hl);

    /**
     * .
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hitlistener
     */
    void removeHitListener(HitListener hl);
}