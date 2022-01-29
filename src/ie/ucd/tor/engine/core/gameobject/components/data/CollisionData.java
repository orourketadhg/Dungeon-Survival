package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collision;

public class CollisionData {

	private final CollisionType collisionType;

	private final Collision A;
	private final Collision B;

	public CollisionData(CollisionType collisionType, Collision a, Collision b) {
		this.collisionType = collisionType;
		A = a;
		B = b;
	}

	public CollisionType getCollisionType() {
		return collisionType;
	}

	public Collision getA() {
		return A;
	}

	public Collision getB() {
		return B;
	}

	public boolean CompareCollision(CollisionData data) {
		if (this.A == data.getA() && this.B == data.getB()) {
			return true;
		}
		else if (this.A == data.getB() && this.B == data.getA()) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean collisionIncludes(Collision collider) {
		return collider == A || collider == B;
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
