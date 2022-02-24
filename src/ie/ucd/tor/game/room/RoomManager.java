package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoomManager extends Behaviour {

	public static final int ROOM_WIDTH = 160;
	public static final int ROOM_HEIGHT = 160;
	public static final Point2D ROOM_SCALE = new Point2D(6, 6);
	public static final Point2D ROOM_POSITION = new Point2D(24, 24);

	private final GameWindow window;
	private final List<RoomData> rooms;

	private GameObject activeRoom;

	public RoomManager(GameWindow window) {
		this.window = window;

		rooms = new ArrayList<>();

	}

	@Override
	public void execute() {

		if (activeRoom.getComponent(RoomController.class).isRoomComplete()) {
			generateNewRoom();
		}

	}

	public void addRoomData(RoomData data) {
		rooms.add(data);
	}

	public RoomController getActiveRoom() {
		return this.activeRoom.getComponent(RoomController.class);
	}

	public void generateStarterRoom() {

		RoomData newRoomData = rooms.get(0);

		// Initialise room
		GameObject newRoom = new GameObject();
		newRoom.getTransform().setPosition(ROOM_POSITION);
		newRoom.getTransform().setScale(ROOM_SCALE);
		newRoom.addComponent(new Sprite(newRoomData.getRoomTexture(), ROOM_WIDTH, ROOM_HEIGHT, 0));
		newRoom.addComponent(new RoomController(newRoomData, window, false));

		this.activeRoom = newRoom;

		window.getBackgroundRenderer().addElement(activeRoom);

	}

	public void generateNewRoom() {

		window.getBackgroundRenderer().removeElement(activeRoom);

		DoorLocation exitedDoor = activeRoom.getComponent(RoomController.class).getExitedDoor();
		activeRoom.getComponent(RoomController.class).destroyRoom();

		DoorLocation entranceDoor = DoorLocation.NORTH;
		switch (exitedDoor) {
			case NORTH -> entranceDoor = DoorLocation.SOUTH;
			case SOUTH -> entranceDoor = DoorLocation.NORTH;
			case EAST -> entranceDoor = DoorLocation.WEST;
			case WEST -> entranceDoor = DoorLocation.EAST;
		}

		DoorLocation finalEntranceDoor = entranceDoor;
		List<RoomData> filteredRooms = rooms.stream().filter((roomData -> roomData.getDoorLocations().contains(finalEntranceDoor))).toList();

		// randomly choose data to generate a new room
		int roomIndex = (int) (Math.random() * filteredRooms.size());
		RoomData newRoomData = filteredRooms.get(roomIndex);

		GameObject newRoom = new GameObject();
		newRoom.getTransform().setPosition(ROOM_POSITION);
		newRoom.getTransform().setScale(ROOM_SCALE);

		// add required components to room
		newRoom.addComponent(new Sprite(newRoomData.getRoomTexture(), ROOM_WIDTH, ROOM_HEIGHT, 0));
		newRoom.addComponent(new RoomController(newRoomData, window, true));

		GameObject player = DungeonSurvival.getInstance().getPlayer();

		switch (entranceDoor) {
			case NORTH -> player.getTransform().getPosition().translate(new Vector2D(0, -((128 - 16)* ROOM_SCALE.getY())));
			case SOUTH -> player.getTransform().getPosition().translate(new Vector2D(0, ((128 - 16)* ROOM_SCALE.getY())));
			case EAST -> player.getTransform().getPosition().translate(new Vector2D(((128 - 16)* ROOM_SCALE.getX()), 0));
			case WEST -> player.getTransform().getPosition().translate(new Vector2D(-((128 - 16)* ROOM_SCALE.getX()), 0));
			default -> player.getTransform().setPosition(new Point2D(512, 512));
		}

		activeRoom = newRoom;

		window.getBackgroundRenderer().addElement(activeRoom);

	}

}
