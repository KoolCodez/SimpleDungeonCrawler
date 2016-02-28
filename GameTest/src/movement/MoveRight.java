package movement;
import misc.SimpleDungeonCrawler;

public class MoveRight extends Thread {
	public void run() {
		int count = 0;
		try {
			while (SimpleDungeonCrawler.movingRight) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					int speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(speed, 0);
					count++;
					if (count % 10 < 5) {
						SimpleDungeonCrawler.charImg = SimpleDungeonCrawler.charRightOpArm;
					} else {
						SimpleDungeonCrawler.charImg = SimpleDungeonCrawler.charRight;
					}
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
