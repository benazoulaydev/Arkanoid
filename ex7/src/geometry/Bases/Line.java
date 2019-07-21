package geometry.Bases;

import java.util.ArrayList;

/**
 *
 * @author Ben azoulay
 *
 */
public class Line {
    private Point start;
    private Point end;

    /**.
     *
     *  constructors
     *
     * @param start  the first point
     *
     * @param end the second point
     *
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**.
     *
     *   constructors
     *
     * @param x1 x coordinate of the first point
     * @param y1 y coordinate of the first point
     * @param x2 x coordinate of the second point
     * @param y2 y coordinate of the second point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**.
     *
     * calculate the length of the line
     *
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }




    /**.
     * create the middle point of this line
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        Point middle =  new Point(middleX, middleY);
        return middle;
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.end;

    }
    /**.
     * check if 2 lines intersect
     *
     * @param other the other line
     *
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        //check orientatio each time and compare it
        int dir1 = direction(this.start, this.end, other.start);
        int dir2 = direction(this.start, this.end, other.end);
        int dir3 = direction(other.start, other.end, this.start);
        int dir4 = direction(other.start, other.end, this.end);

        if (dir1 != dir2 && dir3 != dir4) {
            return true;
        }
        if (dir1 == 0 && this.isPointInLine(other.start)) {
            return true;
        }
        if (dir2 == 0 && this.isPointInLine(other.end)) {
            return true;
        }
        if (dir3 == 0 && other.isPointInLine(this.start)) {
            return true;
        }
        if (dir4 == 0 && other.isPointInLine(this.end)) {
            return true;
        }
        return false;
    }
    /**.
     * get the intersection point if exist
     *
     * @param other the other line
     * @return the intersection point if the lines intersect, and null
     *         otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!(this.isIntersecting(other))) {
            return null;
        }
        Point result;
        result = this.sameX(other);
        if (result != null) {
            return result;
        }
        result = other.sameX(this);
        if (result != null) {
            return result;
        }
        double xa = this.start.getX();
        double xb = this.end.getX();
        double ya = this.start.getY();
        double yb = this.end.getY();
        //calcul slope of equation : ax+b here a
        double firstLineSlope = (yb - ya) / (xb - xa);
        //calcul the b value of equation : ax+b
        double findB = ya - firstLineSlope * xa;


        xa = other.start.getX();
        xb = other.end.getX();
        ya = other.start.getY();
        yb = other.end.getY();
        //calcul slope of equation : mx+p here m
        double sndLineSlope = (yb - ya) / (xb - xa);
        //calcul the p value of equation : mx+p
        double findP = ya - sndLineSlope * xa;
        // result display x and y and create a point
        double xResult = (findB - findP) / (sndLineSlope - firstLineSlope);
        double yResult = sndLineSlope * xResult + findP;
        result =  new Point(xResult, yResult);
        return result;

    }


    /**.
     * check if 2 line are equal
     *
     * @param other
     *            the other line
     * @return true is the lines are equal, false otherwise
     */
    public Point sameX(Line other) {
        Point result;
        double maxY1, minY1, maxY2, minY2;
        double xa = this.start.getX();
        double xb = this.end.getX();
        double ya = this.start.getY();
        double yb = this.end.getY();
        double xc = other.start.getX();
        double xd = other.end.getX();
        double yc = other.start.getY();
        double yd = other.end.getY();

        if (xa == xb) {
            // if the two lines are colineare
            if (xc == xd) {
                //if the x value of the other line equal the new x values
                if (xa ==  xc) {
                    //store the max Y value and min Y of each line
                    if (ya > yb) {
                        maxY1 = ya;
                        minY1 = yb;
                    } else {
                        maxY1 = yb;
                        minY1 = ya;
                    }
                    if (yc > yd) {
                        maxY2 = yc;
                        minY2 = yd;
                    } else {
                        maxY2 = yd;
                        minY2 = yc;
                    }
                    //check if the end of the first line equal to the start of the
                    //second and return the point if not return null
                    if (maxY1 > maxY2) {
                        if (minY1 == maxY2) {
                            result =  new Point(xa, minY1);
                            return result;
                        } else { // else  minY1 != maxY2
                            return null;
                        } // end else  minY1 != maxY2
                    } else { // else maxY1 < maxY2
                        if (minY2 == maxY1) {
                            result =  new Point(xa, minY2);
                            return result;
                        } else { // else  minY2 != maxY1
                            return null;
                        } // end else  minY2 != maxY1
                    } // end else maxY2 < maxY1
                } else { // else the two line have not same x value
                    return null;
                }
            } else {
                // calculate slope
                double sndLineSlope = ((yc - yd)
                        / (xc - xd));
                //calcul the p value of equation : mx+p
                double findP = yc - sndLineSlope * xc;
                // result display x and y and create a point
                double xResult = xa;
                double yResult = sndLineSlope * xResult + findP;
                result =  new Point(xResult, yResult);
                if (!(this.isPointInLine(result))) {
                    return null;
                } else {
                    return result;
                }
            }
        }
        return null;
    }

