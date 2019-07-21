package game;
import backgrounds.MenuBackGround;
import graphics.Handlers.Counter;
import graphics.Handlers.HighScoresTable;
import graphics.Handlers.KeyPressStoppableAnimation;
import graphics.Handlers.ScoreInfo;
import graphics.screenWindow.EndScreen;
import graphics.screenWindow.HighScoresAnimation;
import graphics.screenWindow.MenuAnimation;
import interfaces.LevelInformation;

import biuoop.DialogManager;
import java.io.File;
import java.io.IOException;

import java.util.List;
import biuoop.KeyboardSensor;
import interfaces.Task;
import interfaces.Menu;

//import geometry.Bases.Velocity;
//import java.util.ArrayList;

/**
 * @author Ben Azoulay
 */
public class GameFlow {
    private AnimationRunner runner;
    private Counter scoreCounter = new Counter(0);
    private Counter livesCounter = new Counter(7);

    private KeyboardSensor sensor;
    private int blockNb;
    private File file;

    private HighScoresTable hs;
    private boolean helper = true;

    /**
     * Constructor.
     * @param ar the animation runner
     * @param hs the High Scores Table
     * @param file the file
     */
    public GameFlow(AnimationRunner ar, HighScoresTable hs, File file) {
        this.runner = ar;
        this.sensor = this.runner.getGui().getKeyboardSensor();

        this.file = file;

        this.hs = hs;



    }

    /**
     * runningMenu.
     * @param levels the levels to play
     */
    public  void runningMenu(List<LevelInformation> levels) {

        Task<Void> highT = new Task<Void>() {
            public Void run() {
                runner.run(new KeyPressStoppableAnimation(
                        runner.getGui().getKeyboardSensor(),
                        KeyboardSensor.SPACE_KEY, new HighScoresAnimation(hs, sensor)));
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
        /**.
         * play menu
         */
        Task<Void> playT = new Task<Void>() {
            public Void run() {

                runLevels(levels);
                return null;
            }
        };

        while (true) {


            Menu<Task<Void>> sub = new MenuAnimation<Task<Void>>("sub menu", new MenuBackGround()
                    , sensor);
            MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>(
                    "Arkanoid", new MenuBackGround()
                    , sensor);


            menu.addSelection("s", "Game", playT);
            menu.addSelection("h", "High Scores", highT);
            menu.addSelection("q", "Quit", quitT);
            menu.addSubMenu("w", "submenu", sub);

            sub.addSelection("g", "gameother", playT);
            this.runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
            menu.reset();
        }

    }
    /**
     * run all the levels.
     * @param levels the level list
     */
    public void runLevels(List<LevelInformation> levels) {
        boolean flag = false;


        for (LevelInformation levelInfo : levels) {

            GameLevel game = new GameLevel(levelInfo, this.runner, this.scoreCounter, this.livesCounter, hs);
            game.initialize();


            while (game.getBlocksNb() > 0 && game.getLives().getValue() >= 0) {

                //modify: countdown
                //this.runner.run(new CountdownAnimation(2, 3, game.getSpriteColl()));
                game.playOneTurn();
                if (game.getBallLives().getValue() == 0) {
                    game.getLives().decrease(1);

                }
                if (game.getBlockRemoveListener().getRemainingBlocks() == 0) {
                    this.scoreCounter.increase(100);
                }

            }


            this.blockNb = game.getBlocksNb();
            if (game.getLives().getValue() < 0) {
                break;
            }

        }

        this.runner.run(new KeyPressStoppableAnimation(this.sensor, "space",
                new EndScreen(this.runner.getGui().getKeyboardSensor(),
                        this.blockNb, this.livesCounter, this.scoreCounter)));

        int currentScore = this.getScore();
        if (this.hs.isEnteringHighScore(currentScore)) {
            //add to high score
            DialogManager dialog = this.runner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name",
                    "What is your name?", "");
            this.hs.add(new ScoreInfo(name, currentScore));
            try {
                this.hs.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        this.runner.run(new KeyPressStoppableAnimation(this.sensor, "space",
                new HighScoresAnimation(this.hs, this.sensor)));
        //reset score and live
        this.scoreCounter.setValue(0);
        this.livesCounter.setValue(0);


    }

    /**
     *.
     * @return the lives value
     */
    public int getLives() {
        return this.livesCounter.getValue();
    }

    /**
     *.
     * @return the score value
     */
    public int getScore() {
        return this.scoreCounter.getValue();
    }
//   /**
//     *.
//     * @return list of all the available levels
//     */
//    public List<LevelInformation> getAllLevels() {
//        List<LevelInformation> levels = new ArrayList<LevelInformation>();
//        LevelInformation info;
//        List<Velocity> initialBallVelocities;
//        Velocity v = new Velocity(1, -6);
//
//        initialBallVelocities = new ArrayList<Velocity>();
//
//
//
//        levels.add(getLevel(1));
//        levels.add(getLevel(2));
//        levels.add(getLevel(3));
//        levels.add(getLevel(4));
//
//        return levels;
//    }

//    /**
//     * @param levelNum the level number
//     * @return list of all the available levels
//     */
//    public LevelInformation getLevel(int levelNum) {
//        LevelInformation info;
//        List<Velocity> initialBallVelocities;
//        Velocity v = new Velocity(1, -6);
//
//        initialBallVelocities = new ArrayList<Velocity>();
//
//        if (levelNum == 1) {
//            //level 1
//            v = v.fromAngleAndSpeed(360, 5);
//
//            initialBallVelocities = new ArrayList<Velocity>();
//            initialBallVelocities.add(v);
//            info = new DirectHitLevel(1, initialBallVelocities, 10, 100,
//                    800, 600);
//
//            initialBallVelocities = new ArrayList<Velocity>();
//        } else if (levelNum == 2) {
//            initialBallVelocities = new ArrayList<Velocity>();
//            //level 2
//            double angle = -65;
//            for (int i = 0; i < 10; i++) {
//                v = v.fromAngleAndSpeed(angle, 5);
//                initialBallVelocities.add(v);
//                angle += 14;
//            }
//            info = new WideEasyLevel(10, initialBallVelocities, 10, 600,
//                    800, 600);
//
//        } else if (levelNum == 3) {
//            initialBallVelocities = new ArrayList<Velocity>();
//            //level 3
//            v = v.fromAngleAndSpeed(345, 5);
//            initialBallVelocities.add(v);
//            v = v.fromAngleAndSpeed(375, 5);
//            initialBallVelocities.add(v);
//            info = new Green3Level(2, initialBallVelocities, 10, 100,
//                    800, 600);
//        } else if (levelNum == 4)  {
//            initialBallVelocities = new ArrayList<Velocity>();
//            //level 4
//            v = v.fromAngleAndSpeed(360, 5);
//            initialBallVelocities.add(v);
//            v = v.fromAngleAndSpeed(345, 5);
//            initialBallVelocities.add(v);
//            v = v.fromAngleAndSpeed(375, 5);
//            initialBallVelocities.add(v);
//            info = new FinalFourLevel(3, initialBallVelocities, 10, 100,
//                    800, 600);
//
//        } else {
//            info = null;
//        }
//        return info;
//    }

    /**.
     * sets lives
     * @param lives1 the lives num
     */
    public void setLives(int lives1) {
        this.livesCounter = new Counter(lives1);
    }
    /**.
     * resets score and lives
     * @param lives2 to reset
     */
    public void reset(int lives2) {
        this.scoreCounter = new Counter(0);
        this.setLives(lives2);
    }
}
