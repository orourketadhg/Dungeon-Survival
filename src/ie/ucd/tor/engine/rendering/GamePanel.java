package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.texture.Animator;
import ie.ucd.tor.engine.core.components.texture.Sprite;
import ie.ucd.tor.engine.maths.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {

	private final CopyOnWriteArrayList<GameObject> elements;
	private long animationTimer;

	public GamePanel() {
		this(GameWindow.WIDTH, GameWindow.HEIGHT);
	}

	public GamePanel(int width, int height) {
		this.setBounds(0, 0, width, height);
		this.setOpaque(false);		// allows panel to be translucent
		this.animationTimer = 0;
		elements = new CopyOnWriteArrayList<>();

	}

	public GamePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		elements = new CopyOnWriteArrayList<>();
		this.animationTimer = 0;
	}

	public void update() {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponents(graphics);
		animationTimer += 1;

		drawPanelElements(graphics);

	}

	private void drawPanelElements(Graphics graphics) {
		for (GameObject element: elements) {

			Sprite sprite = element.getComponent(Sprite.class);
			Animator animator = element.getComponent(Animator.class);
			if (!Objects.isNull(animator)) {
				drawAnimatedElement(animator, graphics);
			}
			else if (!Objects.isNull(sprite)) {
				drawElement(sprite, graphics);
			}
		}
	}

	private void drawAnimatedElement(Animator animator, Graphics graphics) {
		// TODO Add animation drawing
	}

	private void drawElement(Sprite sprite, Graphics graphics) {
		Point2D position = sprite.getTransform().getPosition();
		Point2D scale = sprite.getTransform().getScale();

		graphics.drawImage(sprite.getTextureImage(), (int) position.getX(), (int) position.getY(), (int) (position.getX() + scale.getX()), (int) (position.getY() + scale.getY()), 0, 0, (int) scale.getX(), (int) scale.getY(), null);
	}

	public void addElement(GameObject element) {
		elements.add(element);
		sortElements();
	}

	private void sortElements() {
		elements.sort((o1, o2) -> {
			int p1 = o1.getComponent(Sprite.class).getRenderPriority();
			int p2 = o2.getComponent(Sprite.class).getRenderPriority();
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
