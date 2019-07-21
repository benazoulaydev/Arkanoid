package textlevel.Level;

import graphics.Task.SetLevelsTask;
import interfaces.Menu;
import graphics.screenWindow.MenuAnimation;
import game.RunningMenu;
import interfaces.Sprite;
import interfaces.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *
 * @author Ben Azoulay
 *
 * @param <T>
 */
public class LevelSetReader<T> {
    private RunningMenu rm;
    /**
     *
     * @param rm running menu
     */
    public LevelSetReader(RunningMenu rm) {
        this.rm = rm;
    }
    /**
     *
     * @param reader the reader
     * @param title the menu title
     * @param backGround the background
     * @return summenu or null
     */
    public Menu<Task<Void>> fromReader(LineNumberReader reader, String title,
                                       Sprite backGround) {
        MenuAnimation<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(
                title, backGround, this.rm.getKeyBoard());
        try {
            String line = reader.readLine();
            String key = null;
            String message = null;
            //File file = null;
            while (line != null) {
                int numL = reader.getLineNumber();
                if (numL % 2 == 1) {
                    //if it is odd number
                    String[] split = line.split(":");
                    key = split[0];
                    message = split[1];
                } else {
                    //else it is even number
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(line)));
                    LevelSpecificationReader l = new LevelSpecificationReader(
                            this.rm.getWidth(), this.rm.getHeight());
                    SetLevelsTask task = new SetLevelsTask(
                            this.rm, l.fromReader(buffer));
                    subMenu.addSelection(key, message, task);
                }
                line = reader.readLine();
            }
            return subMenu;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file");
            return null;
        } catch (IOException e) {
            System.err.println("Failed reading file");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
    }
}
