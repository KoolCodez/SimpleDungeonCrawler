package movement;
import misc.SimpleDungeonCrawler;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingDown) {
				if (!SimpleDungeonCrawler.movingLeft && ! SimpleDungeonCrawler.movingRight) {
					SimpleDungeonCrawler.movePlayer(0, -1 * SimpleDungeonCrawler.playerSpeed);
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
