package game;


import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import interfaces.Animation;

/**
 * @author Ben Azoulay
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructor.
     * @param g the gui
     */
    public AnimationRunner(GUI g) {
        this.gui = g;
        this.sleeper = new Sleeper();
        this.framesPerSecond = 60;
    }

    /**
     * @param animation var to functions doOneFrame(DrawSurface d) and boolean shouldStop().
     */
    public void run(Animation animation) {


        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            double dt = 1.0 / this.framesPerSecond;
            animation.doOneFrame(d, dt);
            this.gui.show(d);

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }


    }


    /**.
     * get the gui
     *@return the gui
     */
    public GUI getGui() {
        return this.gui;
    }

    /**.
     * close the gui
     */
    public void close() {
        gui.close();
    }

    /**
     * @param framesPerSecond1
     *        sets frames
     *
     */
    public void setFrames(int framesPerSecond1) {
        this.framesPerSecond = framesPerSecond1;
    }
    /**
     * @return framesPerSecond
     * returns the frames
     *
     */
    public int getFrames() {
        return this.framesPerSecond;
    }

}