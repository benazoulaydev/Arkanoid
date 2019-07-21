package graphics.Indicators;



import biuoop.DrawSurface;
import java.awt.Color;
import interfaces.Sprite;

import geometry.Bases.Rectangle;

import game.GameLevel;


/**
 * @author Ben Azoulay
 */
public class LevelIndicator implements Sprite {

    private Rectangle shape;
    private double width;
    private double height;
    private String levelName;

    /**
     * .
     * constructor
     * <p>
     * Create a new rectangle with location and width/height.
     *
     * @param shapeRec the rectangle shape
     * @param widthRec     width of the recatngle
     * @param heightRec     height of the recatngle
     * @param level    the level name
     */
    public LevelIndicator(Rectangle shapeRec, double widthRec, double heightRec, String level) {
        this.shape = shapeRec;
        this.width = widthRec;
        this.height = heightRec;
        this.levelName = level;
    }


    /**
     * draw object.
     * @param surface draw surface
     */
    public void drawOn(DrawSurface surface) {

        surface.setColor(Color.black);

        surface.drawText((int) shape.getRectLines()[0].end().getX() - 200,
                (int) shape.getRectLines()[1].middle().getY() + 5, "Level Name : " + this.levelName, 10);


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
