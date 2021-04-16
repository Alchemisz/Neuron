public class Point {

    double x10, x20;

    public Point(double x10, double x20) {
        this.x10 = RoundDouble.round(x10, 2);
        this.x20 = RoundDouble.round(x20, 2);
    }

    public double getX10() {
        return x10;
    }

    public double getX20() {
        return x20;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x10 = " + x10 +
                ", x20 = " + x20 +
                '}';
    }

}
