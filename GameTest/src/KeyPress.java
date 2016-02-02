import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPress implements KeyListener {
	public static KeyEvent whichKey;
	public static boolean keyIsPressed;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean keyPressedLocal = keyIsPressed;
		keyIsPressed = true;
		if (!keyPressedLocal) {
			Thread t1 = new NewThread(Character.toString(e.getKeyChar()));
			t1.start();
		}
		/*switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (GameTest.loc.y != 0) {
				GameTest.loc.y--;
				GameTest.blankRoom();
				GameTest.eventChangeRooms();
			}
			break;
		case KeyEvent.VK_DOWN:
			if (GameTest.loc.y != 9) {
				GameTest.loc.y++;
				GameTest.blankRoom();
				GameTest.eventChangeRooms();
			}
			break;
		case KeyEvent.VK_LEFT:
			if (GameTest.loc.x != 0) {
				GameTest.loc.x--;
				GameTest.blankRoom();
				GameTest.eventChangeRooms();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (GameTest.loc.x != 9) {
				GameTest.loc.x++;
				GameTest.blankRoom();
				GameTest.eventChangeRooms();
			}
			break; 
		}*/

	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyIsPressed = false;
		synchronized (this) {
			this.notify();
		}
		whichKey = e;
	}

}
