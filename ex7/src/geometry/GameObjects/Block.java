package geometry.GameObjects;

import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import interfaces.HitListener;
import interfaces.Collidable;
import interfaces.Sprite;
import interfaces.HitNotifier;

import interfaces.Fill;
import game.GameLevel;
import graphics.Collections.GameEnvironment;

import geometry.Bases.Rectangle;
import geometry.Bases.Line;
import geometry.Bases.Point;
import geometry.Bases.Velocity;
/**
 * @author Ben Azoulay
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private int numHits;
    private final int prevHits;
    private List<HitListener> hitListeners;
    private double blockHeight;
    private double blockWidth;
    private List<Fill> fills;
    private Color borderColor;


    /**.
     * constructor
     * @param x value
     * @param y value
     * @param height value
     * @param width value
     * @param hitPoints value
     * @param fills value
     * @param stroke value
     */
    public Block(int x, int y, int height, int width, int hitPoints, List<Fill> fills, Color stroke) {
        this.shape = new Rectangle(new Point(x, y), height, width, stroke);
        this.numHits = hitPoints;
        this.fills = new ArrayList<Fill>(fills);
        this.hitListeners = new ArrayList<HitListener>();
        this.prevHits = numHits;
        this.borderColor = stroke;
        this.blockHeight = height;
        this.blockWidth = width;

    }

    /**.
     * constructor
     * @param p point
     * @param height value
     * @param width value
     * @param hitPoints value
     * @param fills value
     * @param stroke value
     */
    public Block(Point p, int height, int width, int hitPoints, List<Fill> fills, Color stroke) {
        this.shape = new Rectangle(p, height, width, stroke);
        this.numHits = hitPoints;
        this.fills = new ArrayList<Fill>(fills);
        this.hitListeners = new ArrayList<HitListener>();
        this.prevHits = numHits;
        this.borderColor = stroke;
        this.blockHeight = height;
        this.blockWidth = width;

    }

    /**
     * .
     * <p>
     * returns the "collision shape" of the object
     *
     * @return the shape
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    /**
     * .
     * <p>
     * the block width
     *
     * @return the block width
     */
    public double getBlockWidth() {
        return this.blockWidth;
    }

    /**
     *.
     * <p>
     * the block height
     *
     * @return the block height
     */
    public double getBlockHeight() {
        return this.blockHeight;
    }

    /**
     * .
     * <p>
     * returns the number of hits of the object
     *
     * @return the number of hits
     */
    public int getHitPoints() {
        return this.numHits;
    }


    /**
     * .
     * changes the velocity as the ball hits the block
     * <p>
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the velocity of the ball
     * @param hitter          the ball that hit a collidable
     * @return currentVelocity
     * the updated velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Line[] borders = this.shape.getRectLines();
        Point[] edges = this.shape.getRectPoints();
        boolean flag = false;

        //notify hit
        this.notifyHit(hitter);

        Point p = new Point(20, hitter.getHeight());
        Block bottomBorder = new Block(20, hitter.getHeight(), 1, hitter.getWidth() - 40,
                this.numHits, this.fills, Color.gray);
        Line[] bottomBordersLine = bottomBorder.getCollisionRectangle().getRectLines();
        if (bottomBordersLine[0].isPointInLine(collisionPoint)) {
            hitter.notifyFallBall(bottomBorder);
        }




        // if collision point not on edge
        if (!collisionPoint.equals(edges[0]) && !collisionPoint.equals(edges[1])
                && !collisionPoint.equals(edges[2]) && !collisionPoint.equals(edges[3])) {
            // up or down
            if (borders[0].isPointInLine(collisionPoint) || borders[2].isPointInLine(collisionPoint)) {
                // if num hit = -1 : killing block
                if (this.numHits == -1) {
                    hitter.notifyFallBall(bottomBorder);
                } else {
                    if (this.numHits == -2) {
                        // if num hit = --2 : living block
                        addNewBall(hitter.getEnvironment(), hitter.getWidth(), hitter.getHeight(),  hitter.getGame());
                    }

                    hitter.getGame().getScoreListener().hitEvent(this,  hitter);
                    currentVelocity.setDy((-1) * dy);
                    flag = true;
                }


            }
            // left or right
            if (borders[1].isPointInLine(collisionPoint) || borders[3].isPointInLine(collisionPoint)) {
                // if num hit = -1 : killing block
                if (this.numHits == -1) {
                    hitter.notifyFallBall(bottomBorder);
                } else {
                    if (this.numHits == -2) {
                        // if num hit = --2 : living block
                        addNewBall(hitter.getEnvironment(), hitter.getWidth(), hitter.getHeight(),  hitter.getGame());
                    }
                    hitter.getGame().getScoreListener().hitEvent(this,  hitter);
                    currentVelocity.setDx((-1) * dx);
                    flag = true;
                }

            }



        } else {
            // has to be on edge

            // if num hit = -1 : killing block
            if (this.numHits == -1) {
                hitter.notifyFallBall(bottomBorder);
            } else {
                if (this.numHits == -2) {
                    // if num hit = --2 : living block
                    addNewBall(hitter.getEnvironment(), hitter.getWidth(), hitter.getHeight(),  hitter.getGame());
                }
                hitter.getGame().getScoreListener().hitEvent(this,  hitter);
                // has to be on edge so change the two velocity
                currentVelocity.setDy((-1) * dy);
                currentVelocity.setDx((-1) * dx);
                flag = true;
            }
        }

        if (flag  && numHits > 0) {
            numHits--;
        }
        return currentVelocity;
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
        g.addCollidable(this);
    }

    /**
     * draw thw block.
     *
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {
            int x = (int) this.shape.getUpperLeft().getX();
            int y = (int) this.shape.getUpperLeft().getY();
            int width = (int) this.shape.getWidth();
            int height = (int) this.shape.getHeight();
//        surface.setColor(this.color);
//        surface.fillRectangle(x, y, width, height);
            int fillNum = this.numHits - 1;
            if (fillNum < 0) {
                fillNum = 0;
            }
            this.fills.get(fillNum).fillBlock(d, x, y);
            if (this.borderColor != null) {
                d.setColor(this.borderColor);
                d.drawRectangle(x, y, height, width);
            }

//        d.setColor(shape.getColor());
//        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
//                (int) shape.getHeight(), (int) shape.getWidth());
//        d.setColor(Color.black);
//        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
//                (int) shape.getHeight(), (int) shape.getWidth());
//
//        d.setColor(Color.WHITE);
    }


    /**
     * remove Block in the game.
     *
     * @param theGame the block to remove from the game
     */
    public void removeFromGame(GameLevel theGame) {

        theGame.removeCollidable(this);
        theGame.removeSprite(this);

    }

    /**
     * add hit listener hl to list of hit.
     *
     * @param hl the hit listener to add
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * .
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hitlistener
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);

    }

    /**
     * .
     * notify the hit
     *
     * @param hitter the hit block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * .
     * add new ball to the game.
     * @param environment the game environement
     * @param width the game width
     * @param height the game height
     * @param game the game
     */
    private void addNewBall(GameEnvironment environment, int width, int height, GameLevel game) {
        Ball ball = new Ball(25, 40, 3, Color.black);
        ball.setWidthHeight(width, height);
        Velocity v = Velocity.fromAngleAndSpeed(180, 4);
        ball.setVelocity(v);
        ball.setEnvironment(environment);
        ball.addToGame(game);

        ball.addHitListener(game.getBallRemoveListener());

        // increase the number of ball by one
        game.getBallLives().increase(1);
    }

    /**
     * .
     * hit Restablish for new game
     */
    public void hitRestablish() {
        this.numHits = this.prevHits;
    }

    /**.
     *
     * @return the fills
     */
    public List<Fill> getFills() {
        return this.fills;
    }

}
