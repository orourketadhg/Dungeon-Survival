package ie.ucd.tor.engine.core.systems;

import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionType;
import ie.ucd.tor.engine.maths.Point2D;

import java.util.*;
import java.util.stream.Collectors;

public class CollisionController {

	private final List<Collider> colliders;

	private List<CollisionData> currentCollisions;
	private List<CollisionData> collisionsLastFrame;

	private static CollisionController instance;

	public CollisionController() {
		colliders = new ArrayList<>();

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

		List<CollisionData> newCollisions = new ArrayList<>();

		for (Collider A: colliders) {
			for (Collider B: colliders) {

				if (A == B) {
					continue;
				}

				if (!A.getGameObject().isEnabled() || !B.getGameObject().isEnabled()) {
					continue;
				}

				// Check if the collision is already recorded
				if (newCollisions.stream().filter((collisionData -> collisionData.collisionIncludes(A) && collisionData.collisionIncludes(B))).toList().size() > 0) {
					continue;
				}

				// check if a collision is occurring between A and B
				boolean currentCollisionStatus = CheckCollision(A, B);
				// check if a past collision occurred between A and B
				boolean pastCollisionStatus = pastCollisionExists(A, B);

				// check if collision data exists containing A and B in the current Frame but not the last frame - Entering Collision Detected
				if (currentCollisionStatus && !pastCollisionStatus) {
					newCollisions.add(new CollisionData(CollisionType.Entering, A, B));
				}

				// check if collision data exists containing A and B in the last Frame but not the current frame- Exiting Collision Detected
				if (!currentCollisionStatus && pastCollisionStatus) {
					newCollisions.add(new CollisionData(CollisionType.Exiting, A, B));
				}

				// check if collision data exists containing A and B in the current Frame and Last Frame - Persisting Collision Detected
				if (currentCollisionStatus && pastCollisionStatus) {
					newCollisions.add(new CollisionData(CollisionType.Persisting, A, B));
				}
			}
		}

		collisionsLastFrame = currentCollisions;
		currentCollisions = newCollisions;

	}

	public List<CollisionData> getCollisions(Collider collider) {
		return currentCollisions.stream().filter((collisionData -> collisionData.collisionIncludes(collider))).collect(Collectors.toList());
	}

	public void addColliderToSystem(Collider collider) {
		colliders.add(collider);
	}

	public void removeColliderToSystem(Collider collider) {
		colliders.remove(collider);
	}

	/**
	 * collision detection method
	 *
	 * @reference Based off collision AABB collision detection from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/
	 *
	 * @param a Collider A
	 * @param b Collider B
	 * @return have the colliders overlapped
	 */
	private Boolean CheckCollision(Collider a, Collider b) {
		Point2D aPosition = a.getTransform().getPosition();
		Point2D bPosition = b.getTransform().getPosition();

		return 	aPosition.getX() + a.getOffset().getX() < bPosition.getX() + b.getOffset().getX() + b.getColliderWidth() &&
				aPosition.getX() + a.getOffset().getX() + a.getColliderWidth() > bPosition.getX() + b.getOffset().getX() &&
				aPosition.getY() + a.getOffset().getY() < bPosition.getY() + b.getOffset().getY() + b.getColliderHeight() &&
				aPosition.getY() + a.getOffset().getY() + a.getColliderHeight() > bPosition.getY() + b.getOffset().getY();

	}

	private boolean pastCollisionExists(Collider A, Collider B) {
		return collisionsLastFrame.stream().filter((collisionData -> collisionData.collisionIncludes(A) && collisionData.collisionIncludes(B) && collisionData.getCollisionType() != CollisionType.Exiting)).toList().size() > 0;
	}

}
