
public class MoveRight extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight) {
				if (!SimpleDungeonCrawler.movingDown && !SimpleDungeonCrawler.movingUp) {
					SimpleDungeonCrawler.movePlayer("right");
				}
				Thread.sleep(20);
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
