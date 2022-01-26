package ie.ucd.tor.engine.core.components;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.maths.Point2D;

public class Transform extends Component {

	private Point2D position;

	public Transform() {
		position = Point2D.Zero;
	}

	@Override
	public void registerParent(GameObject gameObject, Transform transform) {
		super.registerParent(gameObject, this);
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

}
