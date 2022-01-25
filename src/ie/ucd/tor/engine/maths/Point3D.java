package ie.ucd.tor.engine.maths;

public class Point3D {

	private double x;
	private double y;
	private double z;

	public static Point3D Zero = new Point3D();

	public Point3D() {
		setX(0.0f);
		setY(0.0f);
		setZ(0.0f);
	}

	public Point3D(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public String toString() {
		return ("(" + getX() + ", " + getY() + ", " + getZ() + ")");
	}

	public Point3D Add(Vector3D Additional) {
		return new Point3D(this.getX() + Additional.getX(), this.getY() + Additional.getY(), this.getZ() + Additional.getZ());
	}

	public Point3D Subtract(Vector3D Minus) {
		return new Point3D(this.getX() - Minus.getX(), this.getY() - Minus.getY(), this.getZ() - Minus.getZ());
	}

	public Vector3D Subtract(Point3D Minus) {
		return new Vector3D(this.getX() - Minus.getX(), this.getY() - Minus.getY(), this.getZ() - Minus.getZ());
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

}


