package ie.ucd.tor.game.util;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.game.room.data.BlockedAreaData;

import java.util.List;

public class Util {

	/**
	 * @reference Based off collision AABB collision detection from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/
	 */
	public static boolean validateMove(GameObject gameObject, Point2D position, List<BlockedAreaData> blockedAreas) {

		// get players collision area
		Collider playerCollider = gameObject.getComponent(Collider.class);
		int playerWidth = playerCollider.getColliderWidth();
		int playerHeight = playerCollider.getColliderHeight();

		boolean isIntersecting = false;

		for (BlockedAreaData area : blockedAreas) {

			if (!isIntersecting) {
				isIntersecting =
						position.getX() < area.getPosition().getX() + area.getWidth() &&
								position.getX() + playerWidth > area.getPosition().getX() &&
								position.getY() < area.getPosition().getY() + area.getHeight() &&
								position.getY() + playerHeight > area.getPosition().getY();
			}

		}

		return !isIntersecting;
	}
}
