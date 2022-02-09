package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collider;

public class CollisionData {

	private final CollisionType collisionType;

	private final Collider A;
	private final Collider B;

	public CollisionData(CollisionType collisionType, Collider a, Collider b) {
		this.collisionType = collisionType;
		A = a;
		B = b;
	}

	public CollisionType getCollisionType() {
		return collisionType;
	}

	public Collider getA() {
		return A;
	}

	public Collider getB() {
		return B;
	}

	public boolean CompareCollision(CollisionData data) {
		if (this.A == data.getA() && this.B == data.getB()) {
			return true;
		}
		else return this.A == data.getB() && this.B == data.getA();
	}

	public boolean collisionIncludes(Collider collider) {
		return collider == A || collider == B;
	}

	public GameObject getOther(Collider collider) {

		if (!collisionIncludes(collider)) {
			return null;
		}

		if (collider == A) {
			return B.getGameObject();
		}

		return A.getGameObject();
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
