package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collision;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.data.DoorLocation;

public class DoorController extends Behaviour {

	private final DoorLocation door;
	private boolean exitAttempt;

	public DoorController(DoorLocation door) {
		this.door = door;
	}

	@Override
	public void Execute() {
		exitAttempt = false;

//		for (CollisionData collision : gameObject.getComponent(Collision.class).getCollisions()) {
//			if (collision.collisionIncludes(DungeonSurvival.getInstance().getPlayer().getComponent(Collision.class))) {
//				exitAttempt = true;
//			}
//		}

	}

	public DoorLocation getDoor() {
		return door;
	}

	public boolean getExitAttempt() {
		return exitAttempt;
	}

}
