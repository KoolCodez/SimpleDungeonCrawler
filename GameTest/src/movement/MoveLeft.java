package movement;
import misc.SimpleDungeonCrawler;

public class MoveLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					int speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(-speed, 0);
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
