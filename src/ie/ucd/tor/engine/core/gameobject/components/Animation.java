package ie.ucd.tor.engine.core.gameobject.components;

import ie.ucd.tor.engine.core.gameobject.components.data.SpriteData;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteSheetData;

import java.util.HashMap;

public class Animation extends Component {

	private final HashMap<String, SpriteSheetData> animationMap;
	private final int renderPriority;
	private SpriteSheetData currentAnimation;
	private SpriteData currentAnimationFrame;

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
			currentAnimation = newAnimation;
		}
	}

	public SpriteSheetData getAnimations(String animationName) {
		return animationMap.get(animationName);
	}

	public SpriteSheetData getCurrentAnimation() {
		return currentAnimation;
	}

	public SpriteData calculateNextSprite(long animationTime, double animationSpeed) {
		int animationFramePosition = (int) ((animationTime / animationSpeed) % getCurrentAnimation().getNumSprites());
		currentAnimationFrame = currentAnimation.getSpriteSheetData().get(animationFramePosition);
		return currentAnimationFrame;
	}

	public int getRenderPriority() {
		return renderPriority;
	}
}
