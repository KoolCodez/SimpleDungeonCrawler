package movement;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MoveLeft extends Thread{
	public void run() {
		int count = 0;
		try {
			while (MovementController.movingLeft) {
				if (!MovementController.movingDown && !MovementController.movingUp) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(-speed, 0);
					count++;
					if (count % 10 < 5) {
						Images.charImgIndex = Images.charLeftOpArmIndex;
					} else {
						Images.charImgIndex = Images.charLeftIndex;
					}
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
