package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collision;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionType;

import java.util.*;
import java.util.stream.Collectors;

public class CollisionController {

	private final List<GameObject> gameObjectsWithColliders;

	private final List<CollisionData> currentCollisions;
	private final List<CollisionData> collisionsLastFrame;

	private static CollisionController instance;

	public CollisionController() {
		gameObjectsWithColliders = new ArrayList<>();

		currentCollisions = new ArrayList<>();
		collisionsLastFrame = new ArrayList<>();

	}

	public static CollisionController getInstance() {
		if (instance == null) {
			instance = new CollisionController();
		}
		return instance;
	}

	public void UpdateCollisions() {
		collisionsLastFrame.clear();
		Collections.copy(collisionsLastFrame, currentCollisions);
		currentCollisions.clear();

		for (GameObject A: gameObjectsWithColliders) {
			for (GameObject B: gameObjectsWithColliders) {
				if (A.equals(B)) {
					continue;
				}

				if (currentCollisionExists(A, B)) {
					continue;
				}

				// check if a collision is occurring between A and B
				boolean currentCollisionStatus = CheckCollision(A, B);
				boolean pastCollisionStatus = pastCollisionExists(A, B);

				// check if collision data exists containing A and B in the current Frame but not the last frame - Entering Collision Detected
				if (currentCollisionStatus && !pastCollisionStatus) {
					currentCollisions.add(new CollisionData(CollisionType.Entering, A, B));
				}

				// check if collision data exists containing A and B in the last Frame but not the current frame- Exiting Collision Detected
				if (!currentCollisionStatus && pastCollisionStatus) {
					currentCollisions.add(new CollisionData(CollisionType.Exiting, A, B));
				}

				// check if collision data exists containing A and B in the current Frame and Last Frame - Persisting Collision Detected
				if (currentCollisionStatus && pastCollisionStatus) {
					currentCollisions.add(new CollisionData(CollisionType.Persisting, A, B));
				}
			}
		}
	}

	public List<CollisionData> getCollisionsWithGameObject(GameObject gameObject) {
		return currentCollisions.stream().filter((collisionData -> collisionData.collisionIncludes(gameObject))).collect(Collectors.toList());
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

	private Boolean CheckCollision(GameObject A, GameObject B) {
		return false;
	}

	private boolean pastCollisionExists(GameObject A, GameObject B) {
		return collisionsLastFrame.stream().filter((collisionData -> collisionData.collisionIncludes(A) && collisionData.collisionIncludes(B))).toList().size() > 0;
	}

	private boolean currentCollisionExists(GameObject A, GameObject B) {
		return currentCollisions.stream().filter((collisionData -> collisionData.collisionIncludes(A) && collisionData.collisionIncludes(B))).toList().size() > 0;
	}

}
