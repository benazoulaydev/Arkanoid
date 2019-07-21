package geometry.GameObjects;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import interfaces.Collidable;
import interfaces.Sprite;

import game.GameLevel;

import geometry.Bases.Rectangle;
import geometry.Bases.Line;
import geometry.Bases.Point;
import geometry.Bases.Velocity;

/**
 *
 * @author Ben azoulay
 *
 */
public class Paddle implements Sprite, Collidable {



    private biuoop.KeyboardSensor keyboard;
    private Rectangle shape;
    private GUI gui;
    private Color color;
    private Line[] arrayLineForRegion;
    private GameLevel game;
    private double width;
    private double height;
    private double paddleHeight;

    private int paddleSpeed;

    // Constructor

    /**
     * @param upperLeft1 the upper left corner of the paddle
     * @param width1     the width of the paddle
     * @param height1    the height of the paddle
     * @param theGui        the gui
     * @param widthS        the width of the screen
     * @param heightS        the height of the screen

     */
    public Paddle(Point upperLeft1, double width1, double height1, GUI theGui, double widthS, double heightS) {
        // color black as default
        this.shape = new Rectangle(upperLeft1, width1, 1, Color.yellow);

        this.color = this.shape.getColor();
        this.paddleHeight = height1;

        this.gui = theGui;
        this.width = widthS;
        this.height = heightS;
        //assign 10 for paddle speed
        this.paddleSpeed = 5;
    }

    /**
     * @param upperLeft1 the upper left corner of the paddle
     * @param width1     the width of the paddle
     * @param height1    the height of the paddle
     * @param speed        the speed
     * @param gui        the gui
     * @param color        the color
     * @param g        the GameLevel
     */
    public Paddle(Point upperLeft1, double width1, double height1, int speed, GUI gui, GameLevel g, Color color) {
        this.shape = new Rectangle(upperLeft1, 1, width1, color);
        updateRegion();
        this.gui = gui;
        this.color = color;
        this.game = g;
        this.paddleSpeed = speed;
        this.paddleHeight = height1;


    }
    /**.
     * changes paddle coordinates and creates a new paddle
     * as paddle moves left
     * @param dt the change in time
     */
    //moving paddle to the left
    //and updating the regions on the paddle
    public void moveLeft(double dt) {
        int newSpeed = (int) (this.paddleSpeed * dt);
        Point upper = new Point(this.shape.getUpperLeft().getX()
                - newSpeed, this.shape.getUpperLeft().getY());
        if (upper.getX() - 20 > -1) {
            this.shape.changeUpperLeft(upper);
            updateRegion();
        }
    }
    /**.
     * changes paddle coordinates and creates a new paddle
     * as paddle moves right
     * @param dt the change in time
     */
    //moving paddle to the right
    //and updating the regions on the paddle
    public void moveRight(double dt) {
        int newSpeed = (int) (this.paddleSpeed * dt);
        Point upper = new Point(this.shape.getUpperLeft().getX() + newSpeed,
                this.shape.getUpperLeft().getY());
        if (upper.getX() + this.shape.getHeight() + 20 < this.game.getWidth() + 1) {
            this.shape.changeUpperLeft(upper);
            updateRegion();
        }
    }


    /**.
     * set the paddle speed to :
     * @param speedForPaddle the speed of the paddle to set
     */
    public void setPaddleSpeed(int speedForPaddle) {

        this.paddleSpeed = speedForPaddle;
    }

    /**.
     * get the paddle speed
     *@return the paddle speed
     */
    public int getPaddleSpeed() {

        return this.paddleSpeed;
    }


    /**.
     * if right or left were pressed move the paddle accordingly
     * @param dt the dt
     */
    public void timePassed(double dt) {

        this.keyboard = gui.getKeyboardSensor();
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }

    }


    /**.
     * draw the sprite to the screen
     * draw the paddle on the given DrawSurface
     *
     * @param d draw surface
     */
    public void drawOn(DrawSurface d) {

        d.setColor(color.yellow);
        d.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getHeight(), (int) paddleHeight);
        d.setColor(color.BLACK);
        d.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getHeight(), (int) paddleHeight);


    }

    // Collidable

    /**
     * .
     * returns the "collision shape" of the object
     *
     * @return shape
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * .
     * ball hit the paddle
     *
     * @param collisionPoint the collisation point
     * @param currentVelocity the current velocity
     * @param hitter          the ball that hit a collidable
     * @return the new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Line[] borders = this.shape.getRectLines();
        Line collisionLine = new Line(collisionPoint, collisionPoint);

        Point[] edges = this.shape.getRectPoints();
        updateRegion();
        Line[] regions = this.arrayLineForRegion;


        // if collision point not on edge
        if (!collisionPoint.equals(edges[0]) && !collisionPoint.equals(edges[1])
                && !collisionPoint.equals(edges[2]) && !collisionPoint.equals(edges[3])) {
            // up
            if (borders[0].isPointInLine(collisionPoint)) {
                double speed;

                speed = Math.sqrt((Math.pow(dx, 2)) + (Math.pow(dy, 2)));


                if (regions[0].isPointInLine(collisionPoint) || collisionLine.isIntersecting(borders[1])) {
                    currentVelocity = currentVelocity.fromAngleAndSpeed(300, speed);

                }
                if (regions[1].isPointInLine(collisionPoint)) {
                    currentVelocity = currentVelocity.fromAngleAndSpeed(330, speed);

                }
                if (regions[2].isPointInLine(collisionPoint)) {
                    currentVelocity.setDy((-1) * dy);


                }
                if (regions[3].isPointInLine(collisionPoint)) {
                    currentVelocity = currentVelocity.fromAngleAndSpeed(30, speed);


                }
                if (regions[4].isPointInLine(collisionPoint) || collisionLine.isIntersecting(borders[3])) {
                    currentVelocity = currentVelocity.fromAngleAndSpeed(60, speed);

                }


            }
            // left
            if (borders[1].isPointInLine(collisionPoint)) {
                currentVelocity.setDx((-1) * dx);
            }


            // down
            if (borders[2].isPointInLine(collisionPoint)) {
                currentVelocity.setDy((-1) * dy);
            }


            // right
            if (borders[3].isPointInLine(collisionPoint)) {
                currentVelocity.setDx((-1) * dx);
            }


        } else {
            // has to be on edge so change the two velocity
            currentVelocity.setDy((-1) * dy);
            currentVelocity.setDx((-1) * dx);
        }
        return currentVelocity;
    }

    /**
     * .
     * updates the region as the paddle moves
     */
    //updates the regions on the paddle
    public void updateRegion() {
        this.arrayLineForRegion = new Line[5];
        Point upperLeft = this.shape.getUpperLeft();
        Point upperRight = new Point(this.shape.getUpperLeft().getX() + this.shape.getWidth()
                , this.shape.getUpperLeft().getY());


        double xAxisTmp = upperLeft.getX();
        for (int i = 0; i < 5; i++) {

            this.arrayLineForRegion[i] = new Line(xAxisTmp, upperLeft.getY(),
                    xAxisTmp + (this.shape.getHeight() / 5), upperLeft.getY());
            xAxisTmp = xAxisTmp + (this.shape.getHeight() / 5);

        }
    }


    /**
     * .
     * Add this paddle to the game.
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**.
     * removes the paddle to the game
     * @param g
     *            removes paddle to the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}