package graphics.Handlers;

import interfaces.Collidable;
import geometry.Bases.Point;


/**
 * @author Ben Azoulay
 */
public class CollisionInfo {
    private Point colPoint;
    private Collidable colObject;

    /**
     * .
     * constructor
     * <p>
     * initialize the collision Point and Object
     */
    public CollisionInfo() {
        this.colPoint = null;
        this.colObject = null;
    }

    /**
     * .
     * constructor
     *
     * @param pointCollision  collision point
     * @param objectCollision collision object
     */
    public CollisionInfo(Point pointCollision, Collidable objectCollision) {
        this.colPoint = pointCollision;
        this.colObject = objectCollision;
    }


    /**
     * .
     * <p>
     * sets collision object
     *
     * @param objectCollision the collision object
     */
    public void setCollObject(Collidable objectCollision) {
        this.colObject = objectCollision;
    }

    /**
     * .
     * <p>
     * sets collision point
     *
     * @param pointCollision the collision point
     */
    public void setCollPoint(Point pointCollision) {
        this.colPoint = pointCollision;
    }


    /**
     * .
     * the point at which the collision occurs.
     *
     * @return colPoint
     * the collision point
     */
    public Point collisionPoint() {
        return this.colPoint;

    }

    /**
     * .
     * the collidable object involved in the collision.
     *
     * @return colObject
     * the collision object
     */
    public Collidable collisionObject() {
        return this.colObject;

    }
}
