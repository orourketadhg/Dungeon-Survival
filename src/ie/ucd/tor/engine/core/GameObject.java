package ie.ucd.tor.engine.core;

import ie.ucd.tor.engine.core.components.Component;
import ie.ucd.tor.engine.core.components.Transform;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject {
	private static int ID_COUNT = 0;

	private final int gameObjectID;
	private String name;
	private Transform transform;
	private CopyOnWriteArrayList<Component> attachedComponents;

	public GameObject() {
		gameObjectID = ID_COUNT++;
		name = "";

		attachedComponents = new CopyOnWriteArrayList<Component>();

		Transform tmp = new Transform();
		transform = tmp;
		addComponent(tmp);
	}

	public int getGameObjectID() {
		return gameObjectID;
	}

	public boolean addComponent(Component component) {
		component.registerParent(this, transform);
		return attachedComponents.add(component);
	}

	public <T extends Component> boolean removeComponent(Class<T> cls) {
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

	public List<Component> getComponents() {
		return attachedComponents.stream().toList();
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
