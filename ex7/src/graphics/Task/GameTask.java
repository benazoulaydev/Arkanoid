package graphics.Task;


import java.io.File;
import java.util.List;

import interfaces.LevelInformation;
import interfaces.Task;
import game.AnimationRunner;
import game.GameFlow;
import graphics.Handlers.HighScoresTable;
import game.RunningMenu;

/**
 *
 * @author Ben Azoulay
 *
 */
public class GameTask implements Task<Void> {
    private AnimationRunner ar;
    private GameFlow gf;
    private HighScoresTable hs;
    private List<LevelInformation> runningLevels;
    private File file;
    /**
     *.
     * @param rm the running menu.
     */
    public GameTask(RunningMenu rm) {
        this.ar = rm.getRunner();
        this.gf = rm.getGameFlow();
        this.hs = rm.getTable();
        this.runningLevels = rm.getLevels();
        this.file = rm.getPath();
    }

    /**
     * .
     * the run function
     * @return null
     */
    public Void run() {
        // start the game
        this.gf.runLevels(this.runningLevels);
        int currentScore = this.gf.getScore();
        //check if needed to add to high score

        return null;
    }

}
