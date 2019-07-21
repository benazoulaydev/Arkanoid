package graphics.screenWindow;
import biuoop.DrawSurface;
import interfaces.Menu;
import interfaces.Sprite;
import biuoop.KeyboardSensor;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * @author Ben Azoulay
 *
 * @param <T>
 */
public class MenuAnimation<T> implements Menu<T> {

    private KeyboardSensor keyboard;
    private Sprite background;
    private String menuTitle;
    private T status;
    private boolean stop;
    private List<String> keys;
    private List<Integer> type;
    private List<String> messages;
    private List<Menu<T>> submenus;
    private List<T> vals;



    /**.
     * constructor
     * @param title the menu title
     * @param backGround the background
     * @param keyboard the menu title
     */
    public MenuAnimation(String title, Sprite backGround,
                         KeyboardSensor keyboard) {

        this.keys = new ArrayList<String>();
        this.type = new ArrayList<Integer>();
        this.messages = new ArrayList<String>();
        this.vals = new ArrayList<T>();
        this.submenus = new ArrayList<Menu<T>>();
        this.menuTitle = new String(title);
        this.background = backGround;
        this.status = null;
        this.keyboard = keyboard;
        this.stop = false;

    }




    /**.
     * adds selection
     * @param key the key pressed
     * @param message the message
     * @param returnVal the value returned
     */
    public void addSelection(String key, String message, T returnVal) {
        Integer type1 = 1;
        this.keys.add(key);
        this.type.add(type1);
        this.messages.add(message);
        this.vals.add(returnVal);
    }
    /**.
     *
     * @return status
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * .
     * the game-specific logic and stopping conditions are handled in the
     *
     * @param d the drawSurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //draw background
        this.background.drawOn(d);

//        d.setColor(Color.black);
//        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //high score display
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 100,  180, menuTitle, 35);
        //draw the options
        String key;
        Integer type1;
        String message;
        String text;
        Iterator<String> iteratorK = this.keys.iterator();
        Iterator<Integer> iteratorT = this.type.iterator();
        Iterator<String> iteratorM = this.messages.iterator();
        int y = 230;
        int x = (int) d.getWidth() / 2 - 100;
        d.setColor(new Color(255, 255, 255));
        while (iteratorK.hasNext()) {
            key = iteratorK.next();
            message = iteratorM.next();
            text = "(" + key + ") " + message;
            d.drawText(x, y, text, 30);
            y += 60;
        }
        iteratorK = this.keys.iterator();
        int counter = 0;
        int counter2 = 0;
        while (iteratorK.hasNext()) {
            key = iteratorK.next();
            type1 = iteratorT.next();
            if (type1.intValue() == 1) {
                //regular option
                if (this.keyboard.isPressed(key)) {
                    this.stop = true;
                    this.status = this.vals.get(counter);
                }
                counter++;
            }
            if (type1.intValue() == 2) {
                if (this.keyboard.isPressed(key)) {
                    //sub menu option
                    Menu<T> sub = this.submenus.get(counter2);
                    this.keys = sub.getKeys();
                    this.messages = sub.getMessages();
                    this.type = sub.getTypes();
                    this.vals = sub.getVals();
                    this.submenus = sub.getSubMenus();
                    this.background = sub.getBackGround();
                    this.menuTitle = sub.getTitle();
                    //exit loop
                    break;
                }
                counter2++;
            }
        }
    }
    /**
     * .
     * should stop method
     *
     * @return  stop
     */
    public boolean shouldStop() {
        return this.stop;
    }
    /**.
     * resets the menu
     */
    public void reset() {
        this.stop = false;
        this.status = null;
    }




    /**
     * .
     * add a sub menu
     *
     * @param  key the key
     * @param  message the message
     * @param  subMenu the subMenu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        Integer type1 = 2;
        this.keys.add(key);
        this.type.add(type1);
        this.messages.add(message);
        this.submenus.add(subMenu);
    }

    /**
     * .
     * get Keys
     *
     * @return  Keys
     */
    public List<String> getKeys() {
        return this.keys;
    }
    /**
     * .
     * get Messages
     *
     * @return  Messages
     */
    public List<String> getMessages() {
        return this.messages;
    }
    /**
     * .
     * get Vals
     *
     * @return  Vals
     */
    public List<T> getVals() {
        return this.vals;
    }
    /**
     * .
     * get SubMenus
     *
     * @return  SubMenus
     */
    public List<Menu<T>> getSubMenus() {
        return this.submenus;
    }
    /**
     * .
     * get Types
     *
     * @return  Types
     */
    public List<Integer> getTypes() {
        return this.type;
    }
    /**
     * .
     * get BackGround
     *
     * @return  BackGround
     */
    public Sprite getBackGround() {
        return this.background;
    }
    /**
     * .
     * get Title
     *
     * @return  Title
     */
    public String getTitle() {
        return this.menuTitle;
    }

}
