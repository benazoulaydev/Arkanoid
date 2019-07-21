package interfaces;
import java.util.List;

/**
 * @author Ben azoulay
 *
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**.
     * adds selection
     * @param key the key pressed
     * @param message the message
     * @param returnVal the value returned
     */
    void addSelection(String key, String message, T returnVal);
    /**.
     *
     * @return status
     */
    T getStatus();

    /**.
     * adds submenue
     * @param key the key pressed
     * @param message the massage
     * @param subMenu the submenue
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
    /**.
     *
     * @return keys
     */
    List<String> getKeys();
    /**.
     *
     * @return messages
     */
    List<String> getMessages();
    /**.
     *
     * @return values
     */
    List<T> getVals();
    /**.
     *
     * @return submenus
     */
    List<Menu<T>> getSubMenus();
    // 1 - regular option / 2 - sub menu
    /**.
     *
     * @return Types
     */
    List<Integer> getTypes();
    /**.
     *
     * @return backgrounds
     */
    Sprite getBackGround();
    /**.
     *
     * @return title
     */
    String getTitle();
}