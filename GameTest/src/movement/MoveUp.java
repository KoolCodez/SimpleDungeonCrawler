package movement;
import misc.SimpleDungeonCrawler;

public class MoveUp extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingUp) {
				if (!SimpleDungeonCrawler.movingRight && !SimpleDungeonCrawler.movingLeft) {
					SimpleDungeonCrawler.movePlayer("up");
				}					
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
