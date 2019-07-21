package interfaces;
import geometry.Bases.Rectangle;
import geometry.Bases.Point;
import geometry.Bases.Velocity;
import geometry.GameObjects.Ball;



/**
 * @author Ben azoulay
 */
public interface Collidable {
    // Return the "collision shape" of the object.

    /**
     * .
     * returns the "collision shape" of the object
     *
     * @return shape
     */
    Rectangle getCollisionRectangle();


    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).

    /**
     * .
     * changes the velocity when the ball hits the rectangle block
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter          the ball that hit a collidable
     * @return the new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}