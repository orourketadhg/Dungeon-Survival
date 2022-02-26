package ie.ucd.tor.engine.maths;

public class Vector3D {

	public static Vector3D Zero = new Vector3D(0, 0, 0);
	public static Vector3D One = new Vector3D(1, 1, 1);
	public static Vector3D Right = new Vector3D(1, 0, 0);
	public static Vector3D Left = new Vector3D(-1, 0, 0);
	public static Vector3D Up = new Vector3D(0, 1, 0);
	public static Vector3D Down = new Vector3D(0, -1, 0);
	public static Vector3D Forward = new Vector3D(0, 0, 1);
	public static Vector3D Backward = new Vector3D(0, 0, -1);

	private double x = 0;
	private double y = 0;
	private double z = 0;

	public Vector3D() {
		setX(0.0f);
		setY(0.0f);
		setZ(0.0f);
	}

	public Vector3D(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vector3D add(Vector3D value) {
		return new Vector3D(this.getX() + value.getX(), this.getY() + value.getY(), this.getZ() + value.getZ());
	}

	public Point3D add(Point3D value) {
		return new Point3D(this.getX() + value.getX(), this.getY() + value.getY(), this.getZ() + value.getZ());
	}

	public Vector3D subtract(Vector3D value) {
		return new Vector3D(this.getX() - value.getX(), this.getY() - value.getY(), this.getZ() - value.getZ());
	}

	public Vector3D scale(float scale) {
		return new Vector3D(this.getX() * scale, this.getY() * scale, this.getZ() * scale);
	}

	public float length() {
		return (float) Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}

	public Vector3D normal() {
		float LengthOfTheVector = this.length();
		return this.scale(1.0f / LengthOfTheVector);
	}

	public static Vector3D normalise(Vector3D value) {
		float valueLength = value.length();
		return value.scale(1.0f / valueLength);
	}

	public double dot(Vector3D v) {
		return (this.getX() * v.getX() + this.getY() * v.getY() + this.getZ() * v.getZ());
	}

	public Vector3D cross(Vector3D v) {
		double u0 = (this.getY() * v.getZ() - getZ() * v.getY());
		double u1 = (getZ() * v.getX() - getX() * v.getZ());
		double u2 = (getX() * v.getY() - getY() * v.getX());
		return new Vector3D(u0, u1, u2);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String toString() {
		return ("(" + getX() + ", " + getY() + ", " + getZ() + ")");
	}

}
