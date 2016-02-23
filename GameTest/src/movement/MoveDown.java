package movement;
import misc.SimpleDungeonCrawler;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingDown) {
				if (!SimpleDungeonCrawler.movingLeft && ! SimpleDungeonCrawler.movingRight) {
					int speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(0, speed);
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
