package graphics.Handlers;
import java.io.Serializable;

/**
 * @author Ben Azoulay
 */
public class ScoreInfo implements Serializable {

    private String userName;
    private int userScore;

    /**
     * .
     *constructor
     * @param name the name of the user
     * @param score the score of the user
     */
    public ScoreInfo(String name, int score) {
        this.userName = name;
        this.userScore = score;

    }
    /**
     * .
     * get user Name
     * @return the name
     */
    public String getName() {
        return this.userName;
    }

    /**
     * .
     *get user Score
     * @return the score
     */
    public int getScore() {
        return this.userScore;

    }
}

