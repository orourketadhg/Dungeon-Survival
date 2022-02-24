package ie.ucd.tor.game.player;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.game.enemy.EnemyController;

public class Damage extends Behaviour {

	private int damageValue = 1;

	public Damage(int damage) {
		this.damageValue = damage;
	}

	@Override
	public void execute() {
		Collider damageCollider = gameObject.getComponent(Collider.class);

		for (CollisionData collision : damageCollider.getCollisions()) {
			GameObject other = collision.getOther(damageCollider);
			if (other.hasComponent(EnemyController.class)) {
				other.getComponent(EnemyController.class).doDamage(damageValue);
			}
		}
	}

}
