package graphics.screenWindow;
import interfaces.Animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * @author Ben Azoulay
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * .
     * constructor
     *
     * @param k the keyboard sensor
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * .
     * the game-specific logic and stopping conditions are handled in the
     *
     * @param d the drawSurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.WHITE);
        d.drawText(10, d.getHeight() / 2 - 40, "---------------------------------------------", 32);
        d.drawText(80, d.getHeight() / 2, "|     paused -- press space to continue     |", 32);
        d.drawText(10, d.getHeight() / 2 + 40, "---------------------------------------------", 32);

    }

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    public boolean shouldStop() { return this.stop; }
}
