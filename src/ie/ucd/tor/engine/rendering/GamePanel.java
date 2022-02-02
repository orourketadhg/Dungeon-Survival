package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.gameobject.GameObject;
import ie.ucd.tor.engine.core.gameobject.components.Animation;
import ie.ucd.tor.engine.core.gameobject.components.Sprite;
import ie.ucd.tor.engine.core.gameobject.components.data.SpriteData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.util.ImagesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {

	private static final double ANIMATION_SPEED = 100;

	private final CopyOnWriteArrayList<GameObject> elements;
	private long animationTime;

	public GamePanel() {
		this(GameWindow.WIDTH, GameWindow.HEIGHT);
	}

	public GamePanel(int width, int height) {
		this.setBounds(0, 0, width, height);
		this.setOpaque(false);
		this.animationTime = 0;
		elements = new CopyOnWriteArrayList<>();

	}

	public GamePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		elements = new CopyOnWriteArrayList<>();
		this.animationTime = 0;
	}

	public void update() {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponents(graphics);
		animationTime += 1;

		drawPanelElements(graphics);

	}

	private void drawPanelElements(Graphics graphics) {
		for (GameObject element: elements) {
			if (!element.isEnabled()) {
				continue;
			}

			Sprite sprite = element.getComponent(Sprite.class);
			Animation animation = element.getComponent(Animation.class);
			if (!Objects.isNull(animation)) {
				if(!animation.isEnabled()) {
					continue;
				}
				drawAnimatedElement(animation, graphics);

			}
			else if (!Objects.isNull(sprite)) {
				if(!sprite.isEnabled()) {
					continue;
				}
				drawElement(sprite, graphics);

			}

		}
	}

	private void drawAnimatedElement(Animation animation, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		Point2D position = animation.getTransform().getPosition();
		Point2D scale = animation.getTransform().getScale();

		SpriteData data = animation.calculateNextSprite(animationTime, ANIMATION_SPEED);
		BufferedImage spriteImage = data.getSprite();

		g2.drawImage(spriteImage, (int) position.getX(), (int) position.getY(), (int) (data.getSpriteWidth() * scale.getX()), (int) (data.getSpriteHeight() * scale.getY()), null);
	}

	private void drawElement(Sprite sprite, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		Point2D position = sprite.getTransform().getPosition();
		Point2D scale = sprite.getTransform().getScale();
		SpriteData data = sprite.getSpriteData();

		BufferedImage spriteImage = data.getSprite();

		g2.drawImage(spriteImage, (int) position.getX(), (int) position.getY(), (int) (data.getSpriteWidth() * scale.getX()), (int) (data.getSpriteHeight() * scale.getY()), null);

	}

	public void addElement(GameObject element) {
		elements.add(element);
		sortElements();
	}

	private void sortElements() {
		elements.sort((o1, o2) -> {
			int p1 = o1.hasComponent(Sprite.class) ? o1.getComponent(Sprite.class).getRenderPriority() : o1.getComponent(Animation.class).getRenderPriority();
			int p2 = o2.hasComponent(Sprite.class) ? o2.getComponent(Sprite.class).getRenderPriority() : o2.getComponent(Animation.class).getRenderPriority();
			return p1 - p2;
		});
	}

	public void showPanel() {
		this.setVisible(true);
	}

	public void hidePanel() {
		this.setVisible(false);
	}



}
