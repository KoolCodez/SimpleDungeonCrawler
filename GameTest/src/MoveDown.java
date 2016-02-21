
public class MoveDown extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingDown) {
				while (!SimpleDungeonCrawler.movingLeft && ! SimpleDungeonCrawler.movingRight) {
					SimpleDungeonCrawler.movePlayer("down");
					Thread.sleep(20);
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
