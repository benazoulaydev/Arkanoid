package graphics.screenWindow;
import interfaces.Animation;
import biuoop.DrawSurface;
import graphics.Collections.SpriteCollection;

import java.awt.Color;

/**
 * @author Ben Azoulay
 * // The CountdownAnimation will display the given gameScreen,
 * // for numOfSeconds seconds, and on top of them it will show
 * // a countdown from countFrom back to 1, where each number will
 * // appear on the screen for (numOfSeconds / countFrom) secods, before
 * // it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private SpriteCollection spriteCollection;
    private double secondsNb;
    private int count;
    private int countFr;

    private boolean stop;
    private long startTime;


    /**
     * .
     * constructor
     *
     * @param numOfSeconds  the number of seconds
     * @param countFrom the count from last
     * @param gameScreen the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.startTime = System.currentTimeMillis();
        this.count = countFrom;
        this.countFr = countFrom;
        this.secondsNb = numOfSeconds;
        this.spriteCollection = gameScreen;
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
        this.spriteCollection.drawAllOn(d);

        //long milliseconds = System.currentTimeMillis();
        //int seconds = (int) milliseconds / 1000;
        d.setColor(new Color(220, 20, 60));
        if (this.count > 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2 , this.count + "..." , 32);
        } else {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2 , "GO" , 32);
        }


        long millisecondCount = (long) (1000 * this.secondsNb / this.countFr);
        long usedTime = System.currentTimeMillis() - this.startTime
                - ((this.countFr - this.count) * millisecondCount);
        long timeLeft = millisecondCount - usedTime;
        if (timeLeft <= 0) {
            this.count--;
        }


    }

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    public boolean shouldStop() {
        if (this.count < 0) {
            this.stop = true;
        }
        return this.stop;
    }



}