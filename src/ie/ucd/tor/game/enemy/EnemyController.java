package ie.ucd.tor.game.enemy;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.player.PlayerController;

import java.util.List;

public class EnemyController extends Behaviour {

	protected static final float HEALTH_COOL_DOWN = 1000;
	protected static final float ATTACK_COOL_DOWN = 1000;

	protected final float movementSpeed;
	protected final int damage;
	protected int health;

	protected Vector2D movement = Vector2D.Zero;
	protected Vector2D attackDirection = Vector2D.Zero;

	protected boolean isAttacking;
	protected boolean isDead;
	protected boolean isDying;
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
		if (canMove) {
			move();
		}

		if (canAttack) {
			attack();
		}

		animate();
	}

	public void move() {

	}

	public void attack() {

	}

	public void animate() {

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
