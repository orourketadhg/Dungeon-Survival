package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController {

	private final CopyOnWriteArrayList<GameObject> gameEntities;

	private CollisionController collisionController;
	private BehaviourController behaviourController;

	public GameController() {
		gameEntities = new CopyOnWriteArrayList<>();

		this.collisionController = CollisionController.getInstance();

	}

	public void GameLoop() {
		UpdateCollisions();

		UpdateBehaviour();

	}

	private void UpdateCollisions() {
		this.collisionController.UpdateCollisions();
	}

	private void UpdateBehaviour() {
		this.behaviourController.UpdateBehaviours();
	}

	private void recordEntity(GameObject gameObject) {
		gameEntities.add(gameObject);
	}

	private void removeEntity(GameObject gameObject) {
		gameEntities.remove(gameObject);
	}

}
