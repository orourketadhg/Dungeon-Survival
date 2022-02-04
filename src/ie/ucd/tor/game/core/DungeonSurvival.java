package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.game.player.PlayerController;
import ie.ucd.tor.game.room.Room;

import java.util.ArrayList;
import java.util.List;

public class DungeonSurvival extends GameController {

	private GameObject player;
	private List<Room> rooms;

	@Override
	protected void initialization() {

		/* Player Initialisation */
		this.player = new GameObject();
		this.player.getTransform().setPosition(new Point2D(400, 500));
		this.player.getTransform().setScale(new Point2D(4, 4));
		// Player Behaviours
		this.player.addComponent(new PlayerController());
		// Player Animations
		this.player.addComponent(new Animation());
		this.player.getComponent(Animation.class).AddAnimation("knightStatic", new SpriteSheetData("res/Knight/Knight_static.png", 4,14, 16));
		this.player.getComponent(Animation.class).AddAnimation("knightWalkUp", new SpriteSheetData("res/Knight/Knight_walk_up.png", 8,16, 15));
		this.player.getComponent(Animation.class).AddAnimation("knightWalkDown", new SpriteSheetData("res/Knight/Knight_walk_down.png", 8,15, 15));
		this.player.getComponent(Animation.class).AddAnimation("knightWalkRight", new SpriteSheetData("res/Knight/Knight_walk_right.png", 8,17, 15));
		this.player.getComponent(Animation.class).AddAnimation("knightWalkLeft", new SpriteSheetData("res/Knight/Knight_walk_left.png", 8,22, 15));
		this.player.getComponent(Animation.class).AddAnimation("knightAttackUp", new SpriteSheetData("res/Knight/Knight_attack_up.png", 6,20, 20));
		this.player.getComponent(Animation.class).AddAnimation("knightAttackDown", new SpriteSheetData("res/Knight/Knight_attack_down.png", 6,20, 22));
		this.player.getComponent(Animation.class).AddAnimation("knightAttackRight", new SpriteSheetData("res/Knight/Knight_attack_right.png", 6,24, 18));
		this.player.getComponent(Animation.class).AddAnimation("knightAttackLeft", new SpriteSheetData("res/Knight/Knight_attack_left.png", 6,22, 18));
		// Add player to renderer
		this.gameWindow.getSpriteRenderer().addElement(player);

		/* Room Initialisation */
		rooms = new ArrayList<>();

		initialiseRoom("res/rooms/Room_horizontal_hall.png", new int[]{0, 0, 1, 1});
		initialiseRoom("res/rooms/Room_vertical_hall.png", new int[]{1, 1, 0, 0});
		initialiseRoom("res/rooms/Room_plus_hall.png", new int[]{1, 1, 1, 1});
		initialiseRoom("res/rooms/Room_horizontal.png", new int[]{0, 0, 1, 1});
		initialiseRoom("res/rooms/Room_vertical.png", new int[]{1, 1, 0, 0});
		initialiseRoom("res/rooms/Room_Plus.png", new int[]{1, 1, 1, 1});

	}

	private void initialiseRoom(String roomSpriteLocation, int[] roomDoors) {
		GameObject room = new GameObject();
		room.getTransform().setPosition(new Point2D(24, 24));
		room.getTransform().setScale(new Point2D(6, 6));
		// Components
		room.addComponent(new Sprite(roomSpriteLocation, 160, 160, 1));

		this.rooms.add(new Room(room, roomDoors, null, 0, null, 0, null, 0));

	}



}
