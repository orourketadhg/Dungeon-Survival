package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.texture.Animator;
import ie.ucd.tor.engine.core.components.texture.Sprite;
import ie.ucd.tor.engine.core.components.texture.data.SpriteData;
import ie.ucd.tor.engine.maths.Point2D;
import ie.ucd.tor.engine.util.ImagesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
			Animator animator = element.getComponent(Animator.class);
			if (!Objects.isNull(animator)) {
				if(!animator.isEnabled()) {
					continue;
				}
				drawAnimatedElement(animator, graphics);

			}
			else if (!Objects.isNull(sprite)) {
				if(!sprite.isEnabled()) {
					continue;
				}
				drawElement(sprite, graphics);

			}

		}
	}

	private void drawAnimatedElement(Animator animator, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		Point2D position = animator.getTransform().getPosition();
		Point2D scale = animator.getTransform().getScale();

		SpriteData data = animator.calculateNextSprite(animationTime, ANIMATION_SPEED);
		BufferedImage spriteImage = data.getSprite();

		if (scale.getX() < 0) {
			spriteImage =  ImagesUtil.calculateImageFlip(spriteImage);
		}

		g2.drawImage(spriteImage, (int) position.getX(), (int) position.getY(), data.getSpriteWidth(), data.getSpriteHeight(), null);
	}

	private void drawElement(Sprite sprite, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		Point2D position = sprite.getTransform().getPosition();
		SpriteData data = sprite.getSpriteData();

		g2.drawImage(data.getSprite(), (int) position.getX(), (int) position.getY(), data.getSpriteWidth(), data.getSpriteHeight(), null);

	}

	public void addElement(GameObject element) {
		elements.add(element);
		sortElements();
	}

	private void sortElements() {
		elements.sort((o1, o2) -> {
			int p1 = o1.hasComponent(Sprite.class) ? o1.getComponent(Sprite.class).getRenderPriority() : o1.getComponent(Animator.class).getRenderPriority();
			int p2 = o2.hasComponent(Sprite.class) ? o2.getComponent(Sprite.class).getRenderPriority() : o2.getComponent(Animator.class).getRenderPriority();
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
