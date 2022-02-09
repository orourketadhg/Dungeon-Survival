package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.core.systems.CollisionController;
import ie.ucd.tor.engine.maths.Point2D;

import java.util.List;

public class Collider extends Component {

	private final int colliderWidth;
	private final int colliderHeight;
	private final Point2D offset;

	public Collider(int colliderWidth, int colliderHeight, Point2D offset) {
		this.colliderWidth = colliderWidth;
		this.colliderHeight = colliderHeight;
		this.offset = offset;
	}

	public int getColliderWidth() {
		return colliderWidth;
	}

	public int getColliderHeight() {
		return colliderHeight;
	}

	public Point2D getOffset() {
		return offset;
	}

	public List<CollisionData> getCollisions() {
		return CollisionController.getInstance().getCollisions(this);
	}

	@Override
	public void enable() {
		super.enable();
		CollisionController.getInstance().addColliderToSystem(this);
	}

	@Override
	public void disable() {
		super.disable();
		CollisionController.getInstance().removeColliderToSystem(this);
	}

	@Override
	public String toString() {
		return "Collision{" +
				"gameObject: " + gameObject +
				", colliderWidth: " + colliderWidth +
				", colliderHeight: " + colliderHeight +
				", offset: "  + offset +
				'}';
	}
}
