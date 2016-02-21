
public class MoveUp extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingUp) {
				if (!SimpleDungeonCrawler.movingRight && !SimpleDungeonCrawler.movingLeft) {
					SimpleDungeonCrawler.movePlayer("up");
				}					
				Thread.sleep(20);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
