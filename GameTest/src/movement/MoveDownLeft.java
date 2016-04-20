package movement;
import misc.SimpleDungeonCrawler;

public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingDown) {
				double speed = SimpleDungeonCrawler.diagSpeed;
				SimpleDungeonCrawler.movePlayer(-speed, 0);
				SimpleDungeonCrawler.movePlayer(0, speed);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