    /**.
     * check if 2 lines intersect
     *
     * @param a
     *         the first point
     * @param b
     *         the snd point
     * @param c
     *         the third point
     * 3 points
     * @return number for orientation 1 : clockwise, 2: anti-clockwise, 0 : no orienation
     * Check whether they are collinear or anti-clockwise or clockwise direction.
     */
    private int direction(Point a, Point b, Point c) {
        //if same slope : (yb-ya)/(xb-xa)=(yc-yb)/(xc-xb) =>   (yb-ya)(xc-xb)-(xb-xa)(yc-yb)
        double checker = (b.getY() - a.getY())
                * (c.getX() - b.getX()) - (b.getX() - a.getX())
                * (c.getY() - b.getY());
        if (checker == 0) {
            return 0;
        } else if (checker < 0) {
            //anti-clockwise
            return 2;
        }
        //clockwise
        return 1;
    }
    /**.
     * check if 2 line are equal
     *
     * @param other
     *            the other line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (this.start.getX() == other.start.getX()
                &&  this.start.getY() == other.start.getY()
                && this.end.getX() == other.end.getX()
                &&  this.end.getY() == other.end.getY()) {
            return true;
        }
        return false;
    }

    /**
     * .
     * <p>
     * check if 2 line are equal
     * <p>
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rect a rectangle
     * @return a point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> list1 = new ArrayList<Point>();
        double distance = -1, tmp;

        Point keepClosestPoint = null;
        list1 = rect.intersectionPoints(this);
        //return the minimum distance from beginning of line to intersection point

        for (int i = 0; i < rect.intersectionPoints(this).size(); i++) {
            if (rect.intersectionPoints(this) != null) {
                tmp = distance;
                distance = list1.get(i).distance(this.start);
                keepClosestPoint = list1.get(i);
                if (tmp < distance && tmp >= 0) {
                    distance = tmp;
                    keepClosestPoint = list1.get(i - 1);
                }

            }
        }
        return keepClosestPoint;

    }

    /**.
     * check if point in line
     *
     * @param p1
     *            the point
     * @return true if in line, false otherwise
     */
    public Boolean isPointInLine(Point p1) {
        double smallNumber = 0.000001;
        double x1 = this.start.getX();
        double x2 = this.end.getX();
        double y1 = this.start.getY();
        double y2 = this.end.getY();
        //range of the line for further check
        double minX = Math.min(x1, x2) - smallNumber;
        double minY = Math.min(y1, y2) - smallNumber;
        double maxX = Math.max(x1, x2) + smallNumber;
        double maxY = Math.max(y1, y2) + smallNumber;

        //check if the point p1 is in the range (on the line)
        if (minX  <= p1.getX()
                && maxX  >= p1.getX()
                && minY  <= p1.getY()
                && maxY  >= p1.getY()) {
            return true;
        }
        return false;
    }
}
