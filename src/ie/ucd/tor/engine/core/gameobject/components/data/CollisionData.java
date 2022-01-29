package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.core.gameobject.GameObject;

public class CollisionData {

	private final CollisionType collisionType;

	private final GameObject A;
	private final GameObject B;

	public CollisionData(CollisionType collisionType, GameObject a, GameObject b) {
		this.collisionType = collisionType;
		A = a;
		B = b;
	}

	public CollisionType getCollisionType() {
		return collisionType;
	}

	public GameObject getA() {
		return A;
	}

	public GameObject getB() {
		return B;
	}

	public boolean Compare(CollisionData data) {
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

}
