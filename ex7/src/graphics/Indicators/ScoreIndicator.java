package graphics.Indicators;

import biuoop.DrawSurface;
import java.awt.Color;

import game.GameLevel;
import interfaces.Sprite;
import geometry.Bases.Rectangle;

import graphics.Handlers.Counter;

/**
 * @author Ben Azoulay
 */
public class ScoreIndicator implements Sprite {

    private Rectangle shape;
    private double width;
    private double height;
    private Counter scoreGame;

    /**
     * .
     * constructor
     * <p>
     * Create a new rectangle with location and width/height.
     *
     * @param shapeRec the rectangle shape
     * @param widthRec     width of the recatngle
     * @param heightRec     height of the recatngle
     * @param score    the score indicator
     */
    public ScoreIndicator(Rectangle shapeRec, double widthRec, double heightRec, Counter score) {
        this.shape = shapeRec;
        this.width = widthRec;
        this.height = heightRec;
        this.scoreGame = score;
    }


    /**
     * draw object.
     * @param surface draw surface
     */
    public void drawOn(DrawSurface surface) {

        //left

        surface.setColor(shape.getColor());
        surface.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getHeight(), (int) shape.getWidth());
        surface.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getHeight(), (int) shape.getWidth());

        surface.setColor(Color.black);
        surface.drawText((int) shape.getRectLines()[0].middle().getX() - 20,
                (int) shape.getRectLines()[1].middle().getY() + 5, "Score : " + scoreGame.getValue(), 10);

    }

    /**
     * alart that time passed.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }

    /**
     * add block to game.
     *
     * @param g game to add the block to sprite and collidable collection
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
