package movement;
import misc.SimpleDungeonCrawler;

public class MoveUp extends Thread {
	public void run() {
		try {
			while (MovementController.movingUp) {
				if (!MovementController.movingRight && !MovementController.movingLeft) {
					double speed = SimpleDungeonCrawler.playerSpeed;
					SimpleDungeonCrawler.movePlayer(0, -speed);
				}					
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
