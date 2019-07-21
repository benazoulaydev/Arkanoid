package geometry.GameObjects;

import biuoop.DrawSurface;
import java.awt.Color;

import game.GameLevel;
import interfaces.HitListener;
import interfaces.Sprite;
import interfaces.HitNotifier;

import graphics.Collections.GameEnvironment;
import graphics.Handlers.CollisionInfo;

import geometry.Bases.Line;
import geometry.Bases.Point;
import geometry.Bases.Velocity;



/**
 * @author ben azoulay
 */
public class Ball implements Sprite, HitNotifier {
    private Point center;
    private int radius;
    private Color color;
    private Velocity vlc;
    private GameEnvironment environment;
    private int width;
    private int height;
    private HitListener hitListeners;

    private GameLevel gameLevel;

    /**
     * .
     * constructor
     *
     * @param center the center point of circle
     * @param r      radius
     * @param color  color of the circle
     */
    public Ball(Point center, int r, Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.radius = r;
        this.color = color;
        //default velocity to 0
        this.vlc = new Velocity(0, 0);
        this.environment = new GameEnvironment();

    }


    /**
     * .
     *
     * @param x     the coordinate x of the center point
     * @param y     the coordinate y of the center point
     * @param r     the radius of the circle
     * @param color the color of the ball
     */
    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.vlc = new Velocity(0, 0);
        // initialize Gameenvironment
        this.environment = new GameEnvironment();
    }

    // accessors

    /**
     * .
     *
     * @param widthToSet  the width
     * @param heightToSet the height
     */
    public void setWidthHeight(int widthToSet, int heightToSet) {
        this.width = widthToSet;
        this.height = heightToSet;
    }

    /**
     * .
     *
     * @return width of the screen
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * .
     *
     * @return height of the screen
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * .
     *
     * @return the center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * .
     *
     * @return the  x axis value of the center ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * .
     *
     * @return the  y axis value of the center ball
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * .
     *
     * @return the  size of the ball (radius)
     */
    public int getSize() {
        return (int) this.radius;
    }

    /**
     * .
     *
     * @return the  color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * .
     *
     * @return the  velocity
     */
    public Velocity getVelocity() {
        return this.vlc;
    }


    /**
     * .
     *
     * @return the game environment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }


    /**
     * .
     *
     * @param v set the velocity
     */
    public void setVelocity(Velocity v) {
        this.vlc.setDx(v.getDx());
        this.vlc.setDy(v.getDy());
    }

    /**
     * .
     *
     * @param dx set the velocity in the x axis
     * @param dy set the velocity in the y axis
     */
    public void setVelocity(double dx, double dy) {
        this.vlc.setDx(dx);
        this.vlc.setDy(dy);
    }

    /**
     * @param ge the game environment
     */
    public void setEnvironment(GameEnvironment ge) {
        this.environment = ge;
    }


    /**
     * .
     * draw the sprite to the screen
     * draw the ball on the given DrawSurface
     *
     * @param surface draw surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * .
     * <p>
     * move the ball one step without entering in an object
     * @param dt the change in time
     */
    public void moveOneStep(double dt) {
        //make the ball dont go out of the screen
        /* if ((this.center.getX() + this.radius >= this.width && this.vlc.getDx() > 0)
                || (this.center.getX() - this.radius <= 0 && this.vlc.getDx() < 0)) {
            this.vlc.setDx(this.vlc.getDx() * -1);
        }
        if ((this.center.getY() + this.radius >= this.height && this.vlc.getDy() > 0)
                || (this.center.getY() - this.radius <= 0 && this.vlc.getDy() < 0)) {
            this.vlc.setDy(this.vlc.getDy() * -1);
        } */


        // new center for the next location of the point
        //Point newCenter = this.getVelocity().applyToPoint(this.center);
        //the expected new center after the move
        double dx = this.getVelocity().getDx() * dt;
        double dy = this.getVelocity().getDy() * dt;
        Point newCenter = new Point(this.center.getX() + dx,
                this.center.getY() + dy);
        Line ballTrajectory = new Line(this.center, newCenter);
        CollisionInfo cInfo = this.environment.getClosestCollision(ballTrajectory);
        if (cInfo == null) {
            //no collision in the trajectory, good to move
            this.center = newCenter;
        } else {
            //there is a hit
            //create 4 variables axis x y for old/new position center of ball
            double x1 = this.center.getX();
            double y1 = this.center.getY();
            double x2 = ballTrajectory.end().getX();
            double y2 = ballTrajectory.end().getY();
            //create helper that will take the new values
            double newX = x1, newY = y1;
            //tilt
            double m = 1;
            //if the trajectory is parralel to the y axis (same x)
            // we cant calculate the linear equation
            if (x1 == x2) {
                if (y1 > y2) {
                    //up
                    newY += 0.1;
                } else {
                    //down
                    newY -= 0.1;
                }
            } else {
                //calculate the linear equation for the trajectory
                //tilt
                m = (-1) * (y1 - y2) / (x1 - x2);
                if (x1 > x2) {
                    //
                    newX += 0.1;
                } else {
                    //right
                    newX -= 0.1;
                }
                if (y1 != y2) {
                    newY = m * newX - m * x1 + y1;
                    newY = (y1 - newY) + y1;
                }
            }

            // new center point
            this.center = new Point(newX, newY);
            //update the velocity
            this.vlc.setVelocity(cInfo.collisionObject().hit(this, cInfo.collisionPoint(), this.vlc));
        }
    }

    /**
     * .
     * all cirlce. bigRectangle, stay in the big rectangle
     */
    public void bigRectangle() {
        //move one step and change the point x, y values
        this.center = this.getVelocity().applyToPoint(this.center);
        if ((this.center.getX() + this.radius >= 500 && this.vlc.getDx() > 0)
                || (this.center.getX() - this.radius <= 50 && this.vlc.getDx() < 0)) {
            this.vlc.setDx(this.vlc.getDx() * -1);
        }
        if ((this.center.getY() + this.radius >= 500 && this.vlc.getDy() > 0)
                || (this.center.getY() - this.radius <= 50 && this.vlc.getDy() < 0)) {
            this.vlc.setDy(this.vlc.getDy() * -1);
        }
    }

    /**
     * .
     * all cirlce.smallRectangle, stay in the small rectangle
     */
    public void smallRectangle() {

        //move one step and change the point x, y values
        this.center = this.getVelocity().applyToPoint(this.center);
        if ((this.center.getX() + this.radius >= 600 && this.vlc.getDx() > 0)
                || (this.center.getX() - this.radius <= 450 && this.vlc.getDx() < 0)) {
            this.vlc.setDx(this.vlc.getDx() * -1);
        }
        if ((this.center.getY() + this.radius >= 600 && this.vlc.getDy() > 0)
                || (this.center.getY() - this.radius <= 450 && this.vlc.getDy() < 0)) {
            this.vlc.setDy(this.vlc.getDy() * -1);
        }
    }

    /**
     * notify the sprite that time has passed
     * alart that time passed.
     * @param dt the dt
     */
    public void timePassed(double dt) {
        moveOneStep(dt);

    }



    /**
     * add ball to game.
     *
     * @param g game to add the ball to sprit collection
     */
    public void addToGame(GameLevel g) {

        this.gameLevel = g;
        g.addSprite(this);
    }

    /**
     * get game to game.
     *@return the game
     */
    public GameLevel getGame() {

        return this.gameLevel;
    }

    /**
     * add hit listener hl to list of hit.
     *
     * @param hl the hit listener to add
     */
    public void addHitListener(HitListener hl) {
        hitListeners = hl;
    }

    /**
     * get hit listener hl.
     *@return the hit listener
     */
    public HitListener getHitListener() {
        return hitListeners;
    }

    /**
     * .
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hitlistener
     */
    public void removeHitListener(HitListener hl) {
        hitListeners = null;

    }

    /**
     * .
     * notify the hit
     *
     * @param hittedBlock the hit block
     */
    public void notifyFallBall(Block hittedBlock) {
        // Make a copy of the hitListeners before iterating over them.


        hitListeners.hitEvent(hittedBlock, this);

    }

    /**
     * remove Block in the game.
     *
     * @param game the block to remove from the game
     */
    public void removeFromGame(GameLevel game) {

        game.removeSprite(this);

    }


}
