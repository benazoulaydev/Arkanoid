package interfaces;
/**
 * @author Ben Azoulay
 *
 * @param <T>
 */
public interface Task<T> {
    /**.
     * task class
     * @return T
     */
    T run();
}
