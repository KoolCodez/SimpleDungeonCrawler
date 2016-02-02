
public class NewThread extends Thread {
	String keyType;
	public NewThread(String in) {
		keyType = in;
	}

	public void run() {
		try {
			while (KeyPress.keyIsPressed) {
				if (keyType.equals("a")) {
					SimpleDungeonCrawler.movePlayer("left");
				}
				if (keyType.equals("d")) {
					SimpleDungeonCrawler.movePlayer("right");
				}
				if (keyType.equals("w")) {
					SimpleDungeonCrawler.movePlayer("up");
				}
				if (keyType.equals("s")) {
					SimpleDungeonCrawler.movePlayer("down");
				}
				Thread.sleep(100);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
