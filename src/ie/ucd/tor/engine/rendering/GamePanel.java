package ie.ucd.tor.engine.rendering;

import ie.ucd.tor.engine.core.GameObject;
import ie.ucd.tor.engine.core.components.Texture;
import ie.ucd.tor.engine.maths.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {

	private CopyOnWriteArrayList<GameObject> elements;

	public GamePanel() {
		this(GameWindow.WIDTH, GameWindow.HEIGHT);
	}

	public GamePanel(int width, int height) {
		this.setBounds(0, 0, width, height);
		this.setOpaque(true);
		elements = new CopyOnWriteArrayList<>();

	}

	public GamePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		elements = new CopyOnWriteArrayList<>();
	}

	public void Update() {
		this.revalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponents(graphics);

		drawPanelElements(graphics);

	}

	private void drawPanelElements(Graphics graphics) {
		for (GameObject element: elements) {

			Texture texture = element.getComponent(Texture.class);
			if (!Objects.isNull(texture)) {

				if (texture.getTextureAnimationStatus()) {
					drawAnimatedElement(texture, graphics);
				}
				else {
					drawElement(texture, graphics);
				}
			}
		}
	}

	private void drawAnimatedElement(Texture texture, Graphics graphics) {
		Point2D position = texture.getTransform().getPosition();
		Point2D scale = texture.getTransform().getScale();

		// TODO add animation timings

		graphics.drawImage(texture.getTextureImage(), (int) position.getX(), (int) position.getY(), (int) scale.getX(), (int) scale.getY(), 0, 0, 0, 0, null);
	}

	private void drawElement(Texture texture, Graphics graphics) {
		Point2D position = texture.getTransform().getPosition();
		Point2D scale = texture.getTransform().getScale();

		graphics.drawImage(texture.getTextureImage(), (int) position.getX(), (int) position.getY(), (int) scale.getX(), (int) scale.getY(), 0, 0, (int) scale.getX(), (int) scale.getY(), null);
	}

	public void addElement(GameObject element) {
		elements.add(element);
	}

	public void showPanel() {
		this.setVisible(true);
	}

	public void hidePanel() {
		this.setVisible(false);
	}



}
