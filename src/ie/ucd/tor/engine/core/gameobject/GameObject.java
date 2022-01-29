package ie.ucd.tor.engine.core.gameobject;

import ie.ucd.tor.engine.core.gameobject.components.Component;
import ie.ucd.tor.engine.core.gameobject.components.Transform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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



		attachedComponents = new CopyOnWriteArrayList<Component>();
		Transform tmp = new Transform();
		transform = tmp;
		addComponent(tmp);
	}

	public int getGameObjectID() {
		return gameObjectID;
	}

	public void addComponent(Component component) {
		component.registerParent(this, transform);
		attachedComponents.add(component);
	}

	public <T extends Component> boolean removeComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return true;
			}
		}
		return false;
	}

	public <T extends Component> boolean hasComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return true;
			}
		}
		return false;
	}

	public <T extends Component> T getComponent(Class<T> cls) {
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				return (T) component;
			}
		}
		return null;
	}

	public <T extends Component> List<T> getComponents(Class<T> cls) {
		List<T> components = new ArrayList<>();
		for (Component component : attachedComponents) {
			if (cls.isInstance(component)) {
				components.add((T) component);
			}
		}
		return components;
	}

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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void enable() {
		isEnabled = true;
	}

	public void disable() {
		isEnabled = false;
	}

}