
public class NewThread extends Thread {
	String keyType;
	public NewThread(String in) {
		keyType = in;
	}

	public void run() {
		try {
			while (KeyPress.keyIsPressed) {
				if (keyType.equals("a")) {
					GameTest.movePlayer("left");
				}
				if (keyType.equals("d")) {
					GameTest.movePlayer("right");
				}
				if (keyType.equals("w")) {
					GameTest.movePlayer("up");
				}
				if (keyType.equals("s")) {
					GameTest.movePlayer("down");
				}
				Thread.sleep(200);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
