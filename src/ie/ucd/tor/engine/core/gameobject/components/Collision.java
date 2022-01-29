package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.core.systems.CollisionController;

import java.util.List;

public class Collision extends Component {

	private int colliderWidth;
	private int colliderHeight;

	public Collision(int colliderWidth, int colliderHeight) {
		this.colliderWidth = colliderWidth;
		this.colliderHeight = colliderHeight;

		CollisionController.getInstance().addGameObjectWithCollider(gameObject);

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

}
