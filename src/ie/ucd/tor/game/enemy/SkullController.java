package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.RoomController;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.util.Util;

import java.util.List;
import java.util.Random;

public class SkullController extends EnemyController {

	protected static final float MOVEMENT_COOL_DOWN = 10;

	private GameObject target;

	private float distanceToTarget;
	private Vector2D directionToTarget;

	private float wanderThreshold;

	public SkullController(int damage, int health, int movementSpeed) {
		super(damage, health, movementSpeed);

		wanderThreshold = 300;
		nextMovementTime = 0;
	}

	@Override
	public void move() {

		if (target == null) {
			double random = Math.random();
			target = random < 0.5 ? DungeonSurvival.getInstance().getPlayerOne() : DungeonSurvival.getInstance().getPlayerTwo();
		}

		long currentTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (nextMovementTime <= currentTime) {
			movement = Vector2D.Zero;

			distanceToTarget = transform.getPosition().distance(target.getTransform().getPosition());
			directionToTarget = Vector2D.normalise(target.getTransform().getPosition().Subtract(transform.getPosition().toVector2D()).toVector2D());
			directionToTarget = Vector2D.round(directionToTarget);

			if (distanceToTarget > wanderThreshold) {
				float randomX = ((float) Math.random() * 2) - 1;
				float randomY = ((float) Math.random() * 2) - 1;

				movement = Vector2D.round(new Vector2D(randomX, randomY));

			}
			else {
				movement = directionToTarget;
			}
		}

		if (movement != Vector2D.Zero) {

			RoomController controller = DungeonSurvival.getInstance().getRoomManager().getComponent(RoomManager.class).getActiveRoom();

			Point2D currentPos = transform.getPosition();
			Point2D nextPos = currentPos.Add(movement.scale(movementSpeed));

			boolean isValid = Util.validateMove(gameObject, nextPos, controller.getBlockedAreas());

			if (isValid) {
				transform.getPosition().translate(movement.scale(movementSpeed));

				nextMovementTime = (long) (currentTime + MOVEMENT_COOL_DOWN);
			}
		}

	}

	@Override
	public void attack() {
		super.attack();
	}

	@Override
	public void animate() {
		super.animate();
	}

	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
	}

}
