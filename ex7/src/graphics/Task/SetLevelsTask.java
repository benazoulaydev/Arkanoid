package graphics.Task;
import interfaces.Task;

import java.util.List;
import game.RunningMenu;
import interfaces.LevelInformation;
/**
 *
 * @author Ben Azoulay
 */
public class SetLevelsTask implements Task<Void> {
    private RunningMenu rm;
    private List<LevelInformation> levelsSet;
    /**.
     * constructor
     * @param rm the running menu
     * @param levels the level list
     */
    public SetLevelsTask(RunningMenu rm, List<LevelInformation> levels) {
        this.rm = rm;
        this.levelsSet = levels;
    }
    /**
     * .
     * the run function
     * @return null
     */
    public Void run() {
        this.rm.setLevels(this.levelsSet);
        GameTask gameT = new GameTask(this.rm);
        gameT.run();
        return null;
    }

}

