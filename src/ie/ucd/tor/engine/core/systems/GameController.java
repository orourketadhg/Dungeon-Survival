package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;

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
		for (GameObject entity: gameEntities) {
			if (entity.isEnabled()) {

			}
		}
	}

	private void UpdateBehaviour() {

		// Execute the Behaviours attached to a GameObject
		for (GameObject entity: gameEntities) {
			if (entity.isEnabled()) {
				List<Behaviour> behavioursList = entity.getComponents(Behaviour.class);
				for (Behaviour behaviour: behavioursList) {
					if (behaviour.isEnabled()) {
						behaviour.Execute();
					}
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
