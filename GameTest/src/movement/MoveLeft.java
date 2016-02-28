package movement;
import misc.SimpleDungeonCrawler;

public class MoveLeft extends Thread{
	public void run() {
		int count = 0;
		try {
			while (SimpleDungeonCrawler.movingLeft) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					int speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(-speed, 0);
					count++;
					if (count % 10 < 5) {
						SimpleDungeonCrawler.charImg = SimpleDungeonCrawler.charLeftOpArm;
					} else {
						SimpleDungeonCrawler.charImg = SimpleDungeonCrawler.charLeft;
					}
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
