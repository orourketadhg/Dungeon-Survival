package ie.ucd.tor.engine.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Singleton pattern
public class InputEventHandler implements KeyListener {

	/* Number keys */
	private static boolean Key1Pressed = false;
	private static boolean Key2Pressed = false;
	private static boolean Key3Pressed = false;
	private static boolean Key4Pressed = false;
	private static boolean Key5Pressed = false;
	private static boolean Key6Pressed = false;
	private static boolean Key7Pressed = false;
	private static boolean Key8Pressed = false;
	private static boolean Key9Pressed = false;
	private static boolean Key0Pressed = false;

	/* Letter Keys */
	private static boolean KeyQPressed = false;
	private static boolean KeyWPressed = false;
	private static boolean KeyEPressed = false;
	private static boolean KeyRPressed = false;
	private static boolean KeyTPressed = false;
	private static boolean KeyYPressed = false;
	private static boolean KeyUPressed = false;
	private static boolean KeyIPressed = false;
	private static boolean KeyOPressed = false;
	private static boolean KeyPPressed = false;
	private static boolean KeyAPressed = false;
	private static boolean KeySPressed = false;
	private static boolean KeyDPressed = false;
	private static boolean KeyFPressed = false;
	private static boolean KeyGPressed = false;
	private static boolean KeyHPressed = false;
	private static boolean KeyJPressed = false;
	private static boolean KeyKPressed = false;
	private static boolean KeyLPressed = false;
	private static boolean KeyZPressed = false;
	private static boolean KeyXPressed = false;
	private static boolean KeyCPressed = false;
	private static boolean KeyVPressed = false;
	private static boolean KeyBPressed = false;
	private static boolean KeyNPressed = false;
	private static boolean KeyMPressed = false;

	/* auxiliary Keys */
	private static boolean KeyEscPressed = false;
	private static boolean KeyTabPressed = false;
	private static boolean KeyShiftPressed = false;
	private static boolean KeyCtrlPressed = false;
	private static boolean KeySpacePressed = false;
	private static boolean KeyPlusPressed = false;
	private static boolean KeyMinusPressed = false;
	private static boolean KeyEnterPressed = false;

	/* Arrow Keys */
	private static boolean KeyArrowUpPressed = false;
	private static boolean KeyArrowDownPressed = false;
	private static boolean KeyArrowLeftPressed = false;
	private static boolean KeyArrowRightPressed = false;

	private static InputEventHandler instance;

	public InputEventHandler() {

	}

	public static InputEventHandler getInstance() {
		if (instance == null) {
			instance = new InputEventHandler();
		}
		return instance;
	}

	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_0 -> setKey0Pressed(true);
			case KeyEvent.VK_1 -> setKey1Pressed(true);
			case KeyEvent.VK_2 -> setKey2Pressed(true);
			case KeyEvent.VK_3 -> setKey3Pressed(true);
			case KeyEvent.VK_4 -> setKey4Pressed(true);
			case KeyEvent.VK_5 -> setKey5Pressed(true);
			case KeyEvent.VK_6 -> setKey6Pressed(true);
			case KeyEvent.VK_7 -> setKey7Pressed(true);
			case KeyEvent.VK_8 -> setKey8Pressed(true);
			case KeyEvent.VK_9 -> setKey9Pressed(true);

			case KeyEvent.VK_Q -> setKeyQPressed(true);
			case KeyEvent.VK_W -> setKeyWPressed(true);
			case KeyEvent.VK_E -> setKeyEPressed(true);
			case KeyEvent.VK_R -> setKeyRPressed(true);
			case KeyEvent.VK_T -> setKeyTPressed(true);
			case KeyEvent.VK_Y -> setKeyYPressed(true);
			case KeyEvent.VK_U -> setKeyUPressed(true);
			case KeyEvent.VK_I -> setKeyIPressed(true);
			case KeyEvent.VK_O -> setKeyOPressed(true);
			case KeyEvent.VK_P -> setKeyPPressed(true);
			case KeyEvent.VK_A -> setKeyAPressed(true);
			case KeyEvent.VK_S -> setKeySPressed(true);
			case KeyEvent.VK_D -> setKeyDPressed(true);
			case KeyEvent.VK_F -> setKeyFPressed(true);
			case KeyEvent.VK_G -> setKeyGPressed(true);
			case KeyEvent.VK_H -> setKeyHPressed(true);
			case KeyEvent.VK_J -> setKeyJPressed(true);
			case KeyEvent.VK_K -> setKeyKPressed(true);
			case KeyEvent.VK_L -> setKeyLPressed(true);
			case KeyEvent.VK_Z -> setKeyZPressed(true);
			case KeyEvent.VK_X -> setKeyXPressed(true);
			case KeyEvent.VK_C -> setKeyCPressed(true);
			case KeyEvent.VK_V -> setKeyVPressed(true);
			case KeyEvent.VK_B -> setKeyBPressed(true);
			case KeyEvent.VK_N -> setKeyNPressed(true);
			case KeyEvent.VK_M -> setKeyMPressed(true);

