package movement;
import misc.SimpleDungeonCrawler;

public class MoveDownRight extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight && SimpleDungeonCrawler.movingDown) {
				int speed = SimpleDungeonCrawler.playerSpeed;
				SimpleDungeonCrawler.movePlayer(speed - 1, -speed - 1);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}