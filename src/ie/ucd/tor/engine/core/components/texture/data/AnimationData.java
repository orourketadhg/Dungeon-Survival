package ie.ucd.tor.engine.core.components.texture.data;

import java.awt.*;

public class AnimationData extends SpriteData {

	// animation data
	private final AnimationDirection textureDirection;
	private final int numVerticalSprites;
	private final int numHorizontalSprites;
	private final int animationSpeed;

	public AnimationData(Image img, int textureWidth, int textureHeight, int spriteWidth, int spriteHeight, AnimationDirection textureMapDirection, AnimationDirection textureDirection, int numVerticalSprites, int numHorizontalSprites, int animationSpeed) {
		super(img, textureWidth, textureHeight, spriteWidth, spriteHeight);
		this.textureDirection = textureDirection;
		this.numVerticalSprites = numVerticalSprites;
		this.numHorizontalSprites = numHorizontalSprites;
		this.animationSpeed = animationSpeed;
	}

	public int getNumVerticalSprites() {
		return numVerticalSprites;
	}

	public int getNumHorizontalSprites() {
		return numHorizontalSprites;
	}

	public int getAnimationSpeed() {
		return animationSpeed;
	}

	public AnimationDirection getTextureDirection() {
		return textureDirection;
	}
}
