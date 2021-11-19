package mm.avidvivarta.Lorenz;

import mm.avidvivarta.renderer.point.Point3D;

public class LorenzAttractor {

	private Point3D point3d;
	private double dt = 0.01;
	private final double SIGMA = 10.0;
	private final double BETA = 8.0 / 3.0;
	private final double ROH = 28.0;
	private double timer = 0;

	private Transform<Point3D> transform = new Transform<Point3D>() {

		@Override
		public Point3D updatePoints(Point3D val) {

			double dx = SIGMA * (val.getY() - val.getX());
			double dy = val.getX() * (ROH - val.getZ()) - val.getY();
			double dz = val.getX() * val.getY() - BETA * val.getZ();
			double nx = val.getX() + dx * dt;
			double ny = val.getY() + dy * dt;
			double nz = val.getZ() + dz * dt;
			return new Point3D(nx, ny, nz);

		}
	};
	// 1, 1, 1
	// 0, 40, 0.01

	public LorenzAttractor() {

		this.point3d = new Point3D(0, 0.1, 0.1);

	}

	public LorenzAttractor(double x, double y, double z) {

		this.point3d = new Point3D(x, y, z);

	}

	public LorenzAttractor(Point3D point3d) {

		this.point3d = new Point3D(point3d.getX(), point3d.getY(), point3d.getZ());

	}

	public LorenzAttractor(double x, double y, double z, double dt) {

		this.point3d = new Point3D(x, y, z);
		this.dt = dt;

	}

	public LorenzAttractor(Point3D point3d, double dt) {

		this.point3d = new Point3D(point3d.getX(), point3d.getY(), point3d.getZ());
		this.dt = dt;

	}

	public void setTransform(Transform<Point3D> transform) {

		this.transform = transform;

	}

	public Point3D iterate() {

		this.timer += this.dt;
		this.point3d = this.transform.updatePoints(this.point3d);
		return this.point3d;

	}

	public double getCurrentTime() {

		return this.timer;

	}

	public double getDt() {

		return this.dt;

	}

	public Point3D getCurrentLocation() {

		return this.point3d;

	}

}
