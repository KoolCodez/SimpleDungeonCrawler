package movement;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MoveLeft extends Thread{
	public void run() {
		int count = 0;
		try {
			while (SimpleDungeonCrawler.movingLeft) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(-speed, 0);
					count++;
					if (count % 10 < 5) {
						Images.charImg = Images.charLeftOpArm;
					} else {
						Images.charImg = Images.charLeft;
					}
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
