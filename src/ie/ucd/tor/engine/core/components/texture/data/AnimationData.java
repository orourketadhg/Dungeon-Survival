package ie.ucd.tor.engine.core.components.texture.data;

import ie.ucd.tor.engine.util.FileIO;

public class AnimationData extends SpriteData {

	// animation data
	private final AnimationDirection textureDirection;
	private final int numSpriteAnimations;

	public AnimationData(String textureLocation, int textureWidth, int textureHeight, int spriteWidth, int spriteHeight, AnimationDirection textureDirection, int numSpriteAnimations) {
		super(FileIO.loadImageTexture(textureLocation), textureWidth, textureHeight, spriteWidth, spriteHeight);
		this.textureDirection = textureDirection;
		this.numSpriteAnimations = numSpriteAnimations;
	}

	public int getNumSpriteAnimations() {
		return numSpriteAnimations;
	}

	public AnimationDirection getTextureDirection() {
		return textureDirection;
	}
}
