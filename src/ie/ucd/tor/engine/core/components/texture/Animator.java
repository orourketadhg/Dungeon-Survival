package ie.ucd.tor.engine.core.components.texture;

import ie.ucd.tor.engine.core.components.Component;
import ie.ucd.tor.engine.core.components.texture.data.SpriteData;
import ie.ucd.tor.engine.core.components.texture.data.SpriteSheetData;

import java.util.HashMap;

public class Animator extends Component {

	private final int renderPriority;
	private SpriteSheetData currentAnimation;
	private SpriteData currentAnimationFrame;
	private HashMap<String, SpriteSheetData> animationMap;

	public Animator() {
		this(0);
	}

	public Animator(int renderPriority) {
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

	public SpriteData getCurrentAnimationFrame() {
		return currentAnimationFrame;
	}

	public void setCurrentAnimation (String animationName) {
		currentAnimation = getAnimations(animationName);
	}

	public int getRenderPriority() {
		return renderPriority;
	}
}
