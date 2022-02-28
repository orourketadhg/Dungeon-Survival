package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.GameObject;

/**
 * Base component class
 */
public abstract class Component {

	// is the component enabled
	protected boolean isEnabled;

	// the GameObject and Transform the component is attached to
	protected GameObject gameObject;
	protected Transform transform;

	public void registerParent(GameObject gameObject, Transform transform) {
		this.gameObject = gameObject;
		this.transform = transform;
		this.isEnabled = true;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void enable() {
		isEnabled = true;
	}

	public void disable() {
		isEnabled = false;
	}

	// ACCESSORS

	public GameObject getGameObject() {
		return gameObject;
	}

	public Transform getTransform() {
		return transform;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
