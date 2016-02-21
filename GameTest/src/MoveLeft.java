

public class MoveLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					SimpleDungeonCrawler.movePlayer("left");
				}
				Thread.sleep(20);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
