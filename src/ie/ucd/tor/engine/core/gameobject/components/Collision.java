package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.core.systems.CollisionController;

import java.util.List;

public class Collision extends Component {

	private final int colliderWidth;
	private final int colliderHeight;

	public Collision(int colliderWidth, int colliderHeight) {
		this.colliderWidth = colliderWidth;
		this.colliderHeight = colliderHeight;
	}

	public int getColliderWidth() {
		return colliderWidth;
	}

	public int getColliderHeight() {
		return colliderHeight;
	}

	public List<CollisionData> getCollisions() {
		return CollisionController.getInstance().getCollisionsWithGameObject(gameObject);
	}

	@Override
	public void enable() {
		super.enable();
		CollisionController.getInstance().addGameObjectWithCollider(gameObject);
	}

	@Override
	public void disable() {
		super.disable();
		CollisionController.getInstance().removeGameObjectWithCollider(gameObject);
	}

}
