package movement;
import misc.SimpleDungeonCrawler;

public class MoveUpRight extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight && SimpleDungeonCrawler.movingUp) {
				int speed = SimpleDungeonCrawler.diagSpeed;
				SimpleDungeonCrawler.movePlayer(speed, -speed);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}