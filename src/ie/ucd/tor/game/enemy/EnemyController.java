package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.player.PlayerController;

import java.util.List;

/**
 * base controller class for all enemy controllers
 */
public abstract class EnemyController extends Behaviour {

	protected static final float HEALTH_COOL_DOWN = 1000;
	protected static final float ATTACK_COOL_DOWN = 1000;

	protected final float movementSpeed;
	protected final int damage;
	protected int health;

	protected Vector2D movement = Vector2D.Zero;
	protected Vector2D attackDirection = Vector2D.Zero;

	protected boolean isAttacking;
	protected boolean isDead;
	protected boolean canMove;
	protected boolean canAttack;

	protected long nextAttackTime;
	protected long nextAnimationTime;
	protected long nextMovementTime;

	public EnemyController(int damage, int health, int movementSpeed) {
		this.damage = damage;
		this.health = health;
		this.movementSpeed = movementSpeed;

		canAttack = true;
		canMove = true;
	}

	@Override
	public void execute() {

		// move the enemy if enabled
		if (canMove) {
			move();
		}

		// attack if eneabled
		if (canAttack) {
			attack();
		}

		// animate the enemy
		animate();
	}

	/**
	 * base method to move the enemy
	 */
	public void move() {

	}

	/**
	 * base method for the enemy to attack a player
	 */
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

	/**
	 * base method to animate the enemy
	 */
	public void animate() {

	}

	/**
	 * Damage the enemy
	 * @param damage, the amount of damage the enemy will take
	 */
	public void takeDamage(int damage) {
		// delay time between when the enemy can take damage
		long currentAnimationTime = DungeonSurvival.getInstance().getSpriteAnimationTime();
		if (currentAnimationTime > nextAttackTime) {
			health = Math.max(0, health - damage);
			nextAttackTime = (long) (currentAnimationTime + HEALTH_COOL_DOWN);

			System.out.println("Enemy has taken " + damage + " Damage");
		}

		if (health <= 0) {
			isDead = true;
		}

	}

	/**
	 * Freeze the enemy
	 */
	public void freeze() {
		this.canAttack = false;
		this.canMove = false;
	}

	/**
	 * Unfreeze the enemy
	 */
	public void unfreeze() {
		this.canAttack = true;
		this.canMove = true;
	}

	// ACCESSORS

	public boolean isDead() {
		return isDead;
	}
}
