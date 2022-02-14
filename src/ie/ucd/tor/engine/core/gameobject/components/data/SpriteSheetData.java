package ie.ucd.tor.engine.core.gameobject.components.data;

import ie.ucd.tor.engine.util.ImagesUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheetData {

	private final String spriteSheetLocation;
	private final ArrayList<SpriteData> spriteSheetData;
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

		// TODO update
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

	private void constructSpriteSheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		for (int i = 0; i < this.numSprites; i++) {
			BufferedImage spriteImage = spriteSheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
			addSpriteToSpriteSheet(spriteImage, spriteWidth, spriteHeight);
		}
	}

	public void addSpriteToSpriteSheet(BufferedImage sprite, int spriteWidth, int spriteHeight) {
		spriteSheetData.add(new SpriteData(sprite, spriteWidth, spriteHeight));
	}

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
