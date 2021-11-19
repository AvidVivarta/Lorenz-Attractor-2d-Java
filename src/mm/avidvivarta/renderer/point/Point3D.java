package mm.avidvivarta.renderer.point;

public class Point3D {

	private double x, y, z;

	public Point3D(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;

	}

	@Override
	public String toString() {

		return "[" + this.x + ", " + this.y + ", " + this.z + "]";

	}

	@Override
	public boolean equals(Object object) {

		if (object == null) return false;
		Point3D p = (Point3D) object;
		if (p.x == this.x && p.y == this.y && p.z == this.z) return true;
		return false;

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

	public double getZ() {

		return z;

	}

	public void setZ(double z) {

		this.z = z;

	}

}
