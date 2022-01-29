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

		for (GameObject A: gameObjectsWithColliders) {
			for (GameObject B: gameObjectsWithColliders) {
				if (A.equals(B)) {
					continue;
				}

				// check if a collision is occurring between A and B
				boolean currentCollisionStatus = CheckCollision(A, B);
				boolean pastCollisionStatus = pastCollisionExists(A, B);

				// check if collision data exists containing A and B in the current Frame but not the last frame - Entering Collision Detected
				// check if collision data exists containing A and B in the last Frame but not the current frame- Exiting Collision Detected
				// check if collision data exists containing A and B in the current Frame and Last Frame - Persisting Collision Detected
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
