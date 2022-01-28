package ie.ucd.tor.engine.core;

import ie.ucd.tor.engine.core.components.Behaviour;
import ie.ucd.tor.engine.core.components.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

	private final CopyOnWriteArrayList<GameObject> gameEntities;

	public GameController() {
		gameEntities = new CopyOnWriteArrayList<>();

	}

	public void GameLoop() {
		UpdateCollisions();

		UpdateBehaviour();

	}

	private void UpdateCollisions() {

	}

	private void UpdateBehaviour() {
		// Execute the Behaviours attached to a GameObject
		for (GameObject entity: gameEntities) {
			if (entity.isEnabled()) {
				List<Behaviour> behaviours = entity.getComponents(Behaviour.class);
				for (Behaviour behaviour: behaviours) {
					behaviour.Execute();
				}
			}
		}
	}

	private void recordEntity(GameObject gameObject) {
		gameEntities.add(gameObject);
	}

	private void removeEntity(GameObject gameObject) {
		gameEntities.remove(gameObject);
	}

}
