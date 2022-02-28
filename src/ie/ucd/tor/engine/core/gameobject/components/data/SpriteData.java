package ie.ucd.tor.engine.core.gameobject.components.data;

import java.awt.image.BufferedImage;

/**
 * Data about a sprite that can be drawn to the screen
 */
public class SpriteData {

	// the image of the sprite
	private final BufferedImage spriteImage;
	// the dimensions of the sprite
	private final int spriteWidth;
	private final int spriteHeight;

	public SpriteData(BufferedImage img, int spriteWidth, int spriteHeight) {
		this.spriteImage = img;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	// ACCESSORS

	public BufferedImage getSprite() {
		return spriteImage;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

}
