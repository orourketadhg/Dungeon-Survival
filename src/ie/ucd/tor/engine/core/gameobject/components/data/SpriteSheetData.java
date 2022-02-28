package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.util.ImagesUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Data about a sprite sheet and the sprites part of that sprite sheet
 */
public class SpriteSheetData {

	// location of the sprite sheet
	private final String spriteSheetLocation;

	// The individual sprites extracted from the sprite sheet
	private final ArrayList<SpriteData> spriteSheetData;

	// dimensions of the sprite sheet
	private final int numSprites;
	private final int spriteWidth;
	private final int spriteHeight;

	private final int animationDuration;
	private final int animationSpeed;

	public SpriteSheetData(String spriteSheet, int numSprites, int spriteWidth, int spriteHeight) {

		spriteSheetData = new ArrayList<>();

		this.spriteSheetLocation = spriteSheet;
		this.numSprites = numSprites;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;

		this.animationDuration = numSprites;
		this.animationSpeed = numSprites;

		BufferedImage spriteSheetImage = ImagesUtil.loadImageTexture(spriteSheet);
		constructSpriteSheet(spriteSheetImage, spriteWidth, spriteHeight);
	}

	public SpriteSheetData(String spriteSheet, int numSprites, int spriteWidth, int spriteHeight, int animationDuration, int animationSpeed) {

		spriteSheetData = new ArrayList<>();

		this.spriteSheetLocation = spriteSheet;
		this.numSprites = numSprites;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.animationDuration = animationDuration;
		this.animationSpeed = animationSpeed;

		BufferedImage spriteSheetImage = ImagesUtil.loadImageTexture(spriteSheet);
		constructSpriteSheet(spriteSheetImage, spriteWidth, spriteHeight);
	}

	/**
	 * Method to extract the sprites from the sprite sheets
	 * @param spriteSheet, the sprite sheet sprites are being extracted from
	 * @param spriteWidth, the width of the sprites
	 * @param spriteHeight, the height of the sprites
	 */
	private void constructSpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		for (int i = 0; i < this.numSprites; i++) {
			// use getSubImage to extract a sprite from the sprite sheet
			BufferedImage spriteImage = spriteSheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
			addSpriteToSpriteSheet(spriteImage, spriteWidth, spriteHeight);
		}
	}

	/**
	 * Add a new sprite to the recorded sprites in this spritesheet data
	 * @param sprite
	 * @param spriteWidth
	 * @param spriteHeight
	 */
	public void addSpriteToSpriteSheet(BufferedImage sprite, int spriteWidth, int spriteHeight) {
		spriteSheetData.add(new SpriteData(sprite, spriteWidth, spriteHeight));
	}

	// ACCESSORS

	public ArrayList<SpriteData> getSpriteSheetData() {
		return spriteSheetData;
	}

	public String getSpriteSheetLocation() {
		return spriteSheetLocation;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getNumSprites() {
		return spriteSheetData.size();
	}
}
