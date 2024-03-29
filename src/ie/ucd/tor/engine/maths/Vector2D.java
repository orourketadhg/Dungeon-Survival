package ie.ucd.tor.engine.maths;

public class Vector2D {
	public static Vector2D Zero = new Vector2D(0, 0);
	public static Vector2D One = new Vector2D(1, 1);
	public static Vector2D Right = new Vector2D(1, 0);
	public static Vector2D Left = new Vector2D(-1, 0);
	public static Vector2D Up = new Vector2D(0, 1);
	public static Vector2D Down = new Vector2D(0, -1);

	private double x = 0;
	private double y = 0;

	public Vector2D() {
		setX(0.0f);
		setY(0.0f);
	}

	public Vector2D(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public Vector2D add(Vector2D value) {
		return new Vector2D(this.getX() + value.getX(), this.getY() + value.getY());
	}

	public Vector2D subtract(Vector2D value) {
		return new Vector2D(this.getX() - value.getX(), this.getY() - value.getY());
	}

	public Point2D add(Point2D value) {
		return new Point2D(this.getX() + value.getX(), this.getY() + value.getY());
	}

	public Vector2D scale(float scale) {
		return new Vector2D(this.getX() * scale, this.getY() * scale);
	}

	public float length() {
		return (float) Math.sqrt(getX() * getX() + getY() * getY());
	}

	public Vector2D normal() {
		float LengthOfTheVector = this.length();
		return this.scale(1.0f / LengthOfTheVector);
	}

	public static Vector2D normalise(Vector2D value) {
		float valueLength = value.length();
		return value.scale(1.0f / valueLength);
	}

	public static Vector2D round(Vector2D value) {
		int roundedX = (int) Math.round(value.getX());
		int roundedY = (int) Math.round(value.getY());
		return new Vector2D(roundedX, roundedY);
	}

	public double dot(Vector3D v) {
		return (this.getX() * v.getX() + this.getY() * v.getY());
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

	public String toString() {
		return ("(" + getX() + ", " + getY() + ")");
	}

}
