package ie.ucd.tor.game.core;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;
import ie.ucd.tor.engine.core.systems.GameController;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.game.player.PlayerController;

public class DungeonSurvival extends GameController {

	private GameObject playerOne;

	@Override
	protected void initialization() {

		initialisePlayer(new Point2D(512, 512));

	}

	private void initialisePlayer(Point2D startingPosition) {
		/* Player Initialisation */
		GameObject player = new GameObject();
		player.getTransform().setPosition(new Point2D(400, 500));
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
	}

}
