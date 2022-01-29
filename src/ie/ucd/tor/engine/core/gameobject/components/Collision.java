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

	}

	public int getColliderWidth() {
		return colliderWidth;
	}

	public int getColliderHeight() {
		return colliderHeight;
	}

	public void setColliderWidth(int colliderWidth) {
		this.colliderWidth = colliderWidth;
	}

	public void setColliderHeight(int colliderHeight) {
		this.colliderHeight = colliderHeight;
	}

	public List<CollisionData> getCollisions() {
		return CollisionController.getInstance().getCollisionsWithGameObject(gameObject);
	}

}
