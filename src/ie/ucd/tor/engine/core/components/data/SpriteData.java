package ie.ucd.tor.engine.core.components.data;

import java.awt.image.BufferedImage;

public class SpriteData {

	private final BufferedImage spriteImage;
	private final int spriteWidth;
	private final int spriteHeight;

	public SpriteData(BufferedImage img, int spriteWidth, int spriteHeight) {
		this.spriteImage = img;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

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
