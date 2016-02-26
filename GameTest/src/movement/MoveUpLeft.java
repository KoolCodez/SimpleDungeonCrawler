package movement;
import misc.SimpleDungeonCrawler;

public class MoveUpLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft && SimpleDungeonCrawler.movingUp) {
				int speed = SimpleDungeonCrawler.diagSpeed;
				SimpleDungeonCrawler.movePlayer(-speed, 0);
				SimpleDungeonCrawler.movePlayer(0, -speed);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}