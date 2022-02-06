package ie.ucd.tor.game.room;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Behaviour;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.maths.Vector2D;
import ie.ucd.tor.engine.rendering.GameWindow;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.data.RoomObjectData;

import java.util.ArrayList;
import java.util.List;

public class RoomController extends Behaviour {

	private final RoomData roomData;
	private final GameWindow window;
	private boolean roomComplete;

	private final List<GameObject> doors;
	private final List<GameObject> decorations;
	private final List<GameObject> collectibles;
	private final List<GameObject> enemies;

	private static final String VERTICAL_DOOR = "res/rooms/decorations/Door_Vertical.png";
	private static final String HORIZONTAL_DOOR = "res/rooms/decorations/Door_horizontal.png";

	public RoomController(RoomData roomData, GameWindow window) {
		this.roomData = roomData;
		this.window = window;
		this.roomComplete = false;

		doors = new ArrayList<>();
		decorations = new ArrayList<>();
		collectibles = new ArrayList<>();
		enemies = new ArrayList<>();

		generateDoors();

		generateDecorations();

		generateCollectibles();

		generateEnemies();

	}

	public void destroyRoom() {
		for (GameObject decoration : decorations) {
			window.getBackgroundRenderer().removeElement(decoration);
		}

		for (GameObject collectible : collectibles) {
			window.getSpriteRenderer().removeElement(collectible);
		}

		for (GameObject enemy : enemies) {
			window.getSpriteRenderer().removeElement(enemy);
		}
	}

	public void setRoomComplete() {
		this.roomComplete = true;
	}

	public boolean isRoomComplete() {
		return roomComplete;
	}

	private void generateDoors() {

		for (DoorLocation doorLocation: roomData.getDoorLocations()) {

			Point2D doorPosition;
			Sprite doorSprite;

			switch (doorLocation) {
				case NORTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (0 * RoomManager.ROOM_SCALE.getY()));
				case SOUTH -> doorPosition = new Point2D(18 + (64 * RoomManager.ROOM_SCALE.getX()), 24 + (144 * RoomManager.ROOM_SCALE.getY()));
				case EAST -> doorPosition = new Point2D(24 + (144 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				case WEST -> doorPosition = new Point2D(24 + (0 * RoomManager.ROOM_SCALE.getX()), 18 + (64 * RoomManager.ROOM_SCALE.getY()));
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			switch (doorLocation) {
				case NORTH, SOUTH -> doorSprite = new Sprite(HORIZONTAL_DOOR, 32, 16, 1);
				case EAST, WEST -> doorSprite = new Sprite(VERTICAL_DOOR, 16, 32, 1);
				default -> throw new IllegalStateException("Unexpected value: " + doorLocation);
			}

			GameObject door = new GameObject();
			door.getTransform().setPosition(doorPosition);
			door.getTransform().setScale(RoomManager.ROOM_SCALE);
			door.addComponent(doorSprite);

			doors.add(door);
			window.getBackgroundRenderer().addElement(door);
		}

	}

	private void generateDecorations() {

		List<RoomObjectData> decorations = roomData.getDecorations();
		int numDecorations = (int) (Math.random() * roomData.getNumDecorations());

		for (int i = 0; i < numDecorations; i++) {
			if (decorations.isEmpty()) {
				continue;
			}

			int randomDecorationIndex = (int) (Math.random() * decorations.size());
			RoomObjectData decoration = decorations.get(randomDecorationIndex);

			if (decoration.getPositions().isEmpty()) {
				continue;
			}

			int randomPositionIndex = (int) (Math.random() * decoration.getPositions().size());
			Point2D decorationPosition = decoration.getPositions().get(randomPositionIndex);

			generateDecoration(decoration.getSpriteLocation(), decorationPosition, decoration.isAnimated());

		}

	}

	private void generateDecoration(String decorationSpriteLocation, Point2D spawnPosition, boolean isAnimatedDecoration) {
		GameObject decoration = new GameObject();

		Point2D position = new Point2D(18 + (spawnPosition.getX() * RoomManager.ROOM_SCALE.getX()), 18 + (spawnPosition.getY() * RoomManager.ROOM_SCALE.getY()));
		decoration.getTransform().setPosition(position);
		decoration.getTransform().setScale(RoomManager.ROOM_SCALE);

		if (isAnimatedDecoration) {
			decoration.addComponent(new Animation(1));
			decoration.getComponent(Animation.class).AddAnimation("Static", new SpriteSheetData(decorationSpriteLocation, 4, 16, 16));
		}
		else {
			decoration.addComponent(new Sprite(decorationSpriteLocation, 16, 16, 1));
		}

		decorations.add(decoration);
		window.getBackgroundRenderer().addElement(decoration);
	}

	private void generateCollectibles() {

	}

	private void generateEnemies() {

	}

	public List<GameObject> getEnemies() {
		return enemies;
	}

}
