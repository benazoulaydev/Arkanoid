package graphics.Task;
import interfaces.Animation;
import game.AnimationRunner;
import interfaces.Task;

/**
 * @author Ben Azoulay
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * .
     *constructor
     * @param runner the animation runner
     * @param highScoresAnimation the table of High-Score
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * .
     * the run function
     * @return null
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}

