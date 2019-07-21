package graphics.Handlers;

//import java.io.*;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @author Ben Azoulay
 */
public class HighScoresTable implements Serializable {
    private static final int MAXSIZE = 10;


    private int sizeTable;
    private List<ScoreInfo> table;



    /**
     * .
     * constructor
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size of the table
     */
    public HighScoresTable(int size) {
        this.sizeTable = size;
        this.table = new ArrayList<ScoreInfo>();
    }

    /**
     * .
     * default table
     */
    public HighScoresTable() {
        this.sizeTable = MAXSIZE;
        this.table = new ArrayList<ScoreInfo>();
    }
    /**.
     * the high score table
     * @param table of the high score
     */
    public HighScoresTable(List<ScoreInfo> table) {
        this.table = new ArrayList<ScoreInfo>(table);
        this.sizeTable = MAXSIZE;
    }


    /**.
     * get high-score table.
     * @return high-score table.
     */
    public List<ScoreInfo> getHighScoreTable() {

        return this.table;
    }

    /**.
     * Add a high-score.
     * @param score info of the score to add
     */
    public void add(ScoreInfo score) {

        int position = getRank(score.getScore());
        if (position <= this.sizeTable) {
            this.table.add(position - 1, score);
            if (this.table.size() > this.sizeTable) {
                this.table.remove(this.sizeTable);
            }
        }
    }



    /**.
     * Return table size.
     * @return table size
     */
    public int size() {
        return this.sizeTable;
    }


    /**.
     * Return the current high scores.
     * The list is sorted such that the highest
     *  scores come first.
     * @return the hight score table
     */
    public List<ScoreInfo> getHighScores() {
        return this.table;
    }



    /**.
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     * @param score the score to compare
     * @return the rank of the score
     */
    public int getRank(int score) {
        ScoreInfo info;
        int counterRank = 1;
        if (this.table.isEmpty()) {
            //if this is the first score
            return counterRank;
        } else {

            Iterator<ScoreInfo> tableIterator = this.table.iterator();
            while (tableIterator.hasNext()) {
                info = tableIterator.next();
                if (score <= info.getScore()) {
                    counterRank++;
                } else {
                    //if the score is higher
                    return counterRank;
                }
            }
            return counterRank;

        }
    }


    /**.
     * Clears the table
     */
    public void clear() {
        this.table.clear();
    }


    /**.
     * Load table data from file.
     * Current table data is cleared.
     * @param filename the file
     * @throws IOException the Exception
     */
    public void load(File filename) throws IOException {
        this.clear();
        try {
            this.loadFromFile(filename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**.
     * Save table data to the specified file.
     * @param filename the file
     * @throws IOException the Exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOut = null;
        try {

            FileOutputStream fileOut = new FileOutputStream(filename);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.table);
            objectOut.close();
            System.out.println("The Object  was successfully written to the high-score file");
        } catch (IOException e) {
            System.err.println("The Object failed to save ");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOut != null) {
                    objectOut.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }


    }


    /**.
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename the file
     * @return  HighScoresTable
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream objectInputStream = null;
        HighScoresTable hS;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));

            hS = new HighScoresTable((List<ScoreInfo>) objectInputStream.readObject());
            return hS;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new HighScoresTable();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing the file: " + filename);
            }

        }
    }

    /**.
     * prints table
     */
    public void printTable() {
        ScoreInfo info;
        Iterator<ScoreInfo> iterator = this.table.iterator();

        while (iterator.hasNext()) {
            info = iterator.next();
            System.out.println("Player name: " + info.getName()
                    + " Score: " + info.getScore());
        }
    }


    /**.
     *
     * @return if table empty true/false
     */
    public boolean isEmpty() {
        return this.table.isEmpty();
    }

    /**.
     * score to enter
     * @param score to enter
     * @return true/false
     */
    public boolean isEnteringHighScore(int score) {
        //if table not full
        if (this.table.size() < this.sizeTable) {
            return true;
        }

        if (!this.table.isEmpty()) {
            ScoreInfo info = this.table.get(this.table.size() - 1);
            if (info.getScore() < score) {
                //if the last score smaller than the entered score return true
                return true;
            }
            return false;
        }
        return false;
    }
}
