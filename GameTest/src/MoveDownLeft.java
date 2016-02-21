public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft && SimpleDungeonCrawler.movingDown) {
				SimpleDungeonCrawler.movePlayer("down left");
				Thread.sleep(20);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
