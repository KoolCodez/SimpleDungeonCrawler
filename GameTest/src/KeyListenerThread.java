import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerThread extends Thread implements KeyListener {
	String keyToListenTo;
	String keyType;
	boolean keyIsPressed = false;
	public KeyListenerThread(String listenTo) {
		keyToListenTo = listenTo;
	}
	
	public void run() {
		try {
			while (keyIsPressed) {
				if (keyToListenTo.equals(keyType)) {
					SimpleDungeonCrawler.movePlayer(keyToListenTo);
				}
				Thread.sleep(50);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyIsPressed = true;
		keyType = Character.toString(e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyIsPressed = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
