public class Point {
    static int numberOfPoints;
    private double x, y;
    

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        numberOfPoints++;
    }

        public Point(Point f) {
        this(f.getX(), f.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(Point p) {
        return this.x == p.x && this.y == p.y;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String toString() {
        return "(" + x + "/" + y + ")";
    }

       
}
