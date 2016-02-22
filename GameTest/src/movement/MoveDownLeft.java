package movement;
import misc.SimpleDungeonCrawler;

public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft && SimpleDungeonCrawler.movingDown) {
				int speed = SimpleDungeonCrawler.playerSpeed;
				SimpleDungeonCrawler.movePlayer(-speed, -speed);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
