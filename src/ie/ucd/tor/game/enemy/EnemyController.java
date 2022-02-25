package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.player.PlayerController;

public class EnemyController extends Behaviour {

	private static final float HEALTH_COOL_DOWN = 1000;
	private static final float ATTACK_COOL_DOWN = 100;

	private final int movementSpeed;
	private final int damage;
	private int health;

	private Vector2D movement = Vector2D.Zero;
	private Vector2D attackDirection = Vector2D.Zero;

	private long nextAttackTime;

	private boolean isAttacking;
	private boolean isDead;
	private boolean isDying;
	private boolean canMove;
	private boolean canAttack;

	private long nextAnimationTime;

	private float distanceToTarget;
	private float attackDistanceThreshold;

	public EnemyController(int damage, int health, int movementSpeed) {
		this.damage = damage;
		this.health = health;
		this.movementSpeed = movementSpeed;

		attackDistanceThreshold = 8;
	}

	@Override
	public void execute() {
		if (canMove) {
			move();
		}

		if (canAttack) {
			attack();
		}
	}

	public void move() {

	}

	public void attack() {

		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();

		if (isAttacking && currentAnimationTime > nextAnimationTime) {
			isAttacking = false;
			canMove = true;
		}

		if (distanceToTarget > attackDistanceThreshold) {
			return;
		}

		if (!isAttacking && currentAnimationTime > nextAnimationTime + ATTACK_COOL_DOWN) {
			attackDirection = new Vector2D(movement.getX(), movement.getY());

			Collider playerOneCollider = DungeonSurvival.getInstance().getPlayerOne().getComponent(Collider.class);
			Collider playerTwoCollider = DungeonSurvival.getInstance().getPlayerTwo().getComponent(Collider.class);

			for (CollisionData collision : gameObject.getComponent(Collider.class).getCollisions()) {
				if (collision.collisionIncludes(playerOneCollider)) {
					playerOneCollider.getGameObject().getComponent(PlayerController.class).damagePlayer(damage);
				}
				else if (collision.collisionIncludes(playerTwoCollider)) {
					playerTwoCollider.getGameObject().getComponent(PlayerController.class).damagePlayer(damage);
				}
			}

			nextAnimationTime = currentAnimationTime + 500;
			isAttacking = true;
			canMove = false;
		}

	}

	public void takeDamage(int damage) {
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextAttackTime) {
			health = Math.max(0, health - damage);
			nextAttackTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println("Enemy has taken " + damage + " Damage");
		}

		if (health <= 0) {
			isDying = true;
		}

	}

	private void freeze() {
		this.canAttack = false;
		this.canMove = false;
	}

	private void unfreeze() {
		this.canAttack = true;
		this.canMove = true;
	}

	public boolean isDead() {
		return isDead;
	}
}
