package geometry.Bases;

/**
*
* @author Ben Azoulay
*
*/


public class Velocity {
  private double dx;
  private double dy;

  /**.
   * constructor
   * Velocity specifies the change in position on the `x` and the `y` axes.
   * @param dy coordinate
   * @param dx coordinate
   *
   */
   public Velocity(double dx, double dy) {
     this.dx = dx;
     this.dy = dy;
   }

   /**
     * @return the dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the dy
     */
    public double getDy() {
        return this.dy;
    }

    /**.
    * initialize the dx value
    *
    * @param x the changing position in x
    *
    */
    public void setDx(double x) {
      this.dx = x;
    }

    /**.
    * initialize the dy value
    *
    * @param y the changing position in y
    *
    */
    public void setDy(double y) {
      this.dy = y;
    }

   /**.
   * Take a point with position (x,y) and return a new point
   * with position (x+dx, y+dy)
   * @param p the changing position in y
   * @return the ball created for new location on screen
   */
   public Point applyToPoint(Point p) {
     double xValue = p.getX() + this.dx;
     double yValue = p.getY() + this.dy;
     Point newBall = new Point(xValue, yValue);
     return newBall;
   }

   /**.
   * specify the velocity in terms and angle and a speed,
   * so instead of specifying dx=2, dy=0, you could specify
   * (90, 2) meaning 2 units in the 90 degrees direction
   * (assuming up is angle 0)
   * @param angle
   *               the angle (the direction to go)
   * @param speed
   *               the speed
   * @return the new velocity
   */
   public static Velocity fromAngleAndSpeed(double angle, double speed) {
     double dx = speed * Math.sin(Math.toRadians(angle));
     double dy = speed * -(Math.cos(Math.toRadians(angle)));
     return new Velocity(dx, dy);
  }

    /**.
     * sets the dx and dy of the velocity
     * @param v
     * sets velocity to get the dx and dy of
     *
     */
    public void setVelocity(Velocity v) {
        this.dx = v.getDx();
        this.dy = v.getDy();
    }


    /**
     * clone.
     * @return clone
     */
    public Velocity clone() {
        Velocity clonedVelocity = new Velocity(dx, dy);
        return clonedVelocity;
    }
}
