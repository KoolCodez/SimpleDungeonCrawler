package movement;
import misc.SimpleDungeonCrawler;

public class MoveRight extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					int speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(speed, 0);
					SimpleDungeonCrawler.charImg = SimpleDungeonCrawler.charRight;
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
