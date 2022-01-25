package ie.ucd.tor.engine.core.components;

import ie.ucd.tor.engine.core.GameObject;

public abstract class Component {

	protected GameObject gameObject;
	protected Transform transform;

	public void registerParent(GameObject gameObject, Transform transform) {
		this.gameObject = gameObject;
		this.transform = transform;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public Transform getTransform() {
		return transform;
	}

	public void Execute() {

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
