package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.rendering.GameWindow;

import java.util.ArrayList;
import java.util.List;

public class RoomManager extends Behaviour {

	private static final int ROOM_WIDTH = 160;
	private static final int ROOM_HEIGHT = 160;
	private static final Point2D ROOM_SCALE = new Point2D(6, 6);
	private static final Point2D ROOM_POSITION = new Point2D(24, 24);

	private final GameWindow window;
	private final List<RoomData> rooms;

	private GameObject activeRoom;

	public RoomManager(GameWindow window) {
		this.window = window;

		rooms = new ArrayList<>();
	}

	@Override
	public void Execute() {
		if (activeRoom.getComponent(RoomController.class).isRoomComplete()) {
			generateNewRoom();
		}

	}

	public void addRoomData(RoomData data) {
		rooms.add(data);
	}

	public GameObject getActiveRoom() {
		return this.activeRoom;
	}

	public void generateNewRoom() {

		window.getBackgroundRenderer().removeElement(activeRoom);

		// randomly choose data to generate a new room
		int roomIndex = (int) (Math.random() * rooms.size());
		RoomData newRoomData = rooms.get(roomIndex);

		// Initialise new room
		GameObject newRoom = new GameObject();
		newRoom.getTransform().setPosition(ROOM_POSITION);
		newRoom.getTransform().setScale(ROOM_SCALE);
		// add required components to room
		newRoom.addComponent(new Sprite(newRoomData.getRoomTexture(), ROOM_WIDTH, ROOM_HEIGHT, 0));
		newRoom.addComponent(new RoomController());

		this.activeRoom = newRoom;

		window.getBackgroundRenderer().addElement(activeRoom);

	}

}
