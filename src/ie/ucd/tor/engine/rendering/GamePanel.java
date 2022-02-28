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

/**
 * Panel to render images in the game
 */
public class GamePanel extends JPanel {

	private static final double ANIMATION_SPEED = 100;

	private final CopyOnWriteArrayList<GameObject> elements;
	private final CopyOnWriteArrayList<JLabel> text;

	private long animationTime;

	public GamePanel() {
		this(GameWindow.WIDTH, GameWindow.HEIGHT);
	}

	public GamePanel(int width, int height) {
		this.setBounds(0, 0, width, height);
		this.setOpaque(false);
		this.setLayout(null);
		this.animationTime = 0;

		elements = new CopyOnWriteArrayList<>();
		text = new CopyOnWriteArrayList<>();

	}

	public GamePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		elements = new CopyOnWriteArrayList<>();
		text = new CopyOnWriteArrayList<>();
		this.animationTime = 0;
	}

	/**
	 * Update/redraw the images in the panel
	 */
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

	/**
	 * Draw all the sprites registered in the system
	 * @param graphics, graphics instance to draw the images
	 */
	private void drawPanelElements(Graphics graphics) {
		for (GameObject element: elements) {
			// continue if the gameObject is disabled
			if (!element.isEnabled()) {
				continue;
			}

			// draw the animated and non-animated sprites
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

	/**
	 * Draw an animated element to the panel
	 * @param animation, the animation data
	 * @param graphics, the graphics instance
	 */
	private void drawAnimatedElement(Animation animation, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		// get position and scale
		Point2D position = animation.getTransform().getPosition();
		Point2D scale = animation.getTransform().getScale();

		// calculate the sprite frame to draw in the animation
		SpriteData data = animation.calculateNextSprite(animationTime, ANIMATION_SPEED);
		BufferedImage spriteImage = data.getSprite();

		// draw the image
		g2.drawImage(spriteImage, (int) position.getX(), (int) position.getY(), (int) (data.getSpriteWidth() * scale.getX()), (int) (data.getSpriteHeight() * scale.getY()), null);
	}

	/**
	 * Draw a sprite element to the panel
	 * @param sprite, the sprite data
	 * @param graphics, the graphics instance
	 */
	private void drawElement(Sprite sprite, Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics.create();

		// get the position and scale
		Point2D position = sprite.getTransform().getPosition();
		Point2D scale = sprite.getTransform().getScale();
		SpriteData data = sprite.getSpriteData();

		// get the sprite
		BufferedImage spriteImage = data.getSprite();

		// draw the image
		g2.drawImage(spriteImage, (int) position.getX(), (int) position.getY(), (int) (data.getSpriteWidth() * scale.getX()), (int) (data.getSpriteHeight() * scale.getY()), null);

	}

	/**
	 * register a new gameObject to the panel
	 * @param element, the new gameObject element
	 */
	public void addElement(GameObject element) {
		elements.add(element);
		sortElements();
	}

	/**
	 * Add a text element to the panel
	 * @param text, the text to be drawn
	 * @param x, the x position
	 * @param y, the y position
	 */
	public void addText(String text, int x, int y) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, 150, 100);
		label.setFont(new Font("Calibri", Font.PLAIN, 64));
		label.setForeground(Color.white);
		this.add(label);
	}

	/**
	 * remove a specific element from the panel
	 * @param element, the element to be removed
	 */
	public void removeElement(GameObject element) {
		elements.remove(element);
	}

	/**
	 * clear the panel of all elements from the panel
	 */
	public void clearElements() {
		elements.clear();
		text.clear();
		
		this.removeAll();

	}

	/**
	 * Sort the elements based on render priority
	 */
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

	public long getAnimationTime() {
		return animationTime;
	}
}
