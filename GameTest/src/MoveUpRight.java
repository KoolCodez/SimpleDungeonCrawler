public class MoveUpRight extends Thread{
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight && SimpleDungeonCrawler.movingUp) {
				SimpleDungeonCrawler.movePlayer("up right");
				Thread.sleep(20);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}