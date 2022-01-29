package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collision;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionController {

	private List<GameObject> gameObjectsWithColliders;

	private List<CollisionData> currentCollisions;
	private List<CollisionData> collisionsLastFrame;

	public CollisionController() {
		gameObjectsWithColliders = new ArrayList<>();

		currentCollisions = new ArrayList<>();
		collisionsLastFrame = new ArrayList<>();
	}

	public void UpdateCollisions() {

	}

	private void distributeCollisions() {

	}

	public List<CollisionData> getNewCollisions() {
		return currentCollisions.stream().filter((collisionData -> !collisionsLastFrame.contains(collisionData))).collect(Collectors.toList());
	}

	public List<CollisionData> getPersistingCollisions() {
		return currentCollisions.stream().filter((collisionData -> collisionsLastFrame.contains(collisionData))).collect(Collectors.toList());
	}

	public List<CollisionData> getExitingCollisions() {
		return collisionsLastFrame.stream().filter((collisionData -> currentCollisions.contains(collisionData))).collect(Collectors.toList());
	}

	public List<CollisionData> getCurrentCollisions() {
		return currentCollisions;
	}

	public List<CollisionData> getCollisionsLastFrame() {
		return collisionsLastFrame;
	}

	public boolean addGameObjectWithCollider(GameObject gameObject) {
		if (!gameObject.hasComponent(Collision.class)) {
			return false;
		}

		return gameObjectsWithColliders.add(gameObject);
	}

	public boolean removeGameObjectWithCollider(GameObject gameObject) {
		return gameObjectsWithColliders.remove(gameObject);
	}

}
