package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteData;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;

import java.util.HashMap;
import java.util.Objects;

/**
 * Animation component to enable a gameObject to have an animated sprite
 */
public class Animation extends Component {

	private final HashMap<String, SpriteSheetData> animationMap;	// recorded animations
	private final int renderPriority;								// priority of drawing
	private SpriteSheetData currentAnimation;
	private String currentAnimationName;
	private SpriteData currentAnimationFrame;
	private int currentAnimationFrameIndex;
	
	private long currentAnimationTime; 

	public Animation() {
		this(0);
	}

	public Animation(int renderPriority) {
		currentAnimation = null;
		animationMap = new HashMap<>();
		this.renderPriority = renderPriority;
	}

	/**
	 * Add a new animation to the component
	 * @param animationName, the access name for the animation
	 * @param newAnimation, the sprite sheet data about the animation
	 */
	public void AddAnimation(String animationName, SpriteSheetData newAnimation) {
		animationMap.put(animationName, newAnimation);

		// set default animation
		if (currentAnimation == null) {
			currentAnimationName = animationName;
			currentAnimation = newAnimation;
		}
	}

	/**
	 * Calculate the next animation frame in the current animation
	 * @param animationTime, current frame time in system
	 * @param animationSpeed, speed the animation will be updated at
	 * @return the next frame in the animation
	 */
	public SpriteData calculateNextSprite(long animationTime, double animationSpeed) {
		int time = (int) (animationTime % animationSpeed);
		
		if (time == animationSpeed - 1) {
			currentAnimationFrameIndex = (currentAnimationFrameIndex + 1) % currentAnimation.getNumSprites();
		}

		currentAnimationFrame = currentAnimation.getSpriteSheetData().get(currentAnimationFrameIndex);
		return currentAnimationFrame;
	}

	/**
	 * Change the current animation
	 * @param animationName, the name the animation is being changed to
	 * @return true if the animation was successfully changed, false otherwise
	 */
	public boolean setCurrentAnimation(String animationName) {
		if (!animationMap.containsKey(animationName)) {
			return false;
		}

		if (Objects.equals(currentAnimationName, animationName)) {
			return true;
		}

		currentAnimationFrameIndex = 0;

		currentAnimation = animationMap.get(animationName);
		currentAnimationName = animationName;

		return true;
	}

	// Accessors

	public SpriteSheetData getAnimations(String animationName) {
		return animationMap.get(animationName);
	}

	public SpriteSheetData getCurrentAnimation() {
		return currentAnimation;
	}

	public int getRenderPriority() {
		return renderPriority;
	}
}
