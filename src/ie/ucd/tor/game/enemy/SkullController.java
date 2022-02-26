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
	protected static final float ATTACK_COOL_DOWN = 500;

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
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (isAttacking && currentAnimationTime > nextAnimationTime) {
			isAttacking = false;
			canMove = true;
		}

		if (!isAttacking && currentAnimationTime > nextAnimationTime + ATTACK_COOL_DOWN) {
			attackDirection = new Vector2D(movement.getX(), movement.getY());

			Collider playerOneCollider = DungeonSurvival.getInstance().getPlayerOne().getComponent(Collider.class);
			Collider playerTwoCollider = DungeonSurvival.getInstance().getPlayerTwo().getComponent(Collider.class);

			List<CollisionData> collisionEvents = gameObject.getComponent(Collider.class).getCollisions();

			for (CollisionData collision : collisionEvents) {
				if (collision.collisionIncludes(playerOneCollider)) {
					playerOneCollider.getGameObject().getComponent(PlayerController.class).damagePlayer(damage);
				}
				else if (collision.collisionIncludes(playerTwoCollider)) {
					playerTwoCollider.getGameObject().getComponent(PlayerController.class).damagePlayer(damage);
				}
			}

			nextAnimationTime = (long) (currentAnimationTime + ATTACK_COOL_DOWN);
			isAttacking = true;
			canMove = false;
		}
	}

	@Override
	public void animate() {
		super.animate();
	}

	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);

		if (health <= 0) {
			isDead = true;
		}
	}
}
