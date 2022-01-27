package ie.ucd.tor.engine.core;

import ie.ucd.tor.engine.core.components.Behaviour;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

	private CopyOnWriteArrayList<GameObject> gameObjects;

	public GameController() {
		gameObjects = new CopyOnWriteArrayList<>();

	}

	public void GameLoop() {

		// Execute the Behaviour attached to a GameObject
		for (GameObject entity: gameObjects) {
			if (entity.isEnabled() && entity.hasComponent(Behaviour.class)) {
				Behaviour entityBehaviour = entity.getComponent(Behaviour.class);
				if (entityBehaviour.isEnabled()) {
					entityBehaviour.Execute();
				}
			}
		}

	}

	private void recordEntity(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	private void removeEntity(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

}
