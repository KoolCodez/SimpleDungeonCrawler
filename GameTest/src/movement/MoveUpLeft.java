package movement;
import misc.SimpleDungeonCrawler;

public class MoveUpLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingUp) {
				double speed = SimpleDungeonCrawler.diagSpeed;
				SimpleDungeonCrawler.movePlayer(-speed, 0);
				SimpleDungeonCrawler.movePlayer(0, -speed);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}