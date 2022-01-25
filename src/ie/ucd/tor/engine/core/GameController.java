package ie.ucd.tor.engine.core;

import ie.ucd.tor.engine.core.components.Component;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

	private CopyOnWriteArrayList<GameObject> gameObjects;

	public GameController() {
		gameObjects = new CopyOnWriteArrayList<>();

	}

	public void GameLoop() {

	}

	private void recordEntity(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

}
