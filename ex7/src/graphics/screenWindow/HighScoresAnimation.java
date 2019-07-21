package graphics.screenWindow;
import biuoop.DrawSurface;

import biuoop.KeyboardSensor;
import graphics.Handlers.HighScoresTable;
import graphics.Handlers.ScoreInfo;
import java.awt.Color;
import java.util.Iterator;
import interfaces.Animation;


/**
 * @author Ben Azoulay
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;
    private KeyboardSensor keyboard;
    private boolean stop;

    /**.
     * constructor
     * @param scoresTable the scores for the table of high score
     * @param k the keyboard sensor
     */
    public HighScoresAnimation(HighScoresTable scoresTable, KeyboardSensor k) {
        this.keyboard = k;
        this.scores = scoresTable;
        this.stop = false;

    }

    /**
     * .
     * the game-specific logic and stopping conditions are handled in the
     *
     * @param d the drawSurface
     * @param dt the dt
     */
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        int textSize = (int) (d.getWidth() / 20);
        String text;
        //high score display
        d.setColor(Color.red);
        text = "High Scores";
        d.drawText(d.getHeight() / 2 - 50 , d.getHeight() / 12, text, textSize);
        //Wall Of Fame
        textSize = (int) (d.getWidth() / 30);
        d.setColor(Color.yellow);
        text = "Arkanoid Wall Of Fame ";
        d.drawText(d.getHeight() / 2 - (3 * textSize) , d.getHeight() / 7, text, textSize);

        d.setColor(Color.WHITE);
        text = "Rank    |    Name   |    Score";
        textSize = (int) (d.getWidth() / 24);
        d.drawText(d.getWidth() / 5, d.getHeight() / 5 + 10, text, textSize);

        text = "________________________________";
        d.drawText(d.getWidth() / 8, d.getHeight() / 5 + 20, text, textSize);

        textSize = d.getHeight() / 2 / this.scores.size() - 3;
        Iterator<ScoreInfo> itor = this.scores.getHighScores().iterator();
        ScoreInfo info;
        int y = (int) d.getHeight() / 5 + 60;
        //iterator for switch case
        int i = 0;
        // string for 1st 2nd ...
        String rankStr = null;
        while (itor.hasNext()) {
            info = itor.next();
            d.setColor(Color.WHITE);
            d.drawText(d.getWidth() / 5 * 2, y, info.getName(), textSize);
            d.drawText(d.getWidth() / 5 * 3, y, Integer.toString(info.getScore()), textSize);

            switch(i) {
                case 0:
                    rankStr = "1ST";
                    break;
                case 1:
                    rankStr = "2ND";
                    break;
                case 2:
                    rankStr = "3RD";
                    break;
                case 3:
                    rankStr = "4TH";
                    break;
                case 4:
                    rankStr = "5TH";
                    break;
                case 5:
                    rankStr = "6TH";
                    break;
                case 6:
                    rankStr = "7TH";
                    break;
                case 7:
                    rankStr = "8TH";
                    break;
                case 8:
                    rankStr = "9TH";
                    break;
                case 9:
                    rankStr = "10TH";
                    break;
                 default:
                    // code block
            }
            i++;
            d.setColor(Color.yellow);
            d.drawText(d.getWidth() / 5 , y, rankStr, textSize);
            y += 30;

        }
        d.setColor(Color.WHITE);
        textSize = (int) (d.getWidth() / 24);
        d.drawText(d.getWidth() / 6 + 30, d.getHeight() / 8 * 7,
                "press space to continue", textSize);
    }

    /**
     * .
     * return true if the loop should stop instead of the while(true);
     *
     * @return true or false
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
