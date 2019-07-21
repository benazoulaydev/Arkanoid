package game;
import graphics.Handlers.HighScoresTable;
import interfaces.LevelInformation;
import biuoop.KeyboardSensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;
import interfaces.Task;
import graphics.screenWindow.HighScoresAnimation;
import graphics.Handlers.KeyPressStoppableAnimation;
import graphics.screenWindow.MenuAnimation;
import backgrounds.MenuBackGround;
import textlevel.Level.LevelSetReader;
import backgrounds.Menu2BackGround;
import interfaces.Menu;
/**
 * @author Ben Azoulay
 */
public class RunningMenu {
    private AnimationRunner ar;
    private GameFlow gf;
    private HighScoresTable hs;
    private List<LevelInformation> runningLevels;
    private File file;
    private int lives;
    /**.
     * constructor
     * @param ar runner
     * @param gf game flow
     * @param hs table
     * @param runningLevels the levels
     * @param file the file path
     */
    public RunningMenu(AnimationRunner ar, GameFlow gf, HighScoresTable hs,
                       List<LevelInformation> runningLevels, File file) {
        this.ar = ar;
        this.gf = gf;
        this.hs = hs;
        this.runningLevels = runningLevels;
        this.file = file;
        this.lives = gf.getLives();
    }
    /**.
     *
     * @return the runner
     */
    public AnimationRunner getRunner() {
        return this.ar;
    }
    /**.
     *
     * @return the game flow
     */
    public GameFlow getGameFlow() {
        return this.gf;
    }
    /**.
     *
     * @return the table
     */
    public HighScoresTable getTable() {
        return this.hs;
    }
    /**
     *
     * @return the keyboard sensor
     */
    public KeyboardSensor getKeyBoard() {
        return this.ar.getGui().getKeyboardSensor();
    }
    /**.
     *
     * @return the running level
     */
    public List<LevelInformation> getLevels() {
        return this.runningLevels;
    }
    /**.
     *
     * @return the wideth
     */
    public int getWidth() {
        return this.ar.getGui().getDrawSurface().getWidth();
    }
    /**.
     *
     * @return the file path
     */
    public File getPath() {
        return this.file;
    }
    /**.
     *
     * @return the height
     */
    public int getHeight() {
        return this.ar.getGui().getDrawSurface().getHeight();
    }
    /**.
     *
     * @param levels the set levels
     */
    public void setLevels(List<LevelInformation> levels) {
        this.runningLevels = levels;
    }
    /**.
     * the play
     */
    public void play() {
        Task<Void> highT = new Task<Void>() {
            public Void run() {
                ar.run(new KeyPressStoppableAnimation(ar.getGui().getKeyboardSensor(),
                        KeyboardSensor.SPACE_KEY, new HighScoresAnimation(hs, ar.getGui().getKeyboardSensor())));
                return null;
            }
        };
        /**.
         * quit menu
         */
        Task<Void> quitT = new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        };
        while (true) {
            MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>(
                    "Arkanoid", new MenuBackGround()
                    , ar.getGui().getKeyboardSensor());

            LevelSetReader<Task<Void>> setR =
                    new LevelSetReader<Task<Void>>(this);
            LineNumberReader reader = null;

            InputStream is = ClassLoader.getSystemClassLoader().
                    getResourceAsStream("level_sets.txt");
            try {
                reader = new LineNumberReader(
                        new BufferedReader(
                                new InputStreamReader(
                                        is)));
            } catch (Exception e) {
                System.err.println("file not found");
                e.printStackTrace(System.err);
            }
            if (reader != null) {
                Menu<Task<Void>> sub =
                        setR.fromReader(reader, "Level Sets", new Menu2BackGround());
                menu.addSelection("h", "High Scores", highT);
                menu.addSubMenu("s", "Start Game", sub);
                menu.addSelection("q", "Quit", quitT);
            }
            ar.run(menu);
            // user selection waiting menu
            Task<Void> task = menu.getStatus();
            task.run();
            menu.reset();
            this.gf.reset(this.lives);
        }
    }
}
