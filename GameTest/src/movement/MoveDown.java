package movement;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (MovementController.movingDown) {
				if (!MovementController.movingLeft && ! MovementController.movingRight) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(0, speed);
					Images.charImgIndex = Images.charFrontIndex;
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
