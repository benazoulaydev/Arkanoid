package graphics.Handlers;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * @author Ben Azoulay
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor theKeyboard;
    private String keyPressed;
    private Animation theAnimation;
    private boolean isAlreadyPressed;
    private boolean stop;

    /**
     * Constructor.
     * @param sensor the keyboard sensor
     * @param key the keyboard button pressed
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.theAnimation = animation;
        this.theKeyboard = sensor;
        this.keyPressed = key;
        this.isAlreadyPressed = true;
    }

    /**
     * .
     * the game-specific logic and stopping conditions are handled in the
     *
     * @param d the drawSurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        theAnimation.doOneFrame(d, dt);
        if (this.theKeyboard.isPressed(this.keyPressed)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
