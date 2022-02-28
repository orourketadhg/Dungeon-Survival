package ie.ucd.tor.engine.core.gameobject;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.Component;
import ie.ucd.tor.engine.core.gameobject.components.Transform;
import ie.ucd.tor.engine.core.systems.BehaviourController;
import ie.ucd.tor.engine.core.systems.CollisionController;
import ie.ucd.tor.engine.rendering.GameWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class to hold the data about an object in the game
 */
public class GameObject {
	private static int ID_COUNT = 0;

	private final int gameObjectID;
	protected boolean isEnabled;
	private String name;
	private final Transform transform;
	private final CopyOnWriteArrayList<Component> attachedComponents;

	public GameObject() {
		gameObjectID = ID_COUNT++;
		name = "";
		isEnabled = true;

		attachedComponents = new CopyOnWriteArrayList<>();

		Transform tmp = new Transform();
		transform = tmp;
		addComponent(tmp);
	}

	public int getGameObjectID() {
		return gameObjectID;
	}

	/**
	 * Add a new component to the gameObject
	 * @param component, the component being added
	 */
	public void addComponent(Component component) {
		component.registerParent(this, transform);
		attachedComponents.add(component);

		// if the component is a collider or behaviour, register the component with their respected execution system
		if (component instanceof Collider) {
			CollisionController.getInstance().addColliderToSystem((Collider) component);
		}
		else if (component instanceof Behaviour) {
			BehaviourController.getInstance().addBehaviourToSystem((Behaviour) component);
		}

	}

	/**
	 * Remove a component from the gameObject
	 * @param cls, the class type of the component being removed
	 * @param <T>, the class type of the component being removed
	 * @return true if successfully removed, false otherwise
	 */
	public <T extends Component> boolean removeComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if this gameObject has this type of gameObject
	 * @param cls, the class type of the component being checked
	 * @param <T>, the class type of the component being checked
	 * @return true if the gameObject has the type of component, false otherwise
	 */
	public <T extends Component> boolean hasComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the first instance of a type of component
	 * @param cls, the class type of the component being searched for
	 * @param <T>, the class type of the component being searched for
	 * @return the first instance of the component
	 */
	public <T extends Component> T getComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return (T) component;
			}
		}
		return null;
	}

	/**
	 * Get the all instances of a type of component
	 * @param cls, the class type of the component being searched for
	 * @param <T>, the class type of the component being searched for
	 * @return the first instance of the component
	 */
	public <T extends Component> List<T> getComponents(Class<T> cls) {
		List<T> components = new ArrayList<>();
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				components.add((T) component);
			}
		}
		return components;
	}

	/* Methods to enable/disable a gameobject */

	public boolean isEnabled() {
		return isEnabled;
	}

	public void enable() {
		isEnabled = true;
	}

	public void disable() {
		isEnabled = false;

		for (Component component: attachedComponents) {
			component.disable();
		}

		GameWindow.getInstance().getBackgroundRenderer().removeElement(this);
		GameWindow.getInstance().getUiRenderer().removeElement(this);
		GameWindow.getInstance().getSpriteRenderer().removeElement(this);

	}

	// ACCESSORS

	public List<Component> getComponents() {
		return attachedComponents.stream().toList();
	}

	public Transform getTransform() {
		return transform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "GameObject{" +
				"gameObjectID=" + gameObjectID +
				", name='" + name + '\'' +
				'}';
	}
}
