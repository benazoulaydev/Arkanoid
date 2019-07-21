package geometry.Bases;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Ben Azoulay
 */

public class Rectangle {
    private double width, height;
    private Point upperLeft;
    private Color shapeColor;


    /**
     * .
     * constructor
     * <p>
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft coordinate
     * @param width     coordinate
     * @param height    coordinate
     * @param c         Color
     */
    public Rectangle(Point upperLeft, double width, double height, Color c) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.shapeColor = c;

    }

    /**
     * .
     * copy constructor
     * <p>
     * Create a new rectangle with location and width/height.
     *
     * @param x      x coordinate
     * @param y      y coordinate
     * @param width  coordinate
     * @param height coordinate
     * @param c      Color
     */
    public Rectangle(int x, int y, double width, double height, Color c) {
        Point p = new Point(x, y);
        this.upperLeft = p;
        this.width = width;
        this.height = height;
        this.shapeColor = c;

    }

    /**
     * .
     * returns the width of the rectangle
     *
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * .
     * returns the height of the rectangle
     *
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * .
     * Returns the color of the rectangle.
     *
     * @return shapeColor
     */
    public Color getColor() {
        return this.shapeColor;
    }


    /**
     * .
     * Returns the upper-left point of the rectangle.
     *
     * @return upperLeft
     * returns a new point with the upper left values
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft);
    }

    /**
     * .
     * <p>
     * Return a (possibly empty) List of intersection points with the specified line.
     * checks for intersection points with line
     * and returns list of them
     *
     * @param line the line to check with
     * @return list
     * the intersection points list
     */
    public ArrayList<Point> intersectionPoints(Line line) {

        ArrayList<Point> list = new ArrayList<Point>(4);
        Line[] rectangleLines = this.getRectLines();
        for (int i = 0; i < rectangleLines.length; i++) {
            Point aPoint = rectangleLines[i].intersectionWith(line);

            if (aPoint != null) {
                list.add(aPoint);
            }
        }
        //check if it exist intersection points and add them to the list
        return list;
    }

    /**
     * .
     * gets the 4 Point corner of the rectangle and create an array of it
     *
     * @return array : an array of 4 corner Points
     */
    public Point[] getRectPoints() {
        double newX = this.upperLeft.getX() + this.width;
        double newY = this.upperLeft.getY() + this.height;
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();
        Point[] array = new Point[4];
        //up left corner
        array[0] = this.getUpperLeft();
        //up right corner
        array[1] = new Point(newX, y);
        //low left corner
        array[2] = new Point(x, newY);
        //low right corner
        array[3] = new Point(newX, newY);
        return array;
    }

    /**
     * .
     * gets the 4 lines of the rectangle and create an array of it
     *
     * @return arrayLine
     * array of the 4 rectangles lines
     */
    public Line[] getRectLines() {
        Line[] arrayLine = new Line[4];
        Point startL1 = new Point(upperLeft.getX(), upperLeft.getY());
        Point endL1 = new Point(startL1.getX(), startL1.getY() + width);
        Point startL2 = new Point(upperLeft.getX(), upperLeft.getY() + width);
        Point endL2 = new Point(startL2.getX() + height, startL2.getY());
        Point startL3 = new Point(upperLeft.getX() + height, upperLeft.getY() + width);
        Point endL3 = new Point(startL3.getX(), startL3.getY() - width);
        Point startL4 = new Point(upperLeft.getX() + height, upperLeft.getY());
        Point endL4 = new Point(upperLeft.getX(), startL4.getY());
        arrayLine[1] = (new Line(endL1, startL1));
        arrayLine[2] = (new Line(endL2, startL2));
        arrayLine[3] = (new Line(endL3, startL3));
        arrayLine[0] = (new Line(endL4, startL4));
        return arrayLine;
    }

    /**
     * .
     * For test purpose
     * gets the 4 lines of the rectangle and create an array of it
     *
     * @return arrayLine
     * array of the 4 rectangles lines
     */
    private Line[] getRectLinesTest() {
        Line[] arrayLine = new Line[4];
        Point[] arrayPoint = this.getRectPoints();
        //up
        arrayLine[0] = new Line(arrayPoint[0], arrayPoint[1]);
        //left
        arrayLine[1] = new Line(arrayPoint[0], arrayPoint[2]);
        //down
        arrayLine[2] = new Line(arrayPoint[2], arrayPoint[3]);
        //right
        arrayLine[3] = new Line(arrayPoint[1], arrayPoint[3]);
        return arrayLine;
    }


    /**.
     * change  the upper left value .
     *
     * @param p
     * the upperleft to update
     *
     *
     */
    public void changeUpperLeft(Point p) {
        this.upperLeft = p;
    }
}
