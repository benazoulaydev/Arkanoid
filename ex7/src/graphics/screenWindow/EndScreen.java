package graphics.screenWindow;
import interfaces.Animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import graphics.Handlers.Counter;

import java.awt.Color;

/**
 * @author Ben Azoulay
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private int nbBlock;
    private Counter counterlive;
    private Counter counterScore;

    /**
     * .
     * constructor
     *
     * @param k the keyboard sensor
     * @param blockNumber the number of blocks in the game
     * @param liveCounter the counter of live in the game
     * @param scoreCounter the counter of score
     */
    public EndScreen(KeyboardSensor k, int blockNumber, Counter liveCounter, Counter scoreCounter) {
        this.keyboard = k;
        this.stop = false;
        this.nbBlock = blockNumber;
        this.counterlive = liveCounter;
        this.counterScore = scoreCounter;
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

        if (this.nbBlock == 0) {
            d.setColor(Color.blue);

            d.drawText(200, d.getHeight() / 2, "You Win! Your score is "
                    + this.counterScore.getValue(), 32);

        } else {
            d.setColor(Color.red);

            d.drawText(200, d.getHeight() / 2, "Game Over. Your score is "
                    + this.counterScore.getValue(), 32);
        }
        d.setColor(Color.PINK);


        d.drawText(150, d.getHeight() / 2 + 40, "|       press space to continue     |", 32);
        d.setColor(Color.WHITE);

        d.drawText(10, d.getHeight() / 2 + 80, "---------------------------------------------", 32);

    }

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    public boolean shouldStop() { return this.stop; }
}
