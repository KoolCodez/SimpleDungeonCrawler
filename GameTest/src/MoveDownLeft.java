public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft && SimpleDungeonCrawler.movingDown) {
				SimpleDungeonCrawler.movePlayer("down left");
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
