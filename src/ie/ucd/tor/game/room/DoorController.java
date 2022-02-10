package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.data.DoorLocation;

public class DoorController extends Behaviour {

	private final DoorLocation door;
	private boolean doorLock;
	private boolean exitAttempt;

	public DoorController(DoorLocation door) {
		this.door = door;
		doorLock = true;
		exitAttempt = false;
	}

	@Override
	public void execute() {
		if (doorLock) {
			return;
		}

		Collider playerCollider = DungeonSurvival.getInstance().getPlayer().getComponent(Collider.class);

		for (CollisionData collision : gameObject.getComponent(Collider.class).getCollisions()) {

			if (collision.collisionIncludes(playerCollider)) {
				exitAttempt = true;
			}

		}

	}

	public DoorLocation getDoor() {
		return door;
	}

	public boolean getExitAttempt() {
		return exitAttempt;
	}

	public void unlockDoor() {
		doorLock = false;
	}

}
