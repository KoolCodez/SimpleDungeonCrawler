package movement;
import misc.SimpleDungeonCrawler;

public class MoveDownRight extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight && SimpleDungeonCrawler.movingDown) {
				SimpleDungeonCrawler.movePlayer("down right");
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}