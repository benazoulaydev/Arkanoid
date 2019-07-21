import biuoop.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import interfaces.LevelInformation;

import game.AnimationRunner;
import game.GameFlow;
import graphics.Handlers.HighScoresTable;
import textlevel.Level.LevelSpecificationReader;
import game.RunningMenu;
/**
 * @author ben azoulay
 */
public class Ass7Game {
    /**.
     * main func
     * @param args argument
     */
    public static void main(String[] args) {

        String path = null;
        if (args.length > 0) {
            path = args[0];
        }
        File file = new File("highscores");
        HighScoresTable hs = HighScoresTable.loadFromFile(file);
        if (hs.isEmpty()) {
            //the table is empty
            try {
                hs.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        GUI gui =  new GUI("Game", 800, 600);
        AnimationRunner ar = new AnimationRunner(gui);

        GameFlow gf = new GameFlow(ar, hs, file);

        // get all the levels from the gam flow
       // List<LevelInformation> levels = gf.getAllLevels();

        // the levels in your game according to the level definition file
        LevelSpecificationReader l = new LevelSpecificationReader(800, 600);
        List<LevelInformation> levels = null;
        BufferedReader buffer = null;
        if (path == null) {
            path = "definitions/level_definitions.txt";
        }
        try {
            InputStream is = ClassLoader.getSystemClassLoader().
                    getResourceAsStream(path);
            buffer = new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
            System.err.println("something went wrong!!! keep trying ;)");
            e.printStackTrace(System.err);
        }
        if (buffer != null) {
            levels = l.fromReader(buffer);
            RunningMenu runMenu = new RunningMenu(ar, gf, hs, levels, file);
            runMenu.play();
        }


//        // the levels in our game according to the args numbers
//        List<LevelInformation> argumentsLevels = new ArrayList<LevelInformation>();
//        if (args.length != 0) {
//            for (int i = 0; i < args.length; i++) {
//                try {
//                    int levelNumber = Integer.parseInt(args[i]);
//                    if (levelNumber >= 1 && levelNumber <= levels.size()) {
//                        argumentsLevels.add(gf.getLevel(levelNumber));
//                    }
//                } catch (Exception e) {
//                    //bad input
//                    continue;
//                }
//            }
//            // start the game
//            gf.runLevels(argumentsLevels);
//        } else {
//            // start the game run all the level
//            //gf.runLevels(levels);
//
//
//            //run only first level
//
//
//
//            //gf.runLevels(argumentsLevels);
//            argumentsLevels.add(gf.getLevel(2));
//            argumentsLevels.add(gf.getLevel(2));
//            argumentsLevels.add(gf.getLevel(1));
//
//            gf.runningMenu(argumentsLevels);
//
//
//
//        }
    }

}