			case KeyEvent.VK_ESCAPE -> setKeyEscPressed(true);
			case KeyEvent.VK_TAB -> setKeyTabPressed(true);
			case KeyEvent.VK_SHIFT -> setKeyShiftPressed(true);
			case KeyEvent.VK_CONTROL -> setKeyCtrlPressed(true);
			case KeyEvent.VK_SPACE -> setKeySpacePressed(true);
			case KeyEvent.VK_ENTER -> setKeyEnterPressed(true);
			case KeyEvent.VK_PLUS -> setKeyPlusPressed(true);
			case KeyEvent.VK_MINUS -> setKeyMinusPressed(true);

			case KeyEvent.VK_UP -> setKeyArrowUpPressed(true);
			case KeyEvent.VK_DOWN -> setKeyArrowDownPressed(true);
			case KeyEvent.VK_LEFT -> setKeyArrowLeftPressed(true);
			case KeyEvent.VK_RIGHT -> setKeyArrowRightPressed(true);
			default -> {
				System.out.println("Unknown Input");
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_1 -> setKey1Pressed(false);
			case KeyEvent.VK_2 -> setKey2Pressed(false);
			case KeyEvent.VK_3 -> setKey3Pressed(false);
			case KeyEvent.VK_4 -> setKey4Pressed(false);
			case KeyEvent.VK_5 -> setKey5Pressed(false);
			case KeyEvent.VK_0 -> setKey0Pressed(false);
			case KeyEvent.VK_6 -> setKey6Pressed(false);
			case KeyEvent.VK_7 -> setKey7Pressed(false);
			case KeyEvent.VK_8 -> setKey8Pressed(false);
			case KeyEvent.VK_9 -> setKey9Pressed(false);

			case KeyEvent.VK_Q -> setKeyQPressed(false);
			case KeyEvent.VK_W -> setKeyWPressed(false);
			case KeyEvent.VK_E -> setKeyEPressed(false);
			case KeyEvent.VK_R -> setKeyRPressed(false);
			case KeyEvent.VK_T -> setKeyTPressed(false);
			case KeyEvent.VK_Y -> setKeyYPressed(false);
			case KeyEvent.VK_U -> setKeyUPressed(false);
			case KeyEvent.VK_I -> setKeyIPressed(false);
			case KeyEvent.VK_O -> setKeyOPressed(false);
			case KeyEvent.VK_P -> setKeyPPressed(false);
			case KeyEvent.VK_A -> setKeyAPressed(false);
			case KeyEvent.VK_S -> setKeySPressed(false);
			case KeyEvent.VK_D -> setKeyDPressed(false);
			case KeyEvent.VK_F -> setKeyFPressed(false);
			case KeyEvent.VK_G -> setKeyGPressed(false);
			case KeyEvent.VK_H -> setKeyHPressed(false);
			case KeyEvent.VK_J -> setKeyJPressed(false);
			case KeyEvent.VK_K -> setKeyKPressed(false);
			case KeyEvent.VK_L -> setKeyLPressed(false);
			case KeyEvent.VK_Z -> setKeyZPressed(false);
			case KeyEvent.VK_X -> setKeyXPressed(false);
			case KeyEvent.VK_C -> setKeyCPressed(false);
			case KeyEvent.VK_V -> setKeyVPressed(false);
			case KeyEvent.VK_B -> setKeyBPressed(false);
			case KeyEvent.VK_N -> setKeyNPressed(false);
			case KeyEvent.VK_M -> setKeyMPressed(false);

			case KeyEvent.VK_ESCAPE -> setKeyEscPressed(false);
			case KeyEvent.VK_TAB -> setKeyTabPressed(false);
			case KeyEvent.VK_SHIFT -> setKeyShiftPressed(false);
			case KeyEvent.VK_CONTROL -> setKeyCtrlPressed(false);
			case KeyEvent.VK_SPACE -> setKeySpacePressed(false);
			case KeyEvent.VK_ENTER -> setKeyEnterPressed(false);
			case KeyEvent.VK_PLUS -> setKeyPlusPressed(false);
			case KeyEvent.VK_MINUS -> setKeyMinusPressed(false);

			case KeyEvent.VK_UP -> setKeyArrowUpPressed(false);
			case KeyEvent.VK_DOWN -> setKeyArrowDownPressed(false);
			case KeyEvent.VK_LEFT -> setKeyArrowLeftPressed(false);
			case KeyEvent.VK_RIGHT -> setKeyArrowRightPressed(false);
			default -> {
				System.out.println("Unknown Input");
			}
		}

	}

	/* key Getters */

	public boolean isKey1Pressed() {
		return Key1Pressed;
	}

	public boolean isKey2Pressed() {
		return Key2Pressed;
	}

	public boolean isKey3Pressed() {
		return Key3Pressed;
	}

	public boolean isKey4Pressed() {
		return Key4Pressed;
	}

	public boolean isKey5Pressed() {
		return Key5Pressed;
	}

	public boolean isKey6Pressed() {
		return Key6Pressed;
	}

	public boolean isKey7Pressed() {
		return Key7Pressed;
	}

	public boolean isKey8Pressed() {
		return Key8Pressed;
	}

	public boolean isKey9Pressed() {
		return Key9Pressed;
	}

	public boolean isKey0Pressed() {
		return Key0Pressed;
	}

	public boolean isKeyQPressed() {
		return KeyQPressed;
	}

	public boolean isKeyWPressed() {
		return KeyWPressed;
	}

	public boolean isKeyEPressed() {
		return KeyEPressed;
	}

	public boolean isKeyRPressed() {
		return KeyRPressed;
	}

	public boolean isKeyTPressed() {
		return KeyTPressed;
	}

	public boolean isKeyYPressed() {
		return KeyYPressed;
	}

	public boolean isKeyUPressed() {
		return KeyUPressed;
	}

	public boolean isKeyIPressed() {
		return KeyIPressed;
	}

	public boolean isKeyOPressed() {
		return KeyOPressed;
	}

	public boolean isKeyPPressed() {
		return KeyPPressed;
	}

	public boolean isKeyAPressed() {
		return KeyAPressed;
	}

	public boolean isKeySPressed() {
		return KeySPressed;
	}

	public boolean isKeyDPressed() {
		return KeyDPressed;
	}

	public boolean isKeyFPressed() {
		return KeyFPressed;
	}

	public boolean isKeyGPressed() {
		return KeyGPressed;
	}

	public boolean isKeyHPressed() {
		return KeyHPressed;
	}

	public boolean isKeyJPressed() {
		return KeyJPressed;
	}

	public boolean isKeyKPressed() {
		return KeyKPressed;
	}

	public boolean isKeyLPressed() {
		return KeyLPressed;
	}

	public boolean isKeyZPressed() {
		return KeyZPressed;
	}

	public boolean isKeyXPressed() {
		return KeyXPressed;
	}

	public boolean isKeyCPressed() {
		return KeyCPressed;
	}

	public boolean isKeyVPressed() {
		return KeyVPressed;
	}

	public boolean isKeyBPressed() {
		return KeyBPressed;
	}

	public boolean isKeyNPressed() {
		return KeyNPressed;
	}

	public boolean isKeyMPressed() {
		return KeyMPressed;
	}

	public boolean isKeyEscPressed() {
		return KeyEscPressed;
	}

	public boolean isKeyTabPressed() {
		return KeyTabPressed;
	}

	public boolean isKeyShiftPressed() {
		return KeyShiftPressed;
	}

	public boolean isKeyCtrlPressed() {
		return KeyCtrlPressed;
	}

	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}

	public boolean isKeyPlusPressed() {
		return KeyPlusPressed;
	}

	public boolean isKeyMinusPressed() {
		return KeyMinusPressed;
	}

	public boolean isKeyEnterPressed() {
		return KeyEnterPressed;
	}

	public boolean isKeyArrowUpPressed() {
		return KeyArrowUpPressed;
	}

	public boolean isKeyArrowDownPressed() {
		return KeyArrowDownPressed;
	}

	public boolean isKeyArrowLeftPressed() {
		return KeyArrowLeftPressed;
	}

	public boolean isKeyArrowRightPressed() {
		return KeyArrowRightPressed;
	}

	/* Key Setters */

	public void setKey1Pressed(boolean key1Pressed) {
		Key1Pressed = key1Pressed;
	}

	public void setKey2Pressed(boolean key2Pressed) {
		Key2Pressed = key2Pressed;
	}

	public void setKey3Pressed(boolean key3Pressed) {
		Key3Pressed = key3Pressed;
	}

	public void setKey4Pressed(boolean key4Pressed) {
		Key4Pressed = key4Pressed;
	}

	public void setKey5Pressed(boolean key5Pressed) {
		Key5Pressed = key5Pressed;
	}

	public void setKey6Pressed(boolean key6Pressed) {
		Key6Pressed = key6Pressed;
	}

	public void setKey7Pressed(boolean key7Pressed) {
		Key7Pressed = key7Pressed;
	}

	public void setKey8Pressed(boolean key8Pressed) {
		Key8Pressed = key8Pressed;
	}

	public void setKey9Pressed(boolean key9Pressed) {
		Key9Pressed = key9Pressed;
	}

	public void setKey0Pressed(boolean key0Pressed) {
		Key0Pressed = key0Pressed;
	}

	public void setKeyQPressed(boolean keyQPressed) {
		KeyQPressed = keyQPressed;
	}

	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}

	public void setKeyEPressed(boolean keyEPressed) {
		KeyEPressed = keyEPressed;
	}

	public void setKeyRPressed(boolean keyRPressed) {
		KeyRPressed = keyRPressed;
	}

	public void setKeyTPressed(boolean keyTPressed) {
		KeyTPressed = keyTPressed;
	}

	public void setKeyYPressed(boolean keyYPressed) {
		KeyYPressed = keyYPressed;
	}

	public void setKeyUPressed(boolean keyUPressed) {
		KeyUPressed = keyUPressed;
	}

	public void setKeyIPressed(boolean keyIPressed) {
		KeyIPressed = keyIPressed;
	}

	public void setKeyOPressed(boolean keyOPressed) {
		KeyOPressed = keyOPressed;
	}

	public void setKeyPPressed(boolean keyPPressed) {
		KeyPPressed = keyPPressed;
	}

	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}

	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}

	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}

	public void setKeyFPressed(boolean keyFPressed) {
		KeyFPressed = keyFPressed;
	}

	public void setKeyGPressed(boolean keyGPressed) {
		KeyGPressed = keyGPressed;
	}

	public void setKeyHPressed(boolean keyHPressed) {
		KeyHPressed = keyHPressed;
	}

	public void setKeyJPressed(boolean keyJPressed) {
		KeyJPressed = keyJPressed;
	}

	public void setKeyKPressed(boolean keyKPressed) {
		KeyKPressed = keyKPressed;
	}

	public void setKeyLPressed(boolean keyLPressed) {
		KeyLPressed = keyLPressed;
	}

	public void setKeyZPressed(boolean keyZPressed) {
		KeyZPressed = keyZPressed;
	}

	public void setKeyXPressed(boolean keyXPressed) {
		KeyXPressed = keyXPressed;
	}

	public void setKeyCPressed(boolean keyCPressed) {
		KeyCPressed = keyCPressed;
	}

	public void setKeyVPressed(boolean keyVPressed) {
		KeyVPressed = keyVPressed;
	}

	public void setKeyBPressed(boolean keyBPressed) {
		KeyBPressed = keyBPressed;
	}

	public void setKeyNPressed(boolean keyNPressed) {
		KeyNPressed = keyNPressed;
	}

	public void setKeyMPressed(boolean keyMPressed) {
		KeyMPressed = keyMPressed;
	}

	public void setKeyEscPressed(boolean keyEscPressed) {
		KeyEscPressed = keyEscPressed;
	}

	public void setKeyTabPressed(boolean keyTabPressed) {
		KeyTabPressed = keyTabPressed;
	}

	public void setKeyShiftPressed(boolean keyShiftPressed) {
		KeyShiftPressed = keyShiftPressed;
	}

	public void setKeyCtrlPressed(boolean keyCtrlPressed) {
		KeyCtrlPressed = keyCtrlPressed;
	}

	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	}

	public void setKeyPlusPressed(boolean keyPlusPressed) {
		KeyPlusPressed = keyPlusPressed;
	}

	public void setKeyMinusPressed(boolean keyMinusPressed) {
		KeyMinusPressed = keyMinusPressed;
	}

	public void setKeyEnterPressed(boolean keyEnterPressed) {
		KeyEnterPressed = keyEnterPressed;
	}

	public void setKeyArrowUpPressed(boolean keyArrowUpPressed) {
		KeyArrowUpPressed = keyArrowUpPressed;
	}

	public void setKeyArrowDownPressed(boolean keyArrowDownPressed) {
		KeyArrowDownPressed = keyArrowDownPressed;
	}

	public void setKeyArrowLeftPressed(boolean keyArrowLeftPressed) {
		KeyArrowLeftPressed = keyArrowLeftPressed;
	}

	public void setKeyArrowRightPressed(boolean keyArrowRightPressed) {
		KeyArrowRightPressed = keyArrowRightPressed;
	}

}
