package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.data.RoomData;
import ie.ucd.tor.game.room.RoomManager;
import ie.ucd.tor.game.room.data.RoomObjectData;

public class DungeonSurvival extends GameController {

	private GameObject playerOne;
	private GameObject playerTwo;
	private GameObject roomManager;

	@Override
	protected void initialization() {

		playerOne = initialisePlayer(new Point2D(512, 512));
		playerTwo = initialisePlayer(new Point2D(512 + 128, 512));

		roomManager = initialiseRoomManager();
		roomManager.getComponent(RoomManager.class).generateNewRoom();

	}

	private GameObject initialisePlayer(Point2D startingPosition) {
		/* Player Initialisation */
		GameObject player = new GameObject();
		player.getTransform().setPosition(startingPosition);
		player.getTransform().setScale(new Point2D(4, 4));
		// Player Behaviours
		player.addComponent(new PlayerController());
		// Player Animations
		player.addComponent(new Animation());
		player.getComponent(Animation.class).AddAnimation("knightStatic", new SpriteSheetData("res/Knight/Knight_static.png", 4,14, 16));
		player.getComponent(Animation.class).AddAnimation("knightWalkUp", new SpriteSheetData("res/Knight/Knight_walk_up.png", 8,16, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkDown", new SpriteSheetData("res/Knight/Knight_walk_down.png", 8,15, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkRight", new SpriteSheetData("res/Knight/Knight_walk_right.png", 8,17, 15));
		player.getComponent(Animation.class).AddAnimation("knightWalkLeft", new SpriteSheetData("res/Knight/Knight_walk_left.png", 8,22, 15));
		player.getComponent(Animation.class).AddAnimation("knightAttackUp", new SpriteSheetData("res/Knight/Knight_attack_up.png", 6,20, 20));
		player.getComponent(Animation.class).AddAnimation("knightAttackDown", new SpriteSheetData("res/Knight/Knight_attack_down.png", 6,20, 22));
		player.getComponent(Animation.class).AddAnimation("knightAttackRight", new SpriteSheetData("res/Knight/Knight_attack_right.png", 6,24, 18));
		player.getComponent(Animation.class).AddAnimation("knightAttackLeft", new SpriteSheetData("res/Knight/Knight_attack_left.png", 6,22, 18));

		// Add player to renderer
		this.gameWindow.getSpriteRenderer().addElement(player);

		return player;

	}

	private GameObject initialiseRoomManager() {
		GameObject roomManager = new GameObject();

		RoomManager manager = new RoomManager(gameWindow);

		// Room Data
		RoomData roomOne = new RoomData("res/rooms/Room_Plus.png", new int[]{1, 1, 1, 1}, 6, 2, 3);

		// decorations
		RoomObjectData roomOneCandle = new RoomObjectData("res/rooms/decorations/candlestick.png", true);
		roomOneCandle.addPosition(new Point2D(16, 16));
		roomOneCandle.addPosition(new Point2D(128, 16));
		roomOneCandle.addPosition(new Point2D(16, 128));
		roomOneCandle.addPosition(new Point2D(128, 128));
		RoomObjectData roomOneFlag = new RoomObjectData("res/rooms/decorations/flag.png", true);
		roomOneFlag.addPosition(new Point2D(48, 0));
		roomOneFlag.addPosition(new Point2D(96, 0));

		roomOne.addDecoration(roomOneCandle);
		roomOne.addDecoration(roomOneFlag);

		// items/collectibles
		RoomObjectData roomOneCoin = new RoomObjectData("res/Items/coin.png", true);
		RoomObjectData roomOnePotion = new RoomObjectData("res/Items/Health_Potion.png", true);

		roomOne.addCollectible(roomOneCoin);
		roomOne.addCollectible(roomOnePotion);

		manager.addRoomData(roomOne);

		roomManager.addComponent(manager);

		return roomManager;
	}

}
