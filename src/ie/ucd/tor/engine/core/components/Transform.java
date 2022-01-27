package ie.ucd.tor.engine.core.components;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.texture.Animator;
import ie.ucd.tor.engine.core.components.texture.Sprite;
import ie.ucd.tor.engine.core.components.texture.data.SpriteData;
import ie.ucd.tor.engine.maths.Point2D;

public class Transform extends Component {

	private Point2D position;

	public Transform() {
		position = Point2D.Zero;
	}

	@Override
	public void registerParent(GameObject gameObject, Transform transform) {
		super.registerParent(gameObject, this);
	}

	public Point2D getPosition() {
		return position;
	}

	public Point2D getScale() {
		if (gameObject.hasComponent(Sprite.class)) {
			SpriteData data = gameObject.getComponent(Sprite.class).getSpriteData();
			return new Point2D(data.getSpriteWidth(), data.getSpriteHeight());
		}
		else if (gameObject.hasComponent(Animator.class)) {
			SpriteData data = gameObject.getComponent(Animator.class).getCurrentAnimationFrame();
			return new Point2D(data.getSpriteWidth(), data.getSpriteHeight());
		}
		return Point2D.Zero;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

}
