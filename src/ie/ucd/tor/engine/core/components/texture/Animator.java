package ie.ucd.tor.engine.core.components.texture;

import ie.ucd.tor.engine.core.components.Component;
import ie.ucd.tor.engine.core.components.texture.data.AnimationData;

import java.util.HashMap;

public class Animator extends Component {

	private AnimationData currentAnimation;
	private HashMap<String, AnimationData> animationMap;

	public Animator() {
		currentAnimation = null;
		animationMap = new HashMap<>();
	}

	// sprite data
	private int spriteWidth = 0;
	private int spriteHeight = 0;

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public void AddAnimation(String animationName, AnimationData newAnimation) {
		if (animationMap.isEmpty()) {
			currentAnimation = newAnimation;
		}
		animationMap.put(animationName, newAnimation);
	}

	public AnimationData getAnimations(String animationName) {
		return animationMap.get(animationName);
	}

	public AnimationData getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation (String animationName) {
		currentAnimation = getAnimations(animationName);
	}

}
