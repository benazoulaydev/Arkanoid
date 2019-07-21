package geometry.Bases;

/**
*
* @author Ben Azoulay
*
*/
public class Point {
  private double x;
  private double y;

    /**.
     * constructor
     *
     * @param y coordinate
     * @param x coordinate
     *
     */
   public Point(double x, double y) {
   this.x = x;
   this.y = y;
   }
    /**.
     * copy constructor
     *
     * @param other point
     *
     */
    public Point(Point other) {
        this.x = other.getX();
        this.y = other.getY();
    }



    /**.
     * calcul distance between two points
     *
     * @param other : the other point
     * @return the distance of this point to the other point
     */
   public double distance(Point other) {
     double distanceX = this.x - other.getX();
     double distanceY = this.y - other.getY();
     // calcul the distance between 2 points
     return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
   }

     /**.
     * check same point
     *
     * @param other : the other point
     * @return true is the points are equal, false otherwise
     */
     public boolean equals(Point other) {
       if (this.x == other.getX() && this.y == other.getY()) {
         return true;
       }
       return false;
     }

   /**.
   *
   *
   * @return the  x axis value
   *
   */
   public double getX() {
     return this.x;
   }

   /**.
   *
   *
   * @return the  y axis value
   *
   */
   public double getY() {
     return this.y;
}
}
