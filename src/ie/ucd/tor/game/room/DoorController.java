package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Collision;
import ie.ucd.tor.engine.core.gameobject.components.data.CollisionData;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.data.DoorLocation;

public class DoorController extends Behaviour {

	private DoorLocation door;
	private RoomController controller;

	public DoorController(RoomController controller, DoorLocation door) {
		this.door = door;
		this.controller = controller;
	}

	@Override
	public void Execute() {
		for (CollisionData collision : gameObject.getComponent(Collision.class).getCollisions()) {
			System.out.println(collision);
		}

//		for (CollisionData collision : gameObject.getComponent(Collision.class).getCollisions()) {
//			if (collision.collisionIncludes(DungeonSurvival.getInstance().getPlayerOne().getComponent(Collision.class)) || collision.collisionIncludes(DungeonSurvival.getInstance().getPlayerTwo().getComponent(Collision.class))) {
//				controller.setExitedDoor(door);
//			}
//		}

	}

	public DoorLocation getDoor() {
		return door;
	}

}
