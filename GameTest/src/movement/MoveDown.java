package movement;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingDown) {
				if (!SimpleDungeonCrawler.movingLeft && ! SimpleDungeonCrawler.movingRight) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(0, speed);
					Images.charImg = Images.charFront;
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
