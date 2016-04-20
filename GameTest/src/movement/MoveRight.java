package movement;
import misc.Images;
import misc.SimpleDungeonCrawler;

public class MoveRight extends Thread {
	public void run() {
		int count = 0;
		try {
			while (MovementController.movingRight) {
				if (!MovementController.movingDown && !MovementController.movingUp) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(speed, 0);
					count++;
					if (count % 10 < 5) {
						Images.charImg = Images.charRightOpArm;
					} else {
						Images.charImg = Images.charRight;
					}
				}
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
