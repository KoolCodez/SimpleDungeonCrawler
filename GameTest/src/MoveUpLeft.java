public class MoveUpLeft extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingLeft && SimpleDungeonCrawler.movingUp) {
				SimpleDungeonCrawler.movePlayer("up left");
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}