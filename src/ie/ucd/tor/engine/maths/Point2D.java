package ie.ucd.tor.engine.maths;

public class Point2D {

	private double x;
	private double y;

	public static Point2D Zero = new Point2D();

	public Point2D() {
		setX(0.0f);
		setY(0.0f);
	}

	public Point2D(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public String toString() {
		return ("(" + getX() + ", " + getY() + ")");
	}

	public Point2D Add(Vector2D Additional) {
		return new Point2D(this.getX() + Additional.getX(), this.getY() + Additional.getY());
	}

	public Point2D Subtract(Vector2D Minus) {
		return new Point2D(this.getX() - Minus.getX(), this.getY() - Minus.getY());
	}

	public Vector2D Subtract(Point3D Minus) {
		return new Vector2D(this.getX() - Minus.getX(), this.getY() - Minus.getY());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
