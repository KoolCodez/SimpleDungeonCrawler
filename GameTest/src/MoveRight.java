
public class MoveRight extends Thread {
	public void run() {
		try {
			while (SimpleDungeonCrawler.movingRight) {
				SimpleDungeonCrawler.movePlayer("right");
				SimpleDungeonCrawler.frame.getContentPane().validate();
				SimpleDungeonCrawler.frame.getContentPane().repaint();
				Thread.sleep(10);
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
