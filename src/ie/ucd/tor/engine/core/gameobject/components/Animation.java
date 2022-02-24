package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteData;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;

import java.util.HashMap;
import java.util.Objects;

public class Animation extends Component {

	private final HashMap<String, SpriteSheetData> animationMap;
	private final int renderPriority;
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

	public void AddAnimation(String animationName, SpriteSheetData newAnimation) {
		animationMap.put(animationName, newAnimation);
		if (currentAnimation == null) {
			currentAnimationName = animationName;
			currentAnimation = newAnimation;
		}
	}

	public SpriteData calculateNextSprite(long animationTime, double animationSpeed) {
		int time = (int) (animationTime % animationSpeed);
		
		if (time == animationSpeed - 1) {
			currentAnimationFrameIndex = (currentAnimationFrameIndex + 1) % currentAnimation.getNumSprites();
		}

		currentAnimationFrame = currentAnimation.getSpriteSheetData().get(currentAnimationFrameIndex);
		return currentAnimationFrame;
	}

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
