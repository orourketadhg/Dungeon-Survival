package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.core.DungeonSurvival;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public void Execute() {
		if (activeRoom.getComponent(RoomController.class).isRoomComplete()) {
			// generateNewRoom();
		}

	}

	public void addRoomData(RoomData data) {
		rooms.add(data);
	}

	public RoomController getActiveRoom() {
		return this.activeRoom.getComponent(RoomController.class);
	}

	public void generateNewRoom() {

		DoorLocation exitedDoor = DoorLocation.NORTH;
		DoorLocation entranceDoor = DoorLocation.SOUTH;

		if (activeRoom != null) {
			exitedDoor = activeRoom.getComponent(RoomController.class).getExitedDoor();
			activeRoom.getComponent(RoomController.class).destroyRoom();
			window.getBackgroundRenderer().removeElement(activeRoom);
		}

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

		// Initialise new room
		GameObject newRoom = new GameObject();
		newRoom.getTransform().setPosition(ROOM_POSITION);
		newRoom.getTransform().setScale(ROOM_SCALE);
		// add required components to room
		newRoom.addComponent(new Sprite(newRoomData.getRoomTexture(), ROOM_WIDTH, ROOM_HEIGHT, 0));
		newRoom.addComponent(new RoomController(newRoomData, window));

		this.activeRoom = newRoom;

		window.getBackgroundRenderer().addElement(activeRoom);

		// DungeonSurvival.getInstance().getPlayerOne().getTransform().setPosition(new Point2D(512, 512));
		// DungeonSurvival.getInstance().getPlayerTwo().getTransform().setPosition(new Point2D(512 + 128, 512));

	}

}
