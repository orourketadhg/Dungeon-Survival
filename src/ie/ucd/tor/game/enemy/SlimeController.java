package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.RoomController;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.util.Util;

public class SlimeController extends EnemyController {

	protected static final float MOVEMENT_COOL_DOWN = 10;
	protected static final float DEATH_TIME = 1000;
	protected static final float DAMAGE_TIME = 300;

	private float attackThreshold = 50;

	private float distanceToTarget;
	private Vector2D directionToTarget;
	private GameObject target;

	private boolean isHurt;
	private boolean isDying;

	private long dyingTime;
	private long hurtTime;

	public SlimeController(int damage, int health, int movementSpeed) {
		super(damage, health, movementSpeed);
	}

	@Override
	public void move() {
		if (target == null) {
			double random = Math.random();
			target = random < 0.5 ? DungeonSurvival.getInstance().getPlayerOne() : DungeonSurvival.getInstance().getPlayerTwo();
		}

		long currentTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		movement = Vector2D.Zero;

		if (nextMovementTime <= currentTime) {

			distanceToTarget = transform.getPosition().distance(target.getTransform().getPosition());

			directionToTarget = Vector2D.normalise(target.getTransform().getPosition().Subtract(transform.getPosition().toVector2D()).toVector2D());
			directionToTarget = Vector2D.round(directionToTarget);

			movement = directionToTarget;
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
		if (directionToTarget.getX() == 0) {
			return;
		}

		if (distanceToTarget > attackThreshold) {
			return;
		}

		super.attack();
	}

	@Override
	public void animate() {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		Animation playerAnimator = gameObject.getComponent(Animation.class);

		String animationName = "";

		if (isDying) {
			animationName = "slimeDeath";

			if (currentAnimationTime > dyingTime) {
				isDead = true;
			}
		}
		if (isHurt) {
			animationName = "slimeDamage";

			if (currentAnimationTime > hurtTime) {
				isHurt = false;
				unfreeze();
			}
		}
		if (isAttacking) {
			if (directionToTarget.getX() == 1) {
				animationName = "slimeAttackRight";
			}
			else if (directionToTarget.getX() == -1) {
				animationName = "slimeAttackLeft";
			}
		}
		else if (canMove) {
			if (movement.getX() == 1) {
				animationName = "slimeWalkRight";
			}
			else if (movement.getX() == -1) {
				animationName = "slimeWalkLeft";
			}
			else if (movement.getY() == 1) {
				animationName = "slimeWalkDown";
			}
			else if (movement.getY() == -1) {
				animationName = "slimeWalkUp";
			}
			else {
				animationName = "knightStatic";
			}
		}
		else {
			animationName = "slimeStatic";
		}

		playerAnimator.setCurrentAnimation(animationName);
	}

	@Override
	public void takeDamage(int damage) {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextAttackTime) {
			health = Math.max(0, health - damage);
			nextAttackTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println("Enemy has taken " + damage + " Damage");
		}

		if (health <= 0) {
			isDying = true;

			dyingTime = (long) (currentAnimationTime + DEATH_TIME);
			freeze();
		}
		else {
			isHurt = true;
			freeze();
			hurtTime = (long) (currentAnimationTime + DAMAGE_TIME);
		}

	}

	@Override
	public boolean isDead() {
		return super.isDead();
	}
}
