package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.maths.Point2D;

public class Transform extends Component {

	private Point2D position;
	private Point2D scale;

	public Transform() {
		position = Point2D.Zero;
		scale = Point2D.Zero;
	}

	@Override
	public void registerParent(GameObject gameObject, Transform transform) {
		super.registerParent(gameObject, this);
	}

	public Point2D getPosition() {
		return position;
	}

	public Point2D getScale() {
		return scale;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setScale(Point2D scale) {
		this.scale = scale;
	}
}
