package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collider;

/**
 * state-based collision data
 */
public class CollisionData {

	// the current state of the collision
	private final CollisionType collisionType;

	// the colliders involved in the collision
	private final Collider A;
	private final Collider B;

	public CollisionData(CollisionType collisionType, Collider a, Collider b) {
		this.collisionType = collisionType;
		A = a;
		B = b;
	}

	/**
	 * Check if collisions are equal
	 *
	 * @param data, the data being compared
	 * @return true if the collision data is equal, false otherwise
	 */
	public boolean CompareCollision(CollisionData data) {
		if (this.A == data.getA() && this.B == data.getB()) {
			return true;
		}
		else return this.A == data.getB() && this.B == data.getA();
	}

	/**
	 * Check if the collision includes a collider
	 * @param collider, the collider being checked if it is involved in the collision
	 * @return true if the collider is involved, false otherwise
	 */
	public boolean collisionIncludes(Collider collider) {
		return collider == A || collider == B;
	}

	/**
	 * Get the other collider in the collision
	 * @param collider, the collider known to be in the collision
	 * @return The other collider, if the provided collider is not involved null is returned
	 */
	public GameObject getOther(Collider collider) {

		if (!collisionIncludes(collider)) {
			return null;
		}

		if (collider == A) {
			return B.getGameObject();
		}

		return A.getGameObject();
	}

	// ACCESSORS

	public CollisionType getCollisionType() {
		return collisionType;
	}

	public Collider getA() {
		return A;
	}

	public Collider getB() {
		return B;
	}

	@Override
	public String toString() {
		return "CollisionData{" +
				"collisionType=" + collisionType +
				", A=" + A +
				", B=" + B +
				'}';
	}


}
