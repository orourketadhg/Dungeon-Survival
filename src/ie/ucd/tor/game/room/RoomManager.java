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

/**
 * Behaviour to control the rooms
 */
public class RoomManager extends Behaviour {

	public static final int ROOM_WIDTH = 160;
	public static final int ROOM_HEIGHT = 160;
	public static final Point2D ROOM_SCALE = new Point2D(6, 6);
	public static final Point2D ROOM_POSITION = new Point2D(24, 24);

	private final GameWindow window;
	private final List<RoomData> rooms;

	private GameObject activeRoom;

	private int clearedRooms;

	public RoomManager(GameWindow window) {
		this.window = window;

		rooms = new ArrayList<>();

		clearedRooms = 0;

	}

	@Override
	public void execute() {

		if (activeRoom.getComponent(RoomController.class).isRoomComplete()) {
			generateNewRoom();
		}

	}

	/**
	 * Generate an empty start room with the PLUS ROOM texture
	 */
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

	/**
	 * Generate a random new room
	 */
	public void generateNewRoom() {

		clearedRooms += 1;

		window.getBackgroundRenderer().removeElement(activeRoom);

		DoorLocation exitedDoor = activeRoom.getComponent(RoomController.class).getExitedDoor();
		activeRoom.getComponent(RoomController.class).destroyRoom();
		activeRoom.disable();

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

		GameObject playerOne = DungeonSurvival.getInstance().getPlayerOne();
		GameObject playerTwo = DungeonSurvival.getInstance().getPlayerTwo();

		Point2D playerOnePos;
		Point2D playerTwoPos;

		// move the players to the other side of the room as the entrance
		switch (entranceDoor) {
			case NORTH -> {
				playerOnePos = new Point2D(18 + (80 * RoomManager.ROOM_SCALE.getX()) - 32, 24 + (0 * RoomManager.ROOM_SCALE.getY()) + 112);
				playerTwoPos = new Point2D(18 + (80 * RoomManager.ROOM_SCALE.getX()) + 32, 24 + (0 * RoomManager.ROOM_SCALE.getY()) + 112);
			}
			case SOUTH -> {
				playerOnePos = new Point2D(18 + (80 * RoomManager.ROOM_SCALE.getX()) + 32, 24 + (144 * RoomManager.ROOM_SCALE.getY()) - 112);
				playerTwoPos = new Point2D(18 + (80 * RoomManager.ROOM_SCALE.getX()) - 32, 24 + (144 * RoomManager.ROOM_SCALE.getY()) - 112);
			}
			case EAST -> {
				playerOnePos = new Point2D(24 + (144 * RoomManager.ROOM_SCALE.getX()) - 112, 18 + (80 * RoomManager.ROOM_SCALE.getY()) + 32);
				playerTwoPos = new Point2D(24 + (144 * RoomManager.ROOM_SCALE.getX()) - 112, 18 + (80 * RoomManager.ROOM_SCALE.getY()) - 32);
			}
			case WEST -> {
				playerOnePos = new Point2D(24 + (0 * RoomManager.ROOM_SCALE.getX()) + 112, 18 + (80 * RoomManager.ROOM_SCALE.getY()) - 32);
				playerTwoPos =  new Point2D(24 + (0 * RoomManager.ROOM_SCALE.getX()) + 112, 18 + (80 * RoomManager.ROOM_SCALE.getY()) + 32);
			}
			default -> {
				playerOnePos = new Point2D(512 - 64, 512);
				playerTwoPos = new Point2D(512 + 64, 512);
			}
		}

		playerOne.getTransform().setPosition(playerOnePos);
		playerTwo.getTransform().setPosition(playerTwoPos);

		activeRoom = newRoom;

		window.getBackgroundRenderer().addElement(activeRoom);

	}

	// ACCESSORS

	public int getClearedRooms() {
		return clearedRooms;
	}

	public void addRoomData(RoomData data) {
		rooms.add(data);
	}

	public RoomController getActiveRoom() {
		return this.activeRoom.getComponent(RoomController.class);
	}

}
