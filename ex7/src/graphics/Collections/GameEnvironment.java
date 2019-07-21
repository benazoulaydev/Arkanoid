package graphics.Collections;

import java.util.ArrayList;
import java.util.List;
import interfaces.Collidable;

import geometry.Bases.Rectangle;
import geometry.Bases.Line;
import geometry.Bases.Point;

import graphics.Handlers.CollisionInfo;

/**
 * @author Ben Azoulay
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * .
     * <p>
     * constructor, to creates an array list
     * for all of the collidable objects
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * .
     * add the given collidable to the environment :
     * to the list
     *
     * @param c new collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }


    /**
     * .
     * remove the given collidable to the environment :
     * to the list
     *
     * @param c new collidable to add
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * .
     * get the list of collidable to the environment :
     *
     * @return list of colidables
     */
    public List<Collidable> getCollidableList() {
        return collidables;
    }


    /**
     * .
     * <p>
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory new collidable to add
     * @return closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {


        //closest colision point variable
        Point closestColPnt;
        //helper
        Point tmp = null;
        // keep the smallest distance
        double smallestDistance;
        // create a rectangle
        Rectangle r;
        // variable for closest collision object
        Collidable closestCol = null;

        //for object in list colidables search for the closer collision
        for (Collidable c : this.collidables) {

            //assign the next shape to r
            r = c.getCollisionRectangle();
            closestColPnt = trajectory.closestIntersectionToStartOfLine(r);

            if (closestColPnt != null && trajectory.isPointInLine(closestColPnt)) {


                if (tmp == null) {
                    tmp = closestColPnt;
                }
                smallestDistance = trajectory.start().distance(tmp);
                if (trajectory.start().distance(closestColPnt) <= smallestDistance) {
                    tmp = closestColPnt;
                    closestCol = c;
                }
            }
        }

        CollisionInfo clInfo = null;
        if (tmp != null) {

            clInfo = new CollisionInfo(tmp, closestCol);
        }

        return clInfo;

    }
}