package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Collider;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.data.BlockedAreaData;
import ie.ucd.tor.game.room.data.DoorLocation;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.room.data.RoomObjectData;

import java.util.ArrayList;
import java.util.List;

public class DungeonSurvival extends GameController {

	private GameObject player;
	private GameObject roomManager;

	private static DungeonSurvival instance;

	public static DungeonSurvival getInstance() {
		if (instance == null) {
			instance = new DungeonSurvival();
		}
		return instance;
	}

	@Override
	protected void initialization() {

		player = initialisePlayer(new Point2D(512, 512));

		roomManager = initialiseRoomManager();
		roomManager.getComponent(RoomManager.class).generateStarterRoom();

	}

	private GameObject initialisePlayer(Point2D startingPosition) {
		/* Player Initialisation */
		GameObject player = new GameObject();
		player.setName("Player");
		player.getTransform().setPosition(startingPosition);
		player.getTransform().setScale(new Point2D(4, 4));
		// Player Behaviours
		player.addComponent(new PlayerController());
		// Player Animations
		player.addComponent(new Animation(10));
		player.getComponent(Animation.class).AddAnimation("knightStatic", new SpriteSheetData("res/Knight/Knight_static.png", 4,14, 16));
		player.getComponent(Animation.class).AddAnimation("knightWalkUp", new SpriteSheetData("res/Knight/Knight_walk_up.png", 8,16, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkDown", new SpriteSheetData("res/Knight/Knight_walk_down.png", 8,15, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkRight", new SpriteSheetData("res/Knight/Knight_walk_right.png", 8,17, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkLeft", new SpriteSheetData("res/Knight/Knight_walk_left.png", 8,22, 15));
		player.getComponent(Animation.class).AddAnimation("knightAttackUp", new SpriteSheetData("res/Knight/Knight_attack_up.png", 6,20, 20));
		player.getComponent(Animation.class).AddAnimation("knightAttackDown", new SpriteSheetData("res/Knight/Knight_attack_down.png", 6,20, 22));
		player.getComponent(Animation.class).AddAnimation("knightAttackRight", new SpriteSheetData("res/Knight/Knight_attack_right.png", 6,24, 18));
		player.getComponent(Animation.class).AddAnimation("knightAttackLeft", new SpriteSheetData("res/Knight/Knight_attack_left.png", 6,22, 18));
		// Add colliders
		player.addComponent(new Collider(16 * 4, 16 * 4, Point2D.Zero));

		// Add player to renderer
		this.gameWindow.getSpriteRenderer().addElement(player);

		return player;

	}

	private GameObject initialiseRoomManager() {
		GameObject roomManager = new GameObject();
		roomManager.setName("Room Manager");

		RoomManager manager = new RoomManager(gameWindow);

		{
			/* Room - plus */
			RoomData room = new RoomData("res/rooms/Room_plus.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH, DoorLocation.EAST, DoorLocation.WEST)), 6, 2, 3);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			room.addBlockedArea(new BlockedAreaData(new Point2D(0, 0), 64, 16));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(48, 0));
			flag.addPosition(new Point2D(96, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData plusRoomBox = new RoomObjectData("res/rooms/decorations/Box.png", true);
			plusRoomBox.addPosition(new Point2D(32, 32));
			plusRoomBox.addPosition(new Point2D(32, 112));
			plusRoomBox.addPosition(new Point2D(112, 32));
			plusRoomBox.addPosition(new Point2D(112, 112));

			room.addInteractable(plusRoomBox);

			// Enemies

			manager.addRoomData(room);

		}

		{
			/* Room - plus hall */
			RoomData room = new RoomData("res/rooms/Room_plus_hall.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH, DoorLocation.EAST, DoorLocation.WEST)), 4, 2, 4);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(72, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);
			box.addPosition(new Point2D(32, 72));
			box.addPosition(new Point2D(72, 32));
			box.addPosition(new Point2D(72, 112));
			box.addPosition(new Point2D(112, 72));

			room.addInteractable(box);

			// Enemies

			manager.addRoomData(room);
		}

		{
			/* Room - horizontal */
			RoomData room = new RoomData("res/rooms/Room_horizontal.png", new ArrayList<>(List.of(DoorLocation.EAST, DoorLocation.WEST)), 6, 0, 6);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(64, 0));
			flag.addPosition(new Point2D(72, 0));
			flag.addPosition(new Point2D(32, 0));
			flag.addPosition(new Point2D(112, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			manager.addRoomData(room);
		}

		{
			/* Room - horizontal hall */
			RoomData room = new RoomData("res/rooms/Room_horizontal_hall.png", new ArrayList<>(List.of(DoorLocation.EAST, DoorLocation.WEST)), 3, 0, 2);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(32, 72));
			candle.addPosition(new Point2D(72, 72));
			candle.addPosition(new Point2D(32, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			manager.addRoomData(room);
		}

		{
			/* Room - vertical */
			RoomData room = new RoomData("res/rooms/Room_vertical.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH)), 8, 4, 0);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(16, 16));
			candle.addPosition(new Point2D(128, 16));
			candle.addPosition(new Point2D(16, 128));
			candle.addPosition(new Point2D(128, 128));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);
			box.addPosition(new Point2D(48, 48));
			box.addPosition(new Point2D(48, 96));
			box.addPosition(new Point2D(96, 48));
			box.addPosition(new Point2D(96, 96));

			room.addInteractable(box);

			manager.addRoomData(room);
		}

		{
			/* Room - vertical hall */
			RoomData room = new RoomData("res/rooms/Room_vertical_hall.png", new ArrayList<>(List.of(DoorLocation.NORTH, DoorLocation.SOUTH)), 3, 0, 4);

			// decorations
			RoomObjectData candle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
			candle.addPosition(new Point2D(72, 72));

			RoomObjectData flag = new RoomObjectData("res/rooms/decorations/flag.png", true);
			flag.addPosition(new Point2D(48, 0));
			flag.addPosition(new Point2D(96, 0));

			room.addDecoration(candle);
			room.addDecoration(flag);

			// Interactive
			RoomObjectData box = new RoomObjectData("res/rooms/decorations/Box.png", true);

			room.addInteractable(box);

			manager.addRoomData(room);
		}

		roomManager.addComponent(manager);

		return roomManager;
	}

	public GameObject getPlayer() {
		return player;
	}

	public GameObject getRoomManager() {
		return roomManager;
	}

	public long getSpriteAnimationTime() {
		return this.gameWindow.getSpriteRenderer().getAnimationTime();
	}

}
